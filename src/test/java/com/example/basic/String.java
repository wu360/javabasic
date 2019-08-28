package com.example.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class String {

    @Test
    public void split() {
        java.lang.String str = "004.034556";
        java.lang.String[] parts = str.split("\\.");
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
}



