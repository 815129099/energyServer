package com.test;

import com.example.demo.DemoApplication;
import com.example.demo.mapper.power.OrigDLDao;
import com.example.demo.mapper.util.UtilDao;
import com.example.demo.service.power.OrigDLService;
import com.example.demo.util.date.DateUtil;
import com.example.demo.util.jna.tcp.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
public class testLog4j2 {

    @Autowired
    private UtilDao utilDao;

    @Autowired(required = false)
    private Client client;


    @Autowired
    private OrigDLDao origDLDao;

    @Autowired
    private OrigDLService origDLService;


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testLog(){
        logger.info("this is info");
        logger.warn("this is warn");
        logger.debug("this is debug");
        logger.error("this is error");
    }

    @Test
    public void testWeek(){
        List<LinkedHashMap> list = this.utilDao.getRecordNumberList();
        List<Map> reList = new ArrayList<Map>();
        List<String> dateList = DateUtil.getWeekDay();

        //如果list为空
        if(StringUtils.isEmpty(list.size())){
            logger.info("list is empty");
            for(int i=0;i<dateList.size();i++){
                Map<String ,Object> map = new LinkedHashMap<>();
                map.put("name",dateList.get(i));
                map.put("value",0);
                reList.add(i,map);
            }
        //如果list的长度大于0且小于7
        }else if(list.size()<7){
            logger.info("list is smaller than 7 and bigger than 0");
            int i = 0;
            while (i<list.size()){
                for(int j=0;j<dateList.size();j++){
                    if(!list.get(i).get("name").equals(dateList.get(j))){
                        Map<String ,Object> map = new LinkedHashMap<>();
                        map.put("name",dateList.get(j));
                        map.put("value",0);
                        System.out.println(dateList.get(j));
                        reList.add(j,map);
                    }else {
                        reList.add(j,list.get(i));
                        i++;
                    }
                }
            }
        }

        for(Map<String, Object> m:reList){
            for (String k : m.keySet()) {
                System.out.println(k+m.get(k).toString());
            }
        }
    }

    @Test
    public void testThread(){

        for(int i=0;i<10;i++){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    System.out.println(Thread.currentThread().getName());
                }
            }.start();
        }
    }

/*
    //多线程为了线程安全是防止注入bean的
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void testTTT() throws Exception {
        byte[] tm = {19,5,1,0,0,0,23,59,59};
        List<Map> mapList = this.utilDao.getErtuIDList();
        byte i = 1;
        while (i<20){
            System.out.println("正在跑第"+i+"天的数据");
            for (Map<String,Object> m:mapList){
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        int port = Integer.parseInt(m.get("port").toString());
                        String host = m.get("ip").toString();
                        int ErtuID = Integer.parseInt(m.get("ErtuID").toString());
                        System.out.println(port+","+host+","+ErtuID);
                        try {
                            client.connect(port,host,ErtuID,tm);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            i++;
            tm[2] = i;
        }
       // client.connect(700,"10.30.23.44",2,tm);
    }
*/
}
