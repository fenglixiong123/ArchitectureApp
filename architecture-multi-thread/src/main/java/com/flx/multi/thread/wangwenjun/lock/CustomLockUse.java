package com.flx.multi.thread.wangwenjun.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author Fenglixiong
 * @Create 2020/8/29 17:18
 * @Description
 **/
public class CustomLockUse {

    public static void main(String[] args) {

        CustomLock customLock = new CustomLockImpl();
        List<Thread> workers = new ArrayList<>();
        //1.创建线程准备抢占锁
        Stream.of("T1","T2","T3","T4","T5").forEach(x->{
            Thread temThread = new Thread(()->{
                try {
                    if(Thread.currentThread().getName().equals("T5")) {
                        customLock.lock(1000);
                    }else {
                        customLock.lock();
                    }
                    System.out.println(Thread.currentThread().getName()+" I get lock start work ...");
                    try {
                        Thread.sleep(5_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" I get lock end work ...");
                } catch (InterruptedException | CustomLock.TimeOutException e) {
                    e.printStackTrace();
                }finally {
                    customLock.unlock();
                }
            },x);
            temThread.start();
            workers.add(temThread);
        });

        //2.监控线程，监控锁的状态
        Thread monitor = new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() +" "+customLock.getBlockedSize()+" " + customLock.getBlockedThread());
            }
        },"MONITOR");
        monitor.setDaemon(true);
        monitor.start();

        //3.让监控线程和主线程一起结束
        //主线程等所有线程结束再结束，因为监控线程为守护线程。监控线程会随着主线程结束而结束
        workers.forEach(x-> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

}
