package com.flx.nio.channel;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/29 15:34
 * @Description: 通道,表示源节点与目标节点的连接。在java NIO中负责缓冲区中数据的传输。Channel本身不存储任何数据
 * 一、通道的特点
 * 1.既可以从通道读取数据，也可以写数据到通道
 * 2.通道可以异步读写
 * 3.通道总是通过Buffer进行交互读写
 *
 * 二、通道主要实现类：
 * |---FileChannel 文件通道
 * |---DatagramChannel 能通过UDP读写网络中的数据
 * |---SocketChannel 能通过TCP读写网络中的数据
 * |---ServerSocketChannel 可以监听新进来的TCP连接，对每进来的连接都会创建一个SocketChannel
 *
 * 三、获取通道：
 * 1.Java对支持通道的类提供了getChannel()方法
 * 本地IO：
 *  FileInputStream，FileOutputStream，RandomAccessFile
 * 网络IO：
 *  Socket，ServerSocket，DataGramSocket
 * 2.Jdk1.7中nio.2针对各个通道提供了静态方法open()
 *      FileChannel.open(path,option)
 * 3.Jdk1.7中nio.2的Files工具类的newByteChannel()方法
 *
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 */
public class ChannelApi {

    public static void main(String[] args) {

    }

}
