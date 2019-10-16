package com.example.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class distinctTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("AA", "BB", "CC", "BB", "CC", "AA", "AA");
        long l = list.stream().distinct().count();
        System.out.println("No. of distinct elements:"+l);
        List<String> output = list.stream().distinct().collect(Collectors.toList());
//        String output = list.stream().distinct().collect(Collectors.joining(","));
        System.out.println(output);
    }

}
