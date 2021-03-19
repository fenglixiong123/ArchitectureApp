package com.flx.multi.thread.wangwenjun.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/14 23:54
 * @Description: 信号量api
 * 许可证，令牌桶
 * 当permit为1时，可以当lock使用
 * acquire() 申请许可证，申请不到就阻塞等待
 * acquire(int count) 申请许指定个数许可证，申请不到就阻塞等待
 * tryAcquire() 尝试去申请许可证，申请不到就放弃
 * tryAcquire(int count) 尝试去申请指定个数许可证，申请不到就放弃
 * tryAcquire(long timeout,TimeUnit unit,int count) 尝试去申请指定个数许可证，指定时间申请不到就放弃
 * acquireUninterruptibly() 申请许可证忽略中断
 * acquireUninterruptibly(int count) 申请许指定数量可证忽略中断
 * release() 释放许可证
 * release(int count) 释放指定数量许可证
 * availablePermits() 剩余许可证数量
 * hasQueuedThreads() 是否还有在队列中的线程
 * getQueueLength() 队列中线程数量
 * drainPermits() 获取所有的许可证
 */
public class SemaphoreApi {

    public static void main(String[] args) throws InterruptedException {

        //许可证为2
        final Semaphore semaphore = new Semaphore(3);

        IntStream.rangeClosed(1,3).forEach(x->{
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" start");
                boolean result = false;
                try {
                    //申请许可证
                    result = semaphore.tryAcquire(2);
                    if(result) {
                        System.out.println(Thread.currentThread().getName() + " get semaphore permit");
                    }else {
                        System.out.println(Thread.currentThread().getName() + " not get semaphore permit");
                    }
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(Thread.currentThread().getName()+" end");
                    if(result) {
                        semaphore.release(2);
                    }
                }
            },"T"+x).start();
        });

        Thread t4 = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" start");
            try {
                //申请许可证
                semaphore.acquireUninterruptibly(2);
                System.out.println(Thread.currentThread().getName() + " get semaphore permit");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+" end");
                semaphore.release(2);

            }
        },"T4");

        t4.start();
        t4.interrupt();

        Thread t5 = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" start");
            try {
                //获取所有德的许可证
                semaphore.drainPermits();
                System.out.println(Thread.currentThread().getName() + " get semaphore permit");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+" end");
                semaphore.release(2);

            }
        },"T5");
        t5.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("availablePermits = "+semaphore.availablePermits());
            System.out.println("hasQueuedThreads = "+semaphore.hasQueuedThreads());
            System.out.println("getQueueLength = "+semaphore.getQueueLength());
            TimeUnit.SECONDS.sleep(3);
        }

    }

}
