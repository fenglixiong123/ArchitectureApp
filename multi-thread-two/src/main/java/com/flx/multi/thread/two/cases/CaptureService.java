package com.flx.multi.thread.two.cases;

import java.util.*;
import java.util.stream.Stream;

/**
 * @Author Fenglixiong
 * @Create 2020/8/29 16:19
 * @Description
 * 数据采集案例
 * 限制线程同时执行数量不能超过5个
 **/
public class CaptureService {

    final static private int MAX_WORK = 5;
    final static private LinkedList<Control> controls = new LinkedList<>();

    public static void main(String[] args) {

        List<Thread> workers = new ArrayList<>();
        Stream.of("M1","M2","M3","M4","M5","M6","M7","M8","M9","M10").
                map(CaptureService::createThread)
                .forEach(t->{
                    t.start();
                    workers.add(t);
                });
        workers.forEach(x-> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Optional.of("All of capture work finished !").ifPresent(System.out::println);
    }

    public static Thread createThread(String name){
        return new Thread(()->{
            Optional.of("The work ["+Thread.currentThread().getName()+"] begin capture data !").ifPresent(System.out::println);
            //工作线程不能超过5个
            synchronized (controls){
                while (controls.size() > MAX_WORK){
                    try {
                        controls.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                controls.addLast(new Control());
            }

            Optional.of("The work ["+Thread.currentThread().getName()+"] is working ...").ifPresent(System.out::println);
            //开始工作采集数据
            try {
                Thread.sleep(20_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (controls){
                Optional.of("The work ["+Thread.currentThread().getName()+"] end capture work !").ifPresent(System.out::println);
                controls.removeFirst();
                controls.notifyAll();
            }
        },name);
    }

    public static class Control{

    }

}
