package com.flx.multi.thread.wangwenjun.juc.utils.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Author Fenglixiong
 * @Create 2021/3/10 23:10
 * @Description reset等价于初始化
 **/
public class CyclicBarrierCase {

    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        new Thread(()->{
            try {
                System.out.println("T1 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("T1 await start");
                cyclicBarrier.await();
                System.out.println("T1 end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                System.out.println("T2 start");
                cyclicBarrier.await();
                System.out.println("T2 end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        TimeUnit.SECONDS.sleep(6);
        System.out.println("parties = "+cyclicBarrier.getParties());
        System.out.println("await number = "+cyclicBarrier.getNumberWaiting());
        System.out.println("is broker = "+cyclicBarrier.isBroken());
        System.out.println("main reset start");
        cyclicBarrier.reset();
        System.out.println("main reset end");
        System.out.println("parties = "+cyclicBarrier.getParties());
        System.out.println("await number = "+cyclicBarrier.getNumberWaiting());
        System.out.println("is broker = "+cyclicBarrier.isBroken());
        System.out.println("main finished !");
    }

}
