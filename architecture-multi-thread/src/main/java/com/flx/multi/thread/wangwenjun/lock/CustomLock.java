package com.flx.multi.thread.wangwenjun.lock;

import java.util.Collection;

/**
 * @Author Fenglixiong
 * @Create 2020/8/29 16:54
 * @Description 自定义锁
 **/
public interface CustomLock {

    class TimeOutException extends Exception{

        public TimeOutException(String message){
            super(message);
        }

    }

    void lock() throws InterruptedException;

    void lock(long timeout) throws InterruptedException,TimeOutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();

}
