package com.flx.multi.thread.wangwenjun.design.future;

import java.util.function.Consumer;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 23:42
 * @Description
 **/
public class FutureService {

    /**
     * 主动拿结果
     * @param task
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(final FutureTask<T> task){
        AsynFuture<T> asynFuture = new AsynFuture<>();
        new Thread(()->{
            T result = task.call();
            asynFuture.done(result);
        }).start();
        return asynFuture;
    }

    /**
     * 被动给结果
     * @param task
     * @param consumer
     * @param <T>
     */
    public <T> void submit(final FutureTask<T> task, final Consumer<T> consumer){
        new Thread(()->{
            T result = task.call();
            consumer.accept(result);
        }).start();
    }

}
