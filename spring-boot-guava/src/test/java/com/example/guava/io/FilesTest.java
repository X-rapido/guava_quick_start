package com.example.guava.io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesTest {

    private String SOURCE_PATH = "/Users/liurenkui/workSpace/Gitee/example/spring-boot-2.0-example/spring-boot-guava/src/main/resources/grava_demo.txt";

    /**
     * 文件拷贝
     */
    @Test
    public void test1() throws Exception {
        Files.copy(new File("from file path"), new File("to file path"));
    }

    /**
     * 文件移动
     */
    @Test
    public void test2() throws Exception {
        Files.move(new File("from file path"), new File("to file path"));
    }

    /**
     * 文件逐行读取
     */
    @Test
    public void test3() throws Exception {
        File file = new File(SOURCE_PATH);
        List<String> lines = Files.readLines(file, Charsets.UTF_8);

        String result = Joiner.on("\n").join(lines);
        System.out.println("读取内容：" + result);
    }

    /**
     * 文件整体读取
     */
    @Test
    public void test4() throws Exception {
//        String result = Files.asCharSource(new File(SOURCE_PATH), Charsets.UTF_8).readFirstLine();    // 第一行
        String result = Files.asCharSource(new File(SOURCE_PATH), Charsets.UTF_8).read();
        System.out.println("读取内容：" + result);
    }

    /**
     * 文件 hash 值
     * <p>
     * 在文件对比时候，可以看两个文件等hashCode 是否相等，比如拷贝文件后是否有损
     */
    @Test
    public void tes54() throws Exception {
        HashCode hashCode = Files.asByteSource(new File(SOURCE_PATH)).hash(Hashing.sha256());
        System.out.println(hashCode);
    }

    /**
     * 文件书写
     */
    @Test
    public void test6() throws Exception {
        Files.asCharSink(new File(SOURCE_PATH), Charsets.UTF_8).write("覆盖 内容");

//        Files.asCharSink(new File(SOURCE_PATH), Charsets.UTF_8, FileWriteMode.APPEND).write("\n\n追加 内容");
    }

    /**
     * 创建空文件
     */
    @Test
    public void test7() throws Exception {
        Files.touch(new File(""));
    }

    /**
     * 递归文件
     */
    @Test
    public void test8() throws Exception {
        String path = "/Users/liurenkui/Desktop";
        List<File> list = new ArrayList<>();

        this.recursiveList(new File(path), list);

        list.forEach(System.out::println);
    }

    private void recursiveList(File root, List<File> fileList) {
        if (root.isHidden()) return;
        if (root.isFile()) {
            fileList.add(root);
        } else {
            File[] files = root.listFiles();
            for (File f : files) {
                recursiveList(f, fileList);
            }
        }
    }


    /**
     * 递归文件
     * <p>
     * breadthFirst 为【广度优先遍历】
     * depthFirstPreOrder 和 depthFirstPostOrder 为【深度优先遍历】
     * <p>
     * 广度优先、深度优先 参考文章：https://www.jishux.com/p/7dbaf8611d052037
     */
    @Test
    public void test9() throws Exception {
        String path = "/Users/liurenkui/Desktop";

        Iterable<File> files = Files.fileTraverser().breadthFirst(new File(path));
        files.forEach(System.out::println);

        // 第一次访问到节点的顺序（Pre-order）
//        Iterable<File> files = Files.fileTraverser().depthFirstPreOrder(new File(path));
//        files.forEach(System.out::println);

        // 访问到最后，然后回退访问节点的顺序（Post-order）
//        Iterable<File> files = Files.fileTraverser().depthFirstPostOrder(new File(path));
//        files.forEach(System.out::println);
    }

}
