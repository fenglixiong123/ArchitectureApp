package com.flx.multi.thread.wangwenjun.communicate;

/**
 * @Author Fenglixiong
 * @Create 2020/9/1 23:13
 * @Description
 * 关键字volatile：线程可见性、有序性
 **/
public class ThreadVolatile {

    //添加此关键字保证线程可见性
    private static volatile int money = 0;

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" money = "+money);
            while (money!=99){

            }
            System.out.println(Thread.currentThread().getName()+" money = "+money);
        },"read").start();

        Thread.sleep(1000);

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" money = "+money);
            money = 99;
            System.out.println(Thread.currentThread().getName()+" money = "+money);
        },"write").start();

    }

}
