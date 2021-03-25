package com.flx.multi.thread.wangwenjun.base;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2020/8/24 2:09
 * @Description
 * 等待其join自己的线程先执行完毕再执行
 **/
public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{

            IntStream.range(1,10).forEach(x-> {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" : "+x);
            });

        });
        Thread t2 = new Thread(()->{

            IntStream.range(1,10).forEach(x-> {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" : "+x);
            });

        });
        Thread t3 = new Thread(()->{

            IntStream.range(1,10).forEach(x-> {
                System.out.println(Thread.currentThread().getName()+" : "+x);
            });

        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("All task finished !");
    }

}
