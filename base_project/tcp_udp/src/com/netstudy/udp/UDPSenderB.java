package com.netstudy.udp;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

// UDP 发送端
public class UDPSenderB {
    public static void main(String[] args) throws IOException {

        //1. 创建 DatagramSocket 对象， 准备在9998端口 接收数据
        DatagramSocket socket = new DatagramSocket(9998);
        //2. 将需要发送的数据， 封装到 DatagramPacket对象
        byte[] data = "hello 明天吃火锅".getBytes(StandardCharsets.UTF_8);

        // 封装
        DatagramPacket pack = new DatagramPacket(data, data.length, InetAddress.getByName("192.168.100.1"), 9999);

        socket.send(pack);


        //（1）. 构建一个DatagramPacket 对象， 准备接受数据
        //      UDP 一个数据包的最大 为64k
        byte[] buf = new byte[64];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        // （2）.调用 接受方法， 将通过网络传输的 DatagramPacket 对象
        //填充到 packet 对象
        // 当有数据包发送到 本机的9998端口时， 就会接收到数据
        // 如果没有数据包发送到 本机的9998端口， 就会阻塞等待
        System.out.println("接收端B 等待...");
        socket.receive(packet);
        // （3）. 可以把packet 进行拆包， 取出数据， 并显示
        int length = packet.getLength(); //实际接受到的数据字节长度
        byte[] data1 = packet.getData(); //接受到的数据
        String s = new String(data1, 0, length);
        System.out.println(s);


        //关闭资源
        socket.close();
        System.out.println("B端退出");


    }
}
