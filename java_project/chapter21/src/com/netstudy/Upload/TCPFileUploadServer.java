package com.netstudy.Upload;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//  文件上传服务端
public class TCPFileUploadServer {

    public static void main(String[] args) throws IOException {

        //1. 服务端在本机监听 8888 端口
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端 在8888端口等待连接....");
        //2.  等待连接
        Socket socket = serverSocket.accept();

        //3. 读取用户端发送的数据
        //      通过Socket得到输入流
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtils.streamToByteArray(bis);
        //4. 将得到 bytes 数组， 写入到指定的路径， 就得到了一个文件
        String destFilePath = "src\\qie2.png";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFilePath));
        bos.write(bytes);
        bos.close();

        //4. 通过 socket 获取 输出流
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("收到图片");
        writer.flush(); // 把内容刷新到数据通道
        socket.shutdownOutput(); //设置写入结束标记


        //关闭其他资源
        writer.close();
        bis.close();
        socket.close();
        serverSocket.close();


    }
}
