package com.flx.multi.thread.base.base;

/**
 * @Author Fenglixiong
 * @Create 2020/7/29 1:11
 * @Description
 * 守护线程：和主线程一起销毁
 * 非守护线程：和主线程互不相关
 * gc属于守护线程，随着主线程一起销毁
 * 用户线程属于非守护线程，是主线程创建的线程
 * 设置线程为守护线程：setDaemon(true)
 **/
public class DaemonThread {

    public static void main(String[] args) {
        Thread sunThread =  new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    System.out.println("子线程："+i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("子线程退出...");
            }
        });
        //设置子线程为守护线程，则子线程会随着主线程一起销毁
        sunThread.setDaemon(true);
        sunThread.start();
        for (int i = 1; i <= 5; i++) {
            System.out.println("主线程："+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("主线程退出...");
    }

}
