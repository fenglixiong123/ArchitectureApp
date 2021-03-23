package com.flx.multi.thread.wangwenjun.juc.utils.phaser;

import java.util.concurrent.Phaser;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/23 17:33
 * @Description:
 */
public class PhaserFinal {

    public static void main(String[] args) {

        final Phaser phaser = new Phaser(4);

        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getUnarrivedParties());

//        phaser.arriveAndAwaitAdvance();
//        System.out.println(phaser.getPhase());
//
//        phaser.arriveAndAwaitAdvance();
//        System.out.println(phaser.getPhase());

    }

}
