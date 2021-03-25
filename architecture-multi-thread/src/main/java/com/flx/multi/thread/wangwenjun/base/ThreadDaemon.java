package com.flx.multi.thread.wangwenjun.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/23 19:20
 * @Description 守护线程
 *
 * 将线程设置为守护线程，则会随着创建自己的线程的结束而结束
 * 问题：子线里面创建的子线程设置为守护线程会守护哪个呢？
 *      守护创建自己的父线程，随着父线程结束而结束
 *      应用场景：心跳检查线程
 **/
public class ThreadDaemon {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName()+" thread start ..");
        Thread t = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+" thread start ..");
                Thread.sleep(60_000);
                System.out.println(Thread.currentThread().getName()+" thread end ..");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //将线程设置为守护线程，则会随着主线程结束而结束
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(5_000);
            System.out.println(Thread.currentThread().getName()+" thread end ..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
