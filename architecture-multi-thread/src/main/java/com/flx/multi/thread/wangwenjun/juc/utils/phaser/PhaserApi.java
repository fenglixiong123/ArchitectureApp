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
 * 注册线程数量=parties=初始parties+register()，一个阶段完成会立马进行第二个阶段
 *
 * phaser.register() 注册线程到phaser,将parties+1
 * phaser.bulkRegister(5) 可以批量注册线程数量
 * phaser.arrive() 到达将parties-1但是不需要等待（注册为0的时候会有异常）
 * phaser.arriveAndAwaitAdvance() 任务完成等待下一步
 * phaser.arriveAndDeregister() 宣布退出，其他线程就不会继续等待它
 * phaser.forceTermination() 强制销毁phaser，已经到达等待的都会结束,后序phaser不再生效
 *
 * phaser.isTerminated() 是否已经销毁
 * phaser.getRegisteredParties() 获取已经注册的线程数量
 * phaser.getArrivedParties() 获取已经到达的线程数量
 * phaser.getUnarrivedParties() 获取尚未到达的线程数量
 *
 * phaser.awaitAdvance(int phaser) 仅仅用来监控phaser是否完成,不参与任务
 * phaser.awaitAdvanceInterruptibly() 可中断的监管等待线程，可以调用interrupt来中断
 *
 * 此方法决定phrase结束的时间
 * boolean onAdvance(int phase, int registeredParties)
 *
 */
public class PhaserApi {

    public static void main(String[] args) throws InterruptedException {

        //parties初始注册数量
        Phaser phaser = new Phaser(1);
        showPhaserInfo(phaser);
        //批量注册5个
        phaser.bulkRegister(5);

        showPhaserInfo(phaser);

        //消灭5个线程，此时parties数量减为0
        IntStream.range(0,6).forEach(e->new Thread(phaser::arriveAndDeregister).start());

        showPhaserInfo(phaser);

        new Thread(()->{
            phaser.register();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
            System.out.println("Thread done.phaser = "+phaser.getPhase());
        }).start();

        //强制结束phaser
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("强制结束phaser");
            phaser.forceTermination();
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
        System.out.println("isTerminated = "+phaser.isTerminated());
        System.out.println("getRegisteredParties = "+phaser.getRegisteredParties());
        System.out.println("getArrivedParties = "+phaser.getArrivedParties());
        System.out.println("getUnarrivedParties = "+phaser.getUnarrivedParties());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

}
