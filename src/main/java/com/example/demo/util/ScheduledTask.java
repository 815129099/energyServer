package com.example.demo.util;

import com.example.demo.entity.Params;
import com.example.demo.mapper.power.OrigDLDao;
import com.example.demo.mapper.util.UtilDao;
import com.example.demo.util.date.DateUtil;
import com.example.demo.util.jna.tcp.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledTask {
    @Autowired
    private UtilDao utilDao;
    @Autowired
    private OrigDLDao origDLDao;
    @Autowired
    private ChargeUtil chargeUtil;
    @Autowired
    private Client client;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*自动采集数据
    * 每点的30分，采集上一个小时的数据
    * */
    @Scheduled(cron = "0 30 * * * ?")
    @Async("asyncServiceExecutor")
    public void AutoGetAllData(){
        System.out.println("触发定时任务");
        byte[] tm = DateUtil.getTmByHour();
        List<Map> mapList = this.utilDao.getErtuIDList();
        for (Map<String, Object> m : mapList) {
            int port = Integer.parseInt(m.get("port").toString());
            String host = m.get("ip").toString();
            int ErtuID = Integer.parseInt(m.get("ErtuID").toString());
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    logger.warn(port + "," + host + "," + ErtuID);
                    try {
                        client.connect(port, host, ErtuID, tm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
                }
    }

    /*自动计算峰平谷数据
    * 每天凌晨4点，自动计算前一天的峰平谷数据
    * */
    @Scheduled(cron = "0 0 4 1/1 * ? ")
    public void AutoGetPeak(){
        List<Map> maps = this.origDLDao.getEMeterNum();
            for (Map<String,Object> m:maps) {
                Params params = new Params();
                params.setEMeterName(m.get("EMeterName").toString());
                params.setEStationName(m.get("EStationName").toString());
                params.setEMeterID(Integer.parseInt(m.get("EMeterID").toString()));
                params.setPowerType("ZxygZ");
                params.setBeginTime(DateUtil.getBeforeDay());
                try {
                    chargeUtil.insertPowerList(params,DateUtil.getBeforeDay1());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
    }
}
