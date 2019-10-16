package com.example.basic.socket.netty3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.util.Scanner;


public class Produce {

    private final String host;
    private final int port;

    public Produce(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler(new ProduceInitializer());
            Channel channel = bootstrap.connect(host, port).sync().channel();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                channel.writeAndFlush(line + "\r\n");
            }
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Produce("localhost", 8000).run();
    }

}