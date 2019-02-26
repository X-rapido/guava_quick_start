package com.example.guava.reflection;

import com.google.common.reflect.TypeToken;
import junit.framework.TestCase;

public class TypeTokenTest extends TestCase {


    public void test() {
        TypeToken<String> stringTok = TypeToken.of(String.class);
        TypeToken<Integer> intTok = TypeToken.of(Integer.class);

    }


}
