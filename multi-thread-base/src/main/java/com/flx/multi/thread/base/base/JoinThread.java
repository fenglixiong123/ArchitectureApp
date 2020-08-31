package com.flx.multi.thread.base.base;

/**
 * @Author Fenglixiong
 * @Create 2020/7/29 1:42
 * @Description
 * 线程执行优先权
 * 调用哪个线程的join方法则要这个线程先于自己执行（即join方法所属的线程有限执行）
 **/
public class JoinThread {

    public static void main(String[] args) {
        final Thread threadA =  new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    System.out.println("子线程A："+i);
                }
            }
        });
        Thread threadB =  new Thread(new Runnable() {
            public void run() {
                try {
                    threadA.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 1; i <= 20; i++) {
                    System.out.println("子线程B："+i);
                }
            }
        });
//        threadA.start();
//        threadB.start();

        ThreadOne threadOne = new ThreadOne();
        threadOne.start();
        ThreadTwo threadTwo = new ThreadTwo(threadOne);
        threadTwo.start();

    }

}

class ThreadOne extends Thread{

    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            System.out.println("子线程One："+i);
        }
    }
}

class ThreadTwo extends Thread{

    public ThreadTwo(Thread t){
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            System.out.println("子线程Two："+i);
        }
    }
}