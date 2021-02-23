package com.flx.multi.thread.wangwenjun.base;

import java.util.Arrays;

/**
 * @Author: Fenglixiong
 * @Date: 2021/2/23 14:10
 * @Description:
 * 使当前线程由执行状态，--->变成为就绪状态--->，让出CPU，在下一个线程执行时候，此线程有可能被执行，也有可能没有被执行
 * 当某个线程调用了yield()方法暂停之后，只有优先级与当前线程相同，或者优先级比当前线程更高的处于就绪状态的线程才会获得执行机会。
 */
public class ThreadYield {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println("t1 work ");
                Arrays.asList(1,2,3,4,5,6,7,8,9).forEach(e->{
                    System.out.println("t1----->"+e);
                    if(e.equals(3)){
                        System.out.println("t1 yield");
                        Thread.yield();
                    }
                });
            }
        };

        Thread t2 = new Thread(()->{
            System.out.println("t2 work ");
            Arrays.asList(1,2,3,4,5,6,7,8,9).forEach(e->{
                System.out.println("t2----->"+e);
            });
        });

        t1.setPriority(Thread.NORM_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();

    }

}
