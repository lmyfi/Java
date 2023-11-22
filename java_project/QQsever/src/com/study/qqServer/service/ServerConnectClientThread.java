package com.study.qqServer.service;

import com.study.qqcommon.Message;
import com.study.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 该类的一个对象和某个客户端保持通信
 */
public class ServerConnectClientThread extends Thread {

    private Socket socket;
    private String userId; //连接到服务端的用户名

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }


    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() { //这里线程处于run的状态， 可以发送/接收消息

        while(true) {
            try {
                System.out.println("服务端和客户端" + userId + "保持通信， 读取数据...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message =(Message) ois.readObject();
                //后面使用message, 根据message的类型， 作相应的业务处理
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    //读取用户列表， 并显示
                    System.out.println(message.getSender() + " 要在线用户列表 ");
                    String onlineUser = ManageClientThreads.getOnlineUser();
                    //返回message
                    //构建一个Message 对象， 返回给客户端
                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message2.setContent(onlineUser);
                    message2.setGetter(message.getSender());
                    //返回给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                }else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                   //根据message获取getter id， 然后得到对应的线程
                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.getServerConnectClientThread(message.getGetter());
                    //得到对应的socket的对象输出流， 将message对象转发给指定的客户端
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message);//转发， 如果客户不在线， 可以保存到数据库， 实现离线留言
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    //需要遍历 管理线程集合， 把所用的线程 socket 得到， 然后把message进行 转发即可
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();

                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {

                        //取出在线用户id
                        String onlineUserId = iterator.next().toString();

                        if (!onlineUserId.equals(message.getSender())) { //排除群发信息的这个用户

                            //进行转发message
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(onlineUserId).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }

                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    //根据getterId 获取对应的线程， 将messaged对象转发
                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.getServerConnectClientThread(message.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());

                    //转发
                    oos.writeObject(message);

                }else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) { //客户端退出
                    System.out.println(message.getSender() + "退出");
                    //将这个客户端对应线程， 从集合中删除
                    ManageClientThreads.removeServerConnectClientThreadThread(message.getSender());
                    socket.close(); //关闭连接
                    //退出while循环
                    break;
                } else {
                    System.out.println("其它数据类型，暂时不处理");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
