package com.flx.multi.thread.wangwenjun.juc.utils.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/14 23:56
 * @Description:
 */
public class SemaphoreCase {

    public static void main(String[] args) {

        final SemaphoreLock semaphoreLock = new SemaphoreLock();

        new Thread(()->{
            try {
                semaphoreLock.lock();
                System.out.println("T1 run start");
                Thread.sleep(5000);
                System.out.println("T1 run end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphoreLock.unlock();
            }
        }).start();

        new Thread(()->{
            try {
                semaphoreLock.lock();
                System.out.println("T2 run start");
                Thread.sleep(5000);
                System.out.println("T2 run end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphoreLock.unlock();
            }
        }).start();

        new Thread(()->{
            try {
                semaphoreLock.lock();
                System.out.println("T3 run start");
                Thread.sleep(5000);
                System.out.println("T3 run end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphoreLock.unlock();
            }
        }).start();

    }

    /**
     * 自定义锁
     */
    static class SemaphoreLock{

        private final Semaphore semaphore = new Semaphore(1);

        public void lock() throws InterruptedException{
            semaphore.acquire();//申请一个许可证
        }

        public void unlock(){
            semaphore.release();//释放一个许可证
        }

    }

}
