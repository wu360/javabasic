package com.example.basic.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TestHashMap {
    // 线程数
    private static final int threadCount = 1;

    /**
     * 相对于HashMap的存而言，取就显得比较简单了。通过key的hash值找到在table数组中的索引处的Entry，然后返回该key对应的value即可。
     * public V get(Object key) {
     * // 若为null，调用getForNullKey方法返回相对应的value
     * if (key == null) return getForNullKey();
     * // 根据该 key 的 hashCode 值计算它的 hash 码
     * int hash = hash(key.hashCode());
     * // 取出 table 数组中指定索引处的值
     * for (Entry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
     * Object k;
     * //若搜索的key与查找的key相同，则返回相对应的value
     * if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
     * return e.value;
     * }
     * return null;
     * }
     **/
    @Test
    public void get() {

    }


    @Test
    public void test() throws InterruptedException {
        // 用于计数线程是否执行完成
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        final HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> map.put(UUID.randomUUID().toString(), "")).start();
        }
        countDownLatch.await();
        log.info(map.toString());
    }

    @Test
    public void hashMap() {
        log.debug("debug");//默认日志级别为info
        log.info("info");
        log.error("error");
        log.warn("warn");
        HashMap map = new HashMap();
//        HashMap<java.lang.String, Integer> map = null;
        map.put("one", "1");
//        map.put("two", map);
        System.out.println(map.get("one"));

        log.info("Using orElse");
//        assertEquals(java.util.Optional.ofNullable(map.get("one")), "1");
    }

    @Test
    public void sort2(){
//        Map<String, String> map = new TreeMap<String, String>(
//                new Comparator<String>() {
//                    public int compare(String obj1, String obj2) {
//                        // 降序排序
//                        return obj2.compareTo(obj1);
//                    }
//                });
        Map<String, String> map = new TreeMap<>(Comparator.reverseOrder());
        map.put("2019-01", "ddddd");
        map.put("2019-11", "aaaaa");
        map.put("2019-05", "bbbbb");
        map.put("2019-12", "ddddd");

        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + map.get(key));
        }
    }
    @Test
    public void sort(){
        // 初始数据
        Map<String, Integer> smap = new HashMap<>();
        smap.put("201910", 11);
        smap.put("201911", 33);
        smap.put("201908", 22);

        // 1.8以前
        List<Map.Entry<String, Integer>> list1 = new ArrayList<>();
        list1.addAll(smap.entrySet());
        Collections.sort(list1, new Comparator<Map.Entry<String, Integer>>() {


            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return Integer.parseInt(o1.getKey()) - Integer.parseInt(o2.getKey());
            }
        });
        for (Map.Entry<String, Integer> entry : list1) {
            System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
        }

        // 1.8使用lambda表达式
        List<Map.Entry<String, Integer>> list2 = new ArrayList<>();
        list2.addAll(smap.entrySet());
        Collections.sort(list2, Comparator.comparingInt(o -> Integer.parseInt(o.getKey())));
        list2.forEach(entry -> {
            System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
        });
    }

    @Test
    public void traverse(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");

        //第一种：普遍使用，二次取值
        System.out.println("通过Map.keySet遍历key和value：");
        for (String key : map.keySet()) {
            System.out.println("key= "+ key + " and value= " + map.get(key));
        }

        //第二种
        System.out.println("通过Map.entrySet使用iterator遍历key和value：");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        //第三种：推荐，尤其是容量大时
        System.out.println("通过Map.entrySet遍历key和value");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        //第四种
        System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
        for (String v : map.values()) {
            System.out.println("value= " + v);
        }
    }
}
