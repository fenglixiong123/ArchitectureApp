package com.flx.nio.selector;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/29 16:49
 * @Description: 选择器
 *
 * 阻塞：
 *
 * 传统的IO都是阻塞式的。当一个线程调用read()或write()的时候，该线程被阻塞，直到有一些数据被读取或者写入，该线程在此期间不能执行其他任务。
 * 因此，在完成网络通信进行IO操作时，由于线程会阻塞，所以服务器必须为每个客户端都提供一个独立的线程进行处理，当服务器需要处理大量的客户端时候，性能会急剧下降
 *
 * 非阻塞：
 *
 * NIO是非阻塞式的。当线程从通道进行读写数据的时候，若没有数据可用时，该线程可以执行其他任务。线程通常将非阻塞式IO空闲时间用于在其他通道执行IO操作，
 * 所以单独的线程可以管理多个输入输出通道。因此NIO可以让服务端使用一个或有限几个线程来同时处理连接到服务端的所有客户端。
 *
 * Selector是非阻塞IO核心
 * 选择器Selector是SelectableChannel对象的多路复用器。
 * Selector可以同时监控多个SelectableChannel的IO状况，也就是说利用Selector可以使用一个单独的线程管理多个Channel
 *
 * 结构图
 * Selectable
 *      |--->AbstractSelectableChannel
 *              |--->SocketChannel
 *              |--->ServerSocketChannel
 *              |--->DatagramChannel
 *
 *              |--->Pipe.SinkChannel
 *              |--->Pipe.SourceChannel
 *
 * 一、使用NIO完成网络通信的三个核心
 *
 * 1.通道(Channel)：负责连接
 *
 * 2.缓冲区(Buffer)：负责数据存取
 *
 * 3.选择器(Selector)：用于监控通道的IO状况
 *
 * SelectorKey选择器监听得到事件
 *      |--->SelectionKey.OP_READ(1)//读
 *      |--->SelectionKey.OP_WRITE(4)//写
 *      |--->SelectionKey.OP_CONNECT(8)//连接
 *      |--->SelectionKey.OP_ACCEPT(16)//接收
 * 若注册时候监听不止一个事件，则可以使用位或操作符连接
 */
public class SelectorApi {

    public static void main(String[] args) {

    }

}
