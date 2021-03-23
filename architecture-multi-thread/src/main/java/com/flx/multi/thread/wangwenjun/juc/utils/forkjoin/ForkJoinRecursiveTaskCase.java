package com.flx.multi.thread.wangwenjun.juc.utils.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/23 11:38
 * @Description: RecursiveTask --->继承ForkJoinTask
 * 可以将大任务拆解为各个小任务进行处理
 */
public class ForkJoinRecursiveTaskCase {

    public static void main(String[] args) {

        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        //新建任务
        CalculatedRecursiveTask task = new CalculatedRecursiveTask(0, 1000);
        //提交任务
        ForkJoinTask<Integer> result = forkJoinPool.submit(task);
        try {
            //等待结果
            Integer sum = result.get();
            //输出结果
            System.out.println("sum = "+sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    private final static int max_threshold = 5;

    private static class CalculatedRecursiveTask extends RecursiveTask<Integer>{

        private final int start;
        private final int end;

        public CalculatedRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if(end-start<=max_threshold){
                return IntStream.rangeClosed(start,end).sum();
            }else {
                int middle = (start+end)/2;
                CalculatedRecursiveTask leftTask = new CalculatedRecursiveTask(start,middle);
                CalculatedRecursiveTask rightTask = new CalculatedRecursiveTask(middle+1,end);
                leftTask.fork();
                rightTask.fork();
                return leftTask.join()+rightTask.join();
            }
        }
    }

}
