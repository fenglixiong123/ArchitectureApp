package com.flx.netty.cases.webchat;

import com.flx.netty.cases.webchat.handler.WeChatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/8 18:54
 * @Description:
 */
public class WebChatClient {

    public static void main(String[] args) throws InterruptedException {

        new WebChatClient("127.0.0.1",9001).run();

    }

    private final String host;
    private final int port;

    public WebChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {

        EventLoopGroup clientGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture future = bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入业务处理
                            pipeline.addLast(new WeChatClientHandler());
                        }
                    })
                    .connect(host, port).sync();
            Channel channel = future.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                //从控制台获取消息
                String message = scanner.nextLine();
                System.out.println(message);
                //通过Channel发送
                channel.writeAndFlush(message+"\r\n");
            }
        }finally {
            clientGroup.shutdownGracefully();
            System.out.println("Client shutdown!");
        }
    }

}
