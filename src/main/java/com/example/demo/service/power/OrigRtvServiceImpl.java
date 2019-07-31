package com.example.demo.service.power;

import com.example.demo.entity.OrigRtv;
import com.example.demo.entity.Params;
import com.example.demo.mapper.power.OrigRtvDao;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
@Service
public class OrigRtvServiceImpl extends ServiceImpl<OrigRtvDao, OrigRtv> implements OrigRtvService {

    @Autowired
    private OrigRtvDao origRtvDao;

    @Override
    public Map getInstantPowerAnalyze(Params params) {
        PageInfo<Map> page = null;
        PageHelper.startPage(Integer.parseInt(params.getPageNum()), Integer.parseInt(params.getPageSize()));
        Map<String,Object> map = new HashMap<>();
        //获取数据库的map
        List<Map> maps = null;
        //图表数据
        List<Map> chartList = new ArrayList<Map>();
        List<LinkedHashMap> newMaps = new ArrayList<LinkedHashMap>();
        if(params.getDateType().equals("day")){
            maps = origRtvDao.getInstantPowerAnalyzeByDay(params);
            int MultiplyRatio = 0;
            double U  = 0.0;
            double I = 0.0;
            if(maps.size()>0){
                MultiplyRatio = Integer.parseInt(maps.get(0).get("MultiplyRatio").toString());
                I = Integer.parseInt(maps.get(0).get("I").toString());
                U = Integer.parseInt(maps.get(0).get("U").toString());
            }
            for(int i=0;i<maps.size()-1;i++){
                Map map1 = maps.get(i);
                if(MultiplyRatio==1){
                    map1.put("Ia",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ia").toString())*I));
                    map1.put("Ib",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ib").toString())*I));
                    map1.put("Ic",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ic").toString())*I));
                    map1.put("Ua",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ua").toString())*U));
                    map1.put("Ub",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ub").toString())*U));
                    map1.put("Uc",String.format("%.2f",Double.parseDouble(maps.get(i).get("Uc").toString())*U));
                }
                //用于数据显示
                chartList.add(i,map1);
            }
        }else if(params.getDateType().equals("week")){
            maps = origRtvDao.getInstantPowerAnalyzeByWeek(params);
            int MultiplyRatio = 0;
            double U  = 0.0;
            double I = 0.0;
            if(maps.size()>0){
                MultiplyRatio = Integer.parseInt(maps.get(0).get("MultiplyRatio").toString());
                U = Integer.parseInt(maps.get(0).get("U").toString());
                I = Integer.parseInt(maps.get(0).get("I").toString());
            }
            for(int i=0;i<maps.size()-1;i++){
                Map map1 = maps.get(i);
                if(MultiplyRatio==1){
                    map1.put("Ia",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ia").toString())*I));
                    map1.put("Ib",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ib").toString())*I));
                    map1.put("Ic",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ic").toString())*I));
                    map1.put("Ua",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ua").toString())*U));
                    map1.put("Ub",String.format("%.2f",Double.parseDouble(maps.get(i).get("Ub").toString())*U));
                    map1.put("Uc",String.format("%.2f",Double.parseDouble(maps.get(i).get("Uc").toString())*U));
                }
                //用于数据显示
                chartList.add(i,map1);
            }
        }
        page = new PageInfo(chartList);
        map.put("page",page);
        map.put("chartList",chartList);
        return map;
    }


}
