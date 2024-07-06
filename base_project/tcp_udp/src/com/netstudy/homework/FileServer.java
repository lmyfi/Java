package com.netstudy.homework;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//文件下载的服务端
public class Homework03Server {
    public static void main(String[] args) throws IOException {

        //1. 监听 端口 9999
        ServerSocket serverSocket = new ServerSocket(9999);
        //2. 等待连接
        System.out.println("等待连接....");
        Socket socket = serverSocket.accept();
        //3. 读取 客户端发送要下载的文件
        InputStream inputStream = socket.getInputStream();
        byte[] b = new byte[1024];
        int len = 0;
        String downFileName = "";
        while ((len = inputStream.read(b)) != -1 ) {
            downFileName += new String(b, 0, len);
        }
        System.out.println("客户端希望下载的文件名" + downFileName);

        // 判断客户端希望下载的文件是什么
        String resFileName = " ";
        if ("暗号".equals(downFileName)) {
            resFileName = "src\\暗号.mp3";
        }else {
            resFileName = "src\\白兰鸽巡游记.mp3";
        }
        // 4.创建输入流， 读取文件
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(resFileName));

        // 5.使用工具类StreamUtils，读取文件
        byte[] bytes = StreamUtils.streamToByteArray(bis);

        // 6. 得到Socket 关联的输出流
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        // 7.写入到数据通道， 返回给客户端
        bos.write(bytes);
        socket.shutdownOutput();

        // 8. 关闭相关流
        bos.close();
        bis.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
        System.out.println("服务端退出...");


    }
}
