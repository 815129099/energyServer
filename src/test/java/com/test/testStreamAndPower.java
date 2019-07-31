package com.test;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Params;
import com.example.demo.mapper.power.OrigDLDao;
import com.example.demo.mapper.power.OrigDLTotalDao;
import com.example.demo.mapper.util.UtilDao;
import com.example.demo.util.date.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
public class testStreamAndPower {

    @Autowired
    private OrigDLDao origDLDao;
    @Autowired
    private OrigDLTotalDao origDLTotalDao;
    @Autowired
    private UtilDao utilDao;

    @Test
    public void test1(){
        Params param = new Params();
        param.setEStationName("同安5B");
        param.setEMeterName("");
        param.setBeginTime(DateUtil.StringToDate("2019-7-1 00:00:00"));
        param.setEndTime(DateUtil.StringToDate("2019-7-2 00:00:00"));
        param.setPowerType("ZxygZ");


        Map<String,Object> map = new HashMap();
        System.out.println(param.getBeginTime());
        System.out.println(param.getEndTime());

        List<Map> list = new ArrayList<>();


        //有数据的电表的电量数据
        List<Map> maps = null;
        //所有电表的信息
        List<Map> maps1 = null;
        if(StringUtils.isEmpty(param.getEMeterName()) && !StringUtils.isEmpty(param.getEStationName())){
            //按厂站查
            maps = origDLDao.getAllDataByEStationName(param);
            //获取厂站下所有电表
            maps1 = origDLDao.getAllDataByEStationName1(param);
        }else if(StringUtils.isEmpty(param.getEMeterName()) && StringUtils.isEmpty(param.getEStationName())){
            //按厂站查
            maps = origDLDao.getAllData(param);
            //获取厂站下所有电表
            maps1 = origDLDao.getAllData1(param);
        }else {
            //按电表查
            maps = origDLDao.getPowerData(param);
            maps1 = origDLDao.getPowerData1(param);
        }

        for (Map m :maps1) {
            System.out.println("this is "+m.get("EMeterID").toString()+"-----------------------------");
            //过滤出该电表下的数据
            List<Map> mapList = maps.stream().filter(ms1 ->m.get("EMeterID").equals(ms1.get("EMeterID"))).collect(Collectors.toList());
            mapList.stream().forEach(map1 -> System.out.println(map1.get("EMeterID").toString()+","+map1.get("TimeTag").toString()+","+map1.get("ZxygZ")
                    +","+map1.get("FxygZ")));
            //如果该电表在该时间段没数据，则--
            if(mapList.size()==0){
                Map<String,Object> map1 = new HashMap<>();
                map1.put("EMeterID",m.get("EMeterID"));
                map1.put("EMeterName",m.get("EMeterName"));
                int MultiplyRatio = Integer.parseInt(m.get("MultiplyRatio").toString());
                Double num;
                if(MultiplyRatio==1){
                    num = Double.valueOf(m.get("num").toString());
                }else {
                    num = 1.0;
                }
                map1.put("num",num);
                map1.put("beginTime", DateUtil.DateToString(param.getBeginTime()));
                map1.put("endTime",DateUtil.DateToString(param.getEndTime()));
                map1.put("difValue","--");
                map1.put("beginNumber","--");
                map1.put("endNumber","--");
                map1.put("powerTotal","--");
                list.add(map1);
            }else {
                //如果有数据
                //num是倍率
                Map<String,Object> map1 = new LinkedHashMap<>();
                //MultiplyRatio变电比
                int MultiplyRatio = Integer.parseInt(mapList.get(0).get("MultiplyRatio").toString());
                Double num;
                if(MultiplyRatio==1){
                    num = Double.valueOf(m.get("num").toString());
                }else {
                    num = 1.0;
                }
                //beginNumber开始读数、endNumber结束读数、difValue读数差、powerTotal总电量
                double beginNumber=0.0 ,endNumber=0.0 ,difValue=0.0 ,powerTotal=0.0 ;
                beginNumber = Double.valueOf(mapList.get(0).get(param.getPowerType()).toString());
                endNumber = Double.valueOf(mapList.get(mapList.size()-1).get(param.getPowerType()).toString());
                difValue = endNumber-beginNumber;
                powerTotal = difValue*num;
                map1.put("num",num);
                map1.put("EMeterName",mapList.get(0).get("EMeterName"));
                map1.put("EMeterID",mapList.get(0).get("EMeterID"));
                map1.put("difValue",String.format("%.4f",difValue));
                map1.put("beginNumber",String.format("%.4f",beginNumber));
                map1.put("endNumber",String.format("%.4f",endNumber));
                map1.put("powerTotal",String.format("%.4f",powerTotal));
                map1.put("beginTime", mapList.get(0).get("TimeTag"));
                map1.put("endTime",mapList.get(mapList.size()-1).get("TimeTag"));
                list.add(map1);
            }

        }
        list.stream().forEach(map1 -> System.out.println(map1.get("EMeterName").toString()+","+map1.get("powerTotal").toString()+","+
                map1.get("num").toString()+","+map1.get("difValue").toString()+","+
                map1.get("beginNumber").toString()+","+map1.get("endNumber").toString()+","+
                map1.get("beginTime").toString()+","+map1.get("endTime").toString()+","+
                map1.get("EMeterID").toString()));
    }

    @Test
    public void test2(){
        Params param = new Params();
        param.setEStationName("同安5B");
        param.setEMeterName("908-5B");
        param.setDateType("hour");
        param.setBeginTime(DateUtil.StringToDate("2019-7-1 00:00:00"));
        param.setEndTime(DateUtil.StringToDate("2019-7-2 00:00:00"));
        param.setPowerType("ZxygZ");
        List<Map> maps = null;
        if(param.getDateType().equals("hour")){
            maps = origDLDao.getPowerAnalyzeByHour(param);


            int MultiplyRatio = Integer.parseInt(maps.get(0).get("MultiplyRatio").toString());
            Double num;
            if(MultiplyRatio==1){
                num = Double.valueOf(maps.get(0).get("num").toString());
            }else {
                num = 1.0;
            }
            for(int i=0;i<maps.size()-1;i++){
                //用于数据显示
                LinkedHashMap map1 = new LinkedHashMap();
                String Time = maps.get(i).get("Time").toString();
                map1.put("Time", Time);

                map1.put("num",num);
                map1.put("beginTime",DateUtil.DateToString((Date) maps.get(i).get("TimeTag")));
                map1.put("endTime",DateUtil.DateToString((Date)maps.get(i+1).get("TimeTag")));
                Double beginNumber = 0.0,endNumber = 0.0;
                beginNumber = Double.valueOf(maps.get(i).get(param.getPowerType()).toString());
                endNumber = Double.valueOf(maps.get(i+1).get(param.getPowerType()).toString());

                map1.put("beginNumber",beginNumber);
                map1.put("endNumber",endNumber);
                int totalNumber = (int)((endNumber-beginNumber)*num);
                map1.put("totalNumber",totalNumber);
                //newMaps.add(i,map1);
                //用于图表显示
                Map<String ,Object> map2 = new LinkedHashMap<>();
                map2.put("name",Time);
                map2.put("value",totalNumber);
               // chartList.add(i,map2);
            }
        }
    }

    @Test
    public void test3(){
        Params param = new Params();
        param.setEStationName("同安5B");
        param.setEMeterName("5B");
        param.setBeginTime(DateUtil.StringToDate("2019-7-1 00:00:00"));
        param.setEndTime(DateUtil.StringToDate("2019-7-2 00:00:00"));
        param.setPowerType("ZxygZ");
        List<Map> maps = this.origDLTotalDao.getEDepartmentData(param);
        maps.stream().forEach(map -> System.out.println(map.get("EMeterID")+","+map.get("EDepartmentName")+","+map.get("EDepartmentNo")));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Test
    public void test4(){
        List<Map> mapList = new ArrayList<>();
        List<Map> maps = this.utilDao.getTotalPower();
        List<String> dateList = DateUtil.getWeekDay();
        //distinct去重保留第一个出现的
       // maps.stream().filter(map -> map.get("EMeterID").toString().equals("1")).filter(distinctByKey(map -> map.get("Time")))
              //  .forEach(map -> System.out.println(map.get("Time")+","+map.get("TimeTag").toString()+","+map.get("EMeterID")));

      //  List<Map> mapList1 = maps.stream().filter(map -> map.get("EMeterID").toString().equals("2")).filter(distinctByKey(map -> map.get("Time")))
       //         .collect(Collectors.toList());
        maps.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(m->m.get("Time").toString()+m.get("EMeterID")))),ArrayList::new )).forEach(map -> System.out.println(map.get("Time")+","+map.get("TimeTag").toString()+","+map.get("EMeterID")));;

        //for(int i=0;i<mapList1.)
    }
}
