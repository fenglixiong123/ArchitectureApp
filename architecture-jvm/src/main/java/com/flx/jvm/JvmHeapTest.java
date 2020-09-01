package com.flx.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fenglixiong
 * @Date: 2020/8/26 19:38
 * @Description:
 */
public class JvmHeapTest {

    byte[] a = new byte[1024*100];//100kb

    public static void main(String[] args) {

        List<JvmHeapTest> personList = new ArrayList<JvmHeapTest>();
        while (true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            personList.add(new JvmHeapTest());
        }

    }

}
