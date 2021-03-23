package com.flx.multi.thread.wangwenjun.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/23 15:06
 * @Description:
 *
 * 相当于动态版本的CyclicBarrier，不固定party梳理
 *
 * phaser会等待所有注册的线程一起完成
 *
 * phaser.register() 注册线程到phaser,将parties+1
 * phaser.bulkRegister(5) 可以批量注册线程数量
 * phaser.arriveAndAwaitAdvance() 任务完成等待下一步
 * phaser.arriveAndDeregister() 宣布退出，其他线程就不会继续等待它
 *
 * phaser.getRegisteredParties() 获取已经注册的线程数量
 * phaser.getArrivedParties() 获取已经到达的线程数量
 * phaser.getUnarrivedParties() 获取尚未到达的线程数量
 */
public class PhaserApi {

    public static void main(String[] args) throws InterruptedException {

        //parties初始注册数量
        Phaser phaser = new Phaser(1);
        showPhaserInfo(phaser);
        //批量注册5个
        phaser.bulkRegister(5);

        showPhaserInfo(phaser);

        //消灭5个线程
        IntStream.range(0,6).forEach(e->new Thread(phaser::arriveAndDeregister).start());

        showPhaserInfo(phaser);

        new Thread(()->{
            phaser.register();
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
            System.out.println("Thread done.");
        }).start();

        //注册party
        phaser.register();
        System.out.println("注册自身");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("任务完成等待");
        //等待await
        phaser.arriveAndAwaitAdvance();
        System.out.println("任务结束");

    }

    /**
     * 展示Phaser信息
     * @param phaser
     */
    private static void showPhaserInfo(Phaser phaser){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getRegisteredParties = "+phaser.getRegisteredParties());
        System.out.println("getArrivedParties = "+phaser.getArrivedParties());
        System.out.println("getUnarrivedParties = "+phaser.getUnarrivedParties());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

}
