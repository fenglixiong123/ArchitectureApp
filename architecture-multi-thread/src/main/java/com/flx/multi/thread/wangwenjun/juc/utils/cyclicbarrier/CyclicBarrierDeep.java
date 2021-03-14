package com.flx.multi.thread.wangwenjun.juc.utils.cyclicbarrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDeep {

    public static void main(String[] args) {
        CustomCountDownLatch countDownLatch = new CustomCountDownLatch(2,()->{
            System.out.println("All finished !");
        });

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                countDownLatch.countDown();
                System.out.println("T1 done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                countDownLatch.countDown();
                System.out.println("T2 done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }


    static class CustomCountDownLatch extends CountDownLatch{

        private final Runnable command;

        public CustomCountDownLatch(int count, Runnable command) {
            super(count);
            this.command = command;
        }

        @Override
        public void countDown() {
            super.countDown();
            if(this.getCount()==0){
                command.run();
            }
        }
    }

}
