package com.example.basic.socket.netty3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MessageHandler extends SimpleChannelInboundHandler<String> {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static MessageFactory factory = FactoryProducer.getInstance();

    /**
     * 加入分组
     * */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    /**
     * 当出现异常就关闭连接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println(incoming.remoteAddress() + "异常");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println(incoming.remoteAddress() + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println(incoming.remoteAddress() + "掉线");
    }

    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String type = msg.substring(0, msg.indexOf("|"));
        String word = msg.substring(msg.indexOf("|") + 1, msg.length());
        // 使用工厂模式
        IMessage message = factory.getMessage(type);

        // 推送消息
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (message == null) {
                if (channel == incoming) {
                    channel.writeAndFlush("[你发送了：] 一个错误的消息类型！！！\n");
                }
            }else {
                message.send(channel, incoming, word);
            }
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }
}
