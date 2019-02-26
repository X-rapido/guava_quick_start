package com.example.guava.collect;

import com.google.common.base.Function;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.Tables;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.Set;


public class TablesTest {

    /**
     * 返回具有指定行键，列键和值的不可变单元格
     */
    @Test
    public void test_immutableCell() {
        Cell<String, Integer, Character> entry = Tables.immutableCell("foo", 1, 'a');
        System.out.println(entry.getRowKey());      // foo
        System.out.println(entry.getColumnKey());   // 1
        System.out.println(entry.getValue());       // a
        System.out.println(entry);                  // (foo,1)=a

        Cell<String, Integer, Character> nullEntry = Tables.immutableCell(null, null, null);
        System.out.println(nullEntry.toString());   // (null,null)=null
    }

    /**
     * transpose rowKey与columnKey翻转
     */
    @Test
    public void test_transpose() {
        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("jack", "java", 98);
        table.put("jack", "php", 65);
        table.put("jack", "ui", 80);
        table.put("jack", "mysql", 86);

        System.out.println("遍历 table");
        Set<Cell<String, String, Integer>> cells = table.cellSet();
        cells.forEach(c -> System.out.println(c.getRowKey() + "-" + c.getColumnKey() + " : " + c.getValue()));

        System.out.println("\n遍历 transposeTable");
        Table<String, String, Integer> transposeTable = Tables.transpose(table);
        transposeTable.cellSet().forEach(c -> System.out.println(c.getRowKey() + "-" + c.getColumnKey() + " : " + c.getValue()));

        System.out.println("\n遍历 transformValues");
        Table<String, String, Object> transformValues = Tables.transformValues(table, new Function<Integer, Object>() {
            @Override
            public Object apply(@Nullable Integer input) {
                return input > 80;
            }
        });
        transformValues.cellSet().forEach(c -> System.out.println(c.getRowKey() + "-" + c.getColumnKey() + " : " + c.getValue()));
    }

}
