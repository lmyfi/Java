package com.netstudy.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

// UDP 接收端
public class UDPReceiverA {
    public static void main(String[] args) throws IOException {

        //1. 创建一份 DatagramSocket 对象， 准备在9999 端口接受数据
        DatagramSocket socket = new DatagramSocket(9999);
        //2. 构建一个DatagramPacket 对象， 准备接受数据
        //      UDP 一个数据包的最大 为64k
        byte[] buf = new byte[64];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        // 3.调用 接受方法， 将通过网络传输的 DatagramPacket 对象
        //填充到 packet 对象
        // 当有数据包发送到 本机的9999端口时， 就会接收到数据
        // 如果没有数据包发送到 本机的9999端口， 就会阻塞等待
        System.out.println("接收端A 等待");
        socket.receive(packet);


        // 将要发送的数据 封装到DatagramPacket 中
        byte[] data1 = "好的， 明天见 ".getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet1 = new DatagramPacket(data1, data1.length, InetAddress.getByName("192.168.100.1"),9998);
        socket.send(packet1);
        // 4. 可以把packet 进行拆包， 取出数据， 并显示
        int length = packet.getLength(); //实际接受到的数据字节长度
        byte[] data = packet.getData(); //接受到的数据
        String s = new String(data, 0, length);
        System.out.println(s);

        //关闭资源
        socket.close();
        System.out.println("A端 退出");

    }
}
