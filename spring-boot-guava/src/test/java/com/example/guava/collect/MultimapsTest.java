package com.example.guava.collect;

import com.google.common.collect.*;
import org.junit.Test;

public class MultimapsTest {

    @Test
    public void asMap() {
        ListMultimap<String, String> listMultimap = LinkedListMultimap.create();
        listMultimap.put("foo", "A");
        listMultimap.put("foo", "B");
        listMultimap.put("foo", "C");

        // 遍历
        listMultimap.forEach((k, v) -> System.out.println("k：" + k + ", v：" + v));

        System.out.println(listMultimap);
    }


    @Test
    public void containsOccurrences(){
        Multiset<String> set1 = HashMultiset.create();
        set1.add("a", 2);
        set1.add("b");
        set1.add("b");

        Multiset<String> set2 = HashMultiset.create();
        set2.add("a", 5);

        System.out.println(set1.containsAll(set2)); // true

        System.out.println(Multisets.containsOccurrences(set1, set2)); // returns false
        System.out.println(set2.remove("a",2));    // 5
        System.out.println(set2.size());            // 3
        System.out.println(set2.removeAll(set1));   // true
        System.out.println(set2.size());            // 0
        System.out.println(set2.isEmpty());         // true
    }

    @Test
    public void copyHighestCountFirst(){
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a", 3);
        multiset.add("b", 5);
        multiset.add("c", 1);
        multiset.add("a");
        multiset.add("a");
        multiset.add("a");

        System.out.println(multiset.count("a"));        // 6
        System.out.println(multiset.size());                    // 12

        ImmutableMultiset<String> highestCountFirst = Multisets.copyHighestCountFirst(multiset);

        System.out.println(highestCountFirst.size());           // 12
        System.out.println(highestCountFirst.entrySet());       // [a x 6, b x 5, c]
        System.out.println(highestCountFirst.elementSet());     // [a, b, c]
    }

    @Test
    public void retainOccurrences(){
        Multiset<String> set1 = HashMultiset.create();
        set1.add("a", 2);
        set1.add("b");
        set1.add("b");

        Multiset<String> set2 = HashMultiset.create();
        set2.add("a", 5);

        System.out.println(set1);       // [a x 2, b x 2]
        System.out.println(set2);       // [a x 5]

        Multisets.retainOccurrences(set1, set2);

        System.out.println("\n"+set1);  // [a x 2]
        System.out.println(set2);       // [a x 5]
    }

    @Test
    public void difference_sum_union(){
        Multiset<String> set1 = HashMultiset.create();
        set1.add("a", 3);
        set1.add("b", 2);
        set1.add("c", 1);

        Multiset<String> set2 = HashMultiset.create();
        set2.add("a");
        set2.add("c");
        set2.add("e");

        // 差集
        Multiset<String> differenceSet = Multisets.difference(set1, set2);
        System.out.println(differenceSet);  // [a x 2, b x 2]

        // 合集
        Multiset<String> sumSet = Multisets.sum(set1, set2);
        System.out.println(sumSet);         // [a x 4, b x 2, c x 2, e]

        // 并集
        Multiset<String> unionSet = Multisets.union(set1, set2);
        System.out.println(unionSet);       // [a x 3, b x 2, c, e]
    }

}
