package com.flx.ark.java8.lambda;

import java.util.Comparator;
import java.util.TreeSet;

public class LambdaTest {

    public static void main(String[] args) {

        testComparator();

    }

    private static void testComparator() {

        //匿名内部类
        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        };

        TreeSet<Integer> treeSet = new TreeSet<>(comparator);

        treeSet.add(5);
        treeSet.add(2);
        treeSet.add(9);
        treeSet.add(1);

        System.out.println(treeSet);

    }

}
