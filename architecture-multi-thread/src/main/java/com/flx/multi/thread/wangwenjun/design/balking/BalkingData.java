package com.flx.multi.thread.wangwenjun.design.balking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @Author Fenglixiong
 * @Create 2020/9/7 23:16
 * @Description
 **/
public class BalkingData {

    private final String fileName;

    private String content;
    private boolean changed;

    public BalkingData(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.changed = true;
    }

    public synchronized void change(String newContent){
        this.content = newContent;
        this.changed = true;
    }

    public synchronized void save() throws IOException {
        if(!changed){
            System.out.println(Thread.currentThread().getName()+" find no change!");
            return;
        }
        doSave();
        this.changed = false;
    }

    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName()+" do save , content = "+content);
        Writer writer = null;
        try {
            writer = new FileWriter(fileName,true);
            writer.write(content);
            writer.write("\n");
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
