package com.example.basic.socket.netty2;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 在Netty中包含下面几个主要的组件：
 * https://www.cnblogs.com/xing901022/p/8678869.html
 * Bootstrap：netty的组件容器，用于把其他各个部分连接起来；如果是TCP的Server端，则为ServerBootstrap.
 * Channel：代表一个Socket的连接
 * EventLoopGroup：一个Group包含多个EventLoop，可以理解为线程池
 * EventLoop：处理具体的Channel，一个EventLoop可以处理多个Channel
 * ChannelPipeline：每个Channel绑定一个pipeline，在上面注册处理逻辑handler
 * Handler：具体的对消息或连接的处理，有两种类型，Inbound和Outbound。分别代表消息接收的处理和消息发送的处理。
 * ChannelFuture：注解回调方法
 */
@Slf4j
public class NettyNioServer {
    public void serve(int port) throws InterruptedException {
        final ByteBuf buffer = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi\r\n", Charset.forName("UTF-8")));
        // 第一步，创建线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 第二步，创建启动类
            ServerBootstrap b = new ServerBootstrap();
            // 第三步，配置各组件
            b.group(bossGroup, workerGroup) //配置boss和worker
                    .channel(NioServerSocketChannel.class) //channel(OioServerSocketChannel.class) 使用阻塞的SocketChannel
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.writeAndFlush(buffer.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            // 第四步，开启监听
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

    @Test
    public void main() throws InterruptedException {
        NettyNioServer server = new NettyNioServer();
        log.info("--------------");
        server.serve(5555);
    }
}
