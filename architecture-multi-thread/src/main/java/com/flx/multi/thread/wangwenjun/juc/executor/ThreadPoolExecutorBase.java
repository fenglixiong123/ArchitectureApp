package com.flx.multi.thread.wangwenjun.juc.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/29 14:49
 * @Description:
 */
public class ThreadPoolExecutorBase {

    private static final AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), r->new Thread(r,"T-"+i.incrementAndGet()), (r, e) -> {throw new RuntimeException("too much task!");});
        IntStream.range(0,20).forEach(i->{
//            poolExecutor.submit("");
        });
    }

}
