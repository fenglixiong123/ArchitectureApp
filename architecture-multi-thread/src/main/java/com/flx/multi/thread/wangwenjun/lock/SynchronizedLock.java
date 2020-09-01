package com.flx.multi.thread.wangwenjun.lock;

/**
 * @Author Fenglixiong
 * @Create 2020/8/25 23:43
 * @Description
 **/
public class SynchronizedLock {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {

        Runnable task = ()->{
//            synchronizedMethod();
            synchronizedPiece();
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();

    }

    /**
     * 同步代码块方式
     */
    public static void synchronizedPiece(){
        synchronized (LOCK){
            System.out.println(Thread.currentThread().getName()+" enter task !");
            try {
                Thread.sleep(200_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 同步方法方式
     */
    public static synchronized void synchronizedMethod(){
        System.out.println(Thread.currentThread().getName()+" enter task !");
        try {
            Thread.sleep(200_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
