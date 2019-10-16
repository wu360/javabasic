package com.example.basic.collection;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CocurrentHashMapTest {
    private Map<Integer, Integer> cache = new ConcurrentHashMap<>(15);

    @Test
    public void test() {
        CocurrentHashMapTest ch = new CocurrentHashMapTest();
        System.out.println(ch.fibonaacci(80));
    }

    /**
     * 我们知道Hashtable是synchronized的，但是ConcurrentHashMap同步性能更好，
     * 因为它仅仅根据同步级别对map的一部分进行上锁。ConcurrentHashMap当然可以代替HashTable，
     * 但是HashTable提供更强的线程安全性。它们都可以用于多线程的环境，但是当Hashtable的大小增加到一定的时候，
     * 性能会急剧下降，因为迭代时需要被锁定很长的时间。
     * 因为ConcurrentHashMap引入了分割(segmentation)，不论它变得多么大，仅仅需要锁定map的某个部分，而其它的线程不需要等到迭代完成才能访问map。
     * 简而言之，在迭代的过程中，ConcurrentHashMap仅仅锁定map的某个部分，而Hashtable则会锁定整个map。
     * CocurrentHashMap在JAVA8中存在一个bug，会进入死循环，原因是递归创建ConcurrentHashMap 对象，但是在1.9已经修复了,场景重现如下
     *
     *
     * Hashtable的实现方式---锁整个hash表；
     *
     * 而右边则是ConcurrentHashMap的实现方式---锁桶（或段）。
     * ConcurrentHashMap将hash表分为16个桶（默认值），诸如get,put,remove等常用操作只锁当前需要用到的桶。
     * 试想，原来 只能一个线程进入，现在却能同时16个写线程进入（写线程才需要锁定，而读线程几乎不受限制，之后会提到），并发性的提升是显而易见的。
     *
     * 更令人惊讶的是ConcurrentHashMap的读取并发，因为在读取的大多数时候都没有用到锁定，所以读取操作几乎是完全的并发操作，
     *     而写操作锁定的粒度又非常细，比起之前又更加快速（这一点在桶更多时表现得更明显些）
     *
     * @param i
     * @return
     */
    public int fibonaacci(Integer i) {
        if (i == 0 || i == 1) {
            return i;
        }

        return cache.computeIfAbsent(i, (key) -> {
            System.out.println("fibonaacci : " + key);
            return fibonaacci(key - 1) + fibonaacci(key - 2);
        });

    }

    /**
     * 作者：深夜里的程序猿
     * 链接：http://www.imooc.com/article/285576
     * 来源：慕课网
     * 本文原创发布于慕课网 ，转载请注明出处，谢谢合作
     * 在step1跟step2中，都只是调用ConcurrentHashMap的方法，各自都是原子操作，是线程安全的。
     * 但是他们组合在一起的时候就会有问题了，
     *
     * @param args
     * @throws InterruptedException
     */

    @Test
    public void test2() throws InterruptedException {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 1);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                map.put("key", map.get("key") + 1);//step 2
            });
        }
        Thread.sleep(3000); //模拟等待执行结束
        System.out.println("------" + map.get("key") + "------");
        executorService.shutdown();
    }

    /**
     * 用原子类 解决上面问题
     *
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();
        AtomicInteger integer = new AtomicInteger(1);
        map.put("key", integer);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> map.get("key").incrementAndGet());
        }
        Thread.sleep(3000); //模拟等待执行结束
        System.out.println("------" + map.get("key") + "------");
        executorService.shutdown();
    }

}
