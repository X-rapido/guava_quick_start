package com.example.guava.collect;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import junit.framework.TestCase;

public class RangeSetTest extends TestCase {

    public void testRangeSet() {
        RangeSet rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10));
        System.out.println(rangeSet);   // [[1..10]]

        rangeSet.add(Range.closedOpen(11, 15));
        System.out.println(rangeSet);   // [[1..10], [11..15)]

        rangeSet.add(Range.open(15, 20));
        System.out.println(rangeSet);   // [[1..10], [11..15), (15..20)]

        rangeSet.add(Range.openClosed(0, 0));
        System.out.println(rangeSet);   // [[1..10], [11..15), (15..20)]

        rangeSet.remove(Range.open(5, 10));
        System.out.println(rangeSet);   // [[1..5], [10..10], [11..15), (15..20)]

        System.out.println("\n遍历");
//        rangeSet.asDescendingSetOfRanges().forEach(System.out::println);  // 倒序
        rangeSet.asRanges().forEach(System.out::println);

        System.out.println("\n互补范围");
        RangeSet complement = rangeSet.complement();
        System.out.println(complement);

        System.out.println("\n包含");
        boolean isIn = rangeSet.contains(15);
        System.out.println(isIn);   //false，因为上述范围不包含元素15.

        System.out.println("\n查找范围");
        Range integerRange = rangeSet.rangeContaining(17);
        System.out.println(integerRange);   // (15..20)
    }
}
