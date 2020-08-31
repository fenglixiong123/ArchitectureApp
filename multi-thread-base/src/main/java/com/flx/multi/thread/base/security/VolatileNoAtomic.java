package com.flx.multi.thread.base.security;

/**
 * @Author Fenglixiong
 * @Create 2020/8/6 2:22
 * volatile关键字
 * 作用保证线程之间可见，但是不保证原子性
 * 强制线程每次读取该值的时候都去“主内存”中取值。
 **/
public class VolatileNoAtomic {

    //只能保证可见性，不能保证原子性
    private static volatile int count = 0;

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        count ++ ;
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
