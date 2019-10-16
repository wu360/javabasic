package com.example.basic.date;


import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateTest {

    /**
     * Instant：它代表的是时间戳，表示时刻，不直接对应年月日信息，需要通过时区转换
     *
     * LocalDateTime: 表示与时区无关的日期和时间信息，不直接对应时刻，需要通过时区转换
     *
     * LocalDate：表示与时区无关的日期，与LocalDateTime相比，只有日期信息，没有时间信息
     *
     * LocalTime：表示与时区无关的时间，与LocalDateTime相比，只有时间信息，没有日期信息
     *
     * ZonedDateTime： 表示特定时区的日期和时间
     *
     * ZoneId/ZoneOffset：表示时区
     */
    @Test
    public void main() {
        int currentMaxDays = getCurrentMonthDay();
        int currentLeftDay = getCurrentLeftDay();
        int maxDaysByDate = getDaysByYearMonth(2019, 8);
        int maxDaysByMonth = getDaysByMonth(2);
        System.out.println("本月剩余天数：" + currentLeftDay);
        System.out.println("本月天数：" + currentMaxDays);
        System.out.println("2017年9月天数：" + maxDaysByDate);
        System.out.println("maxDaysByMonth：" + maxDaysByMonth);
    }

    /**
     * 获取当月剩余天数
     */
    public static int getCurrentLeftDay() {
        Calendar calendar = Calendar.getInstance();
        //获取当前天数
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int letfDate = getCurrentMonthDay() - day;
        return letfDate;
    }

    /**
     * 获取当月的天数
     */
    public static int getCurrentMonthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1); // 把日期设置为当月第一天
        calendar.roll(Calendar.DATE, -1); // 日期回滚一天，也是最后一天
        int maxDate = calendar.get(Calendar.DATE);
        return maxDate;
    }


    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxDate = calendar.get(Calendar.DATE);
        return maxDate;
    }

    @Test
    public void getIncrDateByMonthTest(){
//        log.info(getIncrDateByMonth(0));
    }

    /**
     * 根据年 月 获取对应的月末日期
     * @return
     */
    public static Date getIncrDateByMonth(int monthNum) {
        // 日期增量
        LocalDate date = LocalDate.now();
        LocalDate newDate = date.plus(monthNum, ChronoUnit.MONTHS);
        int month = newDate.getMonthValue();
        int year = newDate.getYear();
        int maxDay = getDaysByYearMonth(year, month);
        String retDate = year +"-"+ month +"-"+ maxDay ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date d = (Date) formatter.parse(retDate);
        return d;
    }

    /**
     * Date转LocalDate
     * @param date
     */
    public static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    /**
     * 根据年月获取对应的月末日期
     * @return
     */
    public static Date getEndDateByDate(Date date) {
        LocalDate localDate = date2LocalDate(date);
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int maxDay = getDaysByYearMonth(year, month);
        String retDate = year +"-"+ month +"-"+ maxDay ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date d = (Date) formatter.parse(retDate);
        return d;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByMonth(int month) {
        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxDate = calendar.get(Calendar.DATE);
        return maxDate;
    }


    @Test
    public void format4() {

        // 时间增量
        LocalTime time = LocalTime.now();
        LocalTime newTime = time.plusHours(2);
        System.out.println("newTime=" + newTime);

        // 日期增量
        LocalDate date = LocalDate.now();
        LocalDate newDate = date.plus(1, ChronoUnit.MONTHS);
//        LocalDate newDate = date.plus(1, ChronoUnit.WEEKS);
        System.out.println("newDate=" + newDate);
    }

    @Test
    public void format2() {
        // 解析日期
        String dateText = "20180924";
        LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("格式化之后的日期=" + date);

        // 格式化日期
        dateText = date.format(DateTimeFormatter.ISO_DATE);
        System.out.println("dateText=" + dateText);
    }

    @Test
    public void format3() {
        // 上海时间
        ZoneId shanghaiZoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime shanghaiZonedDateTime = ZonedDateTime.now(shanghaiZoneId);

        // 东京时间
        ZoneId tokyoZoneId = ZoneId.of("Asia/Tokyo");
        ZonedDateTime tokyoZonedDateTime = ZonedDateTime.now(tokyoZoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("上海时间: " + shanghaiZonedDateTime.format(formatter));
        System.out.println("东京时间: " + tokyoZonedDateTime.format(formatter));
    }

    @Test
    public void format() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 日期时间转字符串
        LocalDateTime now = LocalDateTime.now();
        String nowText = now.format(formatter);
        System.out.println("nowText=" + nowText);

        // 字符串转日期时间
        String datetimeText = "1999-12-31 23:59:59";
        LocalDateTime datetime = LocalDateTime.parse(datetimeText, formatter);
        System.out.println(datetime);
    }
}
