package com.example.basic.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
    /**
     * 从apache官网下载HttpComponents项目jar包
     */
    public static void main(String[] args) {
        new Get().start();
//        new Post().start();
    }
}



class Get extends Thread{
    HttpClient client = HttpClients.createDefault();

    @Override
    public void run() {
        HttpGet get = new HttpGet("http://www.baidu.com");
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            System.out.println(result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

@Slf4j
class Post extends Thread{
    HttpClient client = HttpClients.createDefault();

    @Override
    public void run() {
        HttpPost post = new HttpPost("http://fanyi.youdao.com/openapi.do");
        try {
            List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            parameters.add(new BasicNameValuePair("keyfrom", "wjy-test"));
            parameters.add(new BasicNameValuePair("key", "36384249"));
            parameters.add(new BasicNameValuePair("type", "data"));
            parameters.add(new BasicNameValuePair("doctype", "xml"));
            parameters.add(new BasicNameValuePair("version", "1.1"));
            parameters.add(new BasicNameValuePair("q", "welcome"));
            post.setEntity(new UrlEncodedFormEntity(parameters));
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            log.info(result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}