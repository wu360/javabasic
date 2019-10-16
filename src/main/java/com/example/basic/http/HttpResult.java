package com.example.basic.http;


import lombok.Getter;
import lombok.Setter;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Http请求的响应结果封装类
 */
@Getter
@Setter
public class HttpResult {

    /**
     * 响应的Header信息
     */
    private Header[] headers;

    /**
     * 响应的Cookie信息
     */
    private List<Cookie> cookies;

    /**
     * 响应状态码
     */
    private int statusCode;

    /**
     * 响应内容的类型
     */
    private String contentType;

    /**
     * 响应的内容是否是文本类型
     */
    private boolean isTextType;

    /**
     * 响应的内容（字符串形式）
     */
    private String stringContent;

    /**
     * 响应的内容（字节数组形式）
     */
    private byte[] byteArrayContent;
}