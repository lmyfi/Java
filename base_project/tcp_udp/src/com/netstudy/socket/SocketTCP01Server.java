package com.netstudy.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

//服务端
public class SocketTCP01Server {
    public static void main(String[] args) throws IOException {

        //用9999端口监听， 等待连接
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println(" 服务端， 在9999端口， 等待连接。。");
        //当没有用户连接9999 端口时， 程序会 阻塞， 等待连接
        // 如果有用户连接， 程序继续
        Socket socket = serverSocket.accept();
        System.out.println(" 用户端 socket = " + serverSocket.getClass());

        //通过socket.getInputStream() 读取客户端写入到数据通道的数据， 显示
        InputStream inputStream = socket.getInputStream();
        //IO读取
        byte[] buf = new byte[1024];
        int readLen = 0;
        while ((readLen = inputStream.read(buf)) != -1) {
            System.out.println(new String(buf, 0, readLen));
        }
        //关闭流 和 socket
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
