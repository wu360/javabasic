package com.example.basic.socket.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class ByteBufTest {
    @Test
    public static void main() {
        //创建bytebuf
        ByteBuf buf = Unpooled.copiedBuffer("hello".getBytes());
        log.info(String.valueOf(buf));

        // 读取一个字节
        buf.readByte();
        log.info(String.valueOf(buf));

        // 读取一个字节
        buf.readByte();
        log.info(String.valueOf(buf));

        // 丢弃无用数据
        buf.discardReadBytes();
        log.info(String.valueOf(buf));

        // 清空
        buf.clear();
        log.info(String.valueOf(buf));

        // 写入
        buf.writeBytes("123".getBytes());
        log.info(String.valueOf(buf));

        buf.markReaderIndex();
        log.info("mark:"+buf);

        buf.readByte();
        buf.readByte();
        log.info("read:"+buf);

        buf.resetReaderIndex();
        log.info("reset:"+buf);
    }
}
