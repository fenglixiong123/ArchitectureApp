package com.flx.multi.thread.wangwenjun.juc.queue;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/31 10:38
 * @Description: 阻塞队列
 *
 * 入队：
 * offer(E e)：如果队列没满，立即返回true； 如果队列满了，立即返回false-->不阻塞
 * offer(E e, long timeout, TimeUnit unit)：在队尾插入一个元素,，如果队列已满，则进入等待，带超时功能
 * put(E e)：如果队列满了，一直阻塞，直到队列不满了或者线程被中断-->阻塞
 *
 * 出队：
 * poll()：如果没有元素，直接返回null；如果有元素，出队
 * poll(long timeout, TimeUnit unit)：如果队列不空，出队；如果队列已空且已经超时，返回null
 * take()：如果队列空了，一直阻塞，直到队列不为空或者线程被中断-->阻塞
 *
 * 主要方法：
 * put(E e)：//往队列存放数据。如果队列满了，一直阻塞，直到队列不满了或者线程被中断-->阻塞
 * take()：//从队列获取数据。如果队列空了，一直阻塞，直到队列不为空或者线程被中断-->阻塞
 */
public interface BlockingQueueApi {



}
