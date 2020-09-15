package com.flx.ark.java8.lambda;

import java.util.TreeSet;

public class LambdaTest {

    public static void main(String[] args) {

        testComparator();

    }

    private static void testComparator() {

        //匿名内部类

        TreeSet<Integer> treeSet = new TreeSet<>(Integer::compare);

        treeSet.add(5);
        treeSet.add(2);
        treeSet.add(9);
        treeSet.add(1);

        System.out.println(treeSet);

    }

}
