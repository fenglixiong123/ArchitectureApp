package com.flx.multi.thread.wangwenjun.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Fenglixiong
 * @Date: 2020/12/1 16:32
 * @Description:
 */
public class AtomicIntegerTest1 {

    /**
     * 不保证原子性
     */
    //private static volatile int value = 0;
    private static volatile AtomicInteger value = new AtomicInteger(0);

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {

        /**
         * value = value + 1
         * (1) get value from main memory to local memory
         * (2) add 1 => x
         * (3) assign the value to x
         * (4) flush to main memory
         */
        Thread t1 = new Thread(){
            @Override
            public void run() {
                int x = 0;
                while (x<500){
                    int tem = value.addAndGet(1);
                    System.out.println(Thread.currentThread().getName()+":"+tem);
                    x++;
                }
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                int x = 0;
                while (x<500){
                    int tem = value.addAndGet(1);
                    System.out.println(Thread.currentThread().getName()+":"+tem);
                    x++;
                }
            }
        };

        Thread t3 = new Thread(){
            @Override
            public void run() {
                int x = 0;
                while (x<500){
                    int tem = value.addAndGet(1);
                    System.out.println(Thread.currentThread().getName()+":"+tem);
                    x++;
                }
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Set size : "+value.get());
        System.out.println("main end !");

    }

}
