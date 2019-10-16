package com.example.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@Slf4j
public class TestString {

    /**
     * 1、在字符串不经常发生变化的业务场景优先使用String(代码更清晰简洁)。如常量的声明，少量的字符串操作(拼接，删除等)。
     *
     * 2、在单线程情况下，如有大量的字符串操作情况，应该使用StringBuilder来操作字符串。不能使用String"+"来拼接而是使用，避免产生大量无用的中间对象，耗费空间且执行效率低下（新建对象、回收对象花费大量时间）。如JSON的封装等。
     *
     * 3、在多线程情况下，如有大量的字符串操作情况，应该使用StringBuffer。
     *
     * String还提供了intern()方法。调用该方法时，如果字符串常量池中包括了一个等于此String对象的字符串（由equals方法确定），
     * 则返回池中的字符串的引用。否则，将此String对象添加到池中，并且返回此池中对象的引用
     * **/

    @Test
    public void test(){
        String str1 = new String("001001999"); //不检查字符串常量池的

        String str2 = "bb"; //检查字符串常量池的
        String str3 = str1.substring(6);
        log.info(str3);
    }


    @Test
    public void split() {
        java.lang.String str = " |1| | || |";
        java.lang.String[] parts = str.replaceAll(" ", "").split("\\|",-1);
        assertEquals("0042", parts[0]);
//        assert !parts[0].equals("004") : "already serving";
    }

    @Test
    public void empty() {
        java.lang.String str;
        str = "";
        assertEquals(str, null);
    }
   @Test
    public void test5() {
        java.lang.String path = " jsonaaa.json";


       String[] items =  path.split("(\\\\|\\/)");
       String fileName = items[items.length - 1].replace(".json", "");
        log.info(fileName);
    }

}
