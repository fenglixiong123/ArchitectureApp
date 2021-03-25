package com.flx.multi.thread.wangwenjun.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/24 2:32
 * @Description
 * 场景：多个线程分别去采集各个服务器状态，然后采集完统一记录采集数据以及最后结束时间
 * 思想：
 * 主线程要等各个子线程执行完成后再进行数据写入工作
 * 子线程进行join操作
 **/
public class ThreadJoinDeep {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thread t1 = new Thread(new CaptureTask("M1",5_000,startTime));
        Thread t2 = new Thread(new CaptureTask("M2",20_000,startTime));
        Thread t3 = new Thread(new CaptureTask("M3",10_000,startTime));

        t1.start();
        t2.start();
        t3.start();

        //主线程需要等待采集线程执行完再执行接下来的工作
        t1.join();
        t2.join();
        t3.join();

        long endTime = System.currentTimeMillis();

        System.out.println("Save data begin at "+startTime+" , end at "+endTime);
        System.out.println("Capture data use time "+(endTime-startTime)/1000+" s");
    }

    static class CaptureTask implements Runnable{

        private String machineName;

        private long spendTime;

        private long startTime;

        public CaptureTask(String machineName,long spendTime,long startTime) {
            this.machineName = machineName;
            this.spendTime = spendTime;
            this.startTime = startTime;
        }

        @Override
        public void run() {
            //采集服务器信息
            try {
                Thread.sleep(spendTime);
                System.out.println(machineName+" completed data capture and successful , useTime = "+(System.currentTimeMillis()-startTime)/1000+" s");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getResult(){
            return machineName+" finished !";
        }

    }

}


