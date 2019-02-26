package com.example.guava.string_utilities;

import com.google.common.base.Strings;
import org.junit.Test;

/**
 * Strings 工具
 */
public class StringsTest {

    /**
     * 1、如果为空或null 就返回 null
     * 2、不为空或null 返回当前值
     */
    @Test
    public void test1() {
        System.out.println(Strings.emptyToNull(""));            // null
        System.out.println(Strings.emptyToNull(null));          // null
        System.out.println(Strings.emptyToNull(" "));
        System.out.println(Strings.emptyToNull("hello world")); // hello world
    }

    /**
     * 空值判断
     */
    @Test
    public void test2() {
        System.out.println(Strings.isNullOrEmpty(""));      // true
        System.out.println(Strings.isNullOrEmpty(" "));     // false
        System.out.println(Strings.isNullOrEmpty(null));    // true
    }

    /**
     * 前缀、后缀
     */
    @Test
    public void test3() {
        System.out.println(Strings.commonPrefix("Hello","Hit"));    // D
        System.out.println(Strings.commonPrefix("Hello","Xit"));    // 空
        System.out.println(Strings.commonSuffix("world","xid"));    // d
        System.out.println(Strings.commonSuffix("world","xic"));    // 空
    }

    /**
     * 重复数据
     */
    @Test
    public void test4() {
        System.out.println(Strings.repeat("TingFeng",3));   // TingFengTingFengTingFeng
    }

    /**
     * 数据填充
     *
     * 1、设定长度小于等于字符串长度 - 忽略
     * 2、设定长度大于字符串长度忽略 - 填充
     */
    @Test
    public void test5() {
        // 向前填充
        System.out.println(Strings.padStart("Hello",3,'X'));    // Hello
        System.out.println(Strings.padStart("Hello",8,'X'));    // XXXHello
        // 向后填充
        System.out.println(Strings.padEnd("Hello",3,'X'));      // Hello
        System.out.println(Strings.padEnd("Hello",8,'X'));      // HelloXXX
    }

    @Test
    public void test6() {
    }
}
