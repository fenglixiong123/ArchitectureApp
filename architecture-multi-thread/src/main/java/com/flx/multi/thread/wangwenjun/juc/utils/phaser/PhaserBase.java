package com.flx.multi.thread.wangwenjun.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/23 15:48
 * @Description:
 */
public class PhaserBase {

    public static void main(String[] args) {

        final Phaser phaser = new Phaser();

        //启动5个线程并且注册线程
        IntStream.range(1,6).boxed().map(i->phaser).forEach(Task::new);

        phaser.register();
        phaser.arriveAndAwaitAdvance();
        System.out.println("All of work finished!");

    }

    //线程任务，注册线程，启动线程
    static class Task extends Thread{

        private final Phaser phaser;

        Task(Phaser phaser) {
            this.phaser = phaser;
            this.phaser.register();
            start();
        }

        @Override
        public void run() {
            System.out.println("The worker ["+getName()+"] is working...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The worker ["+getName()+"] is work finished.");
            phaser.arriveAndAwaitAdvance();
        }
    }

}
