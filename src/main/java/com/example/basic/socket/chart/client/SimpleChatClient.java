package com.example.basic.socket.chart.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Slf4j
public class SimpleChatClient {

    private final String host;
    private final int port;
    private static Channel channel;

    public SimpleChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            CountDownLatch lathc = new CountDownLatch(1);
            SimpleChatClientInitializer clientInitializer = new SimpleChatClientInitializer(lathc);
            Bootstrap bootstrap =
                    new Bootstrap().group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(clientInitializer);

            ChannelFuture connect = bootstrap.connect(host, port);

             channel = connect.sync().channel();

            /**
             * 断线重连实现也很简单，就是给通道加上一个断线重连的监听器ChannelFutureListene，
             * 该监听器如果监听到与服务端的连接断开了就会每隔1s触发一次重连操作，
             * 担忧一个问题需要注意的是   ChannelFuture f = b.connect(host,port);
             * 不能加sync(）也就是不能写成 ChannelFuture f = b.connect(host,port).sync()；
             * 不然重连操作无法触发，我也不知道为啥。。。。
             * 还有就是不能有任何关闭通道的代码，也就是group.shutdownGracefully();
             * 不然断线重连无效，因为你已经把该通道关闭了。
             *
             * channelInactive 该方法中也实现了断线重连的功能，以防止在运行过程中突然断线。
             *
             * userEventTriggered：该方法中实现了如果30s内客户端没有向服务端写入任何消息，该方法就会触发向服务端发送心跳信息，从而保持客户端与服务端的长连接。
             *
             *
             *
             * Netty推荐使用addListener的方式来回调异步执行的结果，这种方式优于Future.get，能够更精确地把握异步执行结束的时间。
             * 看一下DefaultPromise的addListener方法，它判断异步任务执行的状态，如果执行完成，就理解通知监听者，否则加入到监听者队列
             *
             * 通知监听者就是找一个线程来执行调用监听的回调函数。
             * ————————————————
             * 版权声明：本文为CSDN博主「iter_zc」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
             * 原文链接：https://blog.csdn.net/ITer_ZC/article/details/39494367
             */
            //断线重连
            connect.addListener((ChannelFutureListener) channelFuture -> {
                if (!channelFuture.isSuccess()) {
                    final EventLoop loop = channelFuture.channel().eventLoop();
                    loop.schedule(new Runnable() {
                        @Override
                        public void run() {
                            log.error("addListener 服务端链接不上，开始重连操作...");
                            run();
                        }
                    }, 1L, TimeUnit.SECONDS);
                } else {
                    channel = channelFuture.channel();
                    log.info("addListener 服务端链接成功...");
                }
            });

//          BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            channel.write("run bbbbbb啊啊啊" + "\r\n");
//              connect.awaitUninterruptibly();
//              Void void1 = connect.getNow();
//              log.info(void1.toString());
            channel.flush();
            lathc.await();//开启等待会等待服务器返回结果之后再执行下面的代码
            log.info("run 服务器返回 1:" + clientInitializer.getServerResult());

            Thread.sleep(30000);

            lathc = new CountDownLatch(1);//此处为控制同步的关键信息，注意此对象的流转
            clientInitializer.resetLathc(lathc);
            channel.write("run bbbbbb啊啊啊1111" + "\r\n");
            channel.flush();
            lathc.await();
            log.info("run 服务器返回 2:" + clientInitializer.getServerResult());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//          group.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {
        new SimpleChatClient("127.0.0.1", 8086).run();
    }
}