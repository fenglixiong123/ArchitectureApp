package com.flx.multi.thread.wangwenjun.juc.utils.countdown;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Fenglixiong
 * @Create 2021/3/8 23:20
 * @Description 给平行任务增加逻辑层次关系
 **/
public class CountDownLatchDeep {

    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {

        Event[] events = {new Event(1),new Event(2)};

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (Event event:events){
            List<Table> tables = capture(event);
            for (Table table:tables){
                TaskBatch taskBatch = new TaskBatch(2);
                TrustSourceColumn columnRunnable = new TrustSourceColumn(taskBatch, table);
                TrustSourceRecordCount countRunnable = new TrustSourceRecordCount(taskBatch, table);
                executor.submit(columnRunnable);
                executor.submit(countRunnable);
            }
        }

    }

    public static List<Table> capture(Event event){
        List<Table> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Table("table-"+event.id+"-"+i,i*1000));
        }
        return list;
    }

    static class TrustSourceRecordCount implements Runnable{
        private final TaskBatch taskBatch;
        private final Table table;
        public TrustSourceRecordCount(TaskBatch taskBatch, Table table) {
            this.taskBatch = taskBatch;
            this.table = table;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(random.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            table.targetRecordCount = table.sourceRecordCount;
//            System.out.println("The table "+table.tableName+" Target [recordCount] capture done and update the data");
            taskBatch.done(table);
        }
    }

    static class TrustSourceColumn implements Runnable{
        private final TaskBatch taskBatch;
        private final Table table;
        public TrustSourceColumn(TaskBatch taskBatch, Table table) {
            this.taskBatch = taskBatch;
            this.table = table;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(random.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            table.targetColumnSchema = table.sourceColumnSchema;
//            System.out.println("The table "+table.tableName+" Target [columns] capture done and update the data");
            taskBatch.done(table);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Event{
        int id = 0;
    }

    interface Watcher{
//        void startWatch();
        void done(Table table);
    }

    static class TaskBatch implements Watcher{

        private CountDownLatch latch;

        public TaskBatch(int size) {
            this.latch = new CountDownLatch(size);
        }

//        @Override
//        public void startWatch() {
//
//        }

        @Override
        public void done(Table table) {
            latch.countDown();
            if(latch.getCount()==0){
                System.out.println("The table "+table.getTableName()+" finish work,["+table+"]");
            }
        }
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    static class Table{
        private String tableName;
        private long sourceRecordCount = 10;
        private long targetRecordCount;
        private String sourceColumnSchema = "<table name='a'><column name='coll' type='varchar2'></table>";
        private String targetColumnSchema = "";

        public Table(String tableName, long sourceRecordCount) {
            this.tableName = tableName;
            this.sourceRecordCount = sourceRecordCount;
        }
    }



}
