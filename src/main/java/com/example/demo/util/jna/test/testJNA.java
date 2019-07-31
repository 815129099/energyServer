package com.example.demo.util.jna.test;


import com.example.demo.entity.OrigDL;
import com.example.demo.entity.OrigRtv;
import com.example.demo.mapper.power.OrigDLDao;
import com.example.demo.mapper.power.OrigRtvDao;
import com.example.demo.mapper.util.UtilDao;
import com.example.demo.util.jna.po.*;
import com.example.demo.util.jna.util.DllUtil;
import com.example.demo.util.jna.util.HexUtil;
import com.example.demo.util.netty.client.TimeClient;
import com.sun.jna.NativeLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


public class testJNA {

    @Autowired
    private OrigDLDao origdlDao;
    @Autowired
    private OrigRtvDao origrtvDao;
    @Autowired
    private UtilDao utilDao;

    protected final static Logger logger = LoggerFactory.getLogger(testJNA.class);
        /*
            5.1请求链路状态
            请求链路，返回false表示失败，返回true表示成功
        */
        public boolean oneStep(int port,String host){
            boolean isSuccess = false;

            S_CLOU102_PACK.ByReference pack = new S_CLOU102_PACK.ByReference();
            pack.typeID = 0x01;
            pack.address = 1;

            //获取pack解析的16进制字符串
            String packMessage = DllUtil.pack(pack);//pack的16进制串
            if(!packMessage.equals("error")){  //返回error表示解析失败 否则返回16进制字符串
                String flag = null;  //采集器返回的16进制串
                try {
                    flag = new TimeClient().connect(port,host,packMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                S_CLOU102_UNPACK.ByReference unpack = new S_CLOU102_UNPACK.ByReference();
                byte[] bytes = HexUtil.toBytes(flag);
                int num = DllUtil.unPack(bytes,bytes.length,unpack);
                if(num==1){
                    isSuccess = true;
                }
            }
            logger.info("-------------------------one-------------------------------");
            return isSuccess;
        }


        /*
        5.2与子站约定参数
         */
        public boolean twoStep(int port,String host){
            boolean isSuccess = false;

            S_CLOU102_PACK.ByReference pack = new S_CLOU102_PACK.ByReference();
            pack.typeID = 0x20;
            pack.address = 1;
            pack.minTimeInterval = 0;

            String packMessage = DllUtil.pack(pack);
            if(!packMessage.equals("error")){  //返回error表示解析失败 否则返回13位16进制字符串
                String flag = null;  //采集器返回的16进制串
                try {
                    //flag = new TimeClient().connect(700,"10.30.25.29",packMessage);
                    flag = new TimeClient().connect(port,host,packMessage);
                    logger.info("flag:"+flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                S_CLOU102_UNPACK.ByReference unpack = new S_CLOU102_UNPACK.ByReference();
                byte[] bytes = HexUtil.toBytes(flag);
                int num = DllUtil.unPack(bytes,bytes.length,unpack);
                if(num==2){
                    isSuccess = true;
                }
            }
            logger.info("-------------------------two-------------------------------");
            return isSuccess;
        }


        /*
            5.3请求子站时钟
         */
        public boolean threeStep(int port,String host) {
            boolean isSuccess = false;
            S_CLOU102_PACK.ByReference pack = new S_CLOU102_PACK.ByReference();
            pack.typeID = 0x02;
            pack.address = 1;
            String packMessage = DllUtil.pack(pack);
            if(!packMessage.equals("error")){  //返回error表示解析失败 否则返回13位16进制字符串
                String flag = null;  //采集器返回的16进制串
                try {
                    //flag = new TimeClient().connect(700,"10.30.25.29",packMessage);
                    flag = new TimeClient().connect(port,host,packMessage);
                    logger.info("flag:"+flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                S_CLOU102_UNPACK.ByReference unpack = new S_CLOU102_UNPACK.ByReference();
                byte[] bytes = HexUtil.toBytes(flag);
                DllUtil.unPack(bytes,bytes.length,unpack);
                logger.info(unpack.toString());
                //LOGGER.info("time:"+HexUtil.bytesToHexFun3(unpack.time));
            }
            logger.info("-------------------------three-------------------------------");
            return isSuccess;
        }

        //5.4与子站对时
        public boolean fourStep(int port,String host) {
            boolean isSuccess = false;
            S_CLOU102_PACK.ByReference pack = new S_CLOU102_PACK.ByReference();
            pack.typeID = 0x03;
            pack.address = 1;
            pack.minTimeInterval = 0;
            pack.compareTheTime = 1;
            pack.time = HexUtil.getDate().getBytes();
            String packMessage = DllUtil.pack(pack);
            if(!packMessage.equals("error")){  //返回error表示解析失败 否则返回13位16进制字符串
                String flag = null;  //采集器返回的16进制串
                try {
                    //flag = new TimeClient().connect(700,"10.30.25.29",packMessage);
                    flag = new TimeClient().connect(port,host,packMessage);
                    logger.info("flag:"+flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                S_CLOU102_UNPACK.ByReference unpack = new S_CLOU102_UNPACK.ByReference();
                byte[] bytes = HexUtil.toBytes(flag);
                DllUtil.unPack(bytes,bytes.length,unpack);
            }
            logger.info("-------------------------four-------------------------------");
            return isSuccess;
        }

        /*
         5.5召唤后续数据帧
         返回false有错误，返回true正确
         */
        public boolean fiveStep(int port, String host,NativeLong meterNum,int type,String ErtuID,int EMeterID){
            int backNum = 1;
            short i = 2;
            S_CLOU102_PACK.ByReference pack = new S_CLOU102_PACK.ByReference();
            pack.typeID = 0x04;
            pack.address = 1;
            pack.minTimeInterval = 0;
            pack.compareTheTime = 0;
            pack.time = "0000-00-00 00:00:00 ".getBytes();
            while (backNum==1) {
                pack.framenum = i;
                pack.write();
                String packMessage = DllUtil.pack(pack);
                if (!packMessage.equals("error")) {  //返回error表示解析失败 否则返回13位16进制字符串
                    String flag = null;  //采集器返回的16进制串
                    try {
                        flag = new TimeClient().connect(port, host, packMessage);
                        logger.info("flag:" + flag);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    S_CLOU102_UNPACK.ByReference unpack = new S_CLOU102_UNPACK.ByReference();

                    byte[] bytes = HexUtil.toBytes(flag);
                    backNum = DllUtil.unPack(bytes, bytes.length, unpack);
                    if (backNum != 1 && backNum != 5) {
                        return false;
                    }else if(backNum != 5 && type == 0){
                            OrigDL origdl = new OrigDL();
                        origdl.setErtuID(Integer.parseInt(ErtuID));
                        origdl.setEMeterNum(meterNum.toString());
                        origdl.setEMeterID(EMeterID);
                            origdl.setTimeTag(HexUtil.bytesToDate(unpack.time));
                            //正向有功总
                            origdl.setZxygZ(unpack.power.positive_active_power);
                            //正向无功总
                            origdl.setZxwgZ(unpack.power.positive_reative_power);
                            //反向无功 总
                            origdl.setFxwgZ(unpack.power.reverse_reactive_power);
                            //反向有功 总
                            origdl.setFxygZ(unpack.power.reverse_active_power);
                            origdlDao.insertOrigDL(origdl);
                    }else if(backNum != 5 && type == 1){

                        OrigRtv origrtv = new OrigRtv();
                        origrtv.setErtuID(10);
                        origrtv.setEMeterNum("46");
                        origrtv.setEMeterID(3);
                        origrtv.setTimeTag(HexUtil.bytesToDate(unpack.time));
                        origrtv.setUa(unpack.instantaneous_value.Ua);
                        origrtv.setUb(unpack.instantaneous_value.Ub);
                        origrtv.setUc(unpack.instantaneous_value.Uc);
                        origrtv.setIa(unpack.instantaneous_value.Ia);
                        origrtv.setIb(unpack.instantaneous_value.Ib);
                        origrtv.setIc(unpack.instantaneous_value.Ic);
                        origrtv.setPa(unpack.instantaneous_value.Pa);
                        origrtv.setPb(unpack.instantaneous_value.Pb);
                        origrtv.setPc(unpack.instantaneous_value.Pc);
                        origrtv.setP(unpack.instantaneous_value.P);
                        origrtv.setQ(unpack.instantaneous_value.Q);
                        origrtv.setQa(unpack.instantaneous_value.Qa);
                        origrtv.setQb(unpack.instantaneous_value.Qb);
                        origrtv.setQc(unpack.instantaneous_value.Qc);
                        origrtv.setCos(unpack.instantaneous_value.PF);
                        origrtv.setCosA(unpack.instantaneous_value.PFa);
                        origrtv.setCosB(unpack.instantaneous_value.PFb);
                        origrtv.setCosC(unpack.instantaneous_value.PFc);
                        origrtv.setHz(unpack.instantaneous_value.Hz);
                        origrtvDao.insertOrigRtv(origrtv);
                    }
                    logger.info(unpack.toString());
                    i++;
                }
            }
            logger.info("-------------------------five-------------------------------");
            return true;
        }

        /*
            5.6召唤总电量数据
            tm[8] 开始时间-截止时间数组
         */
        public boolean sixStep(int port, String host,byte[] tm,NativeLong meterNum,String ErtuID,int EMeterID){
            boolean isSuccess = false;
            S_CLOU102_PACK.ByReference pack = new S_CLOU102_PACK.ByReference();
            pack.typeID = 0x05;
            pack.address = 1;
            pack.minTimeInterval = 0;
            pack.compareTheTime = 0;
            // pointer.setString(0,"2016-08-29 11:06:23");
            pack.time = "0000-00-00 00:00:00 ".getBytes();
            pack.meternum = meterNum;
            pack.framenum = 1;
            TIME_FRAME time_frame = new TIME_FRAME();
            time_frame.tm_year = tm[0];
            time_frame.tm_mon = tm[1];
            time_frame.tm_mday = tm[2];
            time_frame.tm_hour_start = tm[3];
            time_frame.tm_min_start = tm[4];
            time_frame.tm_sec_start = tm[5];
            time_frame.tm_hour_end = tm[6];
            time_frame.tm_min_end = tm[7];
            time_frame.tm_sec_end = tm[8];
            pack.timeFrame = time_frame;
            time_frame.write();
            pack.write();
            String packMessage = DllUtil.pack(pack);
            if(!packMessage.equals("error")){  //返回error表示解析失败 否则返回13位16进制字符串
                String flag = null;  //采集器返回的16进制串
                try {
                    flag = new TimeClient().connect(port,host,packMessage);
                    logger.info("flag:"+flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                S_CLOU102_UNPACK.ByReference unpack = new S_CLOU102_UNPACK.ByReference();
                unpack.instantaneous_value.write();
                unpack.write();
                byte[] bytes = HexUtil.toBytes(flag);
                int num = DllUtil.unPack(bytes,bytes.length,unpack);
                //错误返回null
                if(num==1){
                    isSuccess = true;
                }else if(num==5){
                    return false;
                }
                OrigDL origdl = new OrigDL();
                origdl.setErtuID(Integer.parseInt(ErtuID));
                origdl.setEMeterNum(meterNum.toString());
                origdl.setEMeterID(EMeterID);
                origdl.setTimeTag(HexUtil.bytesToDate(unpack.time));
                //正向有功总
                origdl.setZxygZ(unpack.power.positive_active_power);
                //正向无功总
                origdl.setZxwgZ(unpack.power.positive_reative_power);
                //反向无功 总
                origdl.setFxwgZ(unpack.power.reverse_reactive_power);
                //反向有功 总
                origdl.setFxygZ(unpack.power.reverse_active_power);
                origdlDao.insertOrigDL(origdl);
                logger.info(unpack.toString());
                logger.info(unpack.power.toString());
            }
            logger.info("-------------------------six-------------------------------");
            return isSuccess;
        }


        /*
            5.9召唤瞬时量数据
         */
        public boolean nineStep(int port, String host, byte[] tm,NativeLong meterNum){
            INSTANTANEOUS_VALUE value =  new INSTANTANEOUS_VALUE();
            S_CLOU102_PACK.ByReference pack = new S_CLOU102_PACK.ByReference();
            pack.typeID = 0x09;
            pack.address = 1;
            pack.minTimeInterval = 0;
            pack.compareTheTime = 0;
            pack.time = "0000-00-00 00:00:00 ".getBytes();
            pack.meternum = meterNum;
            pack.framenum = 1;
            TIME_FRAME time_frame = new TIME_FRAME();
            time_frame.tm_year = tm[0];
            time_frame.tm_mon = tm[1];
            time_frame.tm_mday = tm[2];
            time_frame.tm_hour_start = tm[3];
            time_frame.tm_min_start = tm[4];
            time_frame.tm_sec_start = tm[5];
            time_frame.tm_hour_end = tm[6];
            time_frame.tm_min_end = tm[7];
            time_frame.tm_sec_end = tm[8];
            pack.timeFrame = time_frame;
            time_frame.write();
            pack.write();
            String packMessage = DllUtil.pack(pack);
            if(!packMessage.equals("error")){  //返回error表示解析失败 否则返回13位16进制字符串
                String flag = null;  //采集器返回的16进制串
                try {
                    //flag = new TimeClient().connect(700,"10.30.25.29",packMessage);
                    flag = new TimeClient().connect(port,host,packMessage);
                    logger.info("flag:"+flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                S_CLOU102_UNPACK.ByReference unpack = new S_CLOU102_UNPACK.ByReference();
                unpack.instantaneous_value.write();
                unpack.write();
                byte[] bytes = HexUtil.toBytes(flag);
                int num = DllUtil.unPack(bytes,bytes.length,unpack);
                //错误返回null
                if(num!=1){
                    return false;
                }
                OrigRtv origrtv = new OrigRtv();
                origrtv.setErtuID(10);
                origrtv.setEMeterNum("46");
                origrtv.setEMeterID(3);
                origrtv.setTimeTag(HexUtil.bytesToDate(unpack.time));
                origrtv.setUa(unpack.instantaneous_value.Ua);
                origrtv.setUb(unpack.instantaneous_value.Ub);
                origrtv.setUc(unpack.instantaneous_value.Uc);
                origrtv.setIa(unpack.instantaneous_value.Ia);
                origrtv.setIb(unpack.instantaneous_value.Ib);
                origrtv.setIc(unpack.instantaneous_value.Ic);
                origrtv.setP(unpack.instantaneous_value.P);
                origrtv.setPa(unpack.instantaneous_value.Pa);
                origrtv.setPb(unpack.instantaneous_value.Pb);
                origrtv.setPc(unpack.instantaneous_value.Pc);
                origrtv.setQ(unpack.instantaneous_value.Q);
                origrtv.setQa(unpack.instantaneous_value.Qa);
                origrtv.setQb(unpack.instantaneous_value.Qb);
                origrtv.setQc(unpack.instantaneous_value.Qc);
                origrtv.setCos(unpack.instantaneous_value.PF);
                origrtv.setCosA(unpack.instantaneous_value.PFa);
                origrtv.setCosB(unpack.instantaneous_value.PFb);
                origrtv.setCosC(unpack.instantaneous_value.PFc);
                origrtv.setHz(unpack.instantaneous_value.Hz);
                origrtvDao.insertOrigRtv(origrtv);
                logger.info(unpack.toString());
                value = unpack.instantaneous_value;
                logger.info(value.toString());
            }
            logger.info("-------------------------nine-------------------------------");
            return true;
        }



        public void getDataByErtu(){
            List<Map> mapList = this.utilDao.getErtuIDList();
            for (Map<String,Object> m:mapList){
                List<Map> meterNumList = utilDao.getEMeterNumByErtuID(Integer.parseInt(m.get("ErtuID").toString()));
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        byte[] tm = {19,5,1,0,0,0,12,0,0};
                        for (Map meterNumMap:meterNumList){
                            logger.warn("正在跑："+m.get("ErtuID").toString()+"号采集器，"+meterNumMap.get("EMeterNum").toString()+"号电表");
                            getTotalData(Integer.parseInt(m.get("port").toString()), m.get("ip").toString(), tm, new NativeLong(Long.parseLong(meterNumMap.get("EMeterNum").toString())), m.get("ErtuID").toString(), Integer.parseInt(meterNumMap.get("EMeterID").toString()));
                        }
                    }
                }.start();
            }
        }

/*
    @Scheduled(fixedDelay = 1000*60*60*60)
    public void getDataByErtu(){
        List<Map> mapList = this.utilDao.getErtuIDList();
        for (Map<String,Object> m:mapList){
            List<Map> meterNumList = utilDao.getEMeterNumByErtuID(Integer.parseInt(m.get("ErtuID").toString()));
                    byte[] tm = {19,5,1,0,0,0,23,59,59};
                    for (Map meterNumMap:meterNumList){
                        logger.info("正在跑："+m.get("ErtuID").toString()+"号采集器，"+meterNumMap.get("EMeterNum").toString()+"号电表");
                        getTotalData(Integer.parseInt(m.get("port").toString()), m.get("ip").toString(), tm, new NativeLong(Long.parseLong(meterNumMap.get("EMeterNum").toString())), m.get("ErtuID").toString(), Integer.parseInt(meterNumMap.get("EMeterID").toString()));
                    }

        }
    }
*/
    public boolean getTotalData(int port,String host ,byte[] tm ,NativeLong meterNum,String ErtuID,int EMeterID){
        //NativeLong meterNum = new NativeLong(46);
        logger.info("********************************************开始执行**********************************************");
        if(!oneStep(port,host)){
            return false;
        }else if(!twoStep(port,host)){
            return false;
            // }else if(!threeStep(port,host)){
            //      return false;
            // }else if(!fourStep(port,host)){
            //      return false;
        }else if(!sixStep(port,host,tm,meterNum,ErtuID,EMeterID)){
            return false;
        }else if(!fiveStep(port,host,meterNum,0,ErtuID,EMeterID)){
            return false;
        }
        return true;
    }
        /*
        //获取总量电量
        @Scheduled(cron = "0 6 9 * * ?")
        public boolean getTotalData(){
        int port = 700;
        String host = "10.30.34.240" ;
        byte[] tm = {19,5,2,0,0,0,23,59,59};
        NativeLong meterNum = new NativeLong(46);
            logger.info("********************************************开始执行**********************************************");
            if(!oneStep(port,host)){
                return false;
            }else if(!twoStep(port,host)){
                return false;
           // }else if(!threeStep(port,host)){
          //      return false;
           // }else if(!fourStep(port,host)){
          //      return false;
            }else if(!sixStep(port,host,tm,meterNum)){
                return false;
            }else if(!fiveStep(port,host,0)){
                return false;
            }else {
                return true;
            }

        }
*/



    //获取总量电量
    //@Scheduled(cron = "0 55 9 * * ?")
    //@Scheduled(fixedDelay = 1000*60*60)
    /*
    public boolean getTotalData(){
        int port = 700;
        String host = "10.30.34.240" ;
        byte[] tm = {19,5,1,0,0,0,23,59,59};
        NativeLong meterNum = new NativeLong(46);
        logger.info("********************************************开始执行**********************************************");
        int i = 2;
        while (i<14){
            if(!oneStep(port,host)){
                return false;
            }else if(!twoStep(port,host)){
                return false;
                // }else if(!threeStep(port,host)){
                //      return false;
                // }else if(!fourStep(port,host)){
                //      return false;
            }else if(!sixStep(port,host,tm,meterNum)){
                return false;
            }else if(!fiveStep(port,host,0)){
                return false;
            }else {
                tm[2] = (byte)i;
                i++;
            }
        }
        return true;
    }
*/
        //获取瞬时量电量
        //@Scheduled(cron = "0 37 8 * * ?")
       //@Scheduled(fixedDelay = 1000*60*60*6)
    /*
    public boolean getData(){
            int port = 700;
            String host = "10.30.34.240" ;
            byte[] tm = {19,5,7,0,0,0,23,59,59};
            NativeLong meterNum = new NativeLong(46);
            int i = 8 ;
            while (i<13){
                if(!oneStep(port,host)){
                    return false;
                }else if(!twoStep(port,host)){
                    return false;
                    //}else if(!threeStep(port,host)){
                    //    return false;
                    // }else if(!fourStep(port,host)){
                    //     return false;
                }else if(!nineStep(port,host,tm,meterNum)){
                    return false;
                }else if(!fiveStep(port,host,1)){
                    return false;
                }else {
                    tm[2] = (byte)i;
                    i++;
                }
            }
        return true;
    }*/

/*
        public static void main(String[] args) throws Exception {
            System.setProperty("jna.encoding", "GBK");
            byte[] tm = {19,5,5,0,0,0,2,0,0};
            NativeLong meterNum = new NativeLong(62);
            //new testJNA().getTotalData(700,"10.30.34.240",tm,meterNum);

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    oneStep(700,"10.30.25.29");
                    LOGGER.info("------------------------------------oneStep--------------------------------------------------------");
                    twoStep(700,"10.30.25.29");
                    LOGGER.info("------------------------------------twoStep--------------------------------------------------------");
                    //threeStep(700,"10.30.25.29");
                    //LOGGER.info("------------------------------------threeStep--------------------------------------------------------");
                    //fourStep(700,"10.30.25.29");
                    // LOGGER.info("------------------------------------fourStep--------------------------------------------------------");
                    byte[] tm = {19,4,14,0,0,0,2,0,0};
                    //sixStep(700,"10.30.25.29",tm);
                   // LOGGER.info("------------------------------------sixStep--------------------------------------------------------");
                    nineStep(700,"10.30.25.29",tm);
                    LOGGER.info("------------------------------------nineStep--------------------------------------------------------");
                    fiveStep(700,"10.30.25.29");
                    LOGGER.info("------------------------------------fiveStep1--------------------------------------------------------");
                    //fiveStep(700,"10.30.25.29");
                    //LOGGER.info("------------------------------------fiveStep2--------------------------------------------------------");
                    //fiveStep(700,"10.30.25.29",(short)3);
                   // LOGGER.info("------------------------------------fiveStep3--------------------------------------------------------");
                    //fiveStep(700,"10.30.25.29",(short)4);
                   // LOGGER.info("------------------------------------fiveStep4--------------------------------------------------------");
                    // fiveStep(700,"10.30.25.29",(short)5);
                   // LOGGER.info("------------------------------------fiveStep5--------------------------------------------------------");
                    //fiveStep(700,"10.30.25.29",(short)6);
                   // LOGGER.info("------------------------------------fiveStep6--------------------------------------------------------");
                }
            }.start();
*/

        /*
        PointerByReference  send = new PointerByReference(Pointer.NULL);
        IntByReference len = new IntByReference();
        int num = clou102.INSTANCE.pack_clou102(sClou102Pack,send,len);
        byte[] bytes =send.getValue().getByteArray(0,13);
        String str = send.getValue().getString(0);
        LOGGER.info(str);
        LOGGER.info(len.getValue());
        LOGGER.info(num);



        }*/


    }

