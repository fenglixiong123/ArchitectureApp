package com.flx.multi.thread.wangwenjun.juc.utils.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Author Fenglixiong
 * @Create 2021/3/10 21:13
 * @Description 线程之间互相等待,全部执行完毕一起退出
 **/
public class CyclicBarrierBase {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        /**
         * 通过转入线程获得线程执行结果
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            System.out.println("Notify all finished !");
        });

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(8);
                System.out.println("T1 finished !");
                cyclicBarrier.await();
                System.out.println("T1 other finished !");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("T2 finished !");
                cyclicBarrier.await();
                System.out.println("T2 other finished !");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        /**
         * 通过增加冗余数量来获得线程执行结果
         */
        cyclicBarrier.await();
        System.out.println("All Job finished!");

    }

}
