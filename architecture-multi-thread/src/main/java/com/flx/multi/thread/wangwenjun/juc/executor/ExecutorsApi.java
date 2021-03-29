package com.flx.multi.thread.wangwenjun.juc.executor;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/29 17:39
 * @Description: Jdk提供的线程池的静态方法
 *
 * Java提供的静态方法：
 *
 * newCachedThreadPool：用来创建一个可以无限扩大的线程池，适用于负载较轻的场景，执行短期异步任务。（可以使得任务快速得到执行，因为任务时间执行短，可以很快结束，也不会造成cpu过度切换）
 * newFixedThreadPool：创建一个固定大小的线程池，因为采用无界的阻塞队列，所以实际线程数量永远不会变化，适用于负载较重的场景，对当前线程数量进行限制。（保证线程数可控，不会造成线程过多，导致系统负载更为严重）
 * newSingleThreadExecutor：创建一个单线程的线程池，适用于需要保证顺序执行各个任务。
 * newScheduledThreadPool：适用于执行延时或者周期性任务。
 *
 */
public class ExecutorsApi {

}
