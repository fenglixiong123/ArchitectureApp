package com.flx.netty;

import com.flx.netty.base.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author Fenglixiong
 * @Create 2021/3/23 2:04
 * @Description JBOSS提供的Java开源框架，目前在GitHub上面维护
 *
 * 一、Netty介绍？
 *
 * Netty是一个异步的，基于事件驱动的网络应用框架，用于快速开发高性能，高可靠性网络IO程序
 * Netty主要针对TCP协议下，面向Clients段的高并发应用，或者Peer-to-Peer场景下的大量数据持续传输的应用
 * Netty本质是一个NIO框架，适用于服务器通讯相关的多重应用场景
 *
 * 传统IO模式一个请求对应一个线程，而且是阻塞式的，如果大量线程访问，会导致系统性能下降
 *
 * 二、why use netty?
 *
 * 原生NIO繁杂，需要具备多重技能，开发工作量大，处理异常，网络拥堵，重连等麻烦，Bug难以解决等
 *
 * 三、应用场景
 *
 * 互联网RPC框架--->Dubbo
 * 游戏行业、地图服务、大数据Hadoop
 *
 * 四、Reactor模式
 *
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
 * 五、Netty模型
 *
 * 1.Netty抽象出两组线程池，BossGroup专门负责接收客户端的连接，WorkGroup专门负责网络读写操作
 * 2.NioEventLoop表示一个不断循环执行处理任务的线程，每个NioEventLoop都有一个selector，用于监听绑定在其上面的socket网络通道
 * 3.NioEventLoop内部采用串行化设计，从消息读取->解码->处理->编码->发送，适中由IO线程NioEventLoop负责
 *
 * |--->NioEventLoopGroup下包含多个NioEventLoop
 * |--->每个NioEventLoop中包含有一个Selector，一个taskQueue
 * |--->每个NioEventLoop中的Selector上可以注册监听多个NioChannel
 * |--->每个NioChannel只会绑定在唯一的NioEventLoop上
 * |--->每个NioChannel都绑定有一个自己的ChannelPipeline
 *
 * EventLoop工作线程：
 * 具体的工作线程(NioEventLoop)
 *
 * ChannelHandlerContext ctx
 *
 * 含有pipeline管道，channel通道
 * ctx.channel().eventLoop().execute(Runnable task) //提交任务到工作线程的任务队列中
 * ctx.channel().eventLoop().schedule(Runnable task) //提交任务到工作线程的定时任务队列中
 *
 * 六、异步模型
 *
 * 1.异步的概念和同步相对。当一个异步过程调用发出后，调用者不能立刻得到结果。实际处理这个调用的组件在完成后，通过状态、通知和回调来通知调用者
 * 2.Netty中的IO操作是异步的，包括bind，write，connect等操作会简单返回一个ChannelFuture
 * 3.调用者不能立刻获得结果，而是通过Future-Listener机制，用户可以方便的主动获取或者通过通知机制获得IO操作结果
 * 4.Netty的异步模型是简历在future和callback之上的。callback是回调，future是结果
 *
 * Future
 *
 * 表示异步执行的结果，可以通过它检测执行是否完成
 * ChannelFuture是一个接口，可以添加监听器，当监听的事情发生时候，就会通知到监听器
 * |--->isDone判断当前操作是否完成
 * |--->isSuccess判断已完成的当前操作是否成功
 * |--->getCause获取已完成的当前操作失败的原因
 * |--->isCancelled判断已经完成的当前操作是否被取消
 * |--->addListener注册监听器，当操作完成时候，将会通知指定的监听器
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
     *      2.处理accept事件，与client建立连接，生成NioSocketChannel，并将其注册到worker NIOEventLoop上的selector
     *      3.处理任务队列的任务，即runAllTasks
     * 7）每个Worker NioEventLoop循环执行的步骤
     *      1.轮询read，write事件
     *      2.处理io事件，即read，write事件，在对应的NioSocketChannel上处理
     *      3.处理任务队列的任务，即runAllTasks
     * 8)每个Worker NIOEventLoop处理业务时候会使用Pipeline管道，pipeline中包含了channel，即通过pipeline可以获取到对应通道，pipeline中维护了很多处理器
     */
    public void complexReactor(){

    }

    /**
     * 启动类
     *
     * ChannelHandler 通道处理类
     *
     * 入站：相对于server来说，向server发送数据
     * 出站：相对于server来说，接收server的数据
     *
     * |--->ChannelDuplexHandler 用于处理入站和出站IO事件
     * |--->ChannelOutboundHandler 用于处理出站IO事件
     * |--->ChannelInboundHandler 用于处理入站IO事件
     *      |--->channelActive通道就绪事件
     *      |--->channelRead通道读取事件
     *      |--->channelReadComplete通道读取完成事件
     *      |--->exceptionCaught通道发生异常事件
     * 适配器
     * |--->ChannelInboundHandlerAdapter 用于处理出站IO事件
     * |--->ChannelOutboundHandlerAdapter 用于处理出站IO事件
     *
     * ChannelPipeline 通道处理器的容器
     *
     * 提供了ChannelHandler链的容器。读取数据称为入站，发送数据称为出站
     *
     */
    public void bootStrap() throws InterruptedException {
        //创建bossGroup，处理连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //创建workGroup，处理客户端的业务请求
        EventLoopGroup workGroup = new NioEventLoopGroup(8);
        //创建服务器端的启动对象，配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //配置参数
        serverBootstrap.group(bossGroup, workGroup)//设置两个线程组
                .channel(NioServerSocketChannel.class)//设置服务器端的通道实现(NioServerSocketChannel)
                .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                //.handler(null)//给bossGroup设置handler处理器
                .childHandler(new ChannelInitializer<SocketChannel>() {//给workGroup的eventLoop对应的管道设置处理器
                    //创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //给pipeline设置处理器
                        //pipeline业务处理链，可以添加多个，netty会对IO通道进行进行链式处理
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                })
                .bind(8888)//绑定端口
                .sync();//异步
    }

}
