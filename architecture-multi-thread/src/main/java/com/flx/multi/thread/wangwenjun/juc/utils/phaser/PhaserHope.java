package com.flx.multi.thread.wangwenjun.juc.utils.phaser;

import java.util.concurrent.Phaser;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/24 16:54
 * @Description:
 * phaser.awaitAdvanceInterruptibly() 可中断的监管等待线程
 */
public class PhaserHope {

    public static void main(String[] args) {

        final Phaser phaser = new Phaser(3);
        Thread t1 = new Thread(phaser::arriveAndAwaitAdvance);
        t1.start();

        Thread monitor = new Thread(()->{
            try {
                //等待第一阶段完成
                System.out.println("wait for advance:start");
                phaser.awaitAdvanceInterruptibly(phaser.getPhase());
                System.out.println("wait for advance:done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        monitor.start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
                System.out.println("t2 interrupt");
                monitor.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();




        System.out.println("main done .");
    }

}
