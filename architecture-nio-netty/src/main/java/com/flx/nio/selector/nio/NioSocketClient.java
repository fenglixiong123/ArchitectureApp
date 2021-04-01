package com.flx.nio.selector.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

import static com.flx.utils.FileUtils.close;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/1 16:02
 * @Description:
 */
public class NioSocketClient {

    public static void main(String[] args) {

        SocketChannel socketChannel = null;

        try {
            //1.获取通道
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",NioSocketServer.port));
            //2.切换成非阻塞模式
            socketChannel.configureBlocking(false);
            //3.分配缓冲区
            ByteBuffer bf = ByteBuffer.allocate(1024);
            //4.发送数据给服务端
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                bf.put((new Date().toString()+"\n"+scanner.next()).getBytes());
                bf.flip();
                socketChannel.write(bf);
                bf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(socketChannel);
        }

    }

}
