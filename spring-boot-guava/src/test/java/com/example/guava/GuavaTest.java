package com.example.guava;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.*;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

public class GuavaTest {

    /**
     * 1、ImmutableList 只读集合
     */
    @Test
    public void testGuava1() {
        // 1、使用 Arrays.asList 创建只读集合
//        List<String> list = Arrays.asList("jack", "tom", "lily");
//        list.add("vince");

//        // 2、使用 Collections.unmodifiableList 创建只读集合
//        List<String> list = new ArrayList<>();
//        list.add("jack");
//        list.add("tom");
//        list.add("lily");
//        List<String> readList = Collections.unmodifiableList(list);
//        readList.add("vince");

        // 3、使用 ImmutableList.of 创建只读集合
        ImmutableList<String> list = ImmutableList.of("jack", "tom", "lily");
        list.add("vince");
    }

    /**
     * 2、函数式编程：过滤器
     */
    @Test
    public void testGuava2() {
        // 使用 Lists.newArrayList 创建的集合非只读
        ArrayList<String> list = Lists.newArrayList("java", "php", "javaScript", "h5", "jackson");

        // 过滤输出以 j 开头的元素
        Collection<String> result = Collections2.filter(list, (e) -> e.startsWith("j"));
        result.forEach(System.out::println);
    }

    /**
     * 3、函数式编程：转换
     */
    @Test
    public void testGuava3() {
        Set<Long> timeSet = Sets.newHashSet(1520446283952L, 1580446283952L, 1542446287952L);
        Collection<String> timeCollect = Collections2.transform(timeSet, (e) -> new SimpleDateFormat("yyyy-MM-dd").format(e));
        timeCollect.forEach(System.out::println);   // 2018-11-17、2018-03-08、2020-01-31
    }

    /**
     * 4、组合式函数编程
     * <p>
     * 1、如果元素长度大于4，截取
     * 2、转换大写
     * 3、转换集合
     */
    @Test
    public void testGuava4() {
        ArrayList<String> list = Lists.newArrayList("java", "php", "javaScript", "h5", "jackson");

        Function<String, String> f1 = (e) -> e.length() > 4 ? e.substring(0, 4) : e;
        Function<String, String> f2 = (e) -> e.toUpperCase();

        // 组合函数
        Function<String, String> f = Functions.compose(f1, f2);

        // 转换
        Collection<String> strCollect = Collections2.transform(list, f);
        strCollect.forEach(System.out::println);
    }

    /**
     * 5、集合操作：交集、差集、并集
     */
    @Test
    public void testGuava5() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3);
        Set<Integer> set2 = Sets.newHashSet(3, 4, 5);

        System.out.println("-------- 交集 ------");
        // 交集
        Sets.SetView<Integer> v1 = Sets.intersection(set1, set2);
        v1.forEach(System.out::println);

        System.out.println("\n-------- 差集 ------");
        // 差集
        Sets.SetView<Integer> v2 = Sets.difference(set1, set2);
        v2.forEach(System.out::println);

        System.out.println("\n-------- 并集 ------");
        // 并集
        Sets.SetView<Integer> v3 = Sets.union(set1, set2);
        v3.forEach(System.out::println);
    }


    /**
     * 6、Multiset：无序可重复
     */
    @Test
    public void testGuava6() {
        String str = "good good study day day up";
        String[] ss = str.split(" ");

        HashMultiset<String> multiset = HashMultiset.create();
        for (String s : ss) {
            multiset.add(s);
        }

        Set<String> set = multiset.elementSet();

        // 查看相同元素的个数
        set.forEach(s -> System.out.println(s + " : " + multiset.count(s)));
    }

    /**
     * 7、Multimap key 可以重复
     * <p>
     * 将相同的key合并，value值为list
     */
    @Test
    public void testGuava7() {
        Map<String, String> map = new HashMap<>();
        map.put("Java 从入门到精通", "bin");
        map.put("Android 从入门到精通", "bin");
        map.put("PHP 从入门到精通", "jack");
        map.put("笑看人生", "vince");

        // 创建 ArrayListMultimap 将 map 的key，value 反向存储
        ArrayListMultimap<String, String> listMultimap = ArrayListMultimap.create();
        map.forEach((k, v) -> listMultimap.put(v, k));

        System.out.println(listMultimap);

        System.out.println("\n------ 遍历 ArrayListMultimap -----");

        // 遍历，能看出来有两个 key 为 bin
        listMultimap.forEach((k, v) -> System.out.println(k + " : " + v));

        System.out.println("\n------ 遍历 keySet -----");

        listMultimap.keySet().forEach(k -> {
            List<String> values = listMultimap.get(k);
            System.out.println(k + " : " + values);
        });
    }


    /**
     * 8、BiMap：双向 Map (bidirectional Map）键与值不能重复
     */
    @Test
    public void testGuava8() {
        // 使用场景，比如微信号和手机号绑定
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("tingfeng", "13000000000");
        biMap.put("renkui", "18000000000");
        biMap.put("xiaomiao", "15000000000");

        // 使用 inverse 将 key，value 反转
        String username = biMap.inverse().get("13000000000");
        System.out.println("账号：" + username);
    }

    /**
     * 9、双键的 Map --> Table --> rowKey+columnKye+value
     */
    @Test
    public void test1() {
        Table<Object, Object, Object> table = HashBasedTable.create();
        table.put("jack", "java", 98);
        table.put("jack", "php", 65);
        table.put("jack", "ui", 80);
        table.put("jack", "mysql", 86);

        // 遍历
        Set<Table.Cell<Object, Object, Object>> cells = table.cellSet();
        cells.forEach(c -> System.out.println(c.getRowKey() + "-" + c.getColumnKey() + " : " + c.getValue()));
    }

}
