package com.flx.multi.thread.base.security;

/**
 * @Author Fenglixiong
 * @Create 2020/8/6 1:24
 * @Description
 *
 * volatile关键字
 * 作用保证线程之间可见，但是不保证原子性
 * 强制线程每次读取该值的时候都去“主内存”中取值。
 **/
public class VolatileVisible {

    public static void main(String[] args) {
        ThreadOne threadOne = new ThreadOne();
        new Thread(threadOne).start();
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000);
                System.out.println(i+1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadOne.setFlag(false);
    }


}

class ThreadOne implements Runnable{

    //这里如果不加关键字，会导致主线程写入主内存的值不会被实时刷新到线程的本地内存中
    private volatile boolean flag = true;

    @Override
    public void run() {
        System.out.println("Thread start ...");
        while (flag){

        }
        System.out.println("Thread end ...");
    }

    public void setFlag(boolean flag){
        this.flag = flag;
    }

}
