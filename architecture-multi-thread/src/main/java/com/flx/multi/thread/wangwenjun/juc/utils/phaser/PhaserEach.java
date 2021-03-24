package com.flx.multi.thread.wangwenjun.juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/24 15:26
 * @Description:
 * arrive() 执行完不用继续等待
 * 只有main方法执行完会继续等待，等到所有结果完成进行汇总
 */
public class PhaserEach {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser  phaser = new Phaser(5);

        IntStream.range(0,4).forEach(e->(new ArriveTask(phaser)).start());

        phaser.arriveAndAwaitAdvance();
        System.out.println("Phaser 1 done!");

    }

    private static class ArriveTask extends Thread{

        private final Phaser phaser;

        public ArriveTask(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {

            System.out.println(getName()+" start 1.");
            sleep_5();
            System.out.println(getName()+" end 1.");
            phaser.arrive();
            System.out.println(getName()+" arrive 1.");

            sleep_5();
            System.out.println(getName()+" start 2.");
            System.out.println(getName()+" end 2.");
        }
    }

    private static void sleep_5(){
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
