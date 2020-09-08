package com.flx.multi.thread.wangwenjun.design.countdown;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 21:39
 * @Description 此方法可以让线程一起执行完再执行其他步骤
 **/
public class JDKCountDown {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(5);

        System.out.println("准备多线程处理任务.");
        //1.
        IntStream.rangeClosed(1,5).forEach(x->new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" is working");
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        },String.valueOf(x)).start());
        //2.
        latch.await();
        //3.
        System.out.println("多线程处理任务全部结束.准备第二阶段任务");
        System.out.println("///");
        System.out.println("FINISHED");

    }

}
