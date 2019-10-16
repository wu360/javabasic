package com.example.basic.socket.netty5;

import org.springframework.boot.SpringApplication;

public class NettyApplyApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyApplyApplication.class, args);
        Server.run();
    }
}
