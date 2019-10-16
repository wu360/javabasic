package com.example.basic.socket.netty5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {

    //    @PostConstruct
    public static void run() {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChanelInit());
        serverBootstrap = serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
        serverBootstrap = serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        /***
         * 绑定端口并启动去接收进来的连接
         */
        ChannelFuture f = null;
        try {
            f = serverBootstrap.bind(6910).sync();

            System.err.println("启动成功");
            /**
             * 这里会一直等待，直到socket被关闭
             */
            f.channel().closeFuture().sync();
            System.err.println("Start succss!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.err.println("关闭完成!");
        }
    }

    static class ChanelInit extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new StringEncoder());
//            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast(new DispacherHandler());
        }

    }
}
