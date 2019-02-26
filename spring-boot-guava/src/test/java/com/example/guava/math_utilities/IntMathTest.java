package com.example.guava.math_utilities;

import com.google.common.math.IntMath;
import junit.framework.TestCase;
import org.junit.Test;

import java.math.RoundingMode;

public class IntMathTest extends TestCase {

    /**
     * binomial 计算n和k的二项式系数。它确保结果在整数范围内。否则，它给出Integer.MAX_VALUE
     */
    public void test_binomial() {
        int result = IntMath.binomial(6, 3);
        assertEquals(20, result);

        result = IntMath.binomial(Integer.MAX_VALUE, 3);
        assertEquals(Integer.MAX_VALUE, result);
    }

    /**
     * ceilingPowerOfTwo 计算2的最小幂的值，其大于或等于x。结果n是2^(n-1) < x < 2^n
     */
    public void test_ceilingPowerOfTwo() {
        int result = IntMath.ceilingPowerOfTwo(20);
        assertEquals(32, result);
    }

    /**
     * checkedAdd 计算两个参数的总和。这个提供了一个额外的检查，如果结果溢出，则抛出ArithmeticException
     */
    public void test_checkedAdd() {
        int result = IntMath.checkedAdd(1, 2);
        assertEquals(3, result);

//        IntMath.checkedAdd(Integer.MAX_VALUE, 100);
    }

    /**
     * divide 返回p除以q的值，按照mode模式取整
     */
    public void test_divide() {
        int result = IntMath.divide(10, 3, RoundingMode.CEILING);
        assertEquals(4, result);

        // 不需要舍入，如果用此模式进行舍入，应直接抛出ArithmeticException
        int divide = IntMath.divide(10, 3, RoundingMode.UNNECESSARY);
    }

    /**
     * factorial 计算前n个正整数乘积的因子值。如果n = 0，则返回1，如果结果不适合int范围，则返回Integer.MAX_VALUE
     */
    public void test_factorial() {
        int result = IntMath.factorial(5);
        assertEquals(120, result);

        result = IntMath.factorial(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, result);
    }

    /**
     * floorPowerOfTwo 返回2的最大幂，其结果小于或等于x。结果n是 2^n < x < 2^(n+1)
     */
    public void test_floorPowerOfTwo() {
        int result = IntMath.floorPowerOfTwo(30);
        assertEquals(16, result);
    }

    /**
     * gcd a和b的最大公约数
     */
    public void test_gcd() {
        int result = IntMath.gcd(30, 40);
        assertEquals(10, result);
    }

    /**
     * isPowerOfTwo 检查一个整数是不是可以换算成2的指数
     */
    public void test_isPowerOfTwo() {
        boolean result = IntMath.isPowerOfTwo(16);
        assertTrue(result);

        result = IntMath.isPowerOfTwo(20);
        assertFalse(result);
    }

    /**
     * isPrime 质数
     */
    public void test_isPrime() {
        boolean result = IntMath.isPrime(3);
        assertTrue(result);

        result = IntMath.isPrime(20);
        assertFalse(result);
    }

    /**
     * log10 返回10为底的对数。使用提供的舍入模式舍入结果
     */
    public void test_log10() {
        int result = IntMath.log10(30, RoundingMode.CEILING);
        assertEquals(2, result);
    }

    @Test(expected = ArithmeticException.class)
    public void test_log10_2() {
        IntMath.log10(30, RoundingMode.UNNECESSARY);
    }

    /**
     * log2 返回一个整数基于2的对数
     */
    public void test_log2() {
        int result = IntMath.log2(30, RoundingMode.CEILING);
        assertEquals(5, result);
    }

    @Test(expected = ArithmeticException.class)
    public void test_log2_2() {
        IntMath.log2(30, RoundingMode.UNNECESSARY);
    }

    /**
     * mean 计算两个值的平均值
     */
    public void test_mean() {
        int result = IntMath.mean(30, 20);
        assertEquals(25, result);
    }

    /**
     * mod 返回另一个数字的整数除法的余数
     */
    public void test_mod() {
        int result = IntMath.mod(30, 4);
        assertEquals(2, result);
    }

    /**
     * pow 返回b的值为k的幂
     */
    public void test_pow() {
        int result = IntMath.pow(6, 4);
        assertEquals(1296, result);
    }

    /**
     * saturatedAdd 加法
     */
    public void test_saturatedAdd() {
        int result = IntMath.saturatedAdd(6, 4);
        assertEquals(10, result);

        result = IntMath.saturatedAdd(Integer.MAX_VALUE, 1000);
        assertEquals(Integer.MAX_VALUE, result);
    }

    /**
     * sqrt 返回给定数字的平方根。使用提供的舍入模式舍入结果
     */
    public void test_sqrt() {
        int result = IntMath.sqrt(30, RoundingMode.CEILING);
        assertEquals(6, result);
    }

    @Test(expected = ArithmeticException.class)
    public void test_sqrt2() {
        IntMath.sqrt(30, RoundingMode.UNNECESSARY);
    }

}
