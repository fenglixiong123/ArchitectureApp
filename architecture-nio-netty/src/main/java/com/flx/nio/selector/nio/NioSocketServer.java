package com.flx.nio.selector.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static com.flx.utils.FileUtils.close;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/1 16:02
 * @Description:
 */
public class NioSocketServer {

    public final static int port = 8888;

    public static void main(String[] args) {

        ServerSocketChannel serverChannel = null;
        SocketChannel socketChannel = null;

        try {
            //获取通道
            serverChannel = ServerSocketChannel.open();
            //切换非阻塞模式
            serverChannel.configureBlocking(false);
            //绑定连接
            serverChannel.bind(new InetSocketAddress(NioSocketServer.port));
            //获取选择器
            Selector selector = Selector.open();
            //将通道注册到选择器上面,并指定监听事件
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            //轮询式的获取选择器上面已经准备就绪的事件
            //select()方法返回的int值表示有多少通道已经就绪
            //select会阻塞，知道有就绪的事件
            while (selector.select()>0){
                //获取当前选择器中所有注册的选择键（已经就绪的监听事件）
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()){
                    //获取准备就绪的事件
                    SelectionKey selectionKey = it.next();
                    if(selectionKey.isAcceptable()){
                        //获取客户端通道
                        socketChannel = serverChannel.accept();
                        //切换非阻塞模式
                        socketChannel.configureBlocking(false);
                        //将通道注册到Selector
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }else if(selectionKey.isReadable()){
                        //获取当前选择器上读就绪状态的通道
                        SocketChannel s = (SocketChannel) selectionKey.channel();
                        //读取数据
                        ByteBuffer bf = ByteBuffer.allocate(1024);
                        while (s.read(bf)>0){
                            bf.flip();
                            System.out.println("Server receiver : "+new String(bf.array(),0,bf.limit()));
                            bf.clear();
                        }
                    }
                    //取消选择键
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(serverChannel);
            close(socketChannel);
        }

    }

}
