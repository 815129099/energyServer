package com.example.demo.service.power;

import com.example.demo.entity.Params;
import com.example.demo.mapper.power.OrigDLTotalDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrigDLTotalServiceImpl implements OrigDLTotalService{

    @Autowired
    private OrigDLService origDLService;
    @Autowired
    private OrigDLTotalDao origDLTotalDao;


    @Override
    public PageInfo<Map> getEDepartmentData(Params params) {
        PageInfo<Map> page = null;
        List<Map> mapList = new ArrayList<>();
        //获取电表的电量数据
        List<Map> list = origDLService.getPowerData(params).getList();
        //电表对应的部门及成本中心的分摊比例
        List<Map> maps = this.origDLTotalDao.getEDepartmentData(params);
        for (Map m:list) {
            //根据EMeterID匹配分配比例
            List<Map> list1 = maps.stream().filter(map -> map.get("EMeterID").toString().equals(m.get("EMeterID").toString())).collect(Collectors.toList());
            for (Map mm:list1) {
                if(StringUtils.isEmpty(mm.get("EDepartmentRatio")) || StringUtils.isEmpty(mm.get("CostCenterRatio"))){
                    mm.put("powerTotal",m.get("powerTotal"));
                }else {
                    if(m.get("powerTotal").toString().equals("--")){
                        mm.put("powerTotal","--");
                    }else {
                        Double EDepartmentRatio = Double.valueOf(mm.get("EDepartmentRatio").toString());
                        Double CostCenterRatio = Double.valueOf(mm.get("CostCenterRatio").toString());
                        Double powerTotal = Double.valueOf(m.get("powerTotal").toString());
                        mm.put("powerTotal",String.format("%.4f",powerTotal*EDepartmentRatio*CostCenterRatio));
                    }
                }
                mm.put("EMeterName",m.get("EMeterName"));
                mm.put("num",m.get("num"));
                mm.put("difValue",m.get("difValue"));
                mm.put("beginNumber",m.get("beginNumber"));
                mm.put("endNumber",m.get("endNumber"));
                mm.put("beginTime",m.get("beginTime"));
                mm.put("endTime",m.get("endTime"));
                mapList.add(mm);
            }
        }
      //  mapList.stream().forEach(map -> System.out.println(map.get("EMeterID")+","+map.get("EMeterName")+","+map.get("powerTotal")+","+
        //        ","+map.get("EDepartmentRatio")+","+map.get("CostCenterRatio")+","+map.get("beginTime")+","+map.get("endTime")));

        PageHelper.startPage(Integer.parseInt(params.getPageNum()), Integer.parseInt(params.getPageSize()));
        page = new PageInfo(mapList);
        return page;
    }
}
