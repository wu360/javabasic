package com.example.basic.number;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DigitUppercase {

    @Test
    public void test() throws Exception {
        System.out.println(digitUppercase("23000000000.00000"));
    }
    @Test
    public void test2() throws Exception {
       String a = "8a";
       log.info(a.substring(1));
    }

    /**
     * 处理的最大数字达千万亿位 精确到分
     *
     * @return
     */
    public String digitUppercase(String num) throws Exception {
        String fraction[] = {"角", "分"};
        String digit[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        /**
         *      仟        佰        拾         ' '
         ' '    $4        $3        $2         $1
         万     $8        $7        $6         $5
         亿     $12       $11       $10        $9
         */
        String unit1[] = {"", "拾", "佰", "仟"};//把钱数分成段,每四个一段,实际上得到的是一个二维数组
        String unit2[] = {"元", "万", "亿", "万亿"}; //把钱数分成段,每四个一段,实际上得到的是一个二维数组
        BigDecimal bigDecimal = new BigDecimal(num);
        bigDecimal = bigDecimal.multiply(new BigDecimal(100));
//        Double bigDecimal = new Double(name*100);     存在精度问题 eg：145296.8
        String strVal = String.valueOf(bigDecimal.toBigInteger());
        String head = strVal.substring(0, strVal.length() - 2);         //整数部分
        String end = strVal.substring(strVal.length() - 2);              //小数部分
        String endMoney = "";
        String headMoney = "";
        if ("00".equals(end)) {
            endMoney = "整";
        } else {
            if (!end.substring(0, 1).equals("0")) {
                endMoney += digit[Integer.valueOf(end.substring(0, 1))] + "角";
            } else if (end.substring(0, 1).equals("0") && !end.substring(1, 2).equals("0")) {
                endMoney += "零";
            }
            if (!end.substring(1, 2).equals("0")) {
                endMoney += digit[Integer.valueOf(end.substring(1, 2))] + "分";
            }
        }
        char[] chars = head.toCharArray();
        Map<String, Boolean> map = new HashMap<String, Boolean>();//段位置是否已出现zero
        boolean zeroKeepFlag = false;//0连续出现标志
        int vidxtemp = 0;
        for (int i = 0; i < chars.length; i++) {
            int idx = (chars.length - 1 - i) % 4;//段内位置  unit1
            int vidx = (chars.length - 1 - i) / 4;//段位置 unit2
            String s = digit[Integer.valueOf(String.valueOf(chars[i]))];
            if (!"零".equals(s)) {
                headMoney += s + unit1[idx] + unit2[vidx];
                zeroKeepFlag = false;
            } else if (i == chars.length - 1 || map.get("zero" + vidx) != null) {
                headMoney += "";
            } else {
                headMoney += s;
                zeroKeepFlag = true;
                map.put("zero" + vidx, true);//该段位已经出现0；
            }
            if (vidxtemp != vidx || i == chars.length - 1) {
                headMoney = headMoney.replaceAll(unit2[vidx], "");
                headMoney += unit2[vidx];
            }
            if (zeroKeepFlag && (chars.length - 1 - i) % 4 == 0) {
                headMoney = headMoney.replaceAll("零", "");
            }
        }
        return headMoney + endMoney;
    }
}
