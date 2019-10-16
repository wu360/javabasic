package com.example.basic.socket.netty3;

import io.netty.channel.Channel;

public class Email implements IMessage {
    public void send(Channel channel, Channel incoming, String msg) {
        if (channel != incoming) {
            sendEmail(channel, incoming, msg);
        } else {
            channel.writeAndFlush("[你发送了：]" + msg + "\n");
        }
    }

    private void sendEmail(Channel channel, Channel incoming, String msg) {
        channel.writeAndFlush("[" + incoming.remoteAddress() + "] 发送了邮件：" + msg + "\n");
    }
}
