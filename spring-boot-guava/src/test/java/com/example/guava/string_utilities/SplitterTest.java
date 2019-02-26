package com.example.guava.string_utilities;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import junit.framework.TestCase;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Splitter 测试
 */
public class SplitterTest extends TestCase {

    public void test1() {
        List<String> list = Splitter.on("!").splitToList("hello!world");
        System.out.println(list);   // [hello, world]
    }

    /**
     * 过滤空
     */
    public void test2() {
        List<String> list = Splitter.on("!").omitEmptyStrings().splitToList("hello ! world!!!!!!");
        System.out.println(list.size());    // 2
        System.out.println(list);           // [hello ,  world]
    }

    /**
     * 去空格，过滤空
     */
    public void test3() {
        List<String> list = Splitter.on("!").trimResults().omitEmptyStrings().splitToList("hello ! world!  !!!!!");
        System.out.println(list.size());    // 2
        System.out.println(list);           // [hello ,  world]
    }

    /**
     * 分割限制
     */
    public void test4() {
        List<String> list = Splitter.on("#").limit(3).splitToList("hello#world#java#google#scala");
        System.out.println(list.size());    // 3
        System.out.println(list);           // [hello, world, java#google#scala]
    }

    /**
     * 按长度分割
     */
    public void test5() {
        List<String> list = Splitter.fixedLength(3).splitToList("aaabbbccccddd");
        System.out.println(list.size());    // 5
        System.out.println(list);           // [aaa, bbb, ccc, cdd, d]
    }

    /**
     * 正则匹配
     */
    public void test6() {
        /*通过正则表达式初始化拆分器*/
        String str = "apple.banana,,orange,,.";

        List<String> result = Splitter.onPattern("[.|,]").omitEmptyStrings().trimResults().splitToList(str);
        System.out.println(result);     // [apple, banana, orange]
    }

    /**
     * 正则表达式 + 键值对
     */
    public void test7() {
        Map<String, String> map = Splitter.on(Pattern.compile("\\|"))
                .trimResults()
                .omitEmptyStrings()
                .withKeyValueSeparator("=")
                .split("hello=HELLO| world=WORLD|||");

        System.out.println(map.size()); // 2
        System.out.println(map);        // {hello=HELLO, world=WORLD}
    }

    /**
     * CharMatcher.anyOf
     */
    public void test8() {
        /*通过Char初始化拆分器，将String分隔为Iterable*/
        String str = "this, is  , , random , text,";
        List<String> result = Lists.newArrayList(Splitter.on(',').omitEmptyStrings().trimResults().split(str));
        System.out.println(result);     // [this, is, random, text]

        String str1 = "~?~this, is~~ , , random , text,";
        result = Splitter.on(',').omitEmptyStrings().trimResults(CharMatcher.anyOf("~? ")).splitToList(str1);
        System.out.println(result);     // [this, is, random, text]
    }


}
