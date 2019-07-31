package com.example.demo.mapper.power;

import com.example.demo.entity.Charge;
import com.example.demo.entity.OrigDL;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.entity.Params;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
@Mapper
public interface OrigDLDao extends BaseMapper<OrigDL> {

    void insertOrigDL(OrigDL origDL);

    List<Map> getPowerData(Params param);
    List<Map> getPowerData1(Params param);

    List<Map> getPowerAnalyzeByHour(Params params);

    List<Map> getPowerAnalyzeByDay(Params params);

    List<Map> getPowerByMonthAndID(@Param("id") int id,@Param("month") String month,@Param("hours") Set<Integer> set);

    List<Map> getEMeterNum();

    List<Map> getEMeterNumByErtuID(@Param("ErtuID") int ErtuID);

    List<Integer> getEMeterIDByEStationName(@Param("EStationName") String EStationName);

    List<Map> getAllDataByEStationName(Params params);
    List<Map> getAllDataByEStationName1(Params params);
    List<Map> getAllData(Params params);
    List<Map> getAllData1(Params params);

}
