package com.example.guava.basic;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;
import junit.framework.TestCase;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;
import static java.util.Arrays.asList;

/**
 * 排序
 */
public class OrderingTest extends TestCase {

    /**
     * 1、allEqual 允许有null
     */
    public void testAllEqual() {
        Ordering<Object> comparator = Ordering.allEqual();
        assertSame(comparator, comparator.reverse());

        assertEquals(0, comparator.compare(null, null));
        assertEquals(0, comparator.compare(new Object(), new Object()));
        assertEquals(0, comparator.compare("apples", "oranges"));
        assertEquals("Ordering.allEqual()", comparator.toString());

        List<String> strings = ImmutableList.of("b", "a", "d", "c");
        assertEquals(strings, comparator.sortedCopy(strings));
        assertEquals(strings, comparator.immutableSortedCopy(strings));
    }

    /**
     * 2、natural 不能有null
     */
    public void testNatural() {
        Ordering<Integer> comparator = Ordering.natural();
        try {
            comparator.compare(1, null);
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            comparator.compare(null, 2);
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            comparator.compare(null, null);
            fail();
        } catch (NullPointerException expected) {
        }
        assertEquals("Ordering.natural()", comparator.toString());
    }

    /**
     * 3、List集合 复杂排序示例
     */
    public void testComplicatedOrderingExample() {
        Ordering<Iterable<Integer>> example = Ordering.<Integer>natural().nullsFirst().reverse().lexicographical().reverse().nullsLast();

        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList(1);
        List<Integer> list3 = Lists.newArrayList(1, 1);
        List<Integer> list4 = Lists.newArrayList(1, 2);
        List<Integer> list5 = Lists.newArrayList(1, null, 2);
        List<Integer> list6 = Lists.newArrayList(2);

        Integer nullInt = null;
        List<Integer> list7 = Lists.newArrayList(nullInt);
        List<Integer> list8 = Lists.newArrayList(nullInt, nullInt);
        List<List<Integer>> list = Lists.newArrayList(list1, list2, list3, list4, list5, list6, list7, list8, null);

        List<List<Integer>> sorted = example.sortedCopy(list);

        /**
         * [null, null]
         * [null]
         * [1, null, 2]
         * [1, 1]
         * [1, 2]
         * [1]
         * [2]
         * []
         * null
         */
        sorted.forEach(System.out::println);

        // [[null, null], [null], [1, null, 2], [1, 1], [1, 2], [1], [2], [], null]
        assertThat(sorted)
                .containsExactly(
                        Lists.newArrayList(nullInt, nullInt),
                        Lists.newArrayList(nullInt),
                        Lists.newArrayList(1, null, 2),
                        Lists.newArrayList(1, 1),
                        Lists.newArrayList(1, 2),
                        Lists.newArrayList(1),
                        Lists.newArrayList(2),
                        Lists.newArrayList(),
                        null)
                .inOrder();
    }

    /**
     * 4、from 把给定的 Comparator 转化为排序器
     */
    public void testFrom() {
        // String.CASE_INSENSITIVE_ORDER 按照 ASCII 排序
        Ordering<String> caseInsensitiveOrdering = Ordering.from(String.CASE_INSENSITIVE_ORDER);
        assertTrue(caseInsensitiveOrdering.compare("A", "a") == 0);
        assertTrue(caseInsensitiveOrdering.compare("a", "B") < 0);
        assertTrue(caseInsensitiveOrdering.compare("B", "a") > 0);

        ArrayList<String> list = Lists.newArrayList("tingfeng", "abcdef", "ABCDEF", "rapido", "chengxumiao");
        List<String> sortedCopy = caseInsensitiveOrdering.sortedCopy(list);
        sortedCopy.forEach(System.out::println);
    }

    /*
     * 5、explicit（ExplicitOrdering）返回一个Ordering，根据它们的传入的顺序比较对象。只能比较参数列表中存在的对象
     */
    public void testExplicit_none() {
        Comparator<Integer> c = Ordering.explicit(Collections.emptyList());
        try {
            c.compare(0, 0);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        assertEquals("Ordering.explicit([])", c.toString());
    }

    public void testExplicit_one() {
        Comparator<Integer> c = Ordering.explicit(0);
        assertEquals(0, c.compare(0, 0));
        try {
            c.compare(0, 1);
            fail();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        assertEquals("Ordering.explicit([0])", c.toString());
    }

    public void testExplicit_two() {
//        Comparator<Integer> c = Ordering.explicit(42, 5);
//        assertEquals(0, c.compare(5, 5));
//        assertTrue(c.compare(5, 42) > 0);
//        assertTrue(c.compare(42, 5) < 0);

        Comparator<Integer> c = Ordering.explicit(5, 10);
        assertEquals(0, c.compare(5, 5));
        assertTrue(c.compare(5, 10) < 0);
        assertTrue(c.compare(10, 5) > 0);
        try {
            c.compare(5, 666);
            fail();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public void testExplicit_three() {
        // explicit:根据传入对象的顺序排序
        Double first = 0.1;
        Double[] second = {0.2, 0.3, 0.5};
        List<Double> numbers = Lists.asList(first, second);

        //排序比较器：根据原始的大小排序
        Ordering<Double> peopleOrdering = new Ordering<Double>() {
            @Override
            public int compare(Double left, Double right) {
                return Doubles.compare(left, right);
            }
        };
        peopleOrdering.reverse().explicit(numbers).sortedCopy(numbers).forEach(System.out::println);//[0.1, 0.2, 0.3, 0.5]
    }


    public void testExplicit_sortingExample() {
        Comparator<Integer> c = Ordering.explicit(2, 8, 6, 1, 7, 5, 3, 4, 0, 9);
        List<Integer> list = Arrays.asList(0, 3, 5, 6, 7, 8, 9);
        Collections.sort(list, c);

        // 8, 6, 7, 5, 3, 0, 9
        list.forEach(System.out::println);

        assertThat(list).containsExactly(8, 6, 7, 5, 3, 0, 9).inOrder();
    }

    /**
     * key重复异常
     */
    public void testExplicit_withDuplicates() {
        try {
            Ordering.explicit(1, 2, 3, 4, 2);
            fail();
        } catch (IllegalArgumentException expected) {
            expected.printStackTrace();
        }
    }


    /**
     * 6、arbitrary 返回所有对象的任意顺序
     */
    public void testArbitrary_withoutCollisions() {
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }

        Ordering<Object> arbitrary = Ordering.arbitrary();
        Collections.sort(list, arbitrary);

        list.forEach(System.out::println);

        assertEquals("Ordering.arbitrary()", arbitrary.toString());
    }

    /**
     * 7、usingToString
     */
    public void testUsingToString() {
        Ordering<Object> ordering = Ordering.usingToString();
        assertEquals("Ordering.usingToString()", ordering.toString());

        List<String> list = Lists.newArrayList("peida", "jerry", "harry", "eva", "jhon", "neron");
        System.out.println("list:" + list);

        // 使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序;
        Ordering<String> naturalOrdering = Ordering.natural();

        // 使用toString()返回的字符串按字典顺序进行排序；
        Ordering<Object> usingToStringOrdering = Ordering.usingToString();

        // 返回一个所有对象的任意顺序
        Ordering<Object> arbitraryOrdering = Ordering.arbitrary();

        System.out.println("naturalOrdering:" + naturalOrdering.sortedCopy(list));
        System.out.println("usingToStringOrdering:" + usingToStringOrdering.sortedCopy(list));
        System.out.println("arbitraryOrdering:" + arbitraryOrdering.sortedCopy(list));
    }

    /**
     * 8、reverse 取返
     */
    public void testReverse() {
        List<String> list = Lists.newArrayList("peida", "jerry", "harry", "eva", "jhon", "neron");
        Collections.sort(list, Ordering.natural().reverse());

        list.forEach(System.out::println);
    }

    private enum StringLengthFunction implements Function<String, Integer> {
        StringLength;

        @Override
        public Integer apply(String string) {
            return string.length();
        }
    }

    /**
     * 9、onResultOf 将传入function应用到每个元素上面，再通过Ordering进行排序。
     */
    public void testOnResultOf_1() {
        // 外部枚举函数
        Ordering<String> ordering = Ordering.natural().onResultOf(StringLengthFunction.StringLength);
        assertTrue(ordering.compare("to", "be") == 0);
        assertTrue(ordering.compare("or", "not") < 0);
        assertTrue(ordering.compare("that", "to") > 0);
        assertEquals("Ordering.natural().onResultOf(StringLength)", ordering.toString());

        ArrayList<String> list = Lists.newArrayList("tingfeng", "abcds", "ABCDEF", "rapido", "chengxumiao");
        ordering.sortedCopy(list).forEach(System.out::println);
    }

    public void testOnResultOf_2() {
        // 匿名内部类函数
        Ordering<String> ordering = Ordering.natural().onResultOf(new Function<String, Comparable>() {
            @Override
            public Comparable apply(@Nullable String input) {
                return input.length();
            }
        });

        ArrayList<String> list = Lists.newArrayList("tingfeng", "abcds", "ABCDEF", "rapido", "chengxumiao");
        ordering.sortedCopy(list).forEach(System.out::println);
    }

    public void testOnResultOf_3() {
        // lambda 表达式函数
        Ordering<String> ordering = Ordering.natural().reverse().onResultOf(str -> str.length());
        ArrayList<String> list = Lists.newArrayList("tingfeng", "abcds", "ABCDEF", "rapido", "chengxumiao");
        ordering.sortedCopy(list).forEach(System.out::println);
    }

    /**
     * 10、nullsFirst
     */
    public void testNullsFirst_NullsLast() {
        ArrayList<String> list = Lists.newArrayList("tingfeng", null, "abcds", "ABCDEF", null, "rapido", "chengxumiao");
        Collections.sort(list, Ordering.natural().nullsFirst());
        list.forEach(System.out::println);

        Ordering.natural().nullsLast().sortedCopy(list).forEach(System.out::println);

    }

    /**
     * 11、NullsLast
     */
    public void testNullsLast() {
        ArrayList<String> list = Lists.newArrayList("tingfeng", null, "abcds", "ABCDEF", null, "rapido", "chengxumiao");
        Ordering.natural().nullsLast().sortedCopy(list).forEach(System.out::println);
    }

    /**
     * 12、isOrdered 下一个元素大于或等于上一个元素，返回true
     */
    public void testIsOrdered() {
        Ordering<Comparable> ordering = Ordering.natural();

        assertFalse(ordering.isOrdered(asList(5, 3, 0, 9)));
        assertFalse(ordering.isOrdered(asList(0, 5, 3, 9)));

        assertTrue(ordering.isOrdered(asList(0, 3, 5, 9)));
        assertTrue(ordering.isOrdered(asList(0, 0, 3, 3)));
        assertTrue(ordering.isOrdered(asList(0, 3)));
        assertTrue(ordering.isOrdered(Collections.singleton(1)));
        assertTrue(ordering.isOrdered(Collections.<Integer>emptyList()));
    }

    /**
     * 13、isStrictlyOrdered 下一个元素大于上一个元素，返回true
     */
    public void testIsStrictlyOrdered() {
        Ordering<Comparable> ordering = Ordering.natural();

        assertFalse(ordering.isStrictlyOrdered(asList(5, 3, 0, 9)));
        assertFalse(ordering.isStrictlyOrdered(asList(0, 5, 3, 9)));
        assertFalse(ordering.isStrictlyOrdered(asList(0, 0, 3, 3)));

        assertTrue(ordering.isStrictlyOrdered(asList(0, 3, 5, 9)));
        assertTrue(ordering.isStrictlyOrdered(asList(0, 3)));
        assertTrue(ordering.isStrictlyOrdered(Collections.singleton(1)));
        assertTrue(ordering.isStrictlyOrdered(Collections.<Integer>emptyList()));
    }


    /**
     * 判断集合是否只读
     */
    private static void assertListImmutable(List<Integer> result) {
        try {
            result.set(0, 1);
            fail();
        } catch (UnsupportedOperationException expected) {
            // pass
        }
    }

    /**
     * 14、leastOf 有点类似截取集合前几位的概念
     */
    public void testLeastOfIterable_simple_1() {
        List<Integer> result = Ordering.natural().leastOf(Arrays.asList(3, 4, 5, -1), 2);
        assertTrue(result instanceof RandomAccess);
        assertListImmutable(result);
        assertEquals(ImmutableList.of(-1, 3), result);
    }

    public void testLeastOfIterator_simple_1() {
        List<Integer> result = Ordering.natural().leastOf(Iterators.forArray(3, 4, 5, -1), 2);
        assertTrue(result instanceof RandomAccess);
        assertListImmutable(result);
        assertEquals(ImmutableList.of(-1, 3), result);
    }

    public void testLeastOfIterable_simple_nMinusOne_withNullElement() {
        List<Integer> list = Arrays.asList(3, null, 5, -1);
        List<Integer> result = Ordering.natural().nullsLast().leastOf(list, list.size() - 1);
        assertTrue(result instanceof RandomAccess);
        assertListImmutable(result);
        assertEquals(ImmutableList.of(-1, 3, 5), result);
    }

    /**
     * 15、min 和 max
     */
    public void testIteratorMinAndMax() {
        Ordering<Comparable> ordering = Ordering.natural();
        List<Integer> ints = Lists.newArrayList(5, 3, 0, 9);
        assertEquals(9, (int) ordering.max(ints));
        assertEquals(0, (int) ordering.min(ints));

        assertEquals(9, (int) ordering.max(ints.iterator()));
        assertEquals(0, (int) ordering.min(ints.iterator()));

        assertEquals(9, (int) ordering.max(ints.listIterator()));
        assertEquals(0, (int) ordering.min(ints.listIterator()));

        // 当值相同时，返回第一个参数，此处a正确，b就不正确
        Integer a = new Integer(4);
        Integer b = new Integer(4);
        ints = Lists.newArrayList(a, b, b);
        assertSame(a, ordering.max(ints.iterator()));
        assertSame(a, ordering.min(ints.iterator()));
    }

    public void testIteratorMinExhaustsIterator() {
        List<Integer> ints = Lists.newArrayList(9, 0, 3, 5);
        Iterator<Integer> iterator = ints.iterator();
        assertEquals(0, (int) Ordering.natural().min(iterator));
        assertFalse(iterator.hasNext());
    }

    public void testIteratorMaxExhaustsIterator() {
        List<Integer> ints = Lists.newArrayList(9, 0, 3, 5);
        Iterator<Integer> iterator = ints.iterator();
        assertEquals(9, (int) Ordering.natural().max(iterator));
        assertFalse(iterator.hasNext());
    }

}
