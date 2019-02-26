package com.example.guava.basic;

import com.google.common.base.*;
import com.google.common.collect.ImmutableList;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class OptionalTest extends TestCase {

    /**
     * 1、toJavaUtil 静态
     */
    public void testToJavaUtil_static() {
        Optional<String> str = Optional.of("abc");

        assertNull(Optional.toJavaUtil(null));
        assertEquals("abc", Optional.toJavaUtil(str).get());
        assertEquals(java.util.Optional.empty(), Optional.toJavaUtil(Optional.absent()));
        assertEquals(java.util.Optional.of("abc"), Optional.toJavaUtil(Optional.of("abc")));
    }

    /**
     * 2、toJavaUtil 实例
     */
    public void testToJavaUtil_instance() {
        assertEquals(java.util.Optional.empty(), Optional.absent().toJavaUtil());
        assertEquals(java.util.Optional.of("abc"), Optional.of("abc").toJavaUtil());
    }

    /**
     * 3、fromJavaUtil
     */
    public void testFromJavaUtil() {
        assertNull(Optional.fromJavaUtil(null));
        assertEquals(Optional.absent(), Optional.fromJavaUtil(java.util.Optional.empty()));
        assertEquals(Optional.of("abc"), Optional.fromJavaUtil(java.util.Optional.of("abc")));
    }

    /**
     * 4、absent
     */
    public void testAbsent() {
        Optional<String> optionalName = Optional.absent();
        assertFalse(optionalName.isPresent());

        // 异常
        try {
            optionalName.get();
            fail();
        } catch (IllegalStateException expected) {
        }
    }

    /**
     * 5、of
     */
    public void testOf() {
        assertEquals("tingfeng", Optional.of("tingfeng").get());

        // 空指针异常
        try {
            Optional.of(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    /**
     * 6、fromNullable 取值
     */
    public void testFromNullable() {
        Optional<String> optionalName = Optional.fromNullable("tingfeng");
        assertEquals("tingfeng", optionalName.get());
    }

    public void testFromNullable_null() {
        System.out.println(Optional.absent());              // Optional.absent()
        System.out.println(Optional.fromNullable(null));    // Optional.absent()
        assertSame(Optional.absent(), Optional.fromNullable(null));
    }

    /**
     * 7、isPresent 判断
     */
    public void testIsPresent() {
        assertFalse(Optional.absent().isPresent());
        assertTrue(Optional.of("tingfeng").isPresent());
    }

    /**
     * 8、get - or 取值
     */
    public void testGet_present() {
        assertEquals("training", Optional.of("training").get());
    }

    public void testOr_T_present() {
        assertEquals("a", Optional.of("a").or("default"));
    }

    public void testOr_T_absent() {
        assertEquals("default", Optional.absent().or("default"));
    }

    public void testOr_supplier_present() {
        assertEquals("a", Optional.of("a").or(Suppliers.ofInstance("fallback")));
    }

    public void testOr_supplier_absent() {
        assertEquals("fallback", Optional.absent().or(Suppliers.ofInstance("fallback")));
    }

    public void testOr_nullSupplier_absent() {
        Supplier<Object> nullSupplier = Suppliers.ofInstance(null);
        Optional<Object> absentOptional = Optional.absent();
        try {
            absentOptional.or(nullSupplier);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testOr_nullSupplier_present() {
        Supplier<String> nullSupplier = Suppliers.ofInstance(null);
        assertEquals("a", Optional.of("a").or(nullSupplier));
    }

    public void testOr_Optional_present() {
        assertEquals(Optional.of("a"), Optional.of("a").or(Optional.of("fallback")));
    }

    public void testOr_Optional_absent() {
        assertEquals(Optional.of("fallback"), Optional.absent().or(Optional.of("fallback")));
    }

    public void testOrNull_present() {
        assertEquals("a", Optional.of("a").orNull());
    }

    public void testOrNull_absent() {
        assertNull(Optional.absent().orNull());
    }

    /**
     * 9、asSet 只读集合对象
     */
    public void testAsSet_present() {
        Set<String> expected = Collections.singleton("a");
        assertEquals(expected, Optional.of("a").asSet());
    }

    public void testAsSet_absent() {
        assertTrue("Returned set should be empty", Optional.absent().asSet().isEmpty());
    }

    public void testAsSet_presentIsImmutable() {
        Set<String> presentAsSet = Optional.of("a").asSet();
        try {
            presentAsSet.add("b");
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    public void testAsSet_absentIsImmutable() {
        Set<Object> absentAsSet = Optional.absent().asSet();
        try {
            absentAsSet.add("foo");
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    /**
     * 10、transform 转换
     */
    public void testTransform_absent() {
        assertEquals(Optional.absent(), Optional.absent().transform(Functions.identity()));
        assertEquals(Optional.absent(), Optional.absent().transform(Functions.toStringFunction()));
    }

    public void testTransform_presentIdentity() {
        assertEquals(Optional.of("a"), Optional.of("a").transform(Functions.identity()));
    }

    public void testTransform_presentToString() {
        // 转换类型
        assertEquals(Optional.of("42"), Optional.of(42).transform(Functions.toStringFunction()));
    }

    public void testTransform_present_functionReturnsNull() {
        try {
            Optional<String> unused =
                    Optional.of("a")
                            .transform(
                                    new Function<String, String>() {
                                        @Override
                                        public String apply(String input) {
                                            return null;
                                        }
                                    });
            System.out.println(unused);
            fail("Should throw if Function returns null.");
        } catch (NullPointerException expected) {
            expected.printStackTrace();
        }
    }

    public void testTransform_absent_functionReturnsNull() {
        assertEquals(
                Optional.absent(),
                Optional.absent()
                        .transform(
                                new Function<Object, Object>() {
                                    @Override
                                    public Object apply(Object input) {
                                        return null;
                                    }
                                }));
    }

    /**
     * 11、toString
     */
    public void testToString_absent() {
        assertEquals("Optional.absent()", Optional.absent().toString());
    }

    public void testToString_present() {
        assertEquals("Optional.of(training)", Optional.of("training").toString());
    }

    /**
     * 12、presentInstances
     */
    public void testPresentInstances_allPresent() {
        List<Optional<String>> optionals = ImmutableList.of(Optional.of("a"), Optional.of("b"), Optional.of("c"));
        assertThat(Optional.presentInstances(optionals)).containsExactly("a", "b", "c").inOrder();
    }

    public void testPresentInstances_allAbsent() {
        List<Optional<Object>> optionals = ImmutableList.of(Optional.absent(), Optional.absent());
        assertThat(Optional.presentInstances(optionals)).isEmpty();
    }

    public void testPresentInstances_somePresent() {
        List<Optional<String>> optionals = ImmutableList.of(Optional.of("a"), Optional.absent(), Optional.of("c"));
        assertThat(Optional.presentInstances(optionals)).containsExactly("a", "c").inOrder();
    }

    public void testPresentInstances_callingIteratorTwice() {
        List<Optional<String>> optionals = ImmutableList.of(Optional.of("a"), Optional.<String>absent(), Optional.of("c"));
        Iterable<String> onlyPresent = Optional.presentInstances(optionals);
        assertThat(onlyPresent).containsExactly("a", "c").inOrder();
    }

    public void testPresentInstances_wildcards() {
        List<Optional<? extends Number>> optionals = ImmutableList.<Optional<? extends Number>>of(Optional.<Double>absent(), Optional.of(2));
        Iterable<Number> onlyPresent = Optional.presentInstances(optionals);
        assertThat(onlyPresent).containsExactly(2).inOrder();
    }

}
