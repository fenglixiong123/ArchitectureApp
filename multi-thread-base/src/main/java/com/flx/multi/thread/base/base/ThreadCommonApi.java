package com.flx.multi.thread.base.base;

/**
 * @Author Fenglixiong
 * @Create 2020/7/29 0:54
 * @Description Thread常用的api方法
 **/
public class ThreadCommonApi {

    public static void main(String[] args) {
        System.out.println("线程ID---------->"+Thread.currentThread().getId());
        System.out.println("线程名称---------->"+Thread.currentThread().getName());
        //创建线程并运行
        new Thread(new Runnable() {
            public void run() {
                System.out.println("线程ID---------->"+Thread.currentThread().getId());
                System.out.println("线程名称---------->"+Thread.currentThread().getName());
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName()+"----->"+i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
