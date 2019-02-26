package com.example.guava.basic;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Precondition 断言
 * java.util.Objects 断言
 * assert 断言
 */
public class PreconditionsTest {

    /**
     * 空异常
     */
    @Test
    public void test1() {
        Object obj = Preconditions.checkNotNull(null);
    }

    /**
     * 空异常提示语
     */
    @Test
    public void test2() {
        Object obj = Preconditions.checkNotNull(null, "对象不能为空");
    }

    /**
     * 空异常，带格式化的提示语
     */
    @Test
    public void test3() {
        Object obj = Preconditions.checkNotNull(null, "对象不能为空，并且长度必须为 %s", 6);
    }

    /**
     * IllegalArgumentException 异常
     */
    @Test
    public void test4() {
        Preconditions.checkArgument(false);
    }

    /**
     * IllegalArgumentException 异常 - 带提示语
     */
    @Test
    public void test5() {
        Preconditions.checkArgument(false, "未通过验证");
    }

    /**
     * IllegalStateException 异常
     */
    @Test
    public void test6() {
        Preconditions.checkState(false, "未通过验证");
    }

    /**
     * IllegalStateException 异常 - 带提示语
     */
    @Test
    public void test7() {
        Preconditions.checkState(false, "未通过验证");
    }

    /**
     * IndexOutOfBoundsException 异常
     */
    @Test
    public void test8() {
        List<String> list = Arrays.asList("A", "B", "C");
        Preconditions.checkElementIndex(10, list.size());
    }

    /**
     * IndexOutOfBoundsException 异常 - 带提示语
     */
    @Test
    public void test9() {
        List<String> list = ImmutableList.of();
        Preconditions.checkElementIndex(10, list.size(), "元素长度不正确");
    }

    /**
     * java.util.Objects 断言
     */
    @Test
    public void test10() {
        Object obj = Objects.requireNonNull(null, "对象不能为空");
    }

    /**
     * assert 断言
     */
    @Test
    public void test11() {
        List<String> list = null;
        assert list != null : "list 不能为空";
    }


}