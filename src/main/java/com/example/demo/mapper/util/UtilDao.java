package com.example.demo.mapper.util;



import com.example.demo.entity.Record;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
@Mapper
public interface UtilDao  {
    List<LinkedHashMap> getMenuList();

    List<LinkedHashMap> getErtusList();

    List<Record> recordList(@Param("parameter") String parameter);

    Record getTimeAndIp(@Param("geNumber") String geNumber);

    int getTotalNumber();

    int getTotalUserNumber();

    List<LinkedHashMap> getRecordNumberList();
    //获取所有采集器ID
    List<Map> getErtuIDList();
    //获取部门采集器ID
    List<Map> getErtuIDListByList(int[] eList);

    //<!--通过采集器ID获取电表信息-->
    List<Map> getEMeterNumByErtuID(int ErtuID);
    //<!--通过采集器ID获取电表信息-->
    List<Map> getEMeterNumByEMeterID(List<Integer> mList);
    List<Map> getTotalPower();
}
