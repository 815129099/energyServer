package com.example.demo.service.power;

import com.example.demo.entity.ExportPower;
import com.example.demo.entity.OrigDL;
import com.example.demo.entity.Params;
import com.example.demo.mapper.power.OrigDLDao;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.util.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
@Service
public class OrigDLServiceImpl extends ServiceImpl<OrigDLDao, OrigDL> implements OrigDLService {

    @Autowired
    private OrigDLDao origDLDao;


    //获取电量数据
    @Override
    public PageInfo<Map> getPowerData(Params param) {
        System.out.println(param.toString());
        Map<String,Object> map = new HashMap();
        //有数据的电表的电量数据
        List<Map> maps = null;
        //所有电表的信息
        List<Map> maps1 = null;
        PageInfo<Map> page = null;
        /*
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
        List<Map> list = new ArrayList<>();
        int j = 0;
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<maps.size();i++){
            if(idList.contains(Integer.parseInt(maps.get(i).get("EMeterID").toString()))){
                if(i==maps.size()-1 || !idList.get(j-1).equals(Integer.parseInt(maps.get(i+1).get("EMeterID").toString()))){
                    double endNumber1 = 0.0;
                    if(param.getPowerType().equals("ZxygZ")){
                        endNumber1 = Double.valueOf(maps.get(i).get("ZxygZ").toString());
                        list.get(j-1).put("endNumber",endNumber1);
                    }else if(param.getPowerType().equals("FxygZ")){
                        endNumber1 = Double.valueOf(maps.get(i).get("FxygZ").toString());
                        list.get(j-1).put("endNumber",endNumber1);
                    }else if(param.getPowerType().equals("ZxwgZ")){
                        endNumber1 = Double.valueOf(maps.get(i).get("ZxwgZ").toString());
                        list.get(j-1).put("endNumber",endNumber1);
                    }else if(param.getPowerType().equals("FxwgZ")){
                        endNumber1 = Double.valueOf(maps.get(i).get("FxwgZ").toString());
                        list.get(j-1).put("endNumber",endNumber1);
                    }
                    double beginNumber1 =   Double.valueOf(list.get(j-1).get("beginNumber").toString());
                    list.get(j-1).put("difValue",String.format("%.6f",endNumber1-beginNumber1));
                    list.get(j-1).put("powerTotal",String.format("%.6f",(endNumber1-beginNumber1)*Double.valueOf(list.get(j-1).get("num").toString())));
                }
            }else {
                //如果该电表ID不存在就添加
                idList.add(j,Integer.parseInt(maps.get(i).get("EMeterID").toString()));
                j++;
                Map<String,Object> map1 = new LinkedHashMap<>();
                //添加功
                if(param.getPowerType().equals("ZxygZ")){
                    map1.put("beginNumber",Double.valueOf(maps.get(i).get("ZxygZ").toString()));
                }else if(param.getPowerType().equals("FxygZ")){
                    map1.put("beginNumber",Double.valueOf(maps.get(i).get("FxygZ").toString()));
                }else if(param.getPowerType().equals("ZxwgZ")){
                    map1.put("beginNumber",Double.valueOf(maps.get(i).get("ZxwgZ").toString()));
                }else if(param.getPowerType().equals("FxwgZ")){
                    map1.put("beginNumber",Double.valueOf(maps.get(i).get("FxwgZ").toString()));
                }
                int MultiplyRatio = Integer.parseInt(maps.get(i).get("MultiplyRatio").toString());
                Double num;
                if(MultiplyRatio==1){
                    num = Double.valueOf(maps.get(i).get("num").toString());
                }else {
                    num = 1.0;
                }
                map1.put("num",num);
                map1.put("EMeterName",maps.get(i).get("EMeterName").toString());
                map1.put("EMeterID",maps.get(i).get("EMeterID").toString());
                map1.put("beginTime", DateUtil.DateToString(param.getBeginTime()));
                map1.put("endTime",DateUtil.DateToString(param.getEndTime()));
                list.add(j-1,map1);
            }
        }
        //遍历所有电表，筛选出没有数据的电表
        List<Map> collect = maps1.stream().filter(map1->list.stream().noneMatch(map2->map2.get("EMeterID").toString().equals(map1.get("EMeterID").toString()))).collect(Collectors.toList());
        for (Map n:collect) {
            Map<String,Object> map1 = new LinkedHashMap<>();
            int MultiplyRatio = Integer.parseInt(n.get("MultiplyRatio").toString());
            Double num;
            if(MultiplyRatio==1){
                num = Double.valueOf(n.get("num").toString());
            }else {
                num = 1.0;
            }
            map1.put("num",num);
            map1.put("EMeterName",n.get("EMeterName").toString());
            map1.put("EMeterID",n.get("EMeterID").toString());
            map1.put("beginTime", DateUtil.DateToString(param.getBeginTime()));
            map1.put("endTime",DateUtil.DateToString(param.getEndTime()));
            map1.put("difValue","--");
            map1.put("beginNumber","--");
            map1.put("endNumber","--");
            map1.put("powerTotal","--");
            list.add(map1);
        }*/
        List<Map> list = new ArrayList<>();
        //获取电量数据
        maps = origDLDao.getPowerData(param);
        //获取电表信息
        maps1 = origDLDao.getPowerData1(param);
        for (Map m :maps1) {
            //过滤出该电表下的数据
            List<Map> mapList = maps.stream().filter(ms1 ->m.get("EMeterID").equals(ms1.get("EMeterID"))).collect(Collectors.toList());
            //mapList.stream().forEach(map1 -> System.out.println(map1.get("EMeterID").toString()+","+DateUtil.DateToString((Date)map1.get("TimeTag"))+","+map1.get("ZxygZ")
             //       +","+map1.get("FxygZ")));
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
                map1.put("beginTime", DateUtil.DateToString((Date) mapList.get(0).get("TimeTag")));
                map1.put("endTime",DateUtil.DateToString((Date) mapList.get(mapList.size()-1).get("TimeTag")));
                list.add(map1);
            }

        }
        /*
        list.stream().forEach(map1 -> System.out.println(map1.get("EMeterName").toString()+","+map1.get("powerTotal").toString()+","+
                map1.get("num").toString()+","+map1.get("difValue").toString()+","+
                map1.get("beginNumber").toString()+","+map1.get("endNumber").toString()+","+
                map1.get("beginTime").toString()+","+map1.get("endTime").toString()+","+
                map1.get("EMeterID").toString()));*/
        PageHelper.startPage(Integer.parseInt(param.getPageNum()), Integer.parseInt(param.getPageSize()));
        page = new PageInfo(list);
        return page;
    }

    //获取电量分析
    @Override
    public Map getPowerAnalyze(Params params) {
        PageInfo<Map> page = null;
        PageHelper.startPage(Integer.parseInt(params.getPageNum()), Integer.parseInt(params.getPageSize()));
        Map<String,Object> map = new HashMap<>();
        //获取数据库的map
        List<Map> maps = null;
        //图表数据
        List<Map> chartList = new ArrayList<Map>();
        List<LinkedHashMap> newMaps = new ArrayList<LinkedHashMap>();
        if(params.getDateType().equals("hour")){
            maps = origDLDao.getPowerAnalyzeByHour(params);
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
                beginNumber = Double.valueOf(maps.get(i).get(params.getPowerType()).toString());
                endNumber = Double.valueOf(maps.get(i+1).get(params.getPowerType()).toString());

                map1.put("beginNumber",beginNumber);
                map1.put("endNumber",endNumber);
                int totalNumber = (int)((endNumber-beginNumber)*num);
                map1.put("totalNumber",totalNumber);
                newMaps.add(i,map1);
                //用于图表显示
                Map<String ,Object> map2 = new LinkedHashMap<>();
                map2.put("name",Time);
                map2.put("value",totalNumber);
                chartList.add(i,map2);
            }
        }else if(params.getDateType().equals("day")){
            maps = origDLDao.getPowerAnalyzeByDay(params);
            for(int i=0;i<maps.size()-1;i++){
                //用于数据显示
                LinkedHashMap map1 = new LinkedHashMap();
                String Time = maps.get(i).get("Time").toString();
                map1.put("Time", Time);
                int MultiplyRatio = Integer.parseInt(maps.get(i).get("MultiplyRatio").toString());
                Double num;
                if(MultiplyRatio==1){
                    num = Double.valueOf(maps.get(i).get("num").toString());
                }else {
                    num = 1.0;
                }
                map1.put("num",num);
                map1.put("beginTime",DateUtil.DateToString((Date) maps.get(i).get("TimeTag")));
                map1.put("endTime",DateUtil.DateToString((Date)maps.get(i+1).get("TimeTag")));
                Double beginNumber = 0.0,endNumber = 0.0;
                beginNumber = Double.valueOf(maps.get(i).get(params.getPowerType()).toString());
                endNumber = Double.valueOf(maps.get(i+1).get(params.getPowerType()).toString());
                map1.put("beginNumber",beginNumber);
                map1.put("endNumber",endNumber);
                int totalNumber = (int)((endNumber-beginNumber)*num);
                map1.put("totalNumber",totalNumber);
                newMaps.add(i,map1);
                //用于图表显示
                Map<String ,Object> map2 = new LinkedHashMap<>();
                map2.put("name",Time);
                map2.put("value",totalNumber);
                chartList.add(i,map2);
            }
        }

        page = new PageInfo(newMaps);
        map.put("page",page);
        map.put("chartList",chartList);
        return map;
    }

}
