package com.flx.multi.thread.wangwenjun.design.workthread;

import java.util.Random;

/**
 * @Author Fenglixiong
 * @Create 2020/9/10 23:46
 * @Description
 **/
public class WorkThread extends Thread{

    private final Channel channel;

    private static final Random random = new Random(System.currentTimeMillis());

    public WorkThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true){
            Work work = channel.take();
            work.execute();
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
