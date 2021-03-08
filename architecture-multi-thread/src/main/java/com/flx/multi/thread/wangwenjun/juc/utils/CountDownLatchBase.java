package com.flx.multi.thread.wangwenjun.juc.utils;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/8 16:34
 * @Description: 门栓，插销
 */
public class CountDownLatchBase {

    private static Random random = new Random(System.currentTimeMillis());

    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    /**
     * 计数器,总数为10
     * 每调用一次latch.countDown，数量会-1，等计数结束流程会继续走下去
     */
    private static final CountDownLatch latch = new CountDownLatch(10);

    /**
     * 案例：创建线程池分别将数组中偶数*2 奇数*10，最后同时获得结果
     */
    public static void main(String[] args) throws InterruptedException {

        int[] data = query();

        for (int i = 0; i < data.length; i++) {
            executor.execute(new SimpleRunnable(data,i,latch));
        }

        latch.await();

        executor.shutdown();
        System.out.println("Result = "+ Arrays.toString(data));
        System.out.println("All work finished!");

    }

    static class SimpleRunnable implements Runnable{

        private final int[] data;

        private final int index;

        private final CountDownLatch latch;

        public SimpleRunnable(int[] data, int index, CountDownLatch latch) {
            this.data = data;
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int value = data[index];
            if(value%2==0){
                data[index] = value*2;
            }else {
                data[index] = value*10;
            }
            System.out.println(Thread.currentThread().getName()+" has update value : "+data[index]);
            latch.countDown();
        }
    }

    public static int[] query(){
        return new int[]{1,2,3,4,5,6,7,8,9,10};
    }

}
