package com.flx.multi.thread.wangwenjun.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/1 17:15
 * @Description:
 */
public class AtomicStampedReferenceTest2 {

    private static AtomicStampedReference<Integer> atomicStamp = new AtomicStampedReference<>(100,0);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                boolean success = atomicStamp.compareAndSet(100,101,atomicStamp.getStamp(),atomicStamp.getStamp()+1);
                System.out.println("T1 = "+success);
                success = atomicStamp.compareAndSet(101,100,atomicStamp.getStamp(),atomicStamp.getStamp()+1);
                System.out.println("T1 = "+success);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()->{
            try {
                int stamp = atomicStamp.getStamp();
                System.out.println("T2 before sleep,stamp = "+stamp);
                TimeUnit.SECONDS.sleep(2);
                boolean success = atomicStamp.compareAndSet(100,101,stamp,stamp+1);
                System.out.println("T2 = "+success);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

}
