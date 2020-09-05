package com.flx.multi.thread.wangwenjun;

import java.util.concurrent.*;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 0:15
 * @Description
 * jdk自带的future异步任务模式来执行任务
 **/
public class ThreadFutureApi {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //声明任务
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return dealDbWork();
            }
        });
        //声明执行任务的实体
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        //提交任务给线程池
        executorService.submit(task);
        //做其他事情无须等待结果
        System.out.println("do other tings.......");
        Thread.sleep(2000);
        //做完事情来获取结果
        Integer result = task.get();
        System.out.println("obtain result = "+result);
        //关闭线程池
        executorService.shutdown();
    }

    /**
     * 处理数据查询微信昵称有多少个
     * @return
     */
    public static int dealDbWork(){
        try {
            for (int i = 1; i <= 10; i++) {
                Thread.sleep(1000);
                System.out.println("read db..."+i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 999;
    }

}
