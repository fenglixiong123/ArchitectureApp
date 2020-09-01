package com.flx.multi.thread.wangwenjun.pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author Fenglixiong
 * @Create 2020/8/29 22:01
 * @Description
 **/
public class CustomPool extends Thread{


    //线程池数量
    private int poolSize;
    //任务数量
    private final int taskQueueSize;
    //拒绝策略
    private final DiscardPolicy discardPolicy;
    //自增角标
    private static volatile int seq = 1;
    //线程池前缀
    private final static String THREAD_PREFIX = "CUSTOM_THREAD_POOL-";
    //线程组
    private final static ThreadGroup GROUP = new ThreadGroup("CUSTOM_THREAD_GROUP");
    //任务队列
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    //线程队列
    private final static List<WorkTask> THREAD_QUEUE = new ArrayList<>();
    //任务队列默认数量
    private final static int DEFAULT_TASK_QUEUE_SIZE = 50;
    //默认拒绝策略
    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = ()->{ throw new DiscardException("Too much task , discard !"); };
    //线程池销毁
    private volatile boolean destroy = false;

    //线程池初始大小
    private int minPoolSize;
    //线程池最大大小
    private int maxPoolSize;
    //线程池活跃大小
    private int activePoolSize;

    public CustomPool(){
        this(4,8,12,DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public CustomPool(int minPoolSize,int activePoolSize,int maxPoolSize,int taskQueueSize,DiscardPolicy discardPolicy){
        this.minPoolSize = minPoolSize;
        this.activePoolSize = activePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        for (int i = 0; i < minPoolSize; i++) {
            createWorkTask();
        }
        this.poolSize = minPoolSize;
        this.start();
    }

    public void submit(Runnable task){
        if(destroy){
            throw new IllegalStateException("The thread pool already destroy,not allowed to submit new task !");
        }
        synchronized (TASK_QUEUE){
            if(TASK_QUEUE.size() >= taskQueueSize){
                this.discardPolicy.discard();
            }
            TASK_QUEUE.addLast(task);
            System.out.println(Thread.currentThread().getName()+" task submit , taskSize = "+TASK_QUEUE.size());
            TASK_QUEUE.notifyAll();
        }
    }

    @Override
    public void run() {
        while (!destroy){
            System.out.println("Pool-minPoolSize:"+minPoolSize+",activePoolSize:"+activePoolSize+",maxPoolSize:"+maxPoolSize+",poolSize:"+poolSize+",taskQueueSize:"+TASK_QUEUE.size());
            try {
                Thread.sleep(5000);
                //扩充线程
                if(TASK_QUEUE.size()>activePoolSize && poolSize<activePoolSize){
                    for (int i = poolSize; i < activePoolSize; i++) {
                        createWorkTask();
                    }
                    poolSize = activePoolSize;
                }else if(TASK_QUEUE.size()>maxPoolSize && poolSize<maxPoolSize){
                    for (int i = poolSize; i < maxPoolSize; i++) {
                        createWorkTask();
                    }
                    poolSize = maxPoolSize;
                }
                //回收线程
                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && poolSize > activePoolSize) {
                        int killSize = poolSize - activePoolSize;
                        Iterator<WorkTask> it = THREAD_QUEUE.iterator();
                        while (it.hasNext()) {
                            if (killSize <= 0) {
                                break;
                            }
                            WorkTask workTask = it.next();
                            workTask.close();
                            workTask.interrupt();
                            it.remove();
                            killSize--;
                        }
                        poolSize = activePoolSize;
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createWorkTask(){
        WorkTask workTask = new WorkTask(GROUP,THREAD_PREFIX + (seq++));
        workTask.start();
        THREAD_QUEUE.add(workTask);
    }

    public void shutdown() throws InterruptedException {
        while (!TASK_QUEUE.isEmpty()){
            Thread.sleep(50);
        }
        synchronized (THREAD_QUEUE) {
            int initVal = THREAD_QUEUE.size();
            while (initVal > 0) {
                for (WorkTask task : THREAD_QUEUE) {
                    if (task.getTaskStatus() == TaskStatus.BLOCKED) {
                        task.close();
                        task.interrupt();
                        initVal--;
                    } else {
                        Thread.sleep(10);
                    }
                }
            }
            this.destroy = true;
            System.out.println("The thread pool disposed !");
        }
    }

    public boolean isDestroy(){
        return this.destroy;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getTaskQueueSize() {
        return taskQueueSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getActivePoolSize() {
        return activePoolSize;
    }

    //任务状态
    private enum TaskStatus{
        FREE,RUNNING,BLOCKED,DEAD
    }

    public static class DiscardException extends RuntimeException{
        public DiscardException(String message) {
            super(message);
        }
    }

    public interface DiscardPolicy{
        void discard()throws DiscardException;
    }

    private static class WorkTask extends Thread{

        private volatile TaskStatus taskStatus = TaskStatus.FREE;

        public WorkTask(ThreadGroup group, String name) {
            super(group, name);
        }

        public TaskStatus getTaskStatus(){
            return taskStatus;
        }

        public void close(){
            this.taskStatus = TaskStatus.DEAD;
        }

        @Override
        public void run() {
            Runnable task;
            OUTER:
            while (taskStatus!=TaskStatus.DEAD){
                synchronized (TASK_QUEUE){
                    //1.当任务队列为空则等待
                    while (TASK_QUEUE.isEmpty()){
                        try {
                            taskStatus = TaskStatus.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    //2.任务队列不为空，取任务
                    task = TASK_QUEUE.removeFirst();
                }
                if(task!=null){
                    taskStatus = TaskStatus.RUNNING;
                    task.run();
                    taskStatus = TaskStatus.FREE;
                }
            }
        }
    }

}
