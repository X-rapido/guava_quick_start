package com.example.guava.collect;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.List;

public class ListsTest {

    public void test1() {
        List<String> list1 = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            list1.add(i + "");
        }
        System.out.println("list1: " + list1);
        //输出：list1: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

        //2、传入多参数
        List<String> list2 = Lists.newArrayList("1", "2", "3");
        System.out.println("list2: " + list2);
        //输出：list2: [1, 2, 3]

        //3、传入数组
        List<String> list3 = Lists.newArrayList(new String[]{"22", "22"});
        System.out.println("list3: " + list3);
        //输出：list3: [22, 22]

        //4、传入集合
        List<String> list4 = Lists.newArrayList(list1);
        System.out.println("list4: " + list4);
        //输出：list4: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

        //5、使用条件：你确定你的容器会装多少个，不确定就用一般形式的
        //说明：这个容器超过10个还是会自动扩容的。不用担心容量不够用。默认是分配一个容量为10的数组，不够将扩容
        //整个来说的优点有：节约内存，节约时间，节约性能。代码质量提高。
        List<String> list = Lists.newArrayListWithExpectedSize(10);

        //这个方法就是直接返回一个10的数组。
        List<String> list_ = Lists.newArrayListWithCapacity(10);
    }

    @Test
    public void testLists() {
        String str = "i love u";
        String[] strs = {"i like u", "i miss u"};

        List<String> list = Lists.asList(str, strs);
        System.out.println(list);   // [i love u, i like u, i miss u]

        strs[1] = "i hate u";
        System.out.println(list);   // [i love u, i like u, i hate u]

        /**
         * transform：根据传进来的function对fromList进行相应的处理
         * 并将处理得到的结果存入到新的list对象中返回
         */
        List<Object> transformList = Lists.transform(list, new Function<String, Object>() {
            @Override
            public Object apply(@Nullable String input) {
                return input.toUpperCase();
            }
        });
        System.out.println(transformList);      //[I LOVE U, I LIKE U, I HATE U]

        /**
         * partition：根据size传入的List进行切割，切割成符合要求的小的List
         * 并将这些小的List存入一个新的List对象中返回
         */
        List<List<String>> lists = Lists.partition(list, 2);
        System.out.println(lists);      // [[i love u, i like u], [i hate u]]

        /**
         * charactersOf：将传进来的String或者CharSequence分割为单个的字符
         * 并存入到一个ImmutableList对象中返回
         * ImmutableList：一个高性能、不可变的、随机访问列表的实现
         */
        ImmutableList<Character> characters = Lists.charactersOf("tingfeng");
        System.out.println(characters);     // [t, i, n, g, f, e, n, g]

        /**
         * reverse：返回一个传入List内元素倒序后的List
         */
        List<String> reverse = Lists.reverse(list);
        System.out.println(reverse);    // [i hate u, i like u, i love u]
    }

}


