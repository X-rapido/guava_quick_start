package com.example.guava.cache;

import com.google.common.cache.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import junit.framework.TestCase;
import org.assertj.core.util.Lists;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 缓存测试
 */
public class CacheTest extends TestCase {

    public void test1() {
        // 根据员工ID为员工创建缓存
        // 移除监听器
        LoadingCache<String, Object> employeeCache = CacheBuilder.newBuilder()
                .maximumSize(100) // 最多可以缓存100条记录
                .expireAfterAccess(30, TimeUnit.MINUTES) // 缓存将在访问30分钟后过期
                .removalListener((RemovalListener<String, Object>) notification -> System.out.println("notification："+notification))
                .build(new CacheLoader<String, Object>() {

                    // 通常重写一个 load 方法即可
                    @Override
                    public Object load(String key) throws Exception {
                        // 数据库加载
                        return getFromDatabase(key);
                    }

                    @Override
                    public ListenableFuture<Object> reload(String key, Object oldValue) throws Exception {
                        return super.reload(key, oldValue);
                    }
                });


        // 1、单个查询
        System.out.println("1、单个查询");
        try {
            // 在第一次调用时，缓存将填充相应的员工记录
            System.out.println("调用 #1");
            System.out.println(employeeCache.get("100"));
            System.out.println(employeeCache.get("103"));
            System.out.println(employeeCache.get("110"));

            // 第二次调用时，将从缓存中返回数据
            System.out.println("\n调用 #2");
            System.out.println(employeeCache.get("100"));
            System.out.println(employeeCache.get("103"));
            System.out.println(employeeCache.get("110"));

        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 第三次调用时 getUnchecked 不执行检查
        System.out.println("\n调用 #3");
        System.out.println(employeeCache.getUnchecked("100"));
        System.out.println(employeeCache.getUnchecked("103"));
        System.out.println(employeeCache.getUnchecked("110"));

        // 2、批量查询
        try {
            System.out.println("\n2、批量查询");
            ImmutableMap<String, Object> cacheAll = employeeCache.getAll(Lists.newArrayList("100", "103", "110"));
            cacheAll.forEach((k, v) -> System.out.println("K：" + k + "，V：" + v));

            // 3、具有回调的查询
            System.out.println("\n3、具有回调的查询");
            String key = "001";
            System.out.println(employeeCache.get(key, new Callable<Employee>() {
                @Override
                public Employee call() throws Exception {
                    return getFromDatabase(key);
                }
            }));

            // lambda写法
            System.out.println(employeeCache.get(key, (Callable<Employee>) () -> getFromDatabase(key)));

            // 4、显示插入 put
            System.out.println("\n4、显示插入");
            employeeCache.put("001","听风");
            System.out.println(employeeCache.get("001"));

            // 5、更改视图
            ConcurrentMap<String, Object> asMap = employeeCache.asMap();
            asMap.put("100","程序喵");
            employeeCache.getAll(Lists.newArrayList("100", "103", "110")).forEach((k, v) -> System.out.println("K：" + k + "，V：" + v));

        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 6、清除所有缓存
        System.out.println("\n6、清除所有缓存，调用移除监听器");
        employeeCache.invalidateAll();
    }

    private static Employee getFromDatabase(String empId) {
        Map<String, Employee> database = new HashMap();
        database.put("100", new Employee("Mahesh", "Finance", "100"));
        database.put("103", new Employee("Rohan", "IT", "103"));
        database.put("110", new Employee("Sohan", "Admin", "110"));

        database.put("001", new Employee("Tingfeng", "Admin", "001"));
        System.out.println("数据库命中" + empId);
        return database.get(empId);
    }

}
