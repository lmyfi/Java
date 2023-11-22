package com.netstudy.socket;

import java.io.IOException;
import java.io.OutputStream;
import  java.net.*;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

public class SocketTCP01Client {
    public static void main(String[] args) throws IOException {

        // 连接主机的 9999 端口
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        System.out.println("客户端 socket返回 = " + socket.getClass());

        // 用户端， 通过socket 输出流， 传输数据
        OutputStream outputStream = socket.getOutputStream();
        //写数据
        outputStream.write("hello server".getBytes());
        //关闭流对象 和 socket ， 必须关， 防止资源浪费
        outputStream.close();
        socket.close();
        System.out.println("客户端关闭");

    }
}
