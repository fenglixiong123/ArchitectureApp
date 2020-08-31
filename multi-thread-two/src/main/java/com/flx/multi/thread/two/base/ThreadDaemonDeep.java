package com.flx.multi.thread.two.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/24 1:22
 * @Description
 * 将线程设置为守护线程，则会随着主线程结束而结束
 * 问题：子线里面创建的子线程设置为守护线程会守护哪个呢？
 *      守护创建自己的父线程，随着父线程结束而结束
 *      应用场景：心跳检查线程
 **/
public class ThreadDaemonDeep {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+" thread start ..");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" thread start ..");
                Thread innerThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.currentThread().setName("daemonThread");
                            System.out.println(Thread.currentThread().getName()+" thread start ..");
                            for (int i = 0; i < 100; i++) {
                                Thread.sleep(1000);
                                System.out.println(Thread.currentThread().getName()+" health check ...");
                            }
                            System.out.println(Thread.currentThread().getName()+" thread end ..");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                innerThread.setDaemon(true);
                innerThread.start();
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" thread end ..");
            }
        });

//        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" thread end ..");
    }

}
