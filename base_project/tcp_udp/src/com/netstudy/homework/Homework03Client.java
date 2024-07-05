package com.netstudy.homework;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Homework03Client {
    public static void main(String[] args) throws IOException {
        //1. 接收客户端输入， 指定下载文件内
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入下载文件名");
        String downloadFilaName = scanner.next();

        //2. 连接服务端
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        //3. 获取与socket 相关的输出流
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(downloadFilaName.getBytes());
        //设置文件写入结束标志
        socket.shutdownOutput();

        //4. 读取服务端返回的文件（字节数据）
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtils.streamToByteArray(bis);
        //5. 得到一个输出流， 准备将 bytes 写入到磁盘文件
        String filePath = "e:\\" + downloadFilaName + ".mp3";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        bos.write(bytes);

        // 6. 关闭相关的资源
        bos.close();
        bis.close();
        outputStream.close();
        socket.close();
        System.out.println("客户端下载完毕...");


    }
}
