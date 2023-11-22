package com.netstudy.Upload;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//   用户端/客户端
public class TCPFileUploadClient {
    public static void main(String[] args) throws IOException {

        // 用户端连接服务端 8888， 得到 socket 对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
        //创建读取磁盘文件的输入流
        String filepath = "C:\\Users\\lmyfi\\桌面\\04182105770577.jpg";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filepath));

        //bytes 就是filepath 对应的字节数组
        byte[] bytes = StreamUtils.streamToByteArray(bis);

        //通过socket 获取到输出流 ， 将bytes数据发送给服务器
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(bytes);
        bis.close();
        socket.shutdownOutput();//设置写入数据的结束标记

        //=====接受服务端回复的消息

        InputStream inputStream = socket.getInputStream();
        //使用StreamUtils 的方法， 直接将 inputStream 读取到的内容 转成 字符串
        String s = StreamUtils.streamToString(inputStream);
        System.out.println(s);


        // 关闭相关流
        socket.close();
        bis.close();

    }
}
