package com.example.guava.math_utilities;

import com.google.common.math.LongMath;
import junit.framework.TestCase;

public class LongMathTest extends TestCase {

    public void test_mod() {
        int result = LongMath.mod(30L, 4);
        assertEquals(2, result);

        long result2 = LongMath.mod(30L, 4L);
        assertEquals(2L, result2);
    }

}
