package com.example.guava.collect;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.TreeMultiset;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class MultisetsTest extends TestCase {

    public void test1() {
        // 1、创建 multiset 集合
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("d");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("b");
        multiset.add("b");
        multiset.add("b");

        // 个数
        System.out.println("Occurrence of 'b' : " + multiset.count("b"));
        System.out.println("Total Size : " + multiset.size());

        // 遍历
        Set<String> set = multiset.elementSet();
        System.out.print("Set [");
        for (String s : set) {
            System.out.print(s);
        }
        System.out.println("]");

        // 使用迭代器
        Iterator<String> iterator = multiset.iterator();
        System.out.print("MultiSet [");
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
        }
        System.out.println("]");

        // 不同元素的个数
        System.out.println("MultiSet [");
        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            System.out.println("    Element: " + entry.getElement() + ", Occurrence(s): " + entry.getCount());
        }
        System.out.println("]");

        // 删除元素
        multiset.remove("b", 2);

        // 查找元素
        System.out.println("Occurence of 'b' : " + multiset.count("b"));
    }

    public void testNewTreeMultisetComparator() {
        TreeMultiset<String> multiset = TreeMultiset.create(Collections.reverseOrder());
        multiset.add("bar", 3);
        multiset.add("foo", 2);

        assertThat(multiset).containsExactly("foo", "foo", "bar", "bar", "bar").inOrder();
    }

    public void testUnion() {
        Multiset<String> ms1 = HashMultiset.create(Arrays.asList("a", "b", "a"));
        Multiset<String> ms2 = HashMultiset.create(Arrays.asList("a", "b", "b", "c"));
        Multiset<String> union = Multisets.union(ms1, ms2);

        union.forEach(System.out::println);

        assertThat(Multisets.union(ms1, ms2)).containsExactly("a", "a", "b", "b", "c");
    }



}
