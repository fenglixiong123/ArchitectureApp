package com.flx.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/7 16:10
 * @Description: 入站事件
 *
 * 入站：相对于server来说，向server发送数据
 * 出站：相对于server来说，接收server的数据
 *
 * |--->ChannelInboundHandler 用于处理入站IO事件
 * |--->ChannelOutboundHandler 用于处理出站IO事件
 */
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     * @param ctx 通道上下文
     * @param msg 客户端发来的数据
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //判断msg是不是Http请求
        if(msg instanceof HttpRequest){

            String address = ctx.channel().remoteAddress().toString();
            HttpRequest request = (HttpRequest)msg;
            String method = request.method().name();
            String uri = request.uri();
            //拦截favicon.ico请求
            if(uri.equals("/favicon.ico")){
                return;
            }
            //每次请求使用的pipeline和handler都是不一样的
            System.out.println("pipeline.hashcode = "+ctx.pipeline().hashCode()+" handler.hashcode = "+this.hashCode());
            System.out.println("From Client ["+Thread.currentThread().getName()+"] address:["+address+"] method:["+method+"]"+" uri:["+uri+"]");
            //获取请求内容
            //String message = request.toString();
            //System.out.println("From Client ["+Thread.currentThread().getName()+"]"+":["+address+"]"+" say : ["+message+"]");

            //回复http信息给浏览器
            //1.构建response对象
            DefaultFullHttpResponse response = buildResponse("Hello,Http client~");
            //2.将构建好的response返回
            ctx.writeAndFlush(response);
        }

    }

    /**
     * 构建response对象，返回浏览器
     * @param message 返回的具体信息
     * @return response对象
     */
    private static DefaultFullHttpResponse buildResponse(String message){
        ByteBuf content = Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
        return response;
    }

}
