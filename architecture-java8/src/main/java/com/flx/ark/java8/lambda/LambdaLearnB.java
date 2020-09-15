package com.flx.ark.java8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *
 * java8 四大内置函数式接口
 * Consumer<T> :消费接口
 *      void accept(T t)
 * Supplier<T> :供给型接口
 *      T get()
 * Function<T,R>:函数型接口
 *      R apply(T t)
 * Predicate<T>:断言型接口
 *      boolean test(T t)
 *
 */
public class LambdaLearnB {

    public static void main(String[] args) {

        testPredicate();
        testFunction();
        testConsumer();
        testSupply();
    }

    /**
     * 方法型接口
     */
    public static void testFunction(){
        System.out.println(strHandler("asdasasda",e->e.toUpperCase()));
    }

    private static String strHandler(String str, Function<String,String> function){
        return function.apply(str);
    }

    /**
     * 消费性接口
     */
    private static void testConsumer(){
        happy(1000,e-> System.out.println("消费了"+e+"元！"));
    }

    private static void happy(double money, Consumer<Double> consumer){
        consumer.accept(money);
    }

    /**
     * 供给型接口
     */
    private static void testSupply(){
        System.out.println(getNumberList(5,()-> (int) (Math.random()*100)));
    }

    private static List<Integer> getNumberList(int num, Supplier<Integer> supplier){

        List<Integer> list = new ArrayList<>();
        for (int i=0;i<num;i++){
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * 断言型接口
     */
    private static void testPredicate(){
        List list = Arrays.asList("abs","mds","efg");
        System.out.println(filterStr(list,x->x.startsWith("a")));
    }

    private static List<String> filterStr(List<String> list, Predicate<String> predicate){
        List<String> retList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            if(predicate.test(list.get(i))){
                retList.add(list.get(i));
            }
        }
        return retList;
    }

}
