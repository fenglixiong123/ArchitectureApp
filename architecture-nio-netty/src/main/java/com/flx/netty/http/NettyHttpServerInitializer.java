package com.flx.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/7 16:14
 * @Description: 给我们的workGroup的eventLoop对应的管道设置处理器
 */
public class NettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入编码解码器
        //Netty提供的处理http的编码解码器
        pipeline.addLast("MyHttpServerCode",new HttpServerCodec());
        //增加自定义处理器
        pipeline.addLast("MyNettyHttpServerHandler",new NettyHttpServerHandler());

        System.out.println("initChannel ok ~");

    }

}
