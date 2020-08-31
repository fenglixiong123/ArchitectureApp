package com.flx.multi.thread.two;

import java.util.Arrays;

/**
 * @Author Fenglixiong
 * @Create 2020/8/23 17:13
 * @Description
 **/
public class ThreadGroupApi {

    public static void main(String[] args) {
        //创建三个线程
        Thread t1 = new Thread(()-> {
            System.out.println(Thread.currentThread().getName()+" start...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()-> {
            System.out.println(Thread.currentThread().getName()+" start...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(()-> {
            System.out.println(Thread.currentThread().getName()+" start...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //启动三个线程
        t1.start();
        t2.start();
        t3.start();
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //threadGroup.list();

        //打印线程组的名称
        System.out.println("Main.groupName = "+threadGroup.getName());
        //打印线程组的名称
        System.out.println("Thread1.groupName = "+t1.getThreadGroup().getName());
        //打印线程组的数量
        System.out.println("activeCount = "+threadGroup.activeCount());

        //拿出本线程组的所有线程
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        //显示线程组的名字
        Arrays.asList(threads).forEach(e->{
            System.out.println("Thread list : "+e.getName());
        });



    }

}
