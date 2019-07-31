package com.example.demo.util.netty.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TimeClient {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String str;

    public String connect(int port,String host,String n)throws Exception{
        //配置客户端
        //LOGGER.info(port+","+host+n);
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try {
            Bootstrap b=new Bootstrap();
            b.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler(n));
                        }
                    });

            //绑定端口，同步等待成功
            ChannelFuture f = b.connect(host,port).sync();
            Channel channel = f.channel();
            channel.read();

            //等待服务监听端口关闭
            f.channel().closeFuture().sync();
        }finally {
            //优雅退出，释放线程资源
            eventLoopGroup.shutdownGracefully();
        }
        return str;
    }

    public static void main(String[] args)throws Exception{
        /*
        while (true){
            new TimeClient().connect(502,"192.168.4.150");
            LOGGER.info("------------------------------TCP连接已断开,60分钟后自动连接------------------------------------");
            Thread.sleep(1000*60*60);
        }*/
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
                            new TimeClient().connect(Integer.parseInt(str[1]), str[0], str[2]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
       // new TimeClient().connect(502,"192.168.4.150");
    }
}