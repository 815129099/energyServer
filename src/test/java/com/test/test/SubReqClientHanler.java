package com.test.test;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHanler extends ChannelHandlerAdapter{

    public SubReqClientHanler(){

    }


    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        System.out.println("----------------handler channelActive-----准备发送十个数据-------");

        for(int i = 2; i<13; i++){
            char c ='2';
            /*
//			ctx.write(subReq(i));
            SubscribeReq req = new SubscribeReq();
            req.setAddress(Long.toString(System.currentTimeMillis()));
            req.setPhoneNumber("13888886666");
            req.setProductName("Netty Book");
            req.setSubReqID(i);
            req.setUserName("XXYY");
            ctx.writeAndFlush(req);
            Thread.sleep(1000);*/
            String str = "";
            if(i==10){
                c = 'A';
                str = "0000000000060"+c+"030C840004";
            }else if(i==11){
                c = 'B';
                str = "0000000000060"+c+"030C840004";
            }else if(i==12){
                c = 'C';
                str = "0000000000060"+c+"030C840004";
            }else if(i<10){
                str = "0000000000060"+i+"030C840004";
            }
            System.out.println(str+","+System.currentTimeMillis());
            SubscribeReq req = new SubscribeReq();
            req.setAddress(Long.toString(System.currentTimeMillis()));
            req.setPhoneNumber(str);
            req.setProductName("Netty Book");
            req.setSubReqID(i);
            req.setUserName("XXYY");
            ctx.writeAndFlush(req);
            // req = HexUtil.toBytes(str);
            // message= Unpooled.buffer(req.length);
            // message.writeBytes(req);
            //ctx.writeAndFlush(str);
            Thread.sleep(1000);
        }
    }


    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception{
        System.out.println("--------channelRead---服务器发来的数据为：[" + msg.toString() + "]");
    }


    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception{
        System.out.println("----------------handler channelReadComplete");
        ctx.flush();
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("--------------------------------------------------------------------------handler exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

}