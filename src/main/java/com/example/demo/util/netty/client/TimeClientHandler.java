package com.example.demo.util.netty.client;

import com.example.demo.util.jna.util.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private byte[] req;
    public TimeClientHandler(String n){
      //  req= HexUtil.toBytes("000000000006"+n+"030C840004");
        req = HexUtil.toBytes(n);
       // LOGGER.info("req:"+n);
    }


    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive---------当前线程名："+Thread.currentThread().getName()+",当前时间："+System.currentTimeMillis());
                        ByteBuf message=null;
                        message = Unpooled.buffer(req.length);
                        message.writeBytes(req);
                        ctx.writeAndFlush(message);
                    //ctx.close();

        /*
        ByteBuf message=null;
        message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message); */
    }

    //消息在管道中都是以ChannelHandlerContext的形势传递的

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //LOGGER.info(msg.toString()+"123123");
            ByteBuf in = (ByteBuf) msg;
            //LOGGER.info(in.readableBytes());
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            /*
            int i=-1;
            LOGGER.info(in.toString(io.netty.util.CharsetUtil.US_ASCII));
             while (in.isReadable()){
                byte b = in.readByte();
                i++;
                bytes[i] = b;
             }*/
             logger.info(HexUtil.bytesToHexFun3(bytes)+"当前线程名："+Thread.currentThread().getName()+",当前时间："+System.currentTimeMillis());
             TimeClient.str = HexUtil.bytesToHexFun1(bytes);
            /*LOGGER.info(HexUtil.bytesToHexFun1(bytes));
             if (i==19){
                 InsertUtil.insertD(bytes);
             }*/
        }  finally {
            ReferenceCountUtil.release(msg);
        }
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常就关闭
        cause.printStackTrace();
        ctx.close();
    }


    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception{
        logger.info("当前线程名："+Thread.currentThread().getName()+",当前时间："+System.currentTimeMillis()+"--------handler channelReadComplete-------");
        ctx.flush();
        ctx.close();
    }

}