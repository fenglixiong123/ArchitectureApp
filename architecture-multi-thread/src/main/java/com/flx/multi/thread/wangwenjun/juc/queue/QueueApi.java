package com.flx.multi.thread.wangwenjun.juc.queue;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/30 17:03
 * @Description: 超级排队接口Queue
 *
 *
 * boolean add(E e);    //将元素插入队列
 * boolean offer(E e);  //将元素插入队列，与add相比，在容量受限时应该使用这个
 * E remove();  //将队首的元素删除，队列为空则抛出异常
 * E poll();    //将队首的元素删除，队列为空则返回null
 * E element(); //获取队首元素，但不移除，队列为空则抛出异常
 * E peek();    //获取队首元素，但不移除，队列为空则返回null
 *
 * 总结，常用api：
 * 入队--->offer()
 * 出队--->poll()
 * 获取--->peek()
 *
 */
public interface QueueApi {



}
