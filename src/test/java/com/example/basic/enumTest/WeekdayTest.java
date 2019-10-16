package com.example.basic.enumTest;

import org.junit.Test;

public class WeekdayTest {
    @Test
    public void test() {
        Weekday sun = Weekday.SUN;
        System.out.println(sun); // 输出 SUN

        System.out.println("nowday ====> " + Weekday.SAT);
        System.out.println("nowday int ====> " + Weekday.SAT.ordinal());
        System.out.println("nextday ====> " + Weekday.getNextDay(Weekday.SAT)); // 输出 SUN
    }
}
