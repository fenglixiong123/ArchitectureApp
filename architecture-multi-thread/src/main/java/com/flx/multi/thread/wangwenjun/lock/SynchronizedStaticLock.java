package com.flx.multi.thread.wangwenjun.lock;

/**
 * @Author Fenglixiong
 * @Create 2020/8/26 0:30
 * @Description
 * static锁
 **/
public class SynchronizedStaticLock {

    public static void main(String[] args) {

        new Thread(()->{
            m1();
        },"T1").start();

        new Thread(()->{
            m2();
        },"T2").start();

    }

    public synchronized static void m1(){
        try {
            System.out.println(Thread.currentThread().getName()+" enter m1!");
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m2(){
        synchronized (SynchronizedStaticLock.class) {
            try {
                System.out.println(Thread.currentThread().getName() + " enter m1!");
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
