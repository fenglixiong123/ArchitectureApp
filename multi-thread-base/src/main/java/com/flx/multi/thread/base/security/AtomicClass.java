package com.flx.multi.thread.base.security;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Fenglixiong
 * @Create 2020/8/6 2:50
 * @Description
 **/
public class AtomicClass {

    //只能保证可见性，不能保证原子性
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        count.incrementAndGet() ;
                    }
                    System.out.println(Thread.currentThread().getName()+"-count : "+count);
                }
            });
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

}
