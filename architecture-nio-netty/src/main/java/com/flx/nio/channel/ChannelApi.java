package com.flx.nio.channel;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/29 15:34
 * @Description: 通道
 * 1.既可以从通道读取数据，也可以写数据到通道
 * 2.通道可以异步读写
 * 3.通道总是通过Buffer进行读写
 *
 * FileChannel 文件通道
 * DatagramChannel 能通过UDP读写网络中的数据
 * SocketChannel 能通过TCP读写网络中的数据
 * ServerSocketChannel 可以监听新进来的TCP连接，对每进来的连接都会创建一个SocketChannel
 */
public class ChannelApi {

    public static void main(String[] args) {

    }

}
