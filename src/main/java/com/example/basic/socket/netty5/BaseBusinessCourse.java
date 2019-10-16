package com.example.basic.socket.netty5;

import io.netty.channel.ChannelHandlerContext;

public interface BaseBusinessCourse {
    public void doBiz(ChannelHandlerContext context, String message);
}
