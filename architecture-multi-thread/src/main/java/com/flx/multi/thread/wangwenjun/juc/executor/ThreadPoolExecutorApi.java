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
 */
public class ThreadPoolExecutorApi {

    /**
     * What happen?
     * 1.coreSize=1,MaxSize=2,blockingQueue is empty,what happen when submit 3 task?
     * 2.coreSize=1,MaxSize=2,blockingQueue size = 5,what happen when submit 7 task?
     * 3.coreSize=1,MaxSize=2,blockingQueue size = 5,what happen when submit 8 task?
     */
    private final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,2,
            30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3), new CustomThreadFactory("T-"),new CustomRejectPolicy());

    public static void main(String[] args) {

        showExecutorPool(poolExecutor);

        poolExecutor.submit(()->sleepTime(5));//核心线程执行
        showExecutorPool(poolExecutor);
        poolExecutor.submit(()->sleepTime(5));//队列中有一个
        showExecutorPool(poolExecutor);
        poolExecutor.submit(()->sleepTime(5));//继续增加线程数量到最大线程
        showExecutorPool(poolExecutor);
        poolExecutor.submit(()->sleepTime(5));//已经是最大线程，队列也满了，抛出异常
        showExecutorPool(poolExecutor);

    }



    private static void showExecutorPool(ThreadPoolExecutor poolExecutor){
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("getMaximumPoolSize = "+poolExecutor.getMaximumPoolSize());
        System.out.println("getCorePoolSize = "+poolExecutor.getCorePoolSize());
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
                showExecutorPool(poolExecutor);
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
