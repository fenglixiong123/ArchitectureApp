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
 * 核心思想：
 *
 * 提交任务后，会先放入执行队列中，先用核心线程进行执行，核心线程不够用就一直放在队列中，如果队列满了，则继续
 * 创建线程到最大数量，如果超过最大数量，则抛弃任务
 *
 * Java提供的静态方法：
 *
 * newCachedThreadPool：用来创建一个可以无限扩大的线程池，适用于负载较轻的场景，执行短期异步任务。（可以使得任务快速得到执行，因为任务时间执行短，可以很快结束，也不会造成cpu过度切换）
 * newFixedThreadPool：创建一个固定大小的线程池，因为采用无界的阻塞队列，所以实际线程数量永远不会变化，适用于负载较重的场景，对当前线程数量进行限制。（保证线程数可控，不会造成线程过多，导致系统负载更为严重）
 * newSingleThreadExecutor：创建一个单线程的线程池，适用于需要保证顺序执行各个任务。
 * newScheduledThreadPool：适用于执行延时或者周期性任务。
 *
 * 如何配置线程池？
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
