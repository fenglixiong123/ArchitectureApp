package com.flx.multi.thread.wangwenjun.cases;

/**
 * @Author Fenglixiong
 * @Create 2020/8/22 17:28
 * @Description
 * 多窗口售票案例
 * 验证多线程执行会出现数据安全问题，采用线程安全方法解决
 **/
public class ChinaBank {

    public static void main(String[] args) {
        Thread t = new Thread();
        System.out.println(t.getThreadGroup().getName());
        System.out.println(Thread.currentThread().getThreadGroup().getName());
        TicketTask w = new TicketTask();
        Thread t1 = new Thread(w,"柜台1");
        Thread t2 = new Thread(w,"柜台2");
        Thread t3 = new Thread(w,"柜台3");
        t1.start();
        t2.start();
        t3.start();

    }

    static class TicketTask implements Runnable{

        private final int max = 50;

        private int index = 1;

        private final Object MONITOR = new Object();

        public void run() {
            while (true){
                synchronized (MONITOR){
                    if(index > max){
                        break;
                    }
                    System.out.println(Thread.currentThread().getName()+"-当前号码是："+(index++));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}

