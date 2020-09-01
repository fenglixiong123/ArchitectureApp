package com.flx.multi.thread.two.pool;


/**
 * @Author Fenglixiong
 * @Create 2020/8/30 0:24
 * @Description
 **/
public class CustomPoolUse {

    public static void main(String[] args) throws InterruptedException {

        CustomPool customPool = new CustomPool();
        for (int i = 1; i <= 40; i++) {
            customPool.submit(()->{
                System.out.println(Thread.currentThread().getName()+" task start!");
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" task finished!");
            });
            System.out.println(" task ["+i+"] submit!");
        }

        Thread.sleep(50000);
        customPool.shutdown();

    }

}
