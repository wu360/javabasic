package com.example.basic.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpClientUtilTest {

    /**
     * 文本类型返回结果测试URL
     */
    private static String url = "http://trade-route-develop.ntdev.be:8080/fh/finance/balance_info?reportType=000&marketType=HK&stockCode=";
    private static String httpGetByEntityUrl = "http://localhost:8080/api/httpGetByEntity";
    private static String httpPostByFormUrl = "http://localhost:8080/api/httpPostByForm";
    private static String httpPostByJsonUrl = "http://localhost:8080/api/httpPostByJson";

    /**
     * 二进制类型返回结果测试URL
     */
    private static String httpGetBinaryUrl = "https://www.baidu.com";


    public static void main(String[] args) throws IOException {

        // 测试HttpClientUtil的sendGet()：服务端使用Parameter的形式接收Get请求发送过来的数据，并返回文本数据
        for (int i = 34140; i < 99999; i++) {
            JSONObject json = testHttpClientUtil_sendGet(url + String.format("%05d", i));
            int code = (int) json.get("code");
            if (code != 200) {
                break;
            }
        }


        // 测试HttpClientUtil的sendGet()：服务端使用实体类的形式接收Get请求发送过来的数据，并返回文本数据
        // testHttpClientUtil_sendGet(httpGetByEntityUrl);

        // 测试HttpClientUtil的sendGet()：服务端返回字节数据
        // testHttpClientUtil_sendGet_ByteArray(httpGetBinaryUrl);

        // 测试HttpClientUtil的sendPostForm()：服务端接收Post请求发送过来的Form形式的数据，并返回文本数据
        // testHttpClientUtil_sendPostForm(httpPostByFormUrl);

        // 测试HttpClientUtil的sendPostJson()：服务端接收Post请求发送过来的Json形式的数据，并返回文本数据
        // testHttpClientUtil_sendPostJson(httpPostByJsonUrl);
    }

    /**
     * 测试HttpClientUtil的sendGet()
     */
    public static JSONObject testHttpClientUtil_sendGet(String url) throws IOException {
        log.info(url);
        // 创建请求参数
        Map<String, String> parameters = getParameterUser();
        Header[] headers = getHeaders();
        BasicClientCookie[] cookies = getCookies();

        // 发送Get请求并得到响应结果
        HttpResult httpResult = HttpClientUtil.sendGet(url, parameters, headers, cookies, "UTF-8");

        // 处理响应结果
        return handleHttpResult2(httpResult);
    }

    /**
     * 测试HttpClientUtil的sendGet()
     */
    public static void testHttpClientUtil_sendGet_ByteArray(String url) throws IOException {
        // 发送Get请求并得到响应结果
        HttpResult httpResult = HttpClientUtil.sendGet(url, null, null, null, "UTF-8");

        // 处理响应结果
        handleHttpResult(httpResult);
    }

    /**
     * 测试HttpClientUtil的sendPostForm()
     */
    public static void testHttpClientUtil_sendPostForm(String url) throws IOException {
        // 创建请求参数
        Map<String, String> parameters = getParameterUser();
        Header[] headers = getHeaders();
        BasicClientCookie[] cookies = getCookies();

        // 发送Post请求并得到响应结果
        HttpResult httpResult = HttpClientUtil.sendPostForm(url, parameters, headers, cookies, "UTF-8");

        // 处理响应结果
        handleHttpResult(httpResult);
    }

    /**
     * 测试HttpClientUtil的sendPostJson()
     */
    public static void testHttpClientUtil_sendPostJson(String url) throws IOException {
        // 创建请求参数
        String jsonData = getJsonUser();
        Header[] headers = getHeaders();
        BasicClientCookie[] cookies = getCookies();

        // 发送Post请求并得到响应结果
        HttpResult httpResult = HttpClientUtil.sendPostJson(url, jsonData, headers, cookies, "UTF-8");

        // 处理响应结果
        handleHttpResult(httpResult);
    }

    /**
     * 创建并获取Form形式的用户信息
     */
    private static Map<String, String> getParameterUser() {
        Map<String, String> parameterUser = new HashMap<>();
        parameterUser.put("id", "1001");
        parameterUser.put("name", "JavaBigData1024");
        parameterUser.put("sex", "true");
        return parameterUser;
    }

    /**
     * 创建并获取Json形式的用户信息
     */
    private static String getJsonUser() {
        String jsonUser = "{\n" +
                "    \"id\":\"1001\",\n" +
                "    \"name\":\"JavaBigData1024\",\n" +
                "    \"sex\":\"true\"\n" +
                "}";
        return jsonUser;
    }

    /**
     * 创建并获取Header信息
     */
    private static Header[] getHeaders() {
        Header[] headers = new Header[2];
        headers[0] = new BasicHeader("sendHeaderName_1", "sendHeaderValue_1");
        headers[1] = new BasicHeader("sendHeaderName_2", "sendHeaderValue_2");
        return headers;
    }

    /**
     * 创建并获取Cookie信息
     */
    private static BasicClientCookie[] getCookies() {
        BasicClientCookie[] cookies = new BasicClientCookie[2];
        cookies[0] = new BasicClientCookie("sendCookieName_1", "sendCookieValue_1");
        cookies[0].setVersion(0);
        cookies[0].setDomain("xxx.cn");
        cookies[0].setPath("/x");
        cookies[1] = new BasicClientCookie("sendCookieName_2", "sendCookieValue_2");
        cookies[1].setVersion(0);
        cookies[1].setDomain("xxx.cn");
        cookies[1].setPath("/x");
        return cookies;
    }

    /**
     * 处理响应结果
     */
    private static void handleHttpResult(HttpResult httpResult) throws IOException {
        System.out.println("响应状态码:" + httpResult.getStatusCode());
        System.out.println("响应结果类型:" + httpResult.getContentType());
        System.out.println("响应结果是否是文本:" + httpResult.isTextType());
        if (httpResult.isTextType()) {
            System.out.println("\n响应的文本结果如下:");
            System.out.println(httpResult.getStringContent());
        } else {
            System.out.println("\n响应的字节结果如下");
            System.out.println(httpResult.getByteArrayContent());
//            File image = new File("E:/image.jpg");
//            FileOutputStream fos = new FileOutputStream(image);
//            fos.write(httpResult.getByteArrayContent());
//            fos.flush();
//            fos.close();
        }

        if (httpResult.getHeaders() != null) {
            System.out.println("\n响应的Header如下:");
            for (Header header : httpResult.getHeaders()) {
                System.out.println(header.getName() + " : " + header.getValue());
            }
        }

        if (httpResult.getCookies() != null) {
            System.out.println("\n响应的Cookie如下:");
            for (Cookie cookie : httpResult.getCookies()) {
                System.out.println(cookie.getName() + " : " + cookie.getValue() + " : " + cookie.getDomain() + " : " + cookie.getPath());
            }
        }
    }

    private static JSONObject handleHttpResult2(HttpResult httpResult) throws IOException {
        System.out.println("响应结果是否是文本:" + httpResult.isTextType());
        JSONObject result = new JSONObject();
        if (httpResult.isTextType()) {
            String content = httpResult.getStringContent();
            result = JSON.parseObject(content);
        } else {
//            result = String.valueOf(httpResult.getByteArrayContent());
        }

        return result;
    }
}
