package com.flx.multi.thread.base.security;

/**
 * @Author Fenglixiong
 * @Create 2020/7/29 23:19
 * @Description 抢票引发的脏数据，线程安全问题的提出
 * 对公共变量的写操作会发生线程安全问题
 **/
public class DirtyTicket {

    private static int ticketCount = 20;

    public static void main(String[] args) {


        new Thread(() -> {
            while (ticketCount>0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "出售第" + (ticketCount--) + "票");
            }
        }).start();
        new Thread(() -> {
            while (ticketCount>0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "出售第" + (ticketCount--) + "票");
            }
        }).start();
    }

}