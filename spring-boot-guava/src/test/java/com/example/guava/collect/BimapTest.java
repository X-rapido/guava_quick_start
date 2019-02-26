package com.example.guava.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

public class BimapTest{

    @Test
    public void test1(){
        BiMap<String, String> upperToSmall = HashBiMap.create();
        upperToSmall.put("A", "a");
        upperToSmall.put("B", "b");
        upperToSmall.put("C", "c");

        upperToSmall.forcePut("D","c");

        System.out.println(upperToSmall.get("D"));  // c

        BiMap<String, String> smallToUpper = upperToSmall.inverse();
        System.out.println(smallToUpper.get("c"));  // D

        smallToUpper.put("E","e");

        System.out.println(upperToSmall);   // {A=a, B=b, D=c, e=E}
        System.out.println(smallToUpper);   // {a=A, b=B, c=D, E=e}
    }

}
