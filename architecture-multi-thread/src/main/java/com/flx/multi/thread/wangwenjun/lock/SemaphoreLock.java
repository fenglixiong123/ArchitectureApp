package com.flx.multi.thread.wangwenjun.lock;

import java.util.concurrent.Semaphore;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/15 16:51
 * @Description: 许可证锁(可以控制线程并发)
 */
public class SemaphoreLock {

    private final Semaphore semaphore = new Semaphore(1);

    public void lock() throws InterruptedException{
        semaphore.acquire();//申请一个许可证
    }

    public void unlock(){
        semaphore.release();//释放一个许可证
    }

}
