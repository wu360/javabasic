package com.example.basic.array;

import org.junit.Test;

import java.util.LinkedList;

/**
 * 利用linkedList来实现队列
 * 队列: 先进先出
 *
 */
public class QueueTest {
    private LinkedList<String> link = new LinkedList<String>();

    // 放入
    public void put(String str){
        link.addFirst(str);
    }

    // 获取
    public String get(){
        return link.removeLast();
    }

    // 判断是否为空
    public boolean isEmpty(){
        return link.isEmpty();
    }

    @Test
    public  void test() {
        // 测试队列
        QueueTest queue = new QueueTest();

        queue.put("我是第1个进入队列的");
        queue.put("我是第2个进入队列的");
        queue.put("我是第3个进入队列的");
        queue.put("我是第4个进入队列的");

        // 遍历队列
        while (!queue.isEmpty()){
            String str = queue.get();
            System.out.println(str);
        }
        // 打印结果
        /*我是第1个进入队列的
        我是第2个进入队列的
        我是第3个进入队列的
        我是第4个进入队列的*/

    }
}
