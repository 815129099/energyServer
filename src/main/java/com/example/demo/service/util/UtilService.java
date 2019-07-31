package com.example.demo.service.util;

import com.example.demo.entity.Record;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
public interface UtilService  {

    Object getMenuJson();

    Object getErtusJson();

    Record getTimeAndIp(String geNumber);

    int getTotalNumber();

    int getTotalUserNumber();

    Map getRecordNumberList();

    //通过时间段、采集器ID列表、采集类型
    boolean getDataByErtuID(byte[] tm,int[] eList,int type);
    //通过时间段、采集器ID列表、电表ID列表、采集类型
    boolean getDataByErtuID(byte[] tm,int[] eList,List<List> mList,int type);

    Map getTotalPower();


}
