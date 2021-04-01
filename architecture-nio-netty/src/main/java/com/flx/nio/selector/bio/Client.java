package com.flx.nio.selector.bio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

import static com.flx.utils.FileUtils.close;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/1 13:24
 * @Description: 模拟客户端发送图片数据，并接受服务端反馈信息
 */
public class Client {

    public static void main(String[] args) {

        String path = "F:\\test\\runner.gif";

        SocketChannel socketChannel = null;
        FileChannel fileChannel = null;

        try {
            //1.获取和服务端连接的通道
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
            //2.分配缓冲区
            ByteBuffer bf = ByteBuffer.allocate(1024);
            //3.读取本地文件,并发送服务端
            fileChannel = new FileInputStream(path).getChannel();
            while (fileChannel.read(bf)!=-1){
                bf.flip();
                socketChannel.write(bf);
                bf.clear();
            }
            System.out.println("send successful!");
            //4.告诉服务端发送完了
            socketChannel.shutdownOutput();
            //5.读取服务端的反馈信息
            while (socketChannel.read(bf)!=-1){
                bf.flip();
                System.out.println(new String(bf.array(),0,bf.limit()));
                bf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(socketChannel);
            close(fileChannel);
        }

    }

}
