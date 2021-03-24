package com.flx.multi.thread.wangwenjun.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/25 23:02
 * @Description
 * 优雅的方式强制关闭任务
 * 核心思想：通过自身关闭则守护线程关闭
 * 当自身被设置为中断状态则可停止自己
 **/
public class ThreadInterruptStop {

    public static void main(String[] args) {

        ThreadInterruptStop stopService = new ThreadInterruptStop();
        stopService.execute(()->{
            while (true){
                System.out.println(Thread.currentThread().getName()+" hello");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopService.shutdown(1000);

    }

    private boolean finished = false;

    private Thread executeThread;

    public void execute(Runnable task){
        executeThread = new Thread(){
            @Override
            public void run() {
                //守护线程
                Thread runner = new Thread(task);
                runner.setDaemon(true);
                runner.start();
                try {
                    runner.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finished = true;
            }
        };
        executeThread.start();
    }

    public void shutdown(long timeout){
        long currentTime = System.currentTimeMillis();
        while (!finished){
            if(System.currentTimeMillis()-currentTime>=timeout){
                System.out.println("超时了,强制关闭任务！");
                executeThread.interrupt();
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("executeThread 被打断！");
                break;
            }
        }
        finished = false;
    }

}
