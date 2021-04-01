package com.flx.nio.selector.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/1 19:15
 * @Description: UDP版本的客户端
 */
public class NioDatagramClient {

    public static void main(String[] args) {

        try {
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            ByteBuffer bf = ByteBuffer.allocate(1024);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String message = new Date().toString()+"\n"+scanner.next();
                bf.put(message.getBytes());
                bf.flip();
                datagramChannel.send(bf,new InetSocketAddress("127.0.0.1",NioDatagramServer.port));
                bf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
