package com.example.guava.collect;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Collections2Test {

    /**
     * 条件过滤
     */
    @Test
    public void filter() {
        // 过滤一个 List<Integer> 里面大于 10 的元素
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 5, 6, 8, 9, 4, 11, 23, 45, 56, 78);
        Collection<Integer> filterCollection = Collections2.filter(list, input -> input <= 10);
        System.out.println(filterCollection); // [1, 2, 5, 6, 8, 9, 4]
    }

    /**
     * 类型转换
     */
    @Test
    public void transform() {
        Collection<Integer> set = Lists.newArrayList(1123, 4590, 5667, 7800);
        Collection<String> transformtCollection = Collections2.transform(set, input -> new DecimalFormat("#,###").format(input));
        System.out.println(transformtCollection); // [1,123, 4,590, 5,667, 7,800]
    }

    @Test
    public void orderedPermutations() {
        List<String> list = Lists.newArrayList("hello", "world", "javab", "c", "someone");
        Collection<List<String>> collection = Collections2.orderedPermutations(list);

        System.out.println(collection.size());  // 120

        for (List<String> temp : collection) {
            System.out.println(temp);
        }
    }

}
