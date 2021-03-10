package com.flx.multi.thread.wangwenjun.juc.utils;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2021/3/10 21:13
 * @Description 线程之间互相得知彼此的执行情况
 * 主要api：
 *
 * getParties 获得线程个数
 * getNumberWaiting 获得等待线程数量
 * isBroken 判断是否打断
 * reset 重置
 **/
public class CyclicBarrierApi {

    private static final Random random = new Random();

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5,()->{
            System.out.println("All finished !");
        });

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println("The parties is : "+cyclicBarrier.getParties());
                    System.out.println("The broken is : "+cyclicBarrier.isBroken());
                    System.out.println("The number waiting is : "+cyclicBarrier.getNumberWaiting());
                    System.out.println("-----------------------------------------------------------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        IntStream.rangeClosed(1,5).forEach(x->{
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        });


    }

}
