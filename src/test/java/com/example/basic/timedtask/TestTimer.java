package com.example.basic.timedtask;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TestTimer {

    @Test
    public void testScheduled() {
//        @Scheduled(cron = "0/5 * * * * *")
//        public void scheduled(){
//            log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
//        }
//
//        @Scheduled(fixedRate = 5000)
//        public void scheduled1() {
//            log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
//        }
//
//        @Scheduled(fixedDelay = 5000)
//        public void scheduled2() {
//            log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
//        }
    }

    @Test
    public void testScheduledExecutorService() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        service.scheduleAtFixedRate(() -> System.out.println("task ScheduledExecutorService " + new Date()), 0, 3, TimeUnit.SECONDS);

    }

    @Test
    public void testTimerTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task  run:" + new Date());
            }

        };

        Timer timer = new Timer();

        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是每3秒执行一次
        timer.schedule(timerTask, 10, 3000);
    }

    @Test
    public void main() {
        Date now = new Date();    //创建一个Date对象，获取当前时间
        //指定格式化格式
        SimpleDateFormat f = new SimpleDateFormat("今天是 " + "yyyy 年 MM 月 dd 日 E HH 点 mm 分 ss 秒");
        log.info(f.format(now));    //将当前时间袼式化为指定的格式

        //使用静态方法获取实例。只能格式化日期
        DateFormat df1 = DateFormat.getDateInstance();
        //只能格式化时间
        DateFormat df2 = DateFormat.getTimeInstance();
        //格式化日期时间
        DateFormat df3 = DateFormat.getDateTimeInstance();
        //要格式化的Date对象
        Date date = new Date();
        //使用format()格式化Date对象
        System.out.println(df1.format(date));
        System.out.println(df2.format(date));
        System.out.println(df3.format(date));

    }

    @Test
    public void dateTimeFormatter() {
        //使用预定义的格式。和DateFormat不同，以下三个方法方法均没有重载方法，只能这样用。预定义的常量为FormatStyle类的SHORT、MEDIUM、LONG、FULL
        DateTimeFormatter df1 = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);   //只能格式化日期部分
        DateTimeFormatter df2 = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);    //只能格式化时间部分
        DateTimeFormatter df3 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);   //格式化日期时间

        //使用自定义的格式
        DateTimeFormatter df4 = DateTimeFormatter.ofPattern("yy-MM-ddHH:mm:ss");

        //要格式化的时间日期对象，只能用LocalDateTime。只涉及到日期也可以用LocalDate类，只涉及时间也可以用LocalTime类
        LocalDateTime ldt = LocalDateTime.now();

        //格式化,不能用Date类的实例作为参数
        System.out.println(df1.format(ldt));
        System.out.println(df2.format(ldt));
        System.out.println(df3.format(ldt));
        System.out.println(df4.format(ldt));
    }

    @Test
    public void date2String() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("1999-09-09 12:09:12");
        log.info(String.valueOf(date));
//        Date 转 String
        Date d = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = sdf2.format(d);
        log.info(strDate);
    }

    /**
     * Instant只能解析"**T**Z”这种格式的时间，即UTC字符串；
     * <p>
     * ZonedDateTime解析的时间字符串必须是要有年月日时分秒以及时区；
     * <p>
     * LocalDateTime解析的时间字符串必须要有年月日时分秒，但是不能有时区，例如末尾有"Z"的时间表示UTC的0时区就不能解析；
     * <p>
     * LocalDate解析的时间字符串必须只能有年月日，格式如"2018-12-07"，多任何其他字符都不能解析。
     */
    @Test
    public void zoneDateTime() {
        // 上海时间
        ZoneId shanghaiZoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime shanghaiZonedDateTime = ZonedDateTime.now(shanghaiZoneId);

        // 东京时间
        ZoneId tokyoZoneId = ZoneId.of("Asia/Tokyo");
        ZonedDateTime tokyoZonedDateTime = ZonedDateTime.now(tokyoZoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
        System.out.println("上海时间: " + shanghaiZonedDateTime.format(formatter));
        System.out.println("东京时间: " + tokyoZonedDateTime.format(formatter));
    }

    @Test
    public void simpleDateForm() {
        //创建SimpleDateFormat对象，指定样式    2019-05-13 22:39:30
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        //2019年的第133天。占位符是特定的
        SimpleDateFormat sdf2 = new SimpleDateFormat("y年的第D天");
        //要格式化的Date对象
        Date date = new Date();
        //使用format()方法格式化Date对象为字符串，返回字符串
        System.out.println(sdf1.format(date));
        System.out.println(sdf2.format(date));

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.CHINA).format(new Date()));

    }

    @Test
    public void simpleDateForm2() throws ParseException {
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(1569397239);
        String d = format.format(time);
        Date date2 = format.parse(d);
        System.out.println("Format To time:" + time);
        System.out.println("Format To String(Date):" + d);
        System.out.println("Format To Date:" + date2);


        String timeStamp = "1569397239";
        System.out.println("timeStamp=" + timeStamp); //运行输出:timeStamp=1470278082
        System.out.println(System.currentTimeMillis());//运行输出:1470278082980
        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数

        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
        System.out.println("date=" + date);//运行输出:date=2016-08-04 10:34:42

        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println(timeStamp2);  //运行输出:1470278082
    }

    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-ddHH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    public void addDateMinutTest() {
        addDateMinut("2019-09-2513:45:40", 8, "yyyy-MM-ddHH:mm:ss");
    }

    public static String addDateMinut(String day, int hour, String formatStr) {
        formatStr = formatStr == null ? "yyyy-MM-dd HH:mm:ss": formatStr;
        SimpleDateFormat formatIn = new SimpleDateFormat(formatStr);
        SimpleDateFormat formatOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatIn.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        System.out.println("front:" + formatOut.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        System.out.println("after:" + formatOut.format(date));  //显示更新后的日期
        cal = null;
        return formatOut.format(date);
    }

}
