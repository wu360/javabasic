package com.example.basic.socket.netty3;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ProduceInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pi = ch.pipeline();
        pi.addLast("decoder", new StringDecoder());
        pi.addLast("encoder", new StringEncoder());
        pi.addLast("handler", new ProduceHandler());
    }
}