package com.example.demo.util;

import com.example.demo.entity.Charge;
import com.example.demo.entity.Params;
import com.example.demo.entity.PowerAnalyze;
import com.example.demo.mapper.util.ChargeDao;
import com.example.demo.mapper.power.OrigDLDao;
import com.example.demo.mapper.power.PowerAnalyzeDao;
import com.example.demo.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ChargeUtil {

    @Autowired
    private ChargeDao chargeDao;
    @Autowired
    private OrigDLDao origDLDao;
    @Autowired
    private PowerAnalyzeDao powerAnalyzeDao;

    public static void main(String[] args) {
        String[] players = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};

        List<String> p = Arrays.asList(players);

        // p.forEach((player)->System.out.print(player+";"));

        p.stream()
                .filter((player) -> (player.indexOf('S') == -1))
                .forEach((player) -> System.out.print(player + ";"));
    }

    public void insertPowerList(Params param,String TimeTag) throws ParseException {
        PowerAnalyze powerAnalyze = new PowerAnalyze();
        powerAnalyze.setEMeterID(param.getEMeterID());
        powerAnalyze.setTimeTag(DateUtil.StringToDateByPattern(TimeTag,"yyyy-MM-dd"));
        double peak = 0.0, flat = 0.0, ravine=0.0;
        peak+=getPowerByTime(TimeTag+" 08:30:00",TimeTag+" 11:30:00",param);
        peak+=getPowerByTime(TimeTag+" 14:30:00",TimeTag+" 17:30:00",param);
        peak+=getPowerByTime(TimeTag+" 19:00:00",TimeTag+" 21:00:00",param);
        powerAnalyze.setPeak(peak);
        flat+=getPowerByTime(TimeTag+" 07:00:00",TimeTag+" 08:30:00",param);
        flat+=getPowerByTime(TimeTag+" 11:30:00",TimeTag+" 14:30:00",param);
        flat+=getPowerByTime(TimeTag+" 17:30:00",TimeTag+" 19:00:00",param);
        flat+=getPowerByTime(TimeTag+" 21:00:00",TimeTag+" 23:00:00",param);
        powerAnalyze.setFlat(flat);
        ravine+=getPowerByTime(TimeTag+" 00:00:00",TimeTag+" 07:00:00",param);
        Date date = DateUtil.StringToDateByPattern(TimeTag,"yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);
        date = calendar.getTime();
        String TimeTag1 = DateUtil.DateToStringByPattern(date,"yyyy-MM-dd");
        System.out.println("TimeTag1:"+TimeTag1);
        ravine+=getPowerByTime(TimeTag+" 23:00:00",TimeTag1+" 00:00:00",param);
        powerAnalyze.setRavine(ravine);
        powerAnalyze.setPowerTotal(peak+flat+ravine);
        if(peak+flat+ravine==0.0){
            powerAnalyze.setPeakPercent(0.0);
        }else {
            powerAnalyze.setPeakPercent(peak/(peak+flat+ravine));
        }
        System.out.println(powerAnalyze.toString());
        powerAnalyzeDao.insertPowerAnalyze(powerAnalyze);
    }


    //通过时间段获取该时间段电量
    //输入日期字符串yyyy-MM-dd HH:mm:ss
    public double getPowerByTime(String beginTime, String endTime,Params param) throws ParseException {
        System.out.println("表号："+param.getEMeterID()+"开始时间："+beginTime+",结束时间："+endTime+"----------------------------------------------------------");
        // Params param = new Params();
        // param.setEMeterName("2#化学涂层炉");
        // param.setEStationName("同安1B");
        // param.setBeginTime(DateUtil.StringToDate("2019-04-04 00:00:00"));
        // param.setPowerType("ZxygZ");
        String powerType = param.getPowerType();
        List<Map> powerList = this.origDLDao.getPowerAnalyzeByHour(param);

        List<Map> sortList = new ArrayList<>();
        Date beginDate = null;
        Date endDate = null;
        Calendar calendar = Calendar.getInstance();
        //1开始结束都是半点，2开始是半点，3结束是半点，4都不是半点
        int flag = 0;
        if (beginTime.split(":")[1].equals("30") && endTime.split(":")[1].equals("30")) {
            flag = 1;
            //开始时间往前推30分钟
            beginDate = DateUtil.StringToDate(beginTime);
            calendar.setTime(beginDate);
            calendar.add(Calendar.MINUTE,-30);
            beginDate = calendar.getTime();
            //结束时间往后延30分钟
            endDate = DateUtil.StringToDate(endTime);
            calendar.setTime(endDate);
            calendar.add(Calendar.MINUTE,30);
            endDate = calendar.getTime();
        } else if (beginTime.split(":")[1].equals("30") && !endTime.split(":")[1].equals("30")) {
            flag = 2;
            //开始时间往前推30分钟
            beginDate = DateUtil.StringToDate(beginTime);
            calendar.setTime(beginDate);
            calendar.add(Calendar.MINUTE,-30);
            beginDate = calendar.getTime();
            //结束时间往后延30分钟
            endDate = DateUtil.StringToDate(endTime);
        } else if (!beginTime.split(":")[1].equals("30") && endTime.split(":")[1].equals("30")) {
            flag = 3;
            beginDate = DateUtil.StringToDate(beginTime);
            //结束时间往后延30分钟
            endDate = DateUtil.StringToDate(endTime);
            calendar.setTime(endDate);
            calendar.add(Calendar.MINUTE,30);
            endDate = calendar.getTime();
        } else {
            flag = 4;
            beginDate = DateUtil.StringToDate(beginTime);
            endDate = DateUtil.StringToDate(endTime);
        }
        // System.out.println("表号："+param.getEMeterID()+"--------------flag:"+flag+"---------------"+beginDate+"----"+endDate);
        //根据开始结束时间过滤记录
        Date finalBeginDate = beginDate;
        Date finalEndDate = endDate;
        sortList = powerList.stream()
                .filter((map) -> (DateUtil.StringToDate(map.get("TimeTag").toString()).compareTo(finalBeginDate)>=0))
                .filter((map) -> (DateUtil.StringToDate(map.get("TimeTag").toString()).compareTo(finalEndDate)<=0 ))
                //.limit(3)
                //m1在前是升序，m2在前是降序
                .sorted((m1,m2)->(DateUtil.StringToDate(m1.get("TimeTag").toString()).compareTo(DateUtil.StringToDate(m2.get("TimeTag").toString()))))
                .collect(Collectors.toList());
        //.forEach(map -> System.out.print(map.get("TimeTag").toString()));
        //如果列表为空则返回0.0
        if(sortList.size()<=1){
            return 0.0;
        }
        // sortList.stream().forEach((map) -> System.out.println(map.get("TimeTag").toString()));
        double power = 0.0;
        double beginPower = 0.0, endPower = 0.0;
        switch (flag){
            case 1:
                //该时间在开始时间之前
                if(DateUtil.StringToDate(sortList.get(0).get("TimeTag").toString()).compareTo(DateUtil.StringToDate(beginTime))<0){
                    //开始时间往后推一个小时
                    calendar.setTime(beginDate);
                    calendar.add(Calendar.HOUR_OF_DAY,1);
                    beginDate = calendar.getTime();
                    for (Map m:sortList) {
                        if(DateUtil.StringToDateByPattern(m.get("TimeTag").toString(),"yyyy-MM-dd HH:mm:ss").equals(beginDate)){
                            beginPower = (Double.valueOf(m.get(powerType).toString())+Double.valueOf(sortList.get(0).get(powerType).toString()))/2.0000;
                            break;
                        }
                    }
                }else {
                    beginPower = Double.valueOf(sortList.get(0).get(powerType).toString());
                }

                //该时间在开始时间之前
                if(DateUtil.StringToDate(sortList.get(sortList.size()-1).get("TimeTag").toString()).compareTo(DateUtil.StringToDate(endTime))>0){
                    calendar.setTime(endDate);
                    calendar.add(Calendar.HOUR_OF_DAY,-1);
                    endDate = calendar.getTime();
                    for (int i=sortList.size()-1;i>=0;i--) {
                        if(DateUtil.StringToDateByPattern(sortList.get(i).get("TimeTag").toString(),"yyyy-MM-dd HH:mm:ss").equals(endDate)){
                            endPower = (Double.valueOf(sortList.get(sortList.size()-1).get(powerType).toString())+Double.valueOf(sortList.get(i).get(powerType).toString()))/2.0000;
                            break;
                        }
                    }
                }else {
                    endPower = Double.valueOf(sortList.get(sortList.size()-1).get(powerType).toString());
                }
                System.out.println(beginTime+"--------"+beginPower);
                System.out.println(endTime+"--------"+endPower);
                power = endPower-beginPower;
                break;
            //1开始结束都是半点，2开始是半点，3结束是半点，4都不是半点
            case 2:
                //该时间在开始时间之前
                if(DateUtil.StringToDate(sortList.get(0).get("TimeTag").toString()).compareTo(DateUtil.StringToDate(beginTime))<0){
                    //开始时间往后推一个小时
                    calendar.setTime(beginDate);
                    calendar.add(Calendar.HOUR_OF_DAY,1);
                    beginDate = calendar.getTime();
                    for (Map m:sortList) {
                        if(DateUtil.StringToDateByPattern(m.get("TimeTag").toString(),"yyyy-MM-dd HH:mm:ss").equals(beginDate)){
                            // System.out.println("相等的时间是："+DateUtil.StringToDateByPattern(m.get("TimeTag").toString(),"yyyy-MM-dd HH:mm:ss"));
                            beginPower = (Double.valueOf(m.get(powerType).toString())+Double.valueOf(sortList.get(0).get(powerType).toString()))/2.0000;
                            break;
                        }
                    }
                }else {
                    beginPower = Double.valueOf(sortList.get(0).get(powerType).toString());
                }
                endPower = Double.valueOf(sortList.get(sortList.size()-1).get(powerType).toString());
                power = endPower-beginPower;
                System.out.println(beginTime+"--------"+beginPower);
                System.out.println(endTime+"--------"+endPower);
                break;
            case 3:
                //该时间在开始时间之前
                if(DateUtil.StringToDate(sortList.get(sortList.size()-1).get("TimeTag").toString()).compareTo(DateUtil.StringToDate(endTime))>0){
                    calendar.setTime(endDate);
                    calendar.add(Calendar.HOUR_OF_DAY,-1);
                    endDate = calendar.getTime();
                    for (int i=sortList.size()-1;i>=0;i--) {
                        if(DateUtil.StringToDateByPattern(sortList.get(i).get("TimeTag").toString(),"yyyy-MM-dd HH:mm:ss").equals(endDate)){
                            endPower = (Double.valueOf(sortList.get(sortList.size()-1).get(powerType).toString())+Double.valueOf(sortList.get(i).get(powerType).toString()))/2.0000;
                            break;
                        }
                    }
                }else {
                    endPower = Double.valueOf(sortList.get(sortList.size()-1).get(powerType).toString());
                }
                beginPower = Double.valueOf(sortList.get(0).get(powerType).toString());
                power = endPower-beginPower;
                System.out.println(beginTime+"--------"+beginPower);
                System.out.println(endTime+"--------"+endPower);
                break;
            case 4:
                beginPower = Double.valueOf(sortList.get(0).get(powerType).toString());
                endPower = Double.valueOf(sortList.get(sortList.size()-1).get(powerType).toString());
                power = endPower-beginPower;
                System.out.println(beginTime+"--------"+beginPower);
                System.out.println(endTime+"--------"+endPower);
                break;
        }
        Double num =0.0;
        int MultiplyRatio = Integer.parseInt(sortList.get(0).get("MultiplyRatio").toString());
        System.out.println("变比："+MultiplyRatio);
        if(MultiplyRatio==1){
            num = Double.valueOf(sortList.get(0).get("num").toString());
        }else {
            num = 1.0;
        }
        System.out.println("倍率："+num+",时间段的值："+power*num);
        return power*num;
    }


    //获取需要获取的小时列表
    public Set<Integer> getSetHour() {
        //  Map<String,Object> Map = new HashMap<>();
        Charge charge = this.chargeDao.getCharge();
        String[] peakTime = charge.getPeakTime().split(",");
        String[] flatTime = charge.getFlatTime().split(",");
        String[] ravineTime = charge.getRavineTime().split(",");
        //数据库需要查询的时间节点
        Set<Integer> times = new HashSet<>();
        //List<Map> peakMaps = new ArrayList();
        for (String peak : peakTime) {
            /*
            Map<String,Object> map = new HashMap<>();
            String[] str = peak.split("-");
            map.put("beginTime",str[0]);
            map.put("endTime",str[1]);
            peakMaps.add(map);*/
            String[] strings = peak.split("-");
            for (String s : strings) {
                String[] str = s.split(":");
                if (str[1].equals("00")) {
                    times.add(Integer.parseInt(str[0]));
                } else if (str[1].equals("30")) {
                    times.add(Integer.parseInt(str[0]));
                    int hour = Integer.parseInt(str[0]);
                    times.add(hour + 1);
                }
            }
        }

        //  List<Map> flatMaps = new ArrayList();
        for (String flat : flatTime) {
            /*
            Map<String,Object> map = new HashMap<>();
            String[] str = flat.split("-");
            map.put("beginTime",str[0]);
            map.put("endTime",str[1]);
            flatMaps.add(map);*/
            String[] strings = flat.split("-");
            for (String s : strings) {
                String[] str = s.split(":");
                if (str[1].equals("00")) {
                    times.add(Integer.parseInt(str[0]));
                } else if (str[1].equals("30")) {
                    times.add(Integer.parseInt(str[0]));
                    int hour = Integer.parseInt(str[0]);
                    times.add(hour + 1);
                }
            }
        }
        //  List<Map> ravineMaps = new ArrayList();
        for (String ravine : ravineTime) {
            /*
            Map<String,Object> map = new HashMap<>();
            String[] str = ravine.split("-");
            map.put("beginTime",str[0]);
            map.put("endTime",str[1]);
            ravineMaps.add(map);*/
            String[] strings = ravine.split("-");
            for (String s : strings) {
                String[] str = s.split(":");
                if (str[1].equals("00")) {
                    times.add(Integer.parseInt(str[0]));
                } else if (str[1].equals("30")) {
                    times.add(Integer.parseInt(str[0]));
                    int hour = Integer.parseInt(str[0]);
                    times.add(hour + 1);
                }
            }
        }
/*
        System.out.println("------------------peak-----------------");
        for (Map<String,Object> m:peakMaps) {
            System.out.println(m.get("beginTime").toString()+","+m.get("endTime").toString());
        }
        System.out.println("------------------flat-----------------");
        for (Map<String,Object> m:flatMaps) {
            System.out.println(m.get("beginTime").toString()+","+m.get("endTime").toString());
        }
        System.out.println("------------------ravine-----------------");
        for (Map<String,Object> m:ravineMaps) {
            System.out.println(m.get("beginTime").toString()+","+m.get("endTime").toString());
        }
        Map.put("peakTime",peakMaps);
        Map.put("flatTime",flatMaps);
        Map.put("ravineTime",ravineMaps);*/
        times.add(0);
        return times;
    }

    //把各个小时的数据以小时为key存到Map中，第二天0点为24
    public Map getPowerByDay(int EMeterID, String TimeTag) {
        Set<Integer> times = getSetHour();
        Map<String, Map> timeMap = new HashMap<>();
        // List<Map> maps = this.origDLDao.getPowerByMonthAndID(3,"2019-04-01 00:00",times);
        List<Map> maps = this.origDLDao.getPowerByMonthAndID(EMeterID, TimeTag, times);
        for (Map<String, Object> m : maps) {
            System.out.println(m.get("TimeTag").toString());
            String hour = DateUtil.DateToStringByPattern((Date) m.get("TimeTag"), "H");
            if (!timeMap.containsKey(hour)) {
                timeMap.put(hour, m);
            } else {
                timeMap.put("24", m);
            }
        }
        for (Map.Entry<String, Map> entry : timeMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().get("ZxygZ").toString());
        }
        return timeMap;
    }


    public void insertPower() {
        List<Integer> meterList = this.powerAnalyzeDao.getEMeterIDList();
        for (int i = 0; i < meterList.size(); i++) {
            Map<String, Map> timeMap = getPowerByDay(meterList.get(i), "2019-04-01 00:00");
            PowerAnalyze powerAnalyze = new PowerAnalyze();
            Date date = null;
            try {
                date = (Date) timeMap.get("0").get("TimeTag");
            } catch (Exception e) {
                i++;
                continue;
            }
            powerAnalyze.setTimeTag(date);
            //获取峰值
            //08:30-11:30,14:30-17:30,19:00-21:00
            double peak = 0.0;
            peak = getPowerByTimeTag(timeMap, "8:30", "11:30") + getPowerByTimeTag(timeMap, "14:30", "17:30") + getPowerByTimeTag(timeMap, "19", "21");
            powerAnalyze.setPeak(peak);

            //获取平值
            //07:00-08:30,11:30-14:30,17:30-19:00,21:00-23:00
            double flat = 0.0;
            flat = getPowerByTimeTag(timeMap, "7", "8:30") + getPowerByTimeTag(timeMap, "11:30", "14:30") + getPowerByTimeTag(timeMap, "17:30", "19") + getPowerByTimeTag(timeMap, "21", "23");
            powerAnalyze.setFlat(flat);

            //获取谷值
            //23:00-07:00
            double ravine = 0.0;
            ravine = getPowerByTimeTag(timeMap, "23", "24") + getPowerByTimeTag(timeMap, "0", "7");
            powerAnalyze.setRavine(ravine);
            powerAnalyze.setEMeterID(meterList.get(i));
            powerAnalyze.setPeakPercent(powerAnalyze.getPeak() / (powerAnalyze.getFlat() + powerAnalyze.getPeak() + powerAnalyze.getRavine()));
            powerAnalyze.setPowerTotal(getPowerByTimeTag(timeMap, "0", "24"));
            this.powerAnalyzeDao.insertPowerAnalyze(powerAnalyze);
        }
    }


    //根据指定时间段获取该时间段的电量值
    public double getPowerByTimeTag(Map<String, Map> timeMap, String begin, String end) {
        System.out.println("----------开始值：" + begin + ",结束值：" + end + "--------------");
        double ravineBegin = 0, ravineEnd = 0;
        if (isInteger(begin)) {
            ravineBegin = Double.valueOf(timeMap.get(begin).get("ZxygZ").toString());
            if (ravineBegin < 0.0001) {
                ravineBegin = Double.valueOf(timeMap.get(begin).get("ZxwgZ").toString());
            }
            System.out.println("整点：" + ravineBegin);
        } else {
            String[] str = begin.split(":");
            double begin1, begin2;
            begin1 = Double.valueOf(timeMap.get(str[0]).get("ZxygZ").toString());
            if (begin1 < 0.0001) {
                begin1 = Double.valueOf(timeMap.get(str[0]).get("ZxwgZ").toString());
            }
            int num = Integer.parseInt(str[0]) + 1;
            begin2 = Double.valueOf(timeMap.get(String.valueOf(num)).get("ZxygZ").toString());
            if (begin2 < 0.0001) {
                begin2 = Double.valueOf(timeMap.get(String.valueOf(num)).get("ZxwgZ").toString());
            }
            ravineBegin = (begin2 + begin1) / 2.000000;
            System.out.println("半点：" + begin1 + "," + begin2 + ",总：" + ravineBegin);
        }

        if (isInteger(end)) {
            ravineEnd = Double.valueOf(timeMap.get(end).get("ZxygZ").toString());
            if (ravineEnd < 0.0001) {
                ravineEnd = Double.valueOf(timeMap.get(end).get("ZxwgZ").toString());
            }
            System.out.println("整点：" + ravineEnd);
        } else {
            String[] str = end.split(":");
            double end1, end2;
            end1 = Double.valueOf(timeMap.get(str[0]).get("ZxygZ").toString());
            if (end1 < 0.0001) {
                end1 = Double.valueOf(timeMap.get(str[0]).get("ZxwgZ").toString());
            }
            int num = Integer.parseInt(str[0]) + 1;
            end2 = Double.valueOf(timeMap.get(String.valueOf(num)).get("ZxygZ").toString());
            if (end2 < 0.0001) {
                end2 = Double.valueOf(timeMap.get(String.valueOf(num)).get("ZxwgZ").toString());
            }
            ravineEnd = (end2 + end1) / 2.000000;
            System.out.println("半点：" + end1 + "," + end2 + ",总：" + ravineEnd);
        }
        return (ravineEnd - ravineBegin) * Double.valueOf(timeMap.get("0").get("num").toString());
    }

    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


}
