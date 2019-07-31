package com.example.demo.util.jna.tcp;

import com.example.demo.mapper.util.UtilDao;
import com.example.demo.util.jna.util.HexUtil;
import com.example.demo.util.shiro.SpringBeanFactoryUtils;
import com.sun.jna.NativeLong;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private NativeLong meterNum;
    private int ErtuID;
    private int EMeterID;

    private byte[] req;
    private String flag;
    private int step = 1;
    private byte[] tm;
    @Autowired
    private testPck testPck;
    @Autowired
    private UtilDao utilDao;

    public ClientHandler(int ErtuID, byte[] tm,int type) {
        this.ErtuID = ErtuID;
        this.tm = tm;
        this.type = type;
    }
    public ClientHandler(int ErtuID, byte[] tm) {
        this.ErtuID = ErtuID;
        this.tm = tm;
    }

    public ClientHandler(int ErtuID, byte[] tm,int type,List<Map> list) {
        this.ErtuID = ErtuID;
        this.tm = tm;
        this.type = type;
        this.mapList = list;
    }
    /*列表
        EMeterNum
        EMeterID
     */
    private List<Map> mapList;
    //电表列表索引
    private int index = 0;
    //0为查总电量   1为查瞬时量、总电量  2为查瞬时量
    private int type = 1;
    boolean isAll = false;
    List<Byte> byteList = new ArrayList<>();
    //private final AttributeKey<Integer> clientKey = AttributeKey.valueOf("ErtuID");


    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //logger.info(ctx.channel().toString()+",11-."+ctx.name()+",22-."+ctx.channel()+",33-."+ctx.pipeline());
        logger.info("channelActive---------当前线程名：" + Thread.currentThread().getName() + ",当前时间：" + System.currentTimeMillis());
        logger.warn("当前正在采集" + ErtuID + "号采集器");

        this.testPck = SpringBeanFactoryUtils.getBean(testPck.class);
        this.utilDao = SpringBeanFactoryUtils.getBean(UtilDao.class);
        if(null==mapList || mapList.size()==0 ){
            mapList = this.utilDao.getEMeterNumByErtuID(ErtuID);
        }
        if (step == 1) {
            flag = testPck.oneStepPack();
            if (!flag.equals("error")) {
                req = HexUtil.toBytes(flag);
                ByteBuf message = null;
                message = Unpooled.buffer(req.length);
                message.writeBytes(req);
                ctx.writeAndFlush(message);
            }
        }
        /*
        ByteBuf message=null;
        message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message); */
    }

    //消息在管道中都是以ChannelHandlerContext的形势传递的
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            isAll = false;
            ByteBuf in = (ByteBuf) msg;
            byte[] bytes1 = new byte[in.readableBytes()];
            in.readBytes(bytes1);
            if(HexUtil.bytesToHexFun4(bytes1[bytes1.length-1]).equals("16")){
                isAll = true;
                byte[] bytes;
                if(!(byteList==null) || !(byteList.size()==0)){
                    for (int i=0;i<bytes1.length;i++){
                        byteList.add(bytes1[i]);
                    }
                    bytes = new byte[byteList.size()];
                    for (int i=0;i<byteList.size();i++) {
                        bytes[i] = byteList.get(i);
                    }
                }else {
                    bytes = bytes1;
                }
                byteList.clear();

                logger.info(HexUtil.bytesToHexFun3(bytes) + "当前线程名：" + Thread.currentThread().getName() + ",当前时间：" + System.currentTimeMillis());
            /*
            int i=-1;
            LOGGER.info(in.toString(io.netty.util.CharsetUtil.US_ASCII));
             while (in.isReadable()){
                byte b = in.readByte();
                i++;
                bytes[i] = b;
             }*/
                switch (step) {
                    case 1:
                        logger.info("------------------------" + step + "---------------------");
                        flag = HexUtil.bytesToHexFun1(bytes);
                        if (!testPck.oneStepUnPack(flag)) {
                            ctx.close();
                        } else {
                            step = 2;
                            flag = testPck.twoStepPack();
                            if (!flag.equals("error")) {
                                req = HexUtil.toBytes(flag);
                                ByteBuf message = null;
                                message = Unpooled.buffer(req.length);
                                message.writeBytes(req);
                                ctx.writeAndFlush(message);
                            } else {
                                ctx.close();
                            }
                        }

                        break;
                    case 2:
                        logger.info("------------------------" + step + "---------------------");
                        flag = HexUtil.bytesToHexFun1(bytes);
                        if (!testPck.twoStepUnPack(flag)) {
                            ctx.close();
                        } else {
                            if (type == 0) {
                                step = 6;
                            } else {
                                step = 9;
                            }
                            //如果该采集器下有电表，则获取meterNum、EMeterID
                            if (index <= mapList.size() - 1) {
                                meterNum = new NativeLong(Long.parseLong(mapList.get(index).get("EMeterNum").toString()));
                                EMeterID = Integer.parseInt(mapList.get(index).get("EMeterID").toString());
                            } else {
                                logger.warn(ErtuID + "号采集器下没有可采数据的电表");
                                ctx.close();
                            }

                            if (type == 0) {
                                flag = testPck.sixStepPack(tm, meterNum);
                            } else {
                                flag = testPck.nineStepPack(tm, meterNum);
                            }

                            if (!flag.equals("error")) {
                                req = HexUtil.toBytes(flag);
                                ByteBuf message = null;
                                message = Unpooled.buffer(req.length);
                                message.writeBytes(req);
                                ctx.writeAndFlush(message);
                            } else {
                                ctx.close();
                            }
                        }
                        break;
                    case 6:
                        logger.info("------------------------" + step + "," + meterNum + "---------------------");
                        flag = HexUtil.bytesToHexFun1(bytes);
                        int n = testPck.sixStepUnPack(meterNum, ErtuID, EMeterID, flag);
                        if (n == 3) {
                            //解析出现异常
                            ctx.close();
                        } else if (n == 2) {
                            //表明该电表下没有数据
                            logger.warn("------------------------" + meterNum + "没有总电量数据---------------------");
                            index++;
                            if (index <= mapList.size() - 1) {
                                step = 6;
                                meterNum = new NativeLong(Long.parseLong(mapList.get(index).get("EMeterNum").toString()));
                                EMeterID = Integer.parseInt(mapList.get(index).get("EMeterID").toString());
                                flag = testPck.sixStepPack(tm, meterNum);
                                if (!flag.equals("error")) {
                                    req = HexUtil.toBytes(flag);
                                    ByteBuf message = null;
                                    message = Unpooled.buffer(req.length);
                                    message.writeBytes(req);
                                    ctx.writeAndFlush(message);
                                } else {
                                    ctx.close();
                                }
                            } else {
                                logger.warn(ErtuID + "号采集器总电量已采集完毕，共" + index + "个电表");
                                //开始采集瞬时量
                                ctx.close();
                            }
                        } else {
                            //有后续数据
                            step = 5;
                            flag = testPck.fiveStepPack();
                            if (!flag.equals("error")) {
                                req = HexUtil.toBytes(flag);
                                ByteBuf message = null;
                                message = Unpooled.buffer(req.length);
                                message.writeBytes(req);
                                ctx.writeAndFlush(message);
                            } else {
                                ctx.close();
                            }
                        }
                        break;

                    case 5:
                        logger.info("------------------------" + step + "---------------------");
                        flag = HexUtil.bytesToHexFun1(bytes);
                        boolean success = false;
                        if (type == 0) {
                            success = testPck.fiveStepUnPack(meterNum, ErtuID, EMeterID, flag, 0);
                        } else {
                            success = testPck.fiveStepUnPack(meterNum, ErtuID, EMeterID, flag, 1);
                        }
                        //if true 有后续数据
                        if (success) {
                            flag = testPck.fiveStepPack();
                            if (!flag.equals("error")) {
                                req = HexUtil.toBytes(flag);
                                ByteBuf message = null;
                                message = Unpooled.buffer(req.length);
                                message.writeBytes(req);
                                ctx.writeAndFlush(message);
                            } else {
                                ctx.close();
                            }
                        } else {
                            if (type == 0) {
                                logger.warn("-----------------------------" + meterNum + "号电表总电量数据采集完毕---------------------------");
                            } else {
                                logger.warn("-----------------------------" + meterNum + "号电表瞬时量数据采集完毕---------------------------");
                            }
                            index++;
                            //如果该采集器下有电表，则获取meterNum、EMeterID
                            if (index <= mapList.size() - 1) {
                                meterNum = new NativeLong(Long.parseLong(mapList.get(index).get("EMeterNum").toString()));
                                EMeterID = Integer.parseInt(mapList.get(index).get("EMeterID").toString());
                                //如果type=0为取总电量数据  为1取瞬时量数据
                                if (type == 0) {
                                    step = 6;
                                    flag = testPck.sixStepPack(tm, meterNum);
                                } else {
                                    step = 9;
                                    flag = testPck.nineStepPack(tm, meterNum);
                                }
                                if (!flag.equals("error")) {
                                    req = HexUtil.toBytes(flag);
                                    ByteBuf message = null;
                                    message = Unpooled.buffer(req.length);
                                    message.writeBytes(req);
                                    ctx.writeAndFlush(message);
                                } else {
                                    ctx.close();
                                }
                            } else {
                                if (type == 1) {
                                    logger.warn(ErtuID + "号采集器瞬时量数据已采集完毕，共" + index + "个电表");
                                    type = 0;
                                    index = 0;
                                    step = 6;
                                    //开始查瞬时量
                                    meterNum = new NativeLong(Long.parseLong(mapList.get(index).get("EMeterNum").toString()));
                                    EMeterID = Integer.parseInt(mapList.get(index).get("EMeterID").toString());
                                    logger.warn("电表："+meterNum+",ID:"+EMeterID);
                                    flag = testPck.sixStepPack(tm, meterNum);
                                    if (!flag.equals("error")) {
                                        req = HexUtil.toBytes(flag);
                                        ByteBuf message = null;
                                        message = Unpooled.buffer(req.length);
                                        message.writeBytes(req);
                                        ctx.writeAndFlush(message);
                                    } else {
                                        ctx.close();
                                    }
                                } else if(type==2){
                                    logger.warn(ErtuID + "号采集器瞬时量电量数据已采集完毕，共" + index + "个电表");
                                    ctx.close();
                                }else if(type==0){
                                    logger.warn(ErtuID + "号采集器总电量数据已采集完毕，共" + index + "个电表");
                                    ctx.close();
                                }
                               // ctx.close();
                            }
                        }
                        break;

                    case 9:
                        logger.info("------------------------" + step + "," + meterNum + "---------------------");
                        flag = HexUtil.bytesToHexFun1(bytes);
                        int n1 = testPck.nineStepUnPack(meterNum, ErtuID, EMeterID, flag);
                        if (n1 == 3) {
                            ctx.close();
                        } else if (n1 == 2) {
                            logger.warn("------------------------" + meterNum + "没有瞬时量数据---------------------");
                            index++;
                            if (index <= mapList.size() - 1) {
                                step = 9;
                                meterNum = new NativeLong(Long.parseLong(mapList.get(index).get("EMeterNum").toString()));
                                EMeterID = Integer.parseInt(mapList.get(index).get("EMeterID").toString());
                                logger.warn("电表："+meterNum+",ID:"+EMeterID);
                                flag = testPck.nineStepPack(tm, meterNum);
                                if (!flag.equals("error")) {
                                    req = HexUtil.toBytes(flag);
                                    ByteBuf message = null;
                                    message = Unpooled.buffer(req.length);
                                    message.writeBytes(req);
                                    ctx.writeAndFlush(message);
                                } else {
                                    ctx.close();
                                }
                            } else {
                                logger.warn(ErtuID + "号采集器瞬时量数据已采集完毕，共" + index + "个电表");
                                if(type==1){
                                    type = 0;
                                    index = 0;
                                    step = 6;
                                    //开始查瞬时量
                                    meterNum = new NativeLong(Long.parseLong(mapList.get(index).get("EMeterNum").toString()));
                                    EMeterID = Integer.parseInt(mapList.get(index).get("EMeterID").toString());
                                    logger.warn("电表："+meterNum+",ID:"+EMeterID);
                                    flag = testPck.sixStepPack(tm, meterNum);
                                    if (!flag.equals("error")) {
                                        req = HexUtil.toBytes(flag);
                                        ByteBuf message = null;
                                        message = Unpooled.buffer(req.length);
                                        message.writeBytes(req);
                                        ctx.writeAndFlush(message);
                                    } else {
                                        ctx.close();
                                    }
                                }else if(type==2){
                                    ctx.close();
                                }

                            }

                        } else {
                            step = 5;
                            flag = testPck.fiveStepPack();
                            if (!flag.equals("error")) {
                                req = HexUtil.toBytes(flag);
                                ByteBuf message = null;
                                message = Unpooled.buffer(req.length);
                                message.writeBytes(req);
                                ctx.writeAndFlush(message);
                            } else {
                                ctx.close();
                            }
                        }
                        break;
                }
            /*LOGGER.info(HexUtil.bytesToHexFun1(bytes));
             if (i==19){
                 InsertUtil.insertD(bytes);
             }*/
            }
            else {
                System.out.println("用户帧长错误！");
                for (int i = 0;i<bytes1.length;i++){
                    byteList.add(bytes1[i]);
                }
            }

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常就关闭
        cause.printStackTrace();
        ctx.close();
    }

    public int hostSplit(String str) {
        String[] strings = str.split("R:/");
        String host = strings[1].split(":")[0];
        int id = 2;
        System.out.println(host);
        switch (host) {
            case "10.30.23.44":
                id = 2;
                break;
            case "10.30.34.240":
                id = 10;
                break;
        }
        return id;
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("当前线程名：" + Thread.currentThread().getName() + ",当前时间：" + System.currentTimeMillis() + "--------handler channelReadComplete-------"+isAll);
        ctx.flush();
        if(!isAll){
            ctx.read();
        }

    }

    public NativeLong getMeterNum() {
        return meterNum;
    }

    public void setMeterNum(NativeLong meterNum) {
        this.meterNum = meterNum;
    }

    public int getErtuID() {
        return ErtuID;
    }

    public void setErtuID(int ertuID) {
        ErtuID = ertuID;
    }

    public int getEMeterID() {
        return EMeterID;
    }

    public void setEMeterID(int EMeterID) {
        this.EMeterID = EMeterID;
    }

    public byte[] getTm() {
        return tm;
    }

    public void setTm(byte[] tm) {
        this.tm = tm;
    }

}