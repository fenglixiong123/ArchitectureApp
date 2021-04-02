package com.flx.netty;

/**
 * @Author Fenglixiong
 * @Create 2021/3/23 2:04
 * @Description JBOSS提供的Java开源框架，目前在GitHub上面维护
 *
 * 介绍？
 *
 * Netty是一个异步的，基于事件驱动的网络应用框架，用于快速开发高性能，高可靠性网络IO程序
 * Netty主要针对TCP协议下，面向Clients段的高并发应用，或者Peer-to-Peer场景下的大量数据持续传输的应用
 * Netty本质是一个NIO框架，适用于服务器通讯相关的多重应用场景
 *
 * why use nio?
 *
 * 原生NIO繁杂，需要具备多重技能，开发工作量大，处理异常，网络拥堵，重连等麻烦，Bug难以解决等
 *
 * 应用场景
 *
 * 互联网RPC框架--->Dubbo
 * 游戏行业、地图服务、大数据Hadoop
 *
 * 传统IO模式一个请求对应一个线程，而且是阻塞式的，如果大量线程访问，会导致系统性能下降
 *
 * Reactor模式
 * 1.基于IO多路复用：多个连接共用一个阻塞对象
 * 2.基于线程池复用线程资源：不必为每个连接创建线程，连接后将任务交给work线程池处理
 *
 * 核心组成：
 * 1.Reactor：负责监听和分发事件
 * 2.Handlers：实际处理程序执行IO事件
 *
 * 常见模型
 *
 * 1.Reactor单线程模型       容易阻塞
 * 2.Reactor多线程模型       高并发场景性能瓶颈
 * 3.Reactor主从多线程模型    完美解决
 *
 * 主从多线程模型
 *
 * >>>>>>原理
 *
 * 一个MainReactor可以维护多个SubReactor
 *
 * 1）Reactor主线程MainReactor对象通过select监听连接事件，收到事件后，通过Accept处理连接事件
 * 2）当Acceptor处理完连接事件后，MainReactor将连接分配给分配给SubReactor
 * 3）SubReactor将连接加入到连接队列进行监听，并创建handler进行各种事件处理
 * 4）当有新事件发生时，SubReactor就会调用对应的handler处理
 * 5）handler通过read读取数据，分发给后面的worker线程处理
 * 6）worker线程池分配独立的worker线程进行业务处理
 * 7）handler收到响应后，在通过send将结果返回给client
 *
 * >>>>>优缺点
 *
 * 优点：父线程和子线程的数据交互简单职责明确，父线程只需要接收新连接，子线程完成后续业务处理
 *      父线程只需要把新连接传给子线程，子线程无需返回数据
 * 缺点：变成复杂度高
 *
 **/
public class NettyApi {

    /**
     * 主从Reactor简单模型
     *
     * BossGroup--->selector--->accept--->(SocketChannel--->NIOSocketChannel)--->registor to WorkerGroup
     * WorkerGroup--->selector--->read--->handler
     *
     * 1）BossGroup线程维护Selector只关注Accept
     * 2）当接收到Accept事件，获取到对应的SocketChannel，封装成NIOSocketChannel并注册到WorkerGroup线程（事件循环），并进行维护
     * 3）当WorkerGroup线程监听到selector中通道发生自己感兴趣的事件后，就会交给Handler进行处理，handler已经加入通道
     *
     */
    public void simpleReactor(){

    }

    /**
     * 主从Reactor详细模型
     *
     * 1）Netty抽象出两组线程池
     *  BossGroup专门负责接收客户端的连接
     *  WorkerGroup专门负责网络的读写
     * 2）BossGroup和WorkerGroup类型都是NioEventLoopGroup
     * 3）NioEventLoopGroup相当于一个事件循环组，这个组中含有多个事件循环，每一个事件循环是NioEventLoop
     * 4）NioEventLoop表示一个不断循环的执行处理任务的线程，每个NioEventLoop都有一个selector，用于监听绑定在其上面的socket网络通信
     * 5）NioEventLoopGroup可以有多个线程，即可以含有多个NioEventLoop
     * 6）每个Boss NioEventLoop循环执行的步骤：
     *      1.轮询accept事件
     *      2.处理accept事件，与client简历连接，生成NioSocketChannel，并将其注册到worker NIOEventLoop上的selector
     *      3.处理任务队列的任务，即runAllTasks
     * 7）每个Worker NioEventLoop循环执行的步骤
     *      1.轮询read，write事件
     *      2.处理io事件，即read，write事件，在对应的NioSocketChannel上处理
     *      3.处理任务队列的任务，即runAllTasks
     * 8)每个Worker NIOEventLoop处理业务时候会使用Pipeline管道，pipeline中包含了channel，即通过pipeline可以获取到对应通道，pipeline中维护了很多处理器
     */
    public void complexReactor(){

    }

}
