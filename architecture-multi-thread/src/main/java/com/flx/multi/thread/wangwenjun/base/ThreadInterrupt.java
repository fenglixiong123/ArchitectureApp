package com.flx.multi.thread.wangwenjun.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/24 20:30
 * @Description
 * 线程中断：
 * 调用interrupt()方法
 * 作用：给线程设置中断标志
 * 以下情况会收到中断异常
 * sleep
 * wait
 * join
 *
 * interrupted()
 * 静态方法，获得线程中断标识后会将中断标识清除
 * isInterrupted()
 * 只会获得线程是否中断，不会清除中断标识
 *
 **/
public class ThreadInterrupt {

    private static Object MONITOR = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread mainThread = Thread.currentThread();

        Thread commonThread = new Thread(()->{

        });

        Thread sleepThread = new Thread(){
            @Override
            public void run() {

                try {
                    Thread.sleep(50_000);
                } catch (InterruptedException e) {
                    System.out.println("sleepThread收到打断信号！");
                    e.printStackTrace();
                }
            }
        };
        Thread waitThread = new Thread(){
            @Override
            public void run() {
                while (true){
                    synchronized (MONITOR) {
                        try {
                            MONITOR.wait(10_000);
                        } catch (InterruptedException e) {
                            System.out.println("waitThread收到打断信号！");
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Thread joinThread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(20_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        commonThread.start();
        sleepThread.start();
        waitThread.start();
        joinThread.start();

        commonThread.interrupt();
        System.out.println("commonThread interrupt : "+commonThread.isInterrupted());
        sleepThread.interrupt();
        System.out.println("sleepThread interrupt : "+sleepThread.isInterrupted());
        waitThread.interrupt();
        System.out.println("waitThread interrupt : "+waitThread.isInterrupted());
        mainThread.interrupt();
        System.out.println("mainThread interrupt : "+mainThread.isInterrupted());


        try {
            joinThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
            System.out.println("joinThread收到打断信号！");
        }

    }

}
