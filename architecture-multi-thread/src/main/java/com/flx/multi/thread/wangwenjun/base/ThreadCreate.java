package com.flx.multi.thread.wangwenjun.base;

/**
 * @Author Fenglixiong
 * @Create 2020/9/1 22:09
 * @Description 创建线程的几种方式
 * 创建方式：
 * 1.使用继承thread方式
 * 2.使用实现runnable接口方式
 * 3.使用匿名内部类方式
 * 4.使用callback
 * 5.使用线程池
 **/
public class ThreadCreate {

    public static void main(String[] args) {
        //1.使用继承thread方式
        ThreadByThread threadOne = new ThreadByThread();
        //2.使用实现runnable接口方式
        Thread threadTwo = new Thread(new ThreadByRunnable());
        //3.使用匿名内部类方式
        ThreadByAnonymous threadByAnonymous = new ThreadByAnonymous();
        Thread threadThree = threadByAnonymous.createAnonymousThread();
        threadOne.start();
        threadTwo.start();
        threadThree.start();

    }

    /**
     * 1.使用继承thread方式
     */
    static class ThreadByThread extends Thread{
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"-Thread"+"---->"+i);
                if(i==4){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 2.使用实现runnable接口方式
     */
    static class ThreadByRunnable implements Runnable{

        public void run() {
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"-Runnable"+"---->"+i);
            }
        }
    }

    /**
     * 3.使用匿名内部类方式
     */
    static class ThreadByAnonymous{

        public Thread createAnonymousThread(){
            return new Thread(new Runnable() {
                public void run() {
                    for (int i = 1; i <= 10; i++) {
                        System.out.println(Thread.currentThread().getName()+"-Anonymous"+"---->"+i);
                    }
                }
            });
        }

    }

}
