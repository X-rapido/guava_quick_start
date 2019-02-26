package com.example.guava.math_utilities;

import com.google.common.math.DoubleMath;
import junit.framework.TestCase;

public class DoubleMathTest extends TestCase {

    /**
     * isMathematicalInteger
     */
    public void test_isMathematicalInteger() {
        boolean result = DoubleMath.isMathematicalInteger(5);
        assertTrue(result);

        result = DoubleMath.isMathematicalInteger(5.2);
        assertFalse(result);
    }

}
