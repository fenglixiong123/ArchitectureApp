package com.flx.multi.thread.wangwenjun.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Fenglixiong
 * @Create 2020/12/8 22:38
 * @Description
 **/
public class AtomicIntegerTest2 {

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {

        /**
         * create
         */
        final AtomicInteger i = new AtomicInteger();
        System.out.println(i.get());
        i.set(10);
        System.out.println(i.get());
        int a = i.getAndAdd(6);
        System.out.println("a = "+a);
        System.out.println("i = "+i.get());

        //打印AtomicInteger的字段值
        System.out.println(AtomicInteger.class.getDeclaredField("value"));

        //不安全的操作方法
        sum();


    }

    private static int count = 0;

    /**
     * 不安全 结果不一定是10000
     */
    private static void sum() throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int i=0;i<10;i++){
            ts[i] = new Thread(()->{
                for (int c=0;c<1000;c++){
                    count ++;
                }
                System.out.print(count+" ");
            });
            ts[i].start();
        }
        for (int i=0;i<10;i++){
            ts[i].join();
        }

        System.out.println();
        System.out.println("count = "+count);

    }

}
