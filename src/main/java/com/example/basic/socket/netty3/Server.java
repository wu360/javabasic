package com.example.basic.socket.netty3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.testng.annotations.Test;

public class Server {
    private static int port;

    public Server(int port) {
        this.port = port;
    }

    public static void run() throws InterruptedException {

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler((ChannelHandler) new Initializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 64 * 1024)
                    .option(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 32 * 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(port).sync();

            System.out.println("服务器启动了！");
            future.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
            System.out.println("服务器关闭了！");
        }
    }

    @Test
    public  void test() throws InterruptedException {
        new Server(8000).run();
    }
}
