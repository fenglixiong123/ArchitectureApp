package com.flx.multi.thread.wangwenjun.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/29 19:02
 * @Description 线程内异常捕获
 **/
public class ThreadExceptionCapture {

    public static void main(String[] args) {

        Thread t = new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(3_000);
                    System.out.println(3 / 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //设置异常处理类
        //处理线程未捕获的异常
        t.setUncaughtExceptionHandler((thread,throwable)->{
            System.out.println(thread);
            System.out.println(throwable);
        });
        t.start();
    }

}
