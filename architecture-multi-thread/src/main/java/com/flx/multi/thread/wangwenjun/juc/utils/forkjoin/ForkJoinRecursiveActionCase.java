package com.flx.multi.thread.wangwenjun.juc.utils.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/23 12:27
 * @Description:
 */
public class ForkJoinRecursiveActionCase {

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //新建任务
        CalculateRecursiveAction action = new CalculateRecursiveAction(0,100);
        //提交任务
        forkJoinPool.submit(action);
        //等待执行结束
        forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);
        //输出任务
        System.out.println("sum = "+SUM.get());
    }

    private final static AtomicInteger SUM = new AtomicInteger();

    private final static int max_threshold = 3;

    private static class CalculateRecursiveAction extends RecursiveAction{

        private final int start;
        private final int end;

        public CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }


        @Override
        protected void compute() {
            if(end-start<=max_threshold){
                SUM.addAndGet(IntStream.rangeClosed(start,end).sum());
            }else {
                int middle = (start+end)/2;
                CalculateRecursiveAction leftAction = new CalculateRecursiveAction(start,middle);
                CalculateRecursiveAction rightAction = new CalculateRecursiveAction(middle+1,end);
                leftAction.fork();
                rightAction.fork();
            }
        }
    }

}
