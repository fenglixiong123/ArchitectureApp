package com.flx.multi.thread.wangwenjun.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/25 2:29
 * @Description
 * 如何优雅的停止线程
 **/
public class ThreadInterruptDeep {

    public static void main(String[] args) throws InterruptedException {

        stopThreadByVolatile();
        stopThreadByInterrupt();



    }



    /**
     * 真正优雅停止线程的方法
     * 设置线程中断标识
     * 判断是否中断结束程序执行
     */
    public static void stopThreadByInterrupt(){

        Thread t = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("i = "+i);
                    //检测线程中断标志
                    if(this.isInterrupted()){
                        System.out.println("当前线程被中断！");
                        break;//return
                    }
                }
            }
        };

        t.start();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }

    /**
     * 一种停止线程的方式
     * @throws InterruptedException
     */
    public static void stopThreadByVolatile() throws InterruptedException {
        Work work = new Work();
        Thread t = new Thread(work);
        t.start();
        Thread.sleep(2000);
        work.shutdown();
    }

    static class Work extends Thread{

        private volatile boolean running = true;

        private int count = 0;

        @Override
        public void run() {
            while (running){
                System.out.println("Hello : "+(++count));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shutdown(){
            this.running = false;
            System.out.println("Thread shutdown : "+running);
        }

    }

}