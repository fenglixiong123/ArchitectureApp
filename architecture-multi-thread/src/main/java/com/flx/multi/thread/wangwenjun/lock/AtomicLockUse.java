package com.flx.multi.thread.wangwenjun.lock;

/**
 * @Author: Fenglixiong
 * @Date: 2021/2/26 16:04
 * @Description:
 */
public class AtomicLockUse {

    private static AtomicLock atomicLock = new AtomicLock();

    public static void main(String[] args) {

        new Thread(()->{
            try {
                sum();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"T1").start();

        new Thread(()->{
            try {
                sum();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"T2").start();

    }

    private static void sum() throws Exception {
        try {
            atomicLock.tryLock();
            System.out.println(Thread.currentThread().getName() + " start sum...");
            for (int i = 0; i < 100; i++) {
                Thread.sleep(100);
            }
            System.out.println(Thread.currentThread().getName() + " sum = " + 1000);
        }finally {
            atomicLock.unlock();
        }
    }

}
