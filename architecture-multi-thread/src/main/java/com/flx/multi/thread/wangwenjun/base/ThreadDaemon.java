package com.flx.multi.thread.wangwenjun.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/23 19:20
 * @Description 守护线程
 *
 *
 *
 **/
public class ThreadDaemon {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName()+" thread start ..");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+" thread start ..");
                    Thread.sleep(60_000);
                    System.out.println(Thread.currentThread().getName()+" thread end ..");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //将线程设置为守护线程，则会随着主线程结束而结束
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(10_000);
            System.out.println(Thread.currentThread().getName()+" thread end ..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
