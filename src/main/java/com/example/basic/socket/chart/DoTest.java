package com.example.basic.socket.chart;


import com.example.basic.socket.chart.client.SimpleChatClient;
import com.example.basic.socket.chart.server.SimpleChatServer;
import org.testng.annotations.Test;

public class DoTest { //implements CommandLineRunner
//    @Resource
//    private DiscardServer discardServer;
//
//    @Override
//    public void run(String... args) throws Exception {
//        discardServer.run(8086);
//    }

    public static void main(String[] args) throws Exception {
        SimpleChatServer discardServer = new SimpleChatServer(8086);
        discardServer.run();
    }

    @Test
    public void socketClient() throws Exception {
        new SimpleChatClient("127.0.0.1", 8086).run();
    }
}
