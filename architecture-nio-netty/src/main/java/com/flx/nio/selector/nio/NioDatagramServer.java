package com.flx.nio.selector.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/1 19:24
 * @Description: UDP格式的Socket操作
 */
public class NioDatagramServer {

    public final static int port = 9999;

    public static void main(String[] args) {

        try {
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            datagramChannel.bind(new InetSocketAddress(NioDatagramServer.port));
            Selector selector = Selector.open();
            datagramChannel.register(selector, SelectionKey.OP_READ);
            //select会阻塞，知道有就绪的事件
            while (selector.select()>0){
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()){
                    SelectionKey selectionKey = it.next();
                    if(selectionKey.isReadable()){
                        ByteBuffer bf = ByteBuffer.allocate(1024);
                        datagramChannel.receive(bf);
                        bf.flip();
                        System.out.println(new String(bf.array(),0,bf.limit()));
                        bf.clear();
                    }
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
