package com.flx.multi.thread.wangwenjun.design.workthread;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2020/9/11 0:04
 * @Description
 **/
public class TransportThread extends Thread{

    private final Channel channel;

    private static final Random random = new Random(System.currentTimeMillis());

    public TransportThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        IntStream.rangeClosed(1,100).forEach(x->{
            channel.put(new Work(getName(),x));
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
