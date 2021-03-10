package com.flx.multi.thread.wangwenjun.juc.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Author Fenglixiong
 * @Create 2021/3/10 23:10
 * @Description
 **/
public class CyclicBarrierCase {

    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
        cyclicBarrier.reset();
        System.out.println("All finished !");
    }

}
