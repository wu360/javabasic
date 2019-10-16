package com.example.basic.socket.netty5;

import io.netty.channel.ChannelHandlerContext;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TcpDispacher {

    private static TcpDispacher instance = new TcpDispacher();

    private TcpDispacher() {

    }

    public static TcpDispacher getInstance() {
        return instance;
    }


    private static Map<String, Object> coursesTable = new ConcurrentHashMap<>();

    /**
     * 消息流转处理
     *
     * @param channelHandlerContext
     * @param s
     */
    public void messageRecived(ChannelHandlerContext channelHandlerContext, String s) {
        System.err.println("收到的消息:" + s);
//        JSONObject jsonObject = JSONObject.parseObject(s);
//        String code = jsonObject.getString("messageCode");
//        BaseBusinessCourse baseBusinessCourse = (BaseBusinessCourse) coursesTable.get(code);
//        baseBusinessCourse.doBiz(channelHandlerContext, s);
    }


    public void setCourses(Map<String, Object> courseMap) {
        System.err.println("设置map的值");
        if (courseMap != null && courseMap.size() > 0) {
            for (Map.Entry<String, Object> entry : courseMap.entrySet()) {
                coursesTable.put(entry.getKey(), entry.getValue());
            }
        }
    }

}
