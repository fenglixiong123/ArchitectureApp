package com.flx.multi.thread.wangwenjun.design.twophase;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 22:47
 * @Description 线程执行完做一些清理的工作
 * 技巧点：
 * finally{
 *    this.clean();
 * }
 **/
public class TestApp {

    public static void main(String[] args) throws InterruptedException {
        CountIncrement countIncrement = new CountIncrement();
        countIncrement.start();
        Thread.sleep(5000);
        countIncrement.shutdown();
    }

}
