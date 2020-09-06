package com.flx.multi.thread.wangwenjun;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 15:06
 * @Description ThreadLocal讲解
 *
 **/
public class ThreadLocalApi {

    private final static ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
        //设置初始值
        @Override
        protected String initialValue() {
            return "Fenglixiong";
        }
    };

    //记录各个线程的执行初始时间
    private static ThreadLocal<Long> threadLocalTime = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" :"+threadLocal.get());
        }).start();

        new Thread(()->{
            threadLocalTime.set(System.currentTimeMillis());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long costTime = System.currentTimeMillis() - threadLocalTime.get();
            System.out.println(Thread.currentThread().getName()+" costTime:"+costTime);
        }).start();

        new Thread(()->{
            threadLocalTime.set(System.currentTimeMillis());
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long costTime = System.currentTimeMillis() - threadLocalTime.get();
            System.out.println(Thread.currentThread().getName()+" costTime:"+costTime);
        }).start();

    }

}
