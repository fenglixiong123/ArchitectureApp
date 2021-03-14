package com.flx.multi.thread.wangwenjun.juc.utils.countdown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author Fenglixiong
 * @Create 2021/3/8 22:45
 * @Description 主要讲解Latch的方法
 * 1.CountDownLatch退出的两种情况：
 *    (1)计数器为0 new CountDownLatch(0)
 *    (2)await线程受到countDown线程中断
 * 2.等待一定时间自我唤醒
 *    latch.await(2000, TimeUnit.MILLISECONDS);
 **/
public class CountDownLatchApi {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        final Thread mainThread = Thread.currentThread();

        new Thread(()->{
            try {
                Thread.sleep(10000);
                System.out.println("T1 done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            latch.countDown();
            mainThread.interrupt();
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(5000);
                System.out.println("T2 done");
                mainThread.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

//        latch.await();
        latch.await(2000, TimeUnit.MILLISECONDS);
        System.out.println("Main finished !");

    }

}
