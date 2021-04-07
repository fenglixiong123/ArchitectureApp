package com.flx.netty.base.handler;

import com.flx.netty.base.service.ServerService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;


/**
 * @Author: Fenglixiong
 * @Date: 2021/4/6 21:30
 * @Description: 我们自定义一个handler
 *
 * 入站：相对于server来说，向server发送数据
 * 出站：相对于server来说，接收server的数据
 *
 * |--->ChannelInboundHandler 用于处理入站IO事件
 * |--->ChannelOutboundHandler 用于处理出站IO事件
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端的数据
     * @param ctx 上下文对象，
     *            1.含有管道pipeline,
     *            2.通道channel
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //简单处理消息
        //ServerService.simpleRead(ctx,msg);
        //处理耗时任务
        //ServerService.complexRead(ctx,msg);
        //异步处理耗时任务
        //ServerService.complexReadSyn(ctx,msg);
        //异步定时处理耗时任务
        ServerService.complexReadSynSchedule(ctx,msg);
        System.out.println("I have read done!");
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
