package com.example.basic.socket.chart.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel> {

    private CountDownLatch lathc; // 同步请求

    public SimpleChatClientInitializer(CountDownLatch lathc) {
        this.lathc = lathc;
    }

    private SimpleChatClientHandler handler;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        handler =  new SimpleChatClientHandler(lathc);
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(81920, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        //心跳检测
        pipeline.addLast(new IdleStateHandler(0,30,0, TimeUnit.SECONDS));
//        pipeline.addLast(new IdleStateHandler(0, 0, 5));

        pipeline.addLast("handler", handler);
    }

    public String getServerResult(){
        return handler.getResult();
    }
    //重置同步锁
    public void resetLathc(CountDownLatch initLathc) {
        handler.resetLatch(initLathc);
    }

}