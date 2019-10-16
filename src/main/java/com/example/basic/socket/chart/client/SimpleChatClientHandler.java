package com.example.basic.socket.chart.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {

    private CountDownLatch lathc;

    private SimpleChatClient client;

    public SimpleChatClientHandler(CountDownLatch lathc) {
        this.lathc = lathc;
        client = new SimpleChatClient("127.0.0.1", 8086);
    }

    private String msg;

//    @Override
//    protected void channelRead0(ChannelHandlerContext arg0, String arg1) throws Exception {
//        log.info("==========收到服务器消息："+arg1);
//        msg = arg1;
//        lathc.countDown();//消息收取完毕后释放同步锁
//    }

    public void resetLatch(CountDownLatch initLathc) {
        this.lathc = initLathc;
    }

    public String getResult() {
        return msg;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                log.info("READER_IDLE");

            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                /**发送心跳,保持长连接*/
                String s = "ping$_";
                ctx.channel().writeAndFlush(s);
                log.debug("userEventTriggered 心跳发送成功!");
                log.info("心跳发送成功!");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                log.info("ALL_IDLE");
            }

            switch (event.state()) {
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
//        ctx.channel().writeAndFlush("1" + "\r\n");
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        log.info("messageReceived ==========收到服务器消息：" + channelHandlerContext);
        this.msg = msg;
        lathc.countDown();//消息收取完毕后释放同步锁
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive 通道已连接！！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive 断线了。。。。。。");
        //使用过程中断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> client.run(), 1, TimeUnit.SECONDS);
        ctx.fireChannelInactive();
    }
}