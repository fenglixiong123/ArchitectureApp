package com.flx.multi.thread.wangwenjun.juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/23 16:53
 * @Description: 如果有线程中途退出
 * 案例：多人运动，其中一个运动员受伤，提前宣布退出
 * arriveAndDeregister() 等同于parties-1
 */
public class PhaserDeep {

    public static void main(String[] args) {

        final Phaser phaser = new Phaser();

        IntStream.range(1,5).forEach(i->{
            new Athlete(i,phaser).start();
        });
        new InjureAthlete(5,phaser).start();
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

                sport(phaser,no+" : start running...",no+" : end running");

                sport(phaser,no+" : start bicycle...",no+" : end bicycle");

                sport(phaser,no+" : start long jump...",no+" : end long jump");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class InjureAthlete extends Thread{
        private final int no;
        private final Phaser phaser;

        InjureAthlete(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
            phaser.register();
        }

        @Override
        public void run() {
            try {

                sport(phaser,no+" : start running...",no+" : end running");

                sport(phaser,no+" : start bicycle...",no+" : end bicycle");

                System.out.println("Oh shit , I am injure !");
                phaser.arriveAndDeregister();//退出

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sport(Phaser phaser,String startLog,String endLog) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println(startLog);
        TimeUnit.SECONDS.sleep(random.nextInt(5));
        long end = System.currentTimeMillis();
        System.out.println(endLog+"--->score = "+(end-start));
        phaser.arriveAndAwaitAdvance();
    }

}
