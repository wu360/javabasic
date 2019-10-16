package com.example.basic.socket.netty5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DispacherHandler extends SimpleChannelInboundHandler<String> {

    private TcpDispacher tcpDispacher = TcpDispacher.getInstance();

//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//        tcpDispacher.messageRecived(channelHandlerContext, s);
//    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        tcpDispacher.messageRecived(channelHandlerContext, s);
    }
}