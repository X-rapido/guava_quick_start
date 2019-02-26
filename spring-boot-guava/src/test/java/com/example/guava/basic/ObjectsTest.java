package com.example.guava.basic;

import com.google.common.base.Objects;
import junit.framework.TestCase;

public class ObjectsTest extends TestCase {

    /**
     * 确定两个可能是空的对象是否相等。
     */
    public void testEqual() {
        assertTrue(Objects.equal("a", "a"));
        assertTrue(Objects.equal(null, null));

        assertFalse(Objects.equal(null, "a"));
        assertFalse(Objects.equal("a", null));

        String s1 = "foobar";
        String s2 = new String(s1);
        assertTrue(Objects.equal(s1, s2));
    }

    public void testHashCode() {
        int h1 = Objects.hashCode(1, "two", 3.0);
        int h2 = Objects.hashCode(new Integer(1), new String("two"), new Double(3.0));

        assertEquals(h1, h2);
        System.out.println(h1 == h2);   // true

        assertTrue(Objects.hashCode(1, 2, null) != Objects.hashCode(1, 2));
        assertTrue(Objects.hashCode(1, 2, null) != Objects.hashCode(1, null, 2));
        assertTrue(Objects.hashCode(1, null, 2) != Objects.hashCode(1, 2));
        assertTrue(Objects.hashCode(1, 2, 3) != Objects.hashCode(3, 2, 1));
        assertTrue(Objects.hashCode(1, 2, 3) != Objects.hashCode(2, 3, 1));
    }
}
