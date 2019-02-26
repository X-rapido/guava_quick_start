package com.example.guava.collect;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import junit.framework.TestCase;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RangeMapTest extends TestCase {

    public void test1() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "aaa");
        System.out.println(rangeMap);

        rangeMap.put(Range.open(3, 6), "bbb");
        System.out.println(rangeMap);

        rangeMap.put(Range.openClosed(10, 20), "aaa");
        System.out.println(rangeMap);

        rangeMap.put(Range.closed(20, 20), "aaa");
        System.out.println(rangeMap);

        rangeMap.remove(Range.closed(5, 11));
        System.out.println(rangeMap);

        System.out.println("\n遍历");
//        Map<Range<Integer>, String> rangeStringMap = rangeMap.asDescendingMapOfRanges();  // 倒序
        Map<Range<Integer>, String> rangeStringMap = rangeMap.asMapOfRanges();
        rangeStringMap.forEach((k, v) -> System.out.println("Key：" + k + "，Value：" + v));

        // 方法2
//        Map<Range<Integer>, String> rangeStringMap = rangeMap.asMapOfRanges();
//        Set<Map.Entry<Range<Integer>, String>> entries = rangeStringMap.entrySet();
//        Iterator<Map.Entry<Range<Integer>, String>> iterator = entries.iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Range<Integer>, String> next = iterator.next();
//            System.out.println("Key：" + next.getKey() + "，Value：" + next.getValue());
//        }


//        Set<Range<Integer>> rangeSet = rangeMap.asMapOfRanges().keySet();
//        rangeSet.forEach(System.out::println);
    }

}
