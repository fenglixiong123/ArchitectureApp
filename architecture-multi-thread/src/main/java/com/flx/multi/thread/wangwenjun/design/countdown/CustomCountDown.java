package com.flx.multi.thread.wangwenjun.design.countdown;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 21:49
 * @Description 自定义实现的CountDown 此方法可以让线程一起执行完再执行其他步骤
 **/
public class CustomCountDown {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {

        final CustomCountDown countDown = new CustomCountDown(6);

        System.out.println("CustomCountDown：准备多线程处理任务.");
        //1.
        IntStream.rangeClosed(1,6).forEach(x->new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" is working");
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDown.countDown();
        },String.valueOf(x)).start());
        //2.
        countDown.await();
        //3.
        System.out.println("CustomCountDown：多线程处理任务全部结束.准备第二阶段任务");
        System.out.println("///");
        System.out.println("CustomCountDown：FINISHED");

    }

    private final int total;

    private int counter;

    public CustomCountDown(int total) {
        this.total = total;
    }

    public void await() throws InterruptedException {
        synchronized (this){
            while (counter!=total){
                this.wait();
            }
        }
    }

    public void countDown(){
        synchronized (this){
            this.counter++;
            this.notifyAll();
        }
    }

}
