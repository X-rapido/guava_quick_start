package com.example.guava.string_utilities;

import com.google.common.base.CharMatcher;
import junit.framework.TestCase;

public class CharMatcherTest extends TestCase {

    /**
     * collapseFrom 配到的字符做替换
     */
    public void testCollapseFrom() {
        String input = "     Ting Feng        ";
        String result = CharMatcher.breakingWhitespace().collapseFrom(input, '*');
        System.out.println(result);     // *Ting*Feng*

        result = CharMatcher.is(' ').collapseFrom(input, '-');
        System.out.println(result);     // -Ting-Feng-
    }

    /**
     * trimAndCollapseFrom 去掉两边空格，然后执行 collapseFrom 操作
     */
    public void testTrimAndCollapseFrom() {
        String input = "     Ting Feng     ";
        String result = CharMatcher.breakingWhitespace().trimAndCollapseFrom(input, '-');
        System.out.println(result);     // Ting-Feng

        result = CharMatcher.is(' ').trimAndCollapseFrom(input, '-');
        System.out.println(result);     // Ting-Feng
    }

    /**
     * trimFrom 去空格
     * trimLeadingFrom 左边去空格
     * trimTrailingFrom右边去空格
     */
    public void testTrim() {
        System.out.println(CharMatcher.breakingWhitespace().trimFrom("     Ting Feng     "));           // Ting Feng
        System.out.println(CharMatcher.breakingWhitespace().trimLeadingFrom("     Ting Feng     "));    // Ting Feng
        System.out.println(CharMatcher.breakingWhitespace().trimTrailingFrom("     Ting Feng     "));   //      Ting Feng
    }

    /**
     * retainFrom 保留匹配到的字符
     */
    public void testRetainFrom() {
        System.out.println(CharMatcher.breakingWhitespace().retainFrom("   Hi 123 Ting 456 Feng  "));   // "         " 空格
    }

    /**
     * removeFrom 删除所有匹配的字符
     */
    public void testRemoveFrom() {
        System.out.println(CharMatcher.breakingWhitespace().removeFrom("   Hi 123 Ting 456 Feng  "));   // Hi123Ting456Feng
    }

    /**
     * countIn 查找字符在字符串中的个数
     */
    public void testCountIn() {
        System.out.println(CharMatcher.is('a').countIn("TingFeng Sharing the Google Guava Used"));  // 3

        String input = "H*el.lo,}12";
        CharMatcher matcher = CharMatcher.forPredicate(Character::isLetterOrDigit);
        System.out.println(matcher.retainFrom(input));  // Hello12
        System.out.println(matcher.countIn(input));     // 7

        matcher = CharMatcher.inRange('a', 'l');
        System.out.println(matcher.countIn(input));     // 3
    }

    /**
     * indexIn 匹配到的第一个字符的index
     * lastIndexIn 匹配到的最后一个字符的index
     */
    public void testIndexIn_lastIndexIn() {
        String input = "**el.lo,}12";
        CharMatcher matcher = CharMatcher.forPredicate(Character::isLetterOrDigit);
        System.out.println(matcher.indexIn(input));             // 2
        System.out.println(matcher.indexIn(input, 4));    // 5

        System.out.println(matcher.lastIndexIn(input));         // 10
    }

    /**
     * is 匹配参数之内的所有字符
     * isNot 匹配参数之外的所有字符
     */
    public void testIs_isNot(){
        String input = "a, c, z, 1, 2";
        System.out.println(CharMatcher.is(',').retainFrom(input));   // ,,,,
        System.out.println(CharMatcher.is(',').removeFrom(input));   // a c z 1 2

        System.out.println(CharMatcher.isNot(',').retainFrom(input));   // a c z 1 2
        System.out.println(CharMatcher.isNot(',').removeFrom(input));   // ,,,,
    }

    /**
     * 匹配java转义字符
     */
    public void testJavaIsoControl(){
        String input = "ab\tcd\nef\bg";
        CharMatcher matcher = CharMatcher.javaIsoControl();
        System.out.println(matcher.removeFrom(input));  // abcdefg
    }

    /**
     * 两个 Matcher 同时匹配
     */
    public void testDoubleMatcher() {
        CharMatcher matcher0 = CharMatcher.forPredicate(Character::isLetterOrDigit);
        CharMatcher matcher1 = CharMatcher.forPredicate(Character::isLowerCase);

        String result = matcher0.and(matcher1).retainFrom("H*el.lo,}12");
        System.out.println(result); // ell0
    }

    /**
     * matchesAllOf 判断sequence所有字符是否都被charMatcher匹配
     * matchesAnyOf 判断sequence中是否存在字符被charMatcher匹配
     * matchesNoneOf 判断sequence所有字符是否都没被charMatcher匹配
     */
    public void test_matchesAllOf_matchesAnyOf_matchesNoneOf(){
        String input = "**e,l.lo,}12";

        CharMatcher matcher = CharMatcher.is(',');
        System.out.println(matcher.matchesAllOf(input));    // false

        matcher = CharMatcher.is(',');
        System.out.println(matcher.matchesAnyOf(input));    // true

        matcher = CharMatcher.is('?');
        System.out.println(matcher.matchesNoneOf(input));   // true
    }

    /**
     * 匹配任意字符
     */
    public void testAny() {
        String input = "H*el.lo,}12";

        CharMatcher matcher = CharMatcher.any();
        String result = matcher.retainFrom(input);
        System.out.println(result); //  H*el.lo,}12

        matcher = CharMatcher.anyOf("Hel");
        System.out.println(matcher.retainFrom(input)); // Hell
        System.out.println(matcher.removeFrom(input)); // *.o,}12
    }

    /**
     * 匹配 Ascii
     */
    public void testAscii() {
        String input = "あH*el.lo,}12";

        CharMatcher matcher = CharMatcher.ascii();
        System.out.println(matcher.retainFrom(input));  // H*el.lo,}12
        System.out.println(matcher.removeFrom(input));  // あ
    }

    /**
     * negate 返回与当前CharMatcher相反的CharMatcher
     */
    public void testNegate(){
        String input = "あH*el.lo,}12";

        CharMatcher matcher = CharMatcher.ascii().negate();
        System.out.println(matcher.retainFrom(input));     //  あ
        System.out.println(matcher.removeFrom(input));     //  H*el.lo,}12
    }

    /**
     * none 不匹配任何字符,与any()相反
     * noneOf 不匹配CharSequence内的任意一个字符,与anyOf()相反
     */
    public void testNone_noneOf(){
        String input = "H*el.lo,}12";
        CharMatcher matcher = CharMatcher.none();
        System.out.println(matcher.retainFrom(input));          // ""
        System.out.println(matcher.retainFrom(input).length()); // 0

        matcher = CharMatcher.noneOf("Hel");
        System.out.println(matcher.retainFrom(input));          // *.o,}12
        System.out.println(matcher.removeFrom(input));          // Hell
    }

    /**
     * forPredicate 初始化匹配器
     */
    public void testForPredicate() {
//        CharMatcher charMatcher = CharMatcher.forPredicate(new Predicate<Character>() {
//            @Override
//            public boolean apply(@Nullable Character input) {
//                return Character.isLetterOrDigit(input);
//            }
//        });

        // lambda 写法
        CharMatcher charMatcher = CharMatcher.forPredicate(input -> Character.isLetterOrDigit(input));

        String input = "H*el.lo,}12";
        System.out.println(charMatcher.retainFrom(input));  // Hello12

    }

}
