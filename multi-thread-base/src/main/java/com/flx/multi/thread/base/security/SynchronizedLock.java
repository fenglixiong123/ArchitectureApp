package com.flx.multi.thread.base.security;

/**
 * @Author Fenglixiong
 * @Create 2020/7/30 22:22
 * @Description
 * 对公共变量的写操作会发生线程安全问题
 **/
@SuppressWarnings("Duplicates")
public class SynchronizedLock {

    private static int money = 10000;

    /**
     * 对象锁
     */
    private static final Object lock = new Object();

    /**
     * 多线程操作公共变量会引发数据安全问题
     * @param count
     */
    private static void dealMoney(int count){
        System.out.println(Thread.currentThread().getName()+":money left "+money);
        System.out.println(Thread.currentThread().getName()+":deal money "+count);
        if(Thread.currentThread().getName().equals("A")){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        money = money + count;
        System.out.println(Thread.currentThread().getName()+":money = "+money);
    }

    /**
     * 采用synchronized对象锁来避免线程安全问题
     * @param count
     */
    private static void dealMoneyWithLock(int count){
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName()+":money left "+money);
            System.out.println(Thread.currentThread().getName()+":deal money "+count);
            if (Thread.currentThread().getName().equals("A")) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            money = money + count;
            System.out.println(Thread.currentThread().getName()+":money = "+money);
        }
    }

    /**
     * 采用同步函数来避免线程安全问题
     * 原理使用的是synchronized(this)锁
     * 这里我们采用静态同步函数static
     * 当一个变量被static修饰的话存放在永久区，当class文件被加载时候就会被初始化
     * @param count
     */
    private synchronized static void dealMoneyWithLockFunction(int count){
        System.out.println(Thread.currentThread().getName()+":money left "+money);
        System.out.println(Thread.currentThread().getName()+":deal money "+count);
        if (Thread.currentThread().getName().equals("A")) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        money = money + count;
        System.out.println(Thread.currentThread().getName()+":money = "+money);

    }

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("A");
//                dealMoney(5000);
//                dealMoneyWithLock(5000);
                dealMoneyWithLockFunction(5000);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("B");
//                dealMoney(-2000);
//                dealMoneyWithLock(-2000);
                dealMoneyWithLockFunction(-2000);
            }
        });

        t1.start();
        t2.start();

    }

}
