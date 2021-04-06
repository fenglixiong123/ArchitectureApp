package com.flx.netty.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/6 18:35
 * @Description: Netty服务网器端
 * bossGroup和WorkGroup含有子线程数量，默认cpu核数*2
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup,默认创建了2*cpu个子线程
        //只处理连接请求，真正和客户端的业务处理会交给workGroup
        //无线循环
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //创建workGroup
        //处理客户端的业务请求
        EventLoopGroup workGroup = new NioEventLoopGroup(8);
        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用链式变成来设置
            serverBootstrap.group(bossGroup, workGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });//给我们的workGroup的eventLoop对应的管道设置处理器
            System.out.println("Server is ready...");
            //启动服务器并绑定端口
            //绑定一个端口,并且同步，生成一个ChannelFuture对象
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            System.out.println("Server shutdown !");
        }
    }

}
