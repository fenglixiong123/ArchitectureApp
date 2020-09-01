package com.flx.multi.thread.two.base;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Fenglixiong
 * @Create 2020/8/29 20:58
 * @Description
 **/
public class ThreadGroupRun {

    public static void main(String[] args) throws InterruptedException {

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println("CurrentThreadGroup : "+threadGroup.getName());

        ThreadGroup tg1 = new ThreadGroup("tg1");
        ThreadGroup tg2 = new ThreadGroup(tg1,"tg2");

        //当线程组中最后一个线程结束时候，整个线程组销毁
        tg2.setDaemon(true);

        Thread t1 = new Thread(tg1,()->{
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T1");

        t1.start();

        Thread t2 = new Thread(tg2,()->{
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T2");
        t2.start();

        Thread[] threads = new Thread[tg1.activeCount()];
        tg1.enumerate(threads);
        List<String> threadNames = Stream.of(threads).map(Thread::getName).collect(Collectors.toList());

        System.out.println("t1.group.name = "+t1.getThreadGroup().getName());
        System.out.println("t1.group.parent = "+t1.getThreadGroup().getParent().getName());
        System.out.println("t1.group.activeCount = "+t1.getThreadGroup().activeCount());
        System.out.println("t1.group.activeGroupCount = "+t1.getThreadGroup().activeGroupCount());
        System.out.println("t1.group.enumerate = "+threadNames);
        System.out.println("t1.group.isDestroy = "+tg1.isDestroyed());

        System.out.println("t2.group.name = "+tg2.getName());
        System.out.println("t2.group.parent = "+t2.getThreadGroup().getParent().getName());
        System.out.println("t2.group.activeCount = "+t2.getThreadGroup().activeCount());

        Thread.sleep(10_000);

        System.out.println("t1.group.isDestroy = "+tg1.isDestroyed());
        tg1.destroy();
        System.out.println("t1.group.isDestroy = "+tg1.isDestroyed());
        System.out.println("t2.group.isDestroy = "+tg2.isDestroyed());

    }

}
