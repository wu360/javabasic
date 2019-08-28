package com.example.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DateTime {

    @Test
    public void time() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(2000);
        log.info(java.lang.String.valueOf(System.currentTimeMillis()-start));
    }

}