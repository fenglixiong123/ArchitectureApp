package com.flx.multi.thread.two.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/23 18:18
 * @Description 本节描述创建线程时候指定线程栈
 * stackSize:可以指定当前线程所属的线程栈大小
 * 如果指定太大，会导致创建线程数量减小，因为jvm栈总的大小是一定的
 **/
public class ThreadStackChange {

    private static int count = 0;

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    add(0);
                }catch (Error e){
                    System.out.println("When StackOverflowError count = "+count);
                }
            }
        };
        Thread t = new Thread(null,runnable,"StackThread",1<<24);
        t.start();
    }

    public static void add(int i){
        count ++;
        add(i+1);
    }

}
