package com.example.guava.collect;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.*;

public class MapsTest {

    /**
     * newHashMap
     * newHashMapWithExpectedSize
     * newLinkedHashMap
     * 4 种方式遍历
     */
    @Test
    public void test() {
        Map<Integer, Integer> map0 = Maps.newHashMap();
        for (int i = 0; i < 10; i++) {
            map0.put(i, i);
        }
        System.out.println("map0：" + map0);     // map0：{0=0, 1=1, 2=2, 3=3, 4=4, 5=5, 6=6, 7=7, 8=8, 9=9}

        Map<Integer, Integer> map1 = Maps.newHashMap(map0);
        map1.put(10, 10);
        System.out.println("map1：" + map1);     // map1：{0=0, 1=1, 2=2, 3=3, 4=4, 5=5, 6=6, 7=7, 8=8, 9=9, 10=10}

        Map<Integer, Integer> map2 = Maps.newHashMapWithExpectedSize(3);
        map2.put(1, 1);
        map2.put(2, 2);
        map2.put(3, 3);
        System.out.println("map2：" + map2);     // map2：{1=1, 2=2, 3=3}

        Map<Integer, Integer> map3 = Maps.newLinkedHashMap(map1);
        map3.put(11, 11);
        System.out.println("map3：" + map3);     // map3：{0=0, 1=1, 2=2, 3=3, 4=4, 5=5, 6=6, 7=7, 8=8, 9=9, 10=10, 11=11}

        outMapKeyValue(map3);

    }

    /**
     * 遍历Map的四种方法
     */
    private static void outMapKeyValue(Map<Integer, Integer> map3) {
        System.out.println("\n1.通过Map.entrySet遍历key和value");
        for (Map.Entry<Integer, Integer> integerEntry : map3.entrySet()) {
            System.out.println("key：" + integerEntry.getKey() + " value：" + integerEntry.getValue());
        }

        System.out.println("\n2.通过Map.entrySet使用iterator遍历key和value");
        Iterator<Map.Entry<Integer, Integer>> it = map3.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            System.out.println("key：" + entry.getKey() + " value：" + entry.getValue());
        }

        System.out.println("\n3.通过Map.keySet遍历key；根据key得到value");
        for (Integer integer : map3.keySet()) {
            System.out.println("key：" + integer + " value：" + map3.get(integer));
        }

        System.out.println("\n4.通过Map.values()遍历所有的value，但不能遍历key");
        for (Integer integer : map3.values()) {
            System.out.println("value：" + integer);
        }
    }


    /**
     * difference：返回两个给定map之间的差异。
     */
    @Test
    public void difference() {
        Map<String, String> map1 = new HashMap<String, String>() {
            {
                put("a", "1");
            }
        };
        Map<String, String> map2 = new HashMap<String, String>() {
            {
                put("b", "2");
            }
        };
        Map<String, String> map3 = new HashMap<String, String>() {
            {
                put("a", "3");
            }
        };
        Map<String, String> map4 = new HashMap<String, String>() {
            {
                put("a", "1");
            }
        };
        Map<String, String> map5 = ImmutableMap.of("a", "1");

        System.out.println("\nMaps.difference(map1, map4)");
        differencePrint(map1, map4);

        System.out.println("\nMaps.difference(map1, map2)");
        differencePrint(map1, map2);

        System.out.println("\nMaps.difference(map1, map3)");
        differencePrint(map1, map3);

        System.out.println("\nMaps.difference(map1, map5)");
        differencePrint(map1, map5);
    }

    private void differencePrint(Map<String, String> map1, Map<String, String> map4) {
        MapDifference<String, String> difference = Maps.difference(map1, map4);
        System.out.println(difference.areEqual());
        System.out.println(difference.entriesInCommon());
        System.out.println(difference.entriesOnlyOnRight());
        System.out.println(difference.entriesOnlyOnLeft());
        System.out.println(difference.entriesDiffering());
        System.out.println(difference);
    }

    /**
     * transformValues：返回一个map映射，其键值为给定fromMap的键值，其value为给定formMap中value通过Function转换后的值
     * transformEntries：返回一个map映射， 其Entry为给定fromMap.Entry通过给定EntryTransformer转换后的值
     */
    @Test
    public void transformValues_transformEntries() {
        Map<String, Boolean> fromMap = Maps.newHashMap();
        fromMap.put("key", true);
        fromMap.put("value", false);
        // 对传入的元素取反
        System.out.println(Maps.transformValues(fromMap, (Function<Boolean, Object>) input -> !input));        // 输出 {value=true, key=false}

        // value为假，则key变大写
        Maps.EntryTransformer<String, Boolean, String> entryTransformer = (key, value) -> value ? key : key.toUpperCase();
        System.out.println(Maps.transformEntries(fromMap, entryTransformer));       // 输出 {value=VALUE, key=key}
    }

    /**
     * filterKeys：返回给定unfilteredMap中的键值通过给定keyPredicate过滤后的map映射
     * filterValues：返回给定unfilteredMap中的value值通过给定keyPredicate过滤后的map映射
     * filterEntries：返回给定unfilteredMap.Entry中的Entry值通过给定entryPredicate过滤后的map映射
     */
    @Test
    public void filterKeys_filterValues_filterEntries() {
        Map<String, Boolean> fromMap = ImmutableMap.of("abc", true, "bcd", false, "xyz", true);

        Map<String, Boolean> keysMap = Maps.filterKeys(fromMap, new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                // 过滤Map中键值包含字母y的元素
                return input.contains("y");
            }
        });
        System.out.println(keysMap);      // {xyz=true}

        // 过滤Map中key包含字母b的元素
        System.out.println(Maps.filterKeys(fromMap, input -> input.contains("b")));              // {abc=true, bcd=false}

        // 过滤Map中value值为假的元素
        Map<String, Boolean> booleanMap = Maps.filterValues(fromMap, new Predicate<Boolean>() {
            @Override
            public boolean apply(@Nullable Boolean input) {
                return !input;
            }
        });
        System.out.println(booleanMap);        // {bcd=false}
        System.out.println(Maps.filterValues(fromMap, input -> input));   // {abc=true, xyz=true}

        // 过滤Map.Entry中getValue()为真的元素
        Map<String, Boolean> filterEntries = Maps.filterEntries(fromMap, new Predicate<Map.Entry<String, Boolean>>() {
            @Override
            public boolean apply(Map.@Nullable Entry<String, Boolean> input) {
                return input.getValue();
            }
        });
        System.out.println(filterEntries);      // {abc=true, xyz=true}
    }

    /**
     * asMap 视图
     * toMap：返回一个不可变的ImmutableMap实例，其键值为给定keys中去除重复值后的值，其值为键被计算了valueFunction后的值
     * uniqueIndex：返回一个不可变的ImmutableMap实例，其value值为按照给定顺序的给定的values值，键值为相应的值经过给定Function计算后的值
     */
    @Test
    public void testMaps() {
        Set<String> set = Sets.newHashSet("a", "b", "c");
        // Function：大写转换
        Function<String, String> function = input -> input.toUpperCase();
        System.out.println(Maps.asMap(set, function));          // {b=B, c=C, a=A}

        List<String> keys = Lists.newArrayList("a", "b", "c", "a");
        System.out.println(Maps.toMap(keys, function));         // {a=A, b=B, c=C}

        List<String> values = Lists.newArrayList("a", "b", "c", "d");
        System.out.println(Maps.uniqueIndex(values, function));     // {A=a, B=b, C=c, D=d}
    }

}
