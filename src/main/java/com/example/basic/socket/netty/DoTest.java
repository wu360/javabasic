package com.example.basic.socket.netty;


import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class DoTest  { //implements CommandLineRunner
//    @Resource
//    private DiscardServer discardServer;
//
//    @Override
//    public void run(String... args) throws Exception {
//        discardServer.run(8086);
//    }

    public static void main(String[] args) throws Exception {
        DiscardServer discardServer = new DiscardServer();
        discardServer.run(8086);
    }

    @Test
    public void socketClient() {
        try {
            Socket socket = new Socket("localhost", 8086);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write("socketClient test ....");
            printWriter.flush();
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
