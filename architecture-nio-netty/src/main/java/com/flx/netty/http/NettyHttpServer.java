package com.flx.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/7 16:09
 * @Description: Netty进行http开发
 */
public class NettyHttpServer {

    public static void main(String[] args) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(8);

        try{

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyHttpServerInitializer())//自定义通道处理器
                    .bind(9999)
                    .sync();
            channelFuture.addListener((ChannelFutureListener) future -> {
                if(future.isSuccess()){
                    System.out.println("Server start successful !");
                }else {
                    System.out.println("Server start error!");
                }
            });
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

}
