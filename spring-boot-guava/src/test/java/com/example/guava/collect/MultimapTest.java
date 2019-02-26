package com.example.guava.collect;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

public class MultimapTest {


    @Test
    public void test() {
        Multimap<String, Integer> map = HashMultimap.create();
        map.put("FLY", 1);
        map.put("FLY", 2);
        map.put("FLY", 3);
        map.put("ALEX", 4);
        map.put("ALEX", 5);

        System.out.println(map);                    // {ALEX=[4, 5], FLY=[1, 2, 3]}
        System.out.println(map.get("FLY"));         // [1, 2, 3]
        System.out.println(map.size());             // 5
        System.out.println(map.keySet().size());    // 2
        System.out.println(map.get("FLY").size());  // 3

        Map<String, Collection<Integer>> mapView = map.asMap();
        System.out.println(mapView);                // {ALEX=[4, 5], FLY=[1, 2, 3]}
    }

}
