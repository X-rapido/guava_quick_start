package com.example.guava.basic;

import com.google.common.base.Stopwatch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Stopwatch 测试
 */
public class StopwatchTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test1() throws Exception {
        String orderNo = "12345678";

        logger.info("订单 [{}] 开始处理", orderNo);
        Stopwatch stopwatch = Stopwatch.createStarted();

        TimeUnit.SECONDS.sleep(1);  // 1秒处理时间

        logger.info("订单 [{}] 处理完成，耗时 [{}]", orderNo, stopwatch.stop());

    }

    @Test
    public void test2() throws Exception {
        // 创建stopwatch并开始计时
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(1980);

        // 以秒打印从计时开始至现在的所用时间，向下取整
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 1

        // 停止计时
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 1

        // 再次计时
        stopwatch.start();
        Thread.sleep(100);
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 2

        // 重置并开始
        stopwatch.reset().start();
        Thread.sleep(1030);

        // 检查是否运行
        System.out.println(stopwatch.isRunning()); // true
        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS); // 1034
        System.out.println(millis);

        // 打印
        System.out.println(stopwatch.toString()); // 1.034 s
    }
}
