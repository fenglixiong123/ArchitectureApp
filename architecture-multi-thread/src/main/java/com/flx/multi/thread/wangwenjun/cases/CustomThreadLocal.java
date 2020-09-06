package com.flx.multi.thread.wangwenjun.cases;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 15:26
 * @Description
 **/
public class CustomThreadLocal<T> {

    public static void main(String[] args) {
        CustomThreadLocal<String> customThreadLocal = new CustomThreadLocal<>();
        customThreadLocal.set("AAA");
        System.out.println(customThreadLocal.get());
    }

    private final Map<Thread,T> dataMap = new HashMap<>();

    protected T initValue(){
        return null;
    }

    public T get(){
        synchronized (this){
            T result = dataMap.get(Thread.currentThread());
            if(result==null){
                return initValue();
            }
            return result;
        }
    }

    public void set(T value){
        synchronized (this){
            dataMap.put(Thread.currentThread(),value);
        }
    }

}
