package com.flx.multi.thread.wangwenjun.lock;

/**
 * @Author Fenglixiong
 * @Create 2020/8/26 0:17
 * @Description
 * this锁
 **/
public class SynchronizedThisLock {

    public static void main(String[] args) {

        //T1先拿到this锁，T2等待this锁
        SynchronizedThisLock thisLock = new SynchronizedThisLock();

        new Thread(()->{
            thisLock.m1();
        },"T1").start();

        new Thread(()->{
            thisLock.m2();
        },"T2").start();

    }

    public synchronized void m1(){
        try {
            System.out.println(Thread.currentThread().getName()+" enter m1!");
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void m2(){
        synchronized (this) {
            try {
                System.out.println(Thread.currentThread().getName() + " enter m2!");
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


