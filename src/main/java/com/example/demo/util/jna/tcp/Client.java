package com.example.demo.util.jna.tcp;


import com.example.demo.util.shiro.SpringBeanFactoryUtils;
import com.sun.jna.NativeLong;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Client {

    // @Autowired
    //  private ClientHandler clientHandler ;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void connect(int port, String host, int ErtuID, byte[] tm) throws Exception {
        //配置客户端
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientHandler(ErtuID, tm));
                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture f = b.connect(host, port).sync();
            Channel channel = f.channel();
            channel.read();
            //等待服务监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放线程资源
            eventLoopGroup.shutdownGracefully();
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void connectWithType(int port, String host, int ErtuID, byte[] tm,int type) throws Exception {
        //配置客户端
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientHandler(ErtuID,tm,type));
                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture f = b.connect(host, port).sync();
            Channel channel = f.channel();
            channel.read();
            //等待服务监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放线程资源
            eventLoopGroup.shutdownGracefully();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void connectWithType(int port, String host, int ErtuID, byte[] tm, int type, List<Map> list) throws Exception {
        //配置客户端
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientHandler(ErtuID,tm,type,list));
                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture f = b.connect(host, port).sync();
            Channel channel = f.channel();
            channel.read();
            //等待服务监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放线程资源
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        /*
        while (true){
            new TimeClient().connect(502,"192.168.4.150");
            LOGGER.info("------------------------------TCP连接已断开,60分钟后自动连接------------------------------------");
            Thread.sleep(1000*60*60);
        }
        for(int i=0;i<11;i++) {
            if(i!=2){
                String str = "192.168.4.150:502:0"+(i+2);
                new Thread(str) {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            String[] str = getName().split(":");
                            if(str[2].length()==3){
                                str[2] = str[2].substring(1);
                            }
                            new Client().connect(Integer.parseInt(str[1]), str[0], str[2]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
       // new TimeClient().connect(502,"192.168.4.150");*/
    }
}