package com.flx.multi.thread.two;

/**
 * @Author Fenglixiong
 * @Create 2020/8/24 1:48
 * @Description 线程常用的api
 **/
public class ThreadCommonApi {

    public static void main(String[] args) {

        Thread t1 = new Thread(()->{

            System.out.println(Thread.currentThread().getName()+" thread start ..");
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" thread end ..");
        },"t1");
        Thread t2 = new Thread(()->{

            System.out.println(Thread.currentThread().getName()+" thread start ..");
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" thread end ..");
        },"t2");
        System.out.println("State : "+t1.getState());
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
        //获取线程id
        System.out.println("Id : "+t1.getId());
        //获取线程名字
        System.out.println("Name : "+t1.getName());
        //获取线程优先级
        System.out.println("Priority : "+t1.getPriority());
        //获取线程状态
        System.out.println("State : "+t1.getState());
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("State : "+t1.getState());
    }

}
