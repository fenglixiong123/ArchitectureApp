package com.flx.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;


/**
 * @Author: Fenglixiong
 * @Date: 2021/4/6 21:30
 * @Description: 我们自定义一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端的数据
     * @param ctx 上下文对象，含有管道pipeline,通道channel，地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将msg转换成一个ByteBuf
        //ByteBuf是netty提供的
        ByteBuf bf = (ByteBuf)msg;
        System.out.println("From Client address : "+ctx.channel().remoteAddress());
        System.out.println("From Client message : "+bf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕,回复客户端消息
     * @param ctx 上下文对象
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓冲区，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Client~", StandardCharsets.UTF_8));
    }

    /**
     * 处理异常，一般是要关闭通道
     * @param ctx 上下文对象
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
