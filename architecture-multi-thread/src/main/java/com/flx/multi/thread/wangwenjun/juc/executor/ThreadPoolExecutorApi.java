package com.flx.multi.thread.wangwenjun.juc.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/25 15:06
 * @Description: 线程池核心类
 *
 * int corePoolSize,                    核心线程数量
 * int maximumPoolSize,                 最大线程数量
 * long keepAliveTime,                  大于核心线程空闲存活时间
 * TimeUnit unit,                       时间单位
 * BlockingQueue<Runnable> workQueue,   存放任务的队列
 * ThreadFactory threadFactory,         产生线程的工厂
 * RejectedExecutionHandler handler     拒绝策略：忽略，抛出异常，抛弃旧的，直接调用run方法
 *
 * 任务提交时，判断的顺序为 corePoolSize --> workQueue --> maximumPoolSize。
 *
 * corePoolSize:核心线程数量，当有新任务在execute()方法提交时，会执行以下判断：
 *      |--->如果运行的线程少于 corePoolSize，则创建新线程来处理任务，即使线程池中的其他线程是空闲的
 *      |--->如果线程池中的线程数量大于等于 corePoolSize 且小于 maximumPoolSize，则只有当workQueue满时才创建新的线程去处理任务；
 *      |--->如果设置的corePoolSize 和 maximumPoolSize相同，则创建的线程池的大小是固定的，这时如果有新任务提交，若workQueue未满，则将请求放入workQueue中，等待有空闲的线程去从workQueue中取任务并处理；
 *      |--->如果运行的线程数量大于等于maximumPoolSize，这时如果workQueue已经满了，则通过handler所指定的策略来处理任务；
 *
 * workQueue:等待队列，当任务提交时，如果线程池中的线程数量大于等于corePoolSize的时候，把该任务封装成一个Worker对象放入等待队列；
 *      |--->ArrayBlockingQueue是一个有边界的阻塞队列，它的内部实现是一个数组。它的容量在初始化时就确定不变。
 *      |--->LinkedBlockingQueue：阻塞队列大小的配置是可选的，其内部实现是一个链表。吞吐量通常要高于ArrayBlockingQueue。静态工厂方法Executors.newFixedThreadPool()使用了这个队列。
 *      |--->PriorityBlockingQueue：是一个没有边界的队列，所有插入到PriorityBlockingQueue的对象必须实现java.lang.Comparable接口，队列优先级的排序就是按照我们对这个接口的实现来定义的。
 *      |--->SynchronousQueue队列内部仅允许容纳一个元素。当一个线程插入一个元素后会被阻塞，除非这个元素被另一个线程消费。吞吐量通常要高于LinkedBlockingQueue，静态工厂方法Executors.newCachedThreadPool使用了这个队列。
 *
 * threadFactory：它是ThreadFactory类型的变量，用来创建新线程。
 *      |--->默认使用Executors.defaultThreadFactory() 来创建线程。
 *      |--->使用默认的ThreadFactory来创建线程时，会使新创建的线程具有相同的NORM_PRIORITY优先级并且是非守护线程，同时也设置了线程的名称。
 *
 * handler：它是RejectedExecutionHandler类型的变量，表示线程池的饱和策略。如果阻塞队列满了并且没有空闲的线程，这时如果继续提交任务，就需要采取一种策略处理该任务。线程池提供了4种策略：
 *      |--->AbortPolicy：直接抛出异常，这是默认策略；
 *      |--->CallerRunsPolicy：用调用者所在的线程来执行任务；
 *      |--->DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
 *      |--->DiscardPolicy：直接丢弃任务；
 *
 *
 * >>>核心思想：
 *
 * 提交任务后，会首先判断是否达到核心线程数量，如果没有达到就立马创建核心线程来执行任务（此时不管是否有其他核心线程空闲状态），
 * 如果核心线程已满，就往队列里面放，如果队列满了，则继续创建线程到最大数量，如果超过最大数量，则抛弃任务
 *
 * >>>使用线程池的好处？
 *
 * 降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
 * 提高响应速度。当任务到达时，任务可以不需要的等到线程创建就能立即执行。
 * 易于控制线程的并发，提高系统负载能力，不会无休止的创建线程
 * 提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。
 *
 * >>>Java提供的静态方法：
 *
 * newCachedThreadPool：用来创建一个可以无限扩大的线程池，适用于负载较轻的场景，执行短期异步任务。（可以使得任务快速得到执行，因为任务时间执行短，可以很快结束，也不会造成cpu过度切换）
 * newFixedThreadPool：创建一个固定大小的线程池，因为采用无界的阻塞队列，所以实际线程数量永远不会变化，适用于负载较重的场景，对当前线程数量进行限制。（保证线程数可控，不会造成线程过多，导致系统负载更为严重）
 * newSingleThreadExecutor：创建一个单线程的线程池，适用于需要保证顺序执行各个任务。
 * newScheduledThreadPool：适用于执行延时或者周期性任务。
 *
 * >>>如何配置线程池？
 *
 * CPU密集型任务：尽量使用较小的线程池，一般为CPU核心数+1。 因为CPU密集型任务使得CPU使用率很高，若开过多的线程数，会造成CPU过度切换。
 * IO密集型任务：可以使用稍大的线程池，一般为2*CPU核心数。 IO密集型任务CPU使用率并不高，因此可以让CPU在等待IO的时候有其他线程去处理别的任务，充分利用CPU时间。
 * 混合型任务：可以将任务分成IO密集型和CPU密集型任务，然后分别用不同的线程池去处理。
 */
public class ThreadPoolExecutorApi {

    /**
     * What happen?
     * 1.coreSize=1,MaxSize=2,blockingQueue size = 1,what happen when submit 3 task?
     * 2.coreSize=1,MaxSize=2,blockingQueue size = 5,what happen when submit 7 task?
     * 3.coreSize=1,MaxSize=2,blockingQueue size = 5,what happen when submit 8 task?
     */
    private final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            2,5, 30, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2), new CustomThreadFactory("T-"),new CustomRejectPolicy());

    public static void main(String[] args) {

        showExecutorPool(poolExecutor,"init");

        poolExecutor.submit(()->sleepTime(5));//核心线程执行
        showExecutorPool(poolExecutor,"one task");
        poolExecutor.submit(()->sleepTime(5));//队列中有一个
        showExecutorPool(poolExecutor,"two task");
        poolExecutor.submit(()->sleepTime(5));//队列中有二个,队列已满
        showExecutorPool(poolExecutor,"three task");
        poolExecutor.submit(()->sleepTime(5));//继续增加线程数量到最大线程
        showExecutorPool(poolExecutor,"four task");
        poolExecutor.submit(()->sleepTime(5));//已经是最大线程，队列也满了，抛出异常
        showExecutorPool(poolExecutor,"five task");

    }



    private static void showExecutorPool(ThreadPoolExecutor poolExecutor,String message){
        System.out.println("++++++++++++++++++++++++"+message+"++++++++++++++++++++++++++");
        //System.out.println("getMaximumPoolSize = "+poolExecutor.getMaximumPoolSize());
        //System.out.println("getCorePoolSize = "+poolExecutor.getCorePoolSize());
        System.out.println("getQueueSize = "+poolExecutor.getQueue().size());
        System.out.println("getActiveCount = "+poolExecutor.getActiveCount());
    }

    private static final AtomicInteger i = new AtomicInteger(0);

    /**
     * 线程工厂，产生新的线程
     */
    static class CustomThreadFactory implements ThreadFactory{

        private final String namePrefix;

        CustomThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,namePrefix+i.incrementAndGet());
        }
    }

    /**
     * 抛弃策略，选用抛异常的方式
     */
    static class CustomRejectPolicy implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new RuntimeException("queue too much!");
        }
    }



    private static void startMonitor(ThreadPoolExecutor poolExecutor){
        Thread t = new Thread(()->{
            while (true){
                showExecutorPool(poolExecutor,"monitor");
                sleepTime(3);
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private static void sleepTime(long time){
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
