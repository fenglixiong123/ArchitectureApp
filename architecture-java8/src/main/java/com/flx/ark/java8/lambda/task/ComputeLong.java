package com.flx.ark.java8.lambda.task;

@FunctionalInterface
public interface ComputeLong<T,R> {

    public R compute(T t1, T t2);

}
