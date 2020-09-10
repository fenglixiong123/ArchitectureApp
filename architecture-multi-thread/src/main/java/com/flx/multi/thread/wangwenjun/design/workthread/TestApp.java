package com.flx.multi.thread.wangwenjun.design.workthread;

/**
 * @Author Fenglixiong
 * @Create 2020/9/11 0:17
 * @Description
 * 传送带不断生产任务
 * 工人看到有任务
 * 工人来执行任务
 **/
public class TestApp {

    public static void main(String[] args) {

        Channel channel = new Channel(20);
        channel.startWorker();
        new TransportThread("T-A",channel).start();
        new TransportThread("T-B",channel).start();
        new TransportThread("T-c",channel).start();

    }

}
