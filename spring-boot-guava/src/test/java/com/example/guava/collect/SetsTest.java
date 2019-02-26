package com.example.guava.collect;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SetsTest {

    /**
     * filter：返回传入Set集合unfiltered中满足给定Predicate的元素集合Set
     */
    @Test
    public void testFilter() {
        Set<String> set = Sets.newHashSet("i like u", "i miss u", "i love u");
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                //过滤包含字母l的元素
                return input.contains("l");
            }
        };
        System.out.println(Sets.filter(set, predicate));                        // [i like u, i love u]
        System.out.println(Sets.filter(set, input -> input.contains("l")));     // [i like u, i love u]
    }

    /**
     * difference：返回两个set集合的差的不可修改SetView
     * A，B是两个集合，则所有属于A且不属于B的元素构成的集合，叫做A与B的差集
     */
    @Test
    public void testDifference() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4, 5);
        Set<Integer> set2 = Sets.newHashSet(1, 3, 5, 7, 9);
        System.out.println(Sets.difference(set1, set2));        // [2, 4]
    }

    /**
     * symmetricDifference：返回两个set集合的对称差的不可修改SetView
     * 对称差，即两个集合的对称差是只属于其中一个集合，而不属于另一个集合的元素组成的集合
     */
    @Test
    public void testSymmetricDifference() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4, 5);
        Set<Integer> set2 = Sets.newHashSet(1, 3, 5, 7, 9);
        System.out.println(Sets.symmetricDifference(set1, set2));       // [2, 4, 9, 7]
    }

    /**
     * intersection：返回两个set集合的交集的不可修改SetView
     * 两个集合A和集合B的交集是指含有所有既属于A又属于B的元素，而没有其他元素的集合
     */
    @Test
    public void testIntersection() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4, 5);
        Set<Integer> set2 = Sets.newHashSet(1, 3, 5, 7, 9);
        System.out.println(Sets.intersection(set1, set2));      // [1, 3, 5]
    }

    /**
     * Union：返回两个set集合的并集的不可修改SetView
     * 若A和B是集合，则A和B并集是有所有A的元素和所有B的元素，而没有其他元素的集合
     */
    @Test
    public void testUnion() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4, 5);
        Set<Integer> set2 = Sets.newHashSet(1, 3, 5, 7, 9);
        System.out.println(Sets.union(set1, set2));     // [1, 2, 3, 4, 5, 9, 7]
    }

    /**
     * cartesianProduct：返回通过从各给定集中选择一个元素所形成每一个可能的集合
     */
    @Test
    public void testCartesianProduct() {
        Set<String> set1 = Sets.newHashSet("i love u", "i hate u");
        Set<String> set2 = Sets.newHashSet("tom", "jerry");
        Set<List<String>> sets = Sets.cartesianProduct(set1, set2);
        System.out.println(sets);       // [[i hate u, tom], [i hate u, jerry], [i love u, tom], [i love u, jerry]]
    }

    /**
     * powerSet：返回一个set，包含给定set的所有可能父级集合
     */
    @Test
    public void testPowerSet() {
        Set<String> set1 = Sets.newHashSet("A", "B", "C");
        Set<Set<String>> sets = Sets.powerSet(set1);
//        for (Set<String> set : sets) {
//            System.out.println(set);
//        }
        System.out.println(Arrays.toString(sets.toArray()));    // [[], [A], [B], [A, B], [C], [A, C], [B, C], [A, B, C]]
    }
}
