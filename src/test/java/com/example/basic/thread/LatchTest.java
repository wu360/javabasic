package com.example.basic.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Java 中如何模拟真正的同时并发请求？
 * https://mp.weixin.qq.com/s?__biz=MzUzMTA2NTU2Ng==&mid=2247487771&idx=2&sn=1754abbfae82f84439e8a45e597ec25d&chksm=fa496eaacd3ee7bc1decd862bab3a9f389ee590ec443e06948c5e32f06bc8292c4d68dc57a07&scene=0&xtrack=1&key=e15c8f28f8704370b8b4873365235f7aa9d05f1cd419074e2e10dbadc682d714b048c6ddaa2528cfb88f674cd645f565ff483e6f5a0b3dbdd280d176564ee725251f2fbd49091fe6aee814b5d6726d08&ascene=1&uin=MjAzNjI5NzYyMg%3D%3D&devicetype=Windows+10&version=62060833&lang=zh_CN&pass_ticket=f6KT%2BRC3HjL3prut7t70jfbLv4IZ0Jj%2Bpz%2FiPaIveDuIZuakdwVlphSywNWhLbHX
 */
@Slf4j
public class LatchTest {

    @Test
    public void main() throws InterruptedException {
        Runnable taskTemp = new Runnable() {

            // 注意，此处是非线程安全的，留坑
            private int iCounter;

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    // 发起请求
//                    HttpClientOp.doGet("https://www.baidu.com/");
                    iCounter++;
                    log.info("{} [{}] iCounter = {}", System.nanoTime(), Thread.currentThread().getName(), iCounter);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        LatchTest latchTest = new LatchTest();
        latchTest.startTaskAllInOnce(5, taskTemp);
    }

    public long startTaskAllInOnce(int threadNums, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNums);
        for (int i = 0; i < threadNums; i++) {
            Thread t = new Thread(() -> {
                try {
                    // 使线程在此等待，当开始门打开时，一起涌入门中
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        // 将结束门减1，减到0时，就可以开启结束门了
                        endGate.countDown();
                    }
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            });
            t.start();
        }
        long startTime = System.nanoTime();
        System.out.println(startTime + " [" + Thread.currentThread() + "] All thread is ready, concurrent going...");
        // 因开启门只需一个开关，所以立马就开启开始门
        startGate.countDown();
        // 等等结束门开启
        endGate.await();
        long endTime = System.nanoTime();
        System.out.println(endTime + " [" + Thread.currentThread() + "] All thread is completed.");
        return endTime - startTime;
    }
}