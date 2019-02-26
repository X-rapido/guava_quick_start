package com.example.guava.basic;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import junit.framework.TestCase;

import java.util.Set;

/**
 * Range 表示一个间隔或一个序列。它被用于获取一组数字/串在一个特定范围之内。
 */
public class RangeTest extends TestCase {

    public void test1() {
        Range.closedOpen(4, 4).isEmpty();       // returns true
        Range.openClosed(4, 4).isEmpty();       // returns true
        Range.closed(4, 4).isEmpty();           // returns false
        Range.closed(3, 10).lowerEndpoint();    // returns 3
        Range.open(3, 10).lowerEndpoint();      // returns 3
        Range.closed(3, 10).lowerBoundType();   // returns CLOSED
        Range.open(3, 10).upperBoundType();     // returns OPEN
//        Range.open(4, 4).isEmpty();             // Range.open throws IllegalArgumentException
    }

    public void test2() {
//        assertEquals("(1..10)", Range.open(1, 10));
        System.out.println(Range.open(1, 10).toString());
        System.out.println("open：" + Range.open(1, 10));
        System.out.println("closed：" + Range.closed(1, 10));
        System.out.println("closedOpen：" + Range.closedOpen(1, 10));
        System.out.println("openClosed：" + Range.openClosed(1, 10));
        System.out.println("greaterThan：" + Range.greaterThan(10));
        System.out.println("atLeast：" + Range.atLeast(10));
        System.out.println("lessThan：" + Range.lessThan(10));
        System.out.println("atMost：" + Range.atMost(10));
        System.out.println("all：" + Range.all());
        System.out.println("closed：" + Range.closed(10, 10));
        System.out.println("closedOpen：" + Range.closedOpen(10, 10));
        // 会抛出 IllegalArgumentException 异常
//        System.out.println("open：" + Range.open(10, 10));
    }

    /**
     * 相连 isConnected
     */
    public void test3() {
        Range.closed(3, 5).isConnected(Range.open(5, 10));      // returns true
        Range.closed(0, 9).isConnected(Range.closed(3, 4));     // returns true
        Range.closed(0, 5).isConnected(Range.closed(3, 9));     // returns true
        Range.open(3, 5).isConnected(Range.open(5, 10));        // returns false
        Range.closed(1, 5).isConnected(Range.closed(6, 10));    // returns false
    }

    /**
     * 交集 intersection
     */
    public void test4() {
        Range.closed(3, 5).intersection(Range.open(5, 10));     // returns (5, 5]
        Range.closed(0, 9).intersection(Range.closed(3, 4));    // returns [3, 4]
        Range.closed(0, 5).intersection(Range.closed(3, 9));    // returns [3, 5]
        Range.open(3, 5).intersection(Range.open(5, 10));       // throws IAE
        Range.closed(1, 5).intersection(Range.closed(6, 10));   // throws IAE
    }

    /**
     * 跨区间 span
     */
    public void test5() {
        Range.closed(3, 5).span(Range.open(5, 10)); // returns [3, 10)
        Range.closed(0, 9).span(Range.closed(3, 4)); // returns [0, 9]
        Range.closed(0, 5).span(Range.closed(3, 9)); // returns [0, 9]
        Range.open(3, 5).span(Range.open(5, 10)); // returns (3, 10)
        Range.closed(1, 5).span(Range.closed(6, 10)); // returns [1, 10]
    }

    /**
     * 离散域 DiscreteDomain
     */
    public void test6() {
        Range<Integer> range = Range.closed(20, 30);
        print("closed", ContiguousSet.create(range, DiscreteDomain.integers()));

        range = Range.open(20, 30);
        print("open", ContiguousSet.create(range, DiscreteDomain.integers()));

        range = Range.openClosed(20, 30);
        print("openClosed", ContiguousSet.create(range, DiscreteDomain.integers()));

        range = Range.closedOpen(20, 30);
        print("closedOpen", ContiguousSet.create(range, DiscreteDomain.integers()));


        range = Range.greaterThan(20);
        System.out.println("greaterThan: " + ContiguousSet.create(range, DiscreteDomain.integers()).toString());

        range = Range.atLeast(20);
        System.out.println("atLeast: " + ContiguousSet.create(range, DiscreteDomain.integers()).toString());

        range = Range.lessThan(30);
        System.out.println("lessThan: " + ContiguousSet.create(range, DiscreteDomain.integers()).toString());

        range = Range.atMost(30);
        System.out.println("atMost: " + ContiguousSet.create(range, DiscreteDomain.integers()).toString());

        range = Range.all();
        System.out.println("all: " + ContiguousSet.create(range, DiscreteDomain.integers()).toString());
    }

    public static void print(String type, Set<Integer> ranges) {
        System.out.println(type + "：" + ranges + "      list：" + Lists.newArrayList(ranges));
    }

    public void test7() {
        for (Object i : ContiguousSet.create(Range.closed('a', 'z'), LowerCaseDomain.letters())) {
            System.out.print(i + " ");
        }

        System.out.println();

        for(Object i : ContiguousSet.create(Range.atLeast('m'), LowerCaseDomain.letters())) {
            System.out.print(i + " ");
        }
    }


    public void test8(){
        // 1.使用Range.closed() 创建范围: [a,b] = { x | a <= x <= b}
        Range<Integer> range1 = Range.closed(0, 9);
        System.out.println("新建数据范围range1："+range1);
        printRange(range1);

        // 1.1包含关系
        Boolean containVal = range1.contains(1);
        Boolean containAllVal1 = range1.containsAll(Ints.asList(1,3,5));
        Boolean containAllVal2 = range1.containsAll(Ints.asList(1,3,5,12));
        System.out.println("是否包含1："+containVal);
        System.out.println("是否包含1，3，5："+containAllVal1);
        System.out.println("是否包含1，3，5，12："+containAllVal2);

        // 1.2边界值
        Boolean bol1 = range1.hasLowerBound();
        Boolean bol2 = range1.hasUpperBound();
        System.out.println("是否存在最小边界值："+bol1);
        System.out.println("是否存在最大边界值："+bol2);

        Integer lower = range1.lowerEndpoint();
        Integer upper = range1.upperEndpoint();
        System.out.println("最小边界值："+lower);
        System.out.println("最大边界值："+upper);
        System.out.println();


        // 2.使用Range.open()创建范围: (a,b) = { x | a < x < b}
        Range<Integer> range2 = Range.open(0,9);
        System.out.println("新建数据范围range2："+range2);
        printRange(range2);
        System.out.println();


        // 3.使用Range.openClosed()创建范围： (a,b] = { x | a < x <= b}
        Range<Integer> range3 = Range.openClosed(0, 9);
        System.out.println("新建数据范围range3："+range3);
        printRange(range3);
        System.out.println();


        // 4.使用Range.closedOpen()创建范围： [a,b) = { x | a <= x < b}
        Range<Integer> range4 = Range.closedOpen(0, 9);
        System.out.println("新建数据范围range4："+range4);
        printRange(range4);
        System.out.println();


        // 5.右无穷大 a>9
        Range<Integer> range5 = Range.greaterThan(9);
        System.out.println("新建数据范围range5："+range5);

        // 5.1边界值
        Boolean bol3 = range5.hasLowerBound();
        Boolean bol4 = range5.hasUpperBound();
        System.out.println("是否存在最小边界值："+bol3);
        System.out.println("是否存在最大边界值："+bol4);

        Integer lower1 = range5.lowerEndpoint();
        //Integer upper1 = range5.upperEndpoint(); //抛java.lang.IllegalStateException: range unbounded on this side
        System.out.println("最小边界值："+lower1);
        //System.out.println("最大边界值："+upper1);
        System.out.println();

        // 6.子范围
        Range<Integer> range6 = Range.closed(3, 5);
        System.out.println("新建数据范围："+range6);
        printRange(range6);
        Boolean subRange = range1.encloses(range6);
        System.out.println("范围range1是否包含range6："+subRange);
        System.out.println();


        // 7.承接关系
        Range<Integer> range7 = Range.closed(9, 20);
        System.out.println("新建数据范围："+range7);
        printRange(range7);
        Boolean connected = range7.isConnected(range1);
        System.out.println("范围range7是否承接range1："+connected);
        System.out.println();


        // 8.范围交叉,取交集、并集
        Range<Integer> range8 = Range.closed(5, 15);
        Range<Integer> intersection = range1.intersection(range8);
        System.out.println("范围range1和range8的交集："+intersection);
        printRange(intersection);
        Range<Integer> span = range1.span(range8);
        System.out.println("范围range1和range8的并集："+span);
        printRange(span);
    }

    private static void printRange(Range<Integer> range){
        System.out.print("具体数据范围：");
        System.out.print("[ ");
        for(int grade : ContiguousSet.create(range, DiscreteDomain.integers())) {
            System.out.print(grade +" ");
        }
        System.out.println("]");
    }
}
