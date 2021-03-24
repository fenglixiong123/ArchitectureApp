package com.flx.multi.thread.wangwenjun.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/23 17:33
 * @Description: 案例，可以按照百分比的线程注册个数来决定是否结束本轮phaser
 * onAdvance(int phase, int registeredParties) 控制何时phaser结束
 */
public class PhaserFinal {

    public static void main(String[] args) {

        final Phaser phaser = new Phaser(2){
            //可以控制结束
            //true一直是结束状态
            //false永远不结束
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                //原来实现
                //return registeredParties == 0;
                //当有50%线程结束任务时候就结束
                return registeredParties == this.getRegisteredParties()*0.5;
//                return true;
            }
        };

        new OnAdvanceTask("Alex",phaser).start();
        new OnAdvanceTask("Jack",phaser).start();

    }

    static class OnAdvanceTask extends Thread{

        private final Phaser phaser;

        OnAdvanceTask(String name, Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName()+" I am start.phaser = "+phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName()+" I am end.phaser = "+phaser.getPhase());

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(getName()+" I am start.phaser = "+phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName()+" I am end.phaser = "+phaser.getPhase());
        }
    }

}
