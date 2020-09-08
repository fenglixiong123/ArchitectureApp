package com.flx.multi.thread.wangwenjun.design.twophase;

import java.util.Random;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 22:42
 * @Description
 **/
public class CountIncrement extends Thread{

    private volatile boolean terminated = false;

    private int counter = 0;

    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public void run() {
        try{
            while (!terminated){
                System.out.println(Thread.currentThread().getName()+" "+(++counter));
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.clean();
        }
    }

    public void clean(){
        System.out.println("Do some clean work for the second phase.counter = "+counter);
    }

    public void shutdown(){
        this.terminated = true;
        this.interrupt();
    }

}
