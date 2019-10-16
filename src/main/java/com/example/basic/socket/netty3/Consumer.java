package com.example.basic.socket.netty3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.testng.annotations.Test;

/**
 * 原文链接：https://blog.csdn.net/ioiuuiouio/article/details/77416095
 */

public class Consumer {

    private final String host;
    private final int port;

    public Consumer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler((ChannelHandler) new ConsumerInitializer());
            Channel channel = bootstrap.connect(host, port).sync().channel();
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }

    }

    @Test
    public void test() throws InterruptedException {
        new Consumer("localhost", 8000).run();
    }

}