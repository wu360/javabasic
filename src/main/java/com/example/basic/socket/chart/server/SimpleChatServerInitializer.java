package com.example.basic.socket.chart.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * SimpleChatServerInitializer 用来增加多个的处理类到 ChannelPipeline 上，包括编码、解码、SimpleChatServerHandler 等。
 */
public class SimpleChatServerInitializer extends
        ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * 因为TCP在发送过程用可能会发生粘包拆包问题，netty中给了我们很好的解决方法，
         * 就是每次发送消息是已特殊的符号（可自定义）$_ 结尾，只收收到的消息以$_ 符号结尾是该消息才算接收完毕。
         */
        //解决TCP粘包拆包的问题，以特定的字符结尾（$_）
//        pipeline.addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Unpooled.copiedBuffer("$_".getBytes())));

        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //字符串解码和编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        /**
         * 该代码实现了心跳检测，每隔40s检测一次是否要读事件，
         * 如果超过40s你没有读事件的发生，则执行相应的操作（在handler中实现）
         */
        pipeline.addLast(new IdleStateHandler(40,0,0, TimeUnit.SECONDS));

        //服务器的逻辑
        pipeline.addLast("handler", new SimpleChatServerHandler());

        System.out.println("SimpleChatClient:"+ch.remoteAddress() +"连接上");
    }
}