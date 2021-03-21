package com.flx.multi.thread.wangwenjun.juc.locks.reentrant;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/20 19:25
 * @Description:
 *
 * lock() 申请锁，不可被打断
 * lockInterruptibly() 可以被打断的锁
 * unlock() 释放锁
 *
 * tryLock() 申请锁，申请不到就算了
 * tryLock(long time, TimeUnit unit) 尝试申请锁，指定时间申请不到就算了
 *
 */
public class ReentrantLockApi {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        IntStream.range(1,3).forEach(i->new Thread(()->{
//            doLock();
//            doLockBySync();
//            tryLock();
        },"S"+i).start());

        Thread t1 = new Thread(ReentrantLockApi::doLock,"T1");
        t1.start();
        Thread.sleep(1000);
        Thread t2 = new Thread(ReentrantLockApi::doLock,"T2");
        t2.start();
        Thread.sleep(1000);

        //当前锁是否被占用
        Optional.of("isLocked = "+lock.isLocked()).ifPresent(System.out::println);
        //队列中是否还有等待线程
        Optional.of("hasQueuedThreads = "+lock.hasQueuedThreads()).ifPresent(System.out::println);
        //指定线程是否在等待队列中
        Optional.of("hasQueuedThread = "+lock.hasQueuedThread(t2)).ifPresent(System.out::println);
        //等待线程数量
        Optional.of("getQueueLength = "+lock.getQueueLength()).ifPresent(System.out::println);

    }

    /**
     * 测试是否锁在阻塞过程中是否可以被中断
     * 如果用了lockInterruptibly()方法是可以被中断的
     */
    public static void testInterruptibly(){
        try{
            lock.lockInterruptibly();
            Optional.of(Thread.currentThread().getName()+" get lock and will do working .").ifPresent(System.out::println);
            while (true){
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            Optional.of(Thread.currentThread().getName()+" release lock and will quit .").ifPresent(System.out::println);
        }
    }

    public static void doLock(){
        try{
            lock.lock();
            Optional.of(Thread.currentThread().getName()+" get lock and will do working .").ifPresent(System.out::println);
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            Optional.of(Thread.currentThread().getName()+" release lock and will quit .").ifPresent(System.out::println);
        }
    }

    public static void doLockBySync(){
        synchronized (ReentrantLockApi.class) {
            try {
                Optional.of(Thread.currentThread().getName()+" get lock and will do working .").ifPresent(System.out::println);
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Optional.of(Thread.currentThread().getName()+" release lock and will quit .").ifPresent(System.out::println);
            }
        }
    }

    /**
     * 尝试获取锁，获取不到就算了
     */
    public static void tryLock(){
        if(lock.tryLock()){
            try{
                Optional.of(Thread.currentThread().getName()+" get lock and will do working .").ifPresent(System.out::println);
                //todo
            }finally {
                lock.unlock();
                Optional.of(Thread.currentThread().getName()+" release lock and will quit .").ifPresent(System.out::println);
            }
        }else {
            Optional.of(Thread.currentThread().getName()+" get lock failed and quit now .").ifPresent(System.out::println);
        }
    }

}
