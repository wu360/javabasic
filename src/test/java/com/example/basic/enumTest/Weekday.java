package com.example.basic.enumTest;
/*

要点：

使用的是enum关键字而不是class。

多个枚举变量直接用逗号隔开。

枚举变量最好大写，多个单词之间使用”_”隔开（比如：INT_SUM）。

定义完所有的变量后，以分号结束，如果只有枚举变量，而没有自定义变量，分号可以省略（例如上面的代码就忽略了分号）。

在其他类中使用enum变量的时候，只需要【类名.变量名】就可以了，和使用静态变量一样。
————————————————

请注意：这里有三点需要注意：

一定要把枚举变量的定义放在第一行，并且以分号结尾。

构造函数必须私有化。事实上，private是多余的，你完全没有必要写，因为它默认并强制是private，如果你要写，也只能写private，写public是不能通过编译的。

自定义变量与默认的ordinal属性并不冲突，ordinal还是按照它的规则给每个枚举变量按顺序赋值。
————————————————
原文链接：https://blog.csdn.net/qq_31655965/article/details/55049192
 */
public enum  Weekday {
//    SUN,MON,TUS,WED,THU,FRI,SAT
SUN(0),MON(1),TUS(2),WED(3),THU(4),FRI(5),SAT(6);

    private int value;

    private Weekday(int value){
        this.value = value;
    }

    public static Weekday getNextDay(Weekday nowDay){
        int nextDayValue = nowDay.value;

        if (++nextDayValue == 7){
            nextDayValue =0;
        }

        return getWeekdayByValue(nextDayValue);
    }

    public static Weekday getWeekdayByValue(int value) {
        for (Weekday c : Weekday.values()) {
            if (c.value == value) {
                return c;
            }
        }
        return null;
    }
}
