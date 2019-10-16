package com.example.basic.socket.netty3;

import io.netty.channel.Channel;

public interface IMessage {
    void send(Channel channel, Channel incoming, String msg);
}
