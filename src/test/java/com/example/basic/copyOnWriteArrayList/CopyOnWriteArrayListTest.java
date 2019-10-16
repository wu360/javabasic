package com.example.basic.copyOnWriteArrayList;


import org.junit.Test;

import java.util.Iterator;

public class CopyOnWriteArrayListTest {

    @Test
    public void main() {
        CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
        arrayList.add("hello");
        arrayList.add("java");

        Iterator<String> itr = arrayList.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

    }




    /**
     * Java并发编程笔记之CopyOnWriteArrayList源码分析
     * https://www.cnblogs.com/huangjuncong/p/9160713.html
     *
     * 其中迭代器的hasNext方法用来判断是否还有元素，next方法则是具体返回元素。
     * 那么接下来看CopyOnWriteArrayList中迭代器是弱一致性，
     * 所谓弱一致性是指返回迭代器后，其他线程对list的增删改对迭代器不可见，无感知的，那么下面就看看是如何做到的
     * public Iterator<E> iterator() {
     *     return new COWIterator<E>(getArray(), 0);
     * }
     *
     * static final class COWIterator<E> implements ListIterator<E> {
     *     //array的快照版本
     *     private final Object[] snapshot;
     *
     *     //数组下标
     *     private int cursor;
     *
     *     //构造函数
     *     private COWIterator(Object[] elements, int initialCursor) {
     *         cursor = initialCursor;
     *         snapshot = elements;
     *     }
     *
     *     //是否遍历结束
     *     public boolean hasNext() {
     *         return cursor < snapshot.length;
     *     }
     *
     *     //获取元素
     *     public E next() {
     *         if (! hasNext())
     *             throw new NoSuchElementException();
     *         return (E) snapshot[cursor++];
     *     }
     * }
     * 如上代码当调用iterator()方法获取迭代器时候实际是返回一个COWIterator对象，COWIterator的snapshot变量保存了当前list的内容，cursor是遍历list数据的下标。
     *
     * 这里为什么说snapshot是list的快照呢？明明是指针传递的引用，而不是拷贝。如果在该线程使用返回的迭代器遍历元素的过程中，其他线程没有对list进行增删改，那么snapshot本身就是list的array,因为它们是引用关系。
     *
     * 但是如果遍历期间，有其他线程对该list进行了增删改，那么snapshot就是快照了，因为增删改后list里面的数组被新数组替换了，这时候老数组只有被snapshot索引用，所以这也就说明获取迭代器后，使用改迭代器进行变量元素时候，其它线程对该list进行的增删改是不可见的，
     *
     * 因为它们操作的是两个不同的数组，这也就是弱一致性的达成。
     *
     *
     *
     * 如上代码main函数首先初始化了arrayList，然后在启动线程前获取到了arrayList迭代器，子线程ThreadOne启动后首先修改了arrayList第一个元素的值，然后删除了arrayList中坐标为2，3 的元素。
     *
     * 主线程等待子线程执行完毕后使用获取的迭代器遍历数组元素，从打印的结果来看，子线程里面进行的操纵是一个都没有生效，这就是迭代器的弱一致性的效果，需要注意的是获取迭代器必须在子线程操作之前进行。

     * 注意：CopyOnWriteArrayList使用写时拷贝的策略来保证list的一致性，而获取-拷贝-写入 三步并不是原子性的，所以在修改增删改的过程中都是用了独占锁，并保证了同时只有一个线程才能对list数组进行修改。
     *
     * @throws InterruptedException
     */
    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
    @Test
    public void test() throws InterruptedException {
        arrayList.add("hello");
        arrayList.add("Java");
        arrayList.add("welcome");
        arrayList.add("to");
        arrayList.add("hangzhou");

        Thread threadOne = new Thread(() -> {

            //修改list中下标为1的元素为JavaSe
            arrayList.set(1, "JavaSe");
            //删除元素
            arrayList.remove(2);
            arrayList.remove(3);

        });

        //保证在修改线程启动前获取迭代器
        Iterator<String> itr = arrayList.iterator();

        //启动线程
        threadOne.start();

        //等在子线程执行完毕
        threadOne.join();

        //迭代元素
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

    }
}
