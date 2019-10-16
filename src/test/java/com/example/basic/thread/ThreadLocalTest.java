package com.example.basic.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ThreadLocalTest {
    @Test
    public void main() {
        ThreadLocalTest p = new ThreadLocalTest();
        System.out.println("1---" + p);
        Thread t = new Thread(() -> {
            ThreadLocal<ThreadLocalTest> threadLocal = new ThreadLocal<>();
            System.out.println("2---" + threadLocal);
            threadLocal.set(p);
            System.out.println("3---" + threadLocal.get());
            threadLocal.remove();
            try {
                threadLocal.set((ThreadLocalTest) p.clone()); //java.lang.CloneNotSupportedException
                System.out.println("4---" + threadLocal.get());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            System.out.println("5---" + threadLocal);
        });
        t.start();
    }
}
