package com.flx.multi.thread.wangwenjun.juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/23 15:06
 * @Description: 相当于动态版本的CyclicBarrier
 *
 * 运动员案例>>>>>多项赛事的运动员案例
 * register() 注册，等同于parties+1
 * arriveAndAwaitAdvance() 到达并且等待其他线程结束
 */
public class PhaserCase {

    public static void main(String[] args) {

        final Phaser phaser = new Phaser();

        IntStream.range(1,6).forEach(i->{
            new Athlete(i,phaser).start();
        });
    }

    private final static Random random = new Random(System.currentTimeMillis());

    static class Athlete extends Thread{

        private final int no;
        private final Phaser phaser;

        Athlete(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
            phaser.register();
        }

        @Override
        public void run() {
            try {

                long start = System.currentTimeMillis();
                System.out.println(no+" : start running...");
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(no+" : end running...score = "+(System.currentTimeMillis()-start));

                phaser.arriveAndAwaitAdvance();

                start = System.currentTimeMillis();
                System.out.println(no+" : start bicycle...");
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(no+" : end bicycle...score = "+(System.currentTimeMillis()-start));

                phaser.arriveAndAwaitAdvance();

                start = System.currentTimeMillis();
                System.out.println(no+" : start long jump...");
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(no+" : end long jump...score = "+(System.currentTimeMillis()-start));

                phaser.arriveAndAwaitAdvance();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
