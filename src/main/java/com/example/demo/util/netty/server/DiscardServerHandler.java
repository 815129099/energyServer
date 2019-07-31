package com.example.demo.util.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelHandlerAdapter {

    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("--------------------------------handler channelActive------------");

//		for(int i = 0; i<10; i++){
//			SubscribeReq req = new SubscribeReq();
//			req.setAddress("深圳JJYY");
//			req.setPhoneNumber("13888886666");
//			req.setProductName("Netty Book");
//			req.setSubReqID(i);
//			req.setUserName("XXYY");
//			ctx.write(req);
//		}
//		ctx.flush();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        try {
            ByteBuf in = (ByteBuf) msg;
            System.out.println("传输内容是");
            System.out.println(in.toString(CharsetUtil.UTF_8));
            ByteBuf resp= Unpooled.copiedBuffer("收到信息$".getBytes());
            ctx.writeAndFlush(resp);
        }  finally {
            ReferenceCountUtil.release(msg);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常就关闭
        cause.printStackTrace();
        ctx.close();
    }

}