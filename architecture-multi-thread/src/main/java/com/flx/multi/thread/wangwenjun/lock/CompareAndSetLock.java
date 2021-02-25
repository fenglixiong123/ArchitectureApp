package com.flx.multi.thread.wangwenjun.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Fenglixiong
 * @Date: 2021/2/24 18:08
 * @Description:
 */
public class CompareAndSetLock {

    public static void main(String[] args) throws Exception{



    }

    private final AtomicInteger value = new AtomicInteger(0);

    public void tryLock() throws Exception{

        boolean success = value.compareAndSet(0,1);

        if(!success){

        }

    }

}
