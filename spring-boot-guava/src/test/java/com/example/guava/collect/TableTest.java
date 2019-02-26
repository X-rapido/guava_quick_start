package com.example.guava.collect;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * Table 集合
 */
public class TableTest extends TestCase {

    /**
     * 双键的 Map --> Table --> rowKey+columnKye+value
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

    public void test2() {
        //Table<R,C,V> == Map<R,Map<C,V>>
        /*
         *  Company: IBM, Microsoft, TCS
         *  IBM 		-> {101:Mahesh, 102:Ramesh, 103:Suresh}
         *  Microsoft 	-> {101:Sohan, 102:Mohan, 103:Rohan }
         *  TCS 		-> {101:Ram, 102: Shyam, 103: Sunil }
         *
         * */
        Table<String, String, String> table = HashBasedTable.create();

        // 使用员工详细信息初始化表
        table.put("IBM", "101", "Mahesh");
        table.put("IBM", "102", "Ramesh");
        table.put("IBM", "103", "Suresh");

        table.put("Microsoft", "111", "Sohan");
        table.put("Microsoft", "112", "Mohan");
        table.put("Microsoft", "113", "Rohan");

        table.put("TCS", "121", "Ram");
        table.put("TCS", "122", "Shyam");
        table.put("TCS", "123", "Sunil");

        // 获取与IBM对应的Map
        Map<String, String> ibmMap = table.row("IBM");

        System.out.println("IBM员工名单");
        for (Map.Entry<String, String> entry : ibmMap.entrySet()) {
            System.out.println("Emp Id: " + entry.getKey() + ", Name: " + entry.getValue());
        }
        System.out.println();

        // 获取表格的所有唯一键
        Set<String> employers = table.rowKeySet();
        System.out.print("Employers: ");
        employers.forEach(e -> System.out.print(e + " "));
        System.out.println("\n");

        // 得到一个对应102的Map
        Map<String, String> EmployerMap = table.column("102");
        for (Map.Entry<String, String> entry : EmployerMap.entrySet()) {
            System.out.println("Employer: " + entry.getKey() + ", Name: " + entry.getValue());
        }
    }

    public void test3() {
        Table<String, Integer, Integer> table = HashBasedTable.create();
        table.put("A", 1, 100);
        table.put("A", 2, 101);
        table.put("B", 1, 200);
        table.put("B", 2, 201);

        /**
         *  contains(Object rowKey, Object columnKey)：
         *  Table中是否存在指定rowKey和columnKey的映射关系
         */
        boolean containsA3 = table.contains("A", 3); // false
        boolean containColumn2 = table.containsColumn(2); // true
        boolean containsRowA = table.containsRow("A");  // true
        boolean contains201 = table.containsValue(201);  // true

        /**
         * remove(Object rowKey, Object columnKey)：
         * 删除Table中指定行列值的映射关系
         */
        table.remove("A", 2);

        /**
         * get(Object rowKey, Object columnKey)：
         * 获取Table中指定行列值的映射关系
         */
        table.get("B", 2);

        /**
         * column(C columnKey)：返回指定columnKey下的所有rowKey与value映射
         */
        Map<String, Integer> columnMap = table.column(2);

        /**
         * row(R rowKey)：返回指定rowKey下的所有columnKey与value映射
         */
        Map<Integer, Integer> rowMap = table.row("B");

        /**
         * 返回以Table.Cell<R, C, V>为元素的Set集合
         * 类似于Map.entrySet
         */
        Set<Table.Cell<String, Integer, Integer>> cells = table.cellSet();
        for (Table.Cell<String, Integer, Integer> cell : cells) {
            //获取cell的行值rowKey
            cell.getRowKey();
            //获取cell的列值columnKey
            cell.getColumnKey();
            //获取cell的值value
            cell.getValue();
        }
    }
}
