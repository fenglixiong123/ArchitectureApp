package com.flx.netty.base.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/7 12:31
 * @Description:
 */
public class ServerService {

    /**
     * 简单处理任务，立马执行立马返回
     * @param ctx
     * @param msg
     */
    public static void simpleRead(ChannelHandlerContext ctx, Object msg){
        //将msg转换成一个ByteBuf
        //ByteBuf是netty提供的
        ByteBuf bf = (ByteBuf)msg;
        Channel channel = ctx.channel();
        String address = channel.remoteAddress().toString();
        String message = bf.toString(CharsetUtil.UTF_8);
        System.out.println("From Client ["+Thread.currentThread().getName()+"]"+":["+address+"]"+" say : ["+message+"]");
    }

    /**
     * 耗时任务,同样会导致客户端和服务端阻塞等待消息
     * @param ctx
     * @param msg
     */
    public static void complexRead(ChannelHandlerContext ctx, Object msg){
        simpleRead(ctx,msg);
        //执行耗时读库任务
        read_db(10);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Client~task done\n", StandardCharsets.UTF_8));
    }

    /**
     * 耗时任务
     * 会将任务提交到EventLoop的taskQueue中排队等待执行
     * @param ctx
     * @param msg
     */
    public static void complexReadSyn(ChannelHandlerContext ctx, Object msg){
        simpleRead(ctx,msg);
        //会将任务提交到任务队列中去，如果提交多个则会排队执行
        ctx.channel().eventLoop().execute(()->{
            //执行耗时读库任务
            read_db(10);
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Client~task done", StandardCharsets.UTF_8));
        });

    }

    /**
     * 耗时任务
     * 会将任务提交到EventLoop的scheduleTaskQueue中排队等待执行
     * @param ctx
     * @param msg
     */
    public static void complexReadSynSchedule(ChannelHandlerContext ctx, Object msg){
        simpleRead(ctx,msg);
        //会将任务提交到任务队列中去，如果提交多个则会排队执行
        ctx.channel().eventLoop().schedule(()->{
            //执行耗时读库任务
            read_db(10);
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Client~task done", StandardCharsets.UTF_8));
        },5,TimeUnit.SECONDS);

    }

    /**
     * 模拟耗时的读库任务
     * @param time 耗费时间
     */
    private static void read_db(long time){
        try {
            System.out.println("cpu task begin !");
            TimeUnit.SECONDS.sleep(time);
            System.out.println("cpu task end !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
