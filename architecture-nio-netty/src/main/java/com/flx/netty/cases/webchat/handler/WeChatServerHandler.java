package com.flx.netty.cases.webchat.handler;

import com.flx.utils.DateUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: Fenglixiong
 * @Date: 2021/4/8 17:12
 * @Description: 群聊系统服务器端处理器 <String>处理字符串数据
 */
public class WeChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 私聊实现,先登录，将用户信息作为key
     * 使用hashMap管理
     */
    public static Map<String,Channel> channelMap = new HashMap<>();

    /**
     * 定义一个Channel组，管理所有的Channel
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 表示连接建立，一旦连接，第一个被执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天信息推送给其他在线的客户端
        //该方法会遍历所有的channel，并发送消息
        channelGroup.writeAndFlush(DateUtils.now()+" [用户]"+channel.remoteAddress()+" 加入聊天~\n");
        channelGroup.add(channel);
    }

    /**
     * 当该连接分配到具体的worker线程后，该回调会被调用。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    /**
     * 表示channel处于活动状态,提示上线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(DateUtils.now()+" [用户]"+ctx.channel().remoteAddress()+" 上线~");
    }

    /**
     * 服务器读取客户端发来的消息，并转发到指定客户
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel selfChannel = ctx.channel();
        //群发消息，遍历channel，自己发送的消息不能发送给自己
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtils.now()).append(" 在线[").append(channelGroup.size()).append("]")
                .append(" [用户]").append(selfChannel.remoteAddress())
                .append(" 发送了消息：").append("\n")
                .append(msg).append("\n");
        System.out.print(sb.toString());
        channelGroup.forEach(e->{
            //如果是自己发送的就推送给自己其他消息
            if(selfChannel==e){
                String words = DateUtils.now() + " [自己] 发送了消息：\n" + msg + "\n";
                e.writeAndFlush(words);
            }else {
                e.writeAndFlush(sb.toString());
            }
        });
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 表示channel处于非活动状态，提示下线
     * 当连接断开时，该回调会被调用，说明这时候底层的TCP连接已经被断开了。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(DateUtils.now()+" [用户]"+ctx.channel().remoteAddress()+" 离线~");
    }

    /**
     * 当连接关闭后，释放绑定的worker线程；
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * 表示连接断开
     * 会自动从channelGroup中删除下线的人
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(DateUtils.now()+" [用户]"+channel.remoteAddress()+" 离开聊天\n");
    }
}
