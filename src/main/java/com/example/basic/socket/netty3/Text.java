package com.example.basic.socket.netty3;

import io.netty.channel.Channel;

public class Text {
    public void send(Channel channel, Channel incoming, String msg) {
        if (channel != incoming) {
            sendText(channel, incoming, msg);
        } else {
            channel.writeAndFlush("[你发送了：]" + msg + "\n");
        }
    }

    private void sendText(Channel channel, Channel incoming, String msg) {
        channel.writeAndFlush("[" + incoming.remoteAddress() + "] 发送了消息：" + msg + "\n");
    }
}
