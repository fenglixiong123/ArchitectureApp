package com.flx.netty.base;

import com.flx.netty.base.handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/6 18:35
 * @Description:
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

        //创建客户端循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(bossGroup)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端通道实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());//加入处理器
                        }
                    });
            System.out.println("Client is ready...");
            //启动客户端,并连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            System.out.println("Client shutdown !");
        }
    }

}
