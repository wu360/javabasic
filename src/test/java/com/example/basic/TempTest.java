package com.example.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class TempTest {

    @Test
    public void test(){
        getAccountingstandardsName(null);
    }
    private String getAccountingstandardsName(String code){
        String name = "";
        switch (code){
            case "001":
                name = "大陆会计准则";
                break;
            case "002":
                name = "香港会计准则 ";
                break;
            case "003":
                name = "国际会计准则";
                break;
            case "004":
                name = "旧企业会计准则";
                break;
            case "005":
                name = "美国会计准则";
                break;
            case "006":
                name = "新加坡会计准则";
                break;
            case "007":
                name = "国际及香港会计准则";
                break;
            case "008":
                name = "英国会计准则";
                break;
            default :
                name = "其他会计准则";
                break;
        }
        log.info(name);
        return name;
    }
}
