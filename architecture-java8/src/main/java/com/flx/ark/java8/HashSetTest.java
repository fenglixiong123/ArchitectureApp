package com.flx.ark.java8;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HashSetTest {

    public static void main(String[] args) {

        Map map = new HashMap();
        map.put("abc","abs");
        map.put("abc","abx");
        map.put(null,123);
        System.out.println(map);

        Set set = new HashSet();
        set.add(new Person("jack",12));
        set.add(new Person("jack",13));
        set.add(null);
        System.out.println(set);

    }

}
