package com.example.basic.socket.netty5;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
@Biz(value = "10003")
public class LinsenceBizService implements BaseBusinessCourse {
    @Override
    public void doBiz(ChannelHandlerContext context, String message) {
        System.out.println("业务层收到的数据:" + message);
        context.writeAndFlush("{test:\"test\"}\r\n");
    }

}