package com.example.guava.string_utilities;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JoinTest extends TestCase {

    private final List<String> list = Arrays.asList("java", "php", "c#");
    private final List<String> listWithNull = Arrays.asList("java", "php", "c#", null);

    public void testJoin1() {
        String join = Joiner.on(":").join(list);
        System.out.println(join);   // java:php:c#
    }

    /**
     * null对象测试
     */
    public void testJoin2() {
        try {
            String join = Joiner.on(":").join(listWithNull);
            System.out.println(join);
        }catch (NullPointerException e){
        }
    }

    /**
     * null对象跳过
     */
    public void testJoin3() {
        String join = Joiner.on(":").skipNulls().join(listWithNull);
        System.out.println(join);   // java:php:c#
    }

    /**
     * null对象使用默认值
     */
    public void testJoin4() {
        String join = Joiner.on(":").useForNull("默认值").join(listWithNull);
        System.out.println(join);   // java:php:c#:默认值
    }

    /**
     * appendTo 拼接
     */
    public void testJoin5() {
        StringBuilder builder = new StringBuilder("Hello World");
        StringBuilder appendResult = Joiner.on(":").useForNull("默认值").appendTo(builder, listWithNull);
        System.out.println(appendResult);   // Hello Worldjava:php:c#:默认值
    }

    /**
     * appendTo 文件
     *
     * 将结果输出到文件中
     */
    public void testJoin6() {
        try (FileWriter writer = new FileWriter(new File("/Users/liurenkui/Desktop/temp.txt"))) {
            FileWriter fileWriter = Joiner.on(":").useForNull("默认值").appendTo(writer, listWithNull);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MapJoiner 的使用，将 map 转换为字符串
     */
    public void testJoin7() {
        ImmutableMap<String, String> map = ImmutableMap.of("hello", "java", "hi", "python");
        String result = Joiner.on("；").withKeyValueSeparator("=").join(map);
        System.out.println(result); // hello=java；hi=python
    }

    /**
     * java8 测试 joining
     */
    public void testJoin8() {
        String collect = listWithNull.stream().filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining(":"));
        System.out.println(collect);    // java:php:c#
    }
}
