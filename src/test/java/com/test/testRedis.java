package com.test;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Params;
import com.example.demo.mapper.power.OrigDLDao;
import com.example.demo.mapper.util.UtilDao;
import com.example.demo.service.power.OrigDLService;
import com.example.demo.service.util.UtilService;
import com.example.demo.util.ChargeUtil;
import com.example.demo.util.ScheduledTask;
import com.example.demo.util.date.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
public class testRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ChargeUtil chargeUtil;

    @Autowired
    private UtilService utilService;
    @Autowired
    private OrigDLDao origDLDao;
    @Autowired
    private OrigDLService origDLService;
    @Autowired
    private ScheduledTask scheduledTask;
    @Autowired
    private UtilDao utilDao;
    @Test
    public void testTemplate(){
        stringRedisTemplate.opsForValue().set("aaa","2112");
        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
    }

    //计算峰平谷
    @Test
    public void testCharge() throws ParseException {
        List<Map> maps = this.origDLDao.getEMeterNum();
        int i = 29;
            for (Map<String,Object> m:maps) {
                Params params = new Params();
                params.setEMeterName(m.get("EMeterName").toString());
                params.setEStationName(m.get("EStationName").toString());
                params.setEMeterID(Integer.parseInt(m.get("EMeterID").toString()));
                params.setBeginTime(DateUtil.StringToDate("2019-07-" + i + " 00:00:00"));
                params.setPowerType("ZxygZ");
                chargeUtil.insertPowerList(params, "2019-7-" + i);
            }
                i++;
                try {
                    Thread.sleep(1000*10);
                    System.out.println("-------------------------------------------------------------睡眠20秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    }

    //计算峰平谷
    @Test
    public void testCharge1() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String Time = "2019-04-01 00:00:00";
        calendar.setTime(DateUtil.StringToDate(Time));
        int i = 1;
        while (i<31){
            Params params = new Params();
            params.setEMeterName("2#快冷炉");
            params.setEStationName("同安13B");
            params.setEMeterID(303);
            params.setBeginTime(calendar.getTime());
            Time = DateUtil.DateToStringByPattern(calendar.getTime(),"yyyy-MM-dd");
            chargeUtil.insertPowerList(params,Time);
            calendar.add(Calendar.DATE,1);
            i++;
        }

        // chargeUtil.insertPowerList(params,"2019-04-01");
        //  this.chargeUtil.getPowerByTime("2019-04-01 14:30:00","2019-04-01 17:30:00",params);
    }

    @Test
    public void testhhh(){
        Params params = new Params();
        params.setEStationName("同安16B");
        params.setBeginTime(DateUtil.StringToDate("2019-4-1 10:00:00"));
        params.setEndTime(DateUtil.StringToDate("2019-4-1 13:00:00"));
        params.setPowerType("ZxygZ");
        params.setPageSize("10000");
        params.setPageNum("1");
        origDLService.getPowerData(params);
    }

    @Test
    public void testAutoGetPeak(){
        scheduledTask.AutoGetPeak();
    }

    @Test
    public void testErtuIDlIST(){
        int[] eList = {10,11,16};
        List<Map> maps = this.utilDao.getErtuIDListByList(eList);
        maps.stream().forEach(map -> System.out.println(map.get("ErtuID").toString()+","+map.get("ip").toString()));
    }



}
