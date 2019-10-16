package com.example.basic.number;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Slf4j
public class FormatNum {
    /**
     * Integer默认值是null，可以区分未赋值和值为0的情况。比如未参加考试的学生和考试成绩为0的学生
     * 加减乘除和比较运算较多，用int
     * 容器里推荐用Integer。 对于PO实体类，如果db里int型字段允许null，则属性应定义为Integer。 当然，如果系统限定db里int字段不允许null值，则也可考虑将属性定义为int。
     * 对于应用程序里定义的枚举类型， 其值如果是整形，则最好定义为int，方便与相关的其他int值或Integer值的比较
     * Integer提供了一系列数据的成员和操作，如Integer.MAX_VALUE，Integer.valueOf(),Integer.compare(),compareTo(),
     * 不过一般用的比较少。建议，一般用int类型，这样一方面省去了拆装箱，另一方面也会规避数据比较时可能带来的bug。
     */
    @Test
    public void test() {
//        String str = formatNum("2301400000");
//        log.info(str);

        //保留两位小数
        DecimalFormat df1 = new DecimalFormat("0.00");
        DecimalFormat df2 = new DecimalFormat("#.##");
        //0.00表示没数字位置用0补齐，#。##代表有就有没有就没有
        System.out.println(df1.format(0.156));
        System.out.println(df2.format(0.156));

        //高精度实数除法
        MathContext mc = new MathContext(10, RoundingMode.HALF_DOWN); //必须设置精度
        //ROUND_HALF_UP: 遇到0.5的情况时往上近似,例: 1.5 -> 2
        //ROUND_HALF_DOWN : 遇到0.5的情况时往下近似,例: 1.5 -> 1
        BigDecimal a = BigDecimal.valueOf(2216552530000.00);
        BigDecimal b = BigDecimal.valueOf(100000000);
        BigDecimal ans = a.divide(b, mc); //高精度整数不用设置精度，实数必须要
        System.out.println(ans);
        System.out.println(df1.format(ans));
    }

    public String formatNum(String num) {
        StringBuffer sb = new StringBuffer();

        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);

        String formatNumStr = "";
        String nuit = "";


        // 以万为单位处理
        if (b3.compareTo(b1) == -1) {
            sb.append(b3.toString());
        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
                || b3.compareTo(b2) == -1) {
            formatNumStr = b3.divide(b1).toString();
            nuit = "万";
        } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            formatNumStr = b3.divide(b2,2).toString();
            nuit = "亿";
        }
        if (!"".equals(formatNumStr)) {
            int i = formatNumStr.indexOf(".");
            if (i == -1) {
                sb.append(formatNumStr).append(nuit);
            } else {
                i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                if (!v.equals("0")) {
                    sb.append(formatNumStr.substring(0, i + 1)).append(nuit);
                } else {
                    sb.append(formatNumStr.substring(0, i - 1)).append(nuit);
                }
            }
        }
        if (sb.length() == 0)
            return "0";
        return sb.toString();
    }
}
