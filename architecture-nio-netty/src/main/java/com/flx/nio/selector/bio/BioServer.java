package com.flx.nio.selector.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.flx.utils.FileUtils.close;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/1 13:24
 * @Description: 一个阻塞通信的案例
 */
public class BioServer {

    public static void main(String[] args) {

        ServerSocketChannel serverChannel = null;
        SocketChannel socketChannel = null;
        FileChannel fileChannel = null;
        try {
            //1.获取通道
            serverChannel = ServerSocketChannel.open();
            //2.绑定连接
            serverChannel.bind(new InetSocketAddress(8888));
            System.out.println("Server open on 8888:");
            //3.分配缓冲区
            ByteBuffer bf = ByteBuffer.allocate(1024);
            while (true) {
                //4.获取客户端连接的通道
                socketChannel = serverChannel.accept();
                //5.读取客户端发来的数据，保存到本地
                fileChannel = FileChannel.open(Paths.get("F:\\test\\vvv.gif"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
                while (socketChannel.read(bf) != -1) {
                    bf.flip();
                    fileChannel.write(bf);
                    bf.clear();
                }
                System.out.println("Server receive successful!");
                //6.发送反馈信息
                bf.put("服务端接收数据成功！".getBytes());
                bf.flip();
                socketChannel.write(bf);
                bf.clear();
                //7.标志发送结束
                socketChannel.shutdownOutput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(serverChannel);
            close(socketChannel);
            close(fileChannel);
        }

    }

}
