package com.flx.multi.thread.wangwenjun.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Fenglixiong
 * @Date: 2021/2/24 18:08
 * @Description:
 */
public class AtomicLock {

    private final AtomicInteger value = new AtomicInteger(0);

    private Thread currentThread;

    public void tryLock() throws Exception{
        boolean success = value.compareAndSet(0,1);
        if(!success){
            throw new Exception("GetLockFailed!");
        }else {
            currentThread = Thread.currentThread();
        }
    }

    public void unlock(){
        if(0==value.get()){
            return;
        }
        if(currentThread==Thread.currentThread()) {
            value.compareAndSet(1,0);
        }
    }

}
