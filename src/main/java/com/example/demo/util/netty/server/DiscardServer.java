package com.example.demo.util.netty.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/*Netty的架构使用了非常复杂的主从式Reactor线程模型。简单的说就是。
父线程组（代码中的parentBosser）担任（acceptor）的角色。负责接收客户端的连接请求，处理完成请求，
创建一个Channel并注册到子线程组（代码中的childWorker）中的某个线程上面，然后这个线程将负责Channel的读写，
编解码等操作。
 */
public class DiscardServer {
    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("准备运行端口：" + port);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b = b.group(bossGroup, workerGroup)                 //设置它的线程组
                    .channel(NioServerSocketChannel.class)      //设置Channel类型
                    .option(ChannelOption.SO_BACKLOG, 128)   // 设置Channel选项配置   在Netty 以前的版本中都是以字符串来配置的。4.x版本发布之后统一修改为使用ChannelOption类来实现配置。
                    .childHandler(new ChildChannelHandler());   //设置责任链路
            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();    // 绑定并设置监听端口。
            //等待服务监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //退出，释放线程资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        new DiscardServer().run(6666);
    }
}