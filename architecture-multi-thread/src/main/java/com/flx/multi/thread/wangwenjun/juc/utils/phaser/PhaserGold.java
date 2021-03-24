package com.flx.multi.thread.wangwenjun.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/24 15:24
 * @Description:
 * awaitAdvance(int phaser) 仅仅用来监控phaser是否完成
 */
public class PhaserGold {

    public static void main(String[] args) throws InterruptedException {

        final Phaser phaser = new Phaser(6);

        IntStream.range(0,6).boxed().map(i->phaser).forEach(AwaitAdvanceTask::new);

        System.out.println("getArrivedParties = "+phaser.getArrivedParties());

        phaser.awaitAdvance(phaser.getPhase());
        System.out.println("all done!");

    }

    private static class AwaitAdvanceTask extends Thread{

        private final Phaser phaser;


        private AwaitAdvanceTask(Phaser phaser) {
            this.phaser = phaser;
            start();
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName()+" finished work!");
        }
    }

}
