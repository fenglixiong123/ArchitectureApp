package com.flx.netty.base.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/6 22:50
 * @Description:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道就绪时候会触发该方法,发送消息
     * @param ctx 上下文
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Server~", StandardCharsets.UTF_8));
    }

    /**
     * 当通道有读取事件时触发，读取通道消息
     * @param ctx 上下文
     * @param msg 消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bf = (ByteBuf)msg;
        Channel channel = ctx.channel();
        String address = channel.remoteAddress().toString();
        String message = bf.toString(CharsetUtil.UTF_8);
        System.out.println("From Server ["+Thread.currentThread().getName()+"]"+":["+address+"]"+" say : ["+message+"]");
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
