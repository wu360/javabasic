package com.example.basic.socket.netty5;

public class BaseRequest {

    private String messageCode;

    private String content;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toJson() {
        return  "222222222222222222222\r\n";
//        return JSON.toJSONString(this) + "\r\n";
    }
}