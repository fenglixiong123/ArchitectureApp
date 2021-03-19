package com.flx.multi.thread.wangwenjun.juc.utils.semaphore;

import java.util.Collection;
import java.util.concurrent.Semaphore;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/14 23:55
 * @Description: 获取阻塞的线程
 */
public class SemaphoreBase {

    public static void main(String[] args) throws InterruptedException {

        CustomSemaphore semaphore = new CustomSemaphore(1);
        semaphore.acquire(1);
        new Thread(()->{
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }).start();
        System.out.println("hasQueuedThreads = "+semaphore.hasQueuedThreads());
        System.out.println(semaphore.getQueuedThread());
    }

    static class CustomSemaphore extends Semaphore{

        public CustomSemaphore(int permits) {
            super(permits);
        }

        public CustomSemaphore(int permits, boolean fair) {
            super(permits, fair);
        }

        /**
         * 获取阻塞的线程
         * @return
         */
        public Collection<Thread> getQueuedThread(){
            return super.getQueuedThreads();
        }

    }

}
