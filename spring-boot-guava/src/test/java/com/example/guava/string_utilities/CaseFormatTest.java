package com.example.guava.string_utilities;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import junit.framework.TestCase;

public class CaseFormatTest extends TestCase {

    /**
     * converterTo 格式器转化为targetFormat格式
     */
    public void testConverterTo() {
        Converter<String, String> camelConverter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE);
        System.out.println(camelConverter.convert("input_camel"));  // INPUT_CAMEL
    }

    /**
     * to 转换指定类型字符串
     */
    public void testTo() {
        String input = "ting-feng";
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, input));      // tingFeng
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_UNDERSCORE, input)); // ting_feng
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, input));      // TingFeng
    }
}
