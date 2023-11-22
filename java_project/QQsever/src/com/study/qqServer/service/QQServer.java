package com.study.qqServer.service;

import com.study.qqcommon.Message;
import com.study.qqcommon.MessageType;
import com.study.qqcommon.User;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端服务
 */
public class QQServer {
    //服务端属性
    private ServerSocket ss = null;
    //创建一个集合， 存放多个用户， 如果这些用户登陆， 就认为合法
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, ArrayList<Message>> offLineDb = new ConcurrentHashMap<>();

    //用静态代码块， 初始化 validUsers
    static {
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("至尊宝", new User("至尊宝", "123456"));
        validUsers.put("紫霞仙子", new User("紫霞仙子", "123456"));
        validUsers.put("菩提老祖", new User("菩提老祖", "123456"));
    }

    public boolean checkUser(String userId, String passwd) {
        User user = validUsers.get(userId);
        if (user == null) {
            return false;
        }if (!user.getPasswd().equals(passwd)) { //userId 正确， 但是密码错误
            return false;
        }
        return true;
    }
    //构造器
    public QQServer() {
        try {
            System.out.println("服务端在 9999 端口监听");
            //启动推送新闻线程
            new Thread(new SendNewsToAllService()).start();
            ss = new ServerSocket(9999);

            while (true) { //当和某个客户端连接后，会继续监听， 因此while
                Socket socket = ss.accept(); //如果没有客户端连接， 就会阻塞在这里
                //得到socket相关联的输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                //得到socket关联的输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());


                User u = (User) ois.readObject(); //读取客户端发送的User对象
                //创建一个Message对象， 准备回复客户端
                Message message = new Message();
                //验证
                if (checkUser(u.getUserId(), u.getPasswd())) { //验证通过， 登陆成功
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //将Message回复给客户端
                    oos.writeObject(message);
                    //创建一个线程， 和客户端保持通信， 该线程需要持有socket对象
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserId());
                    //启动该线程
                    serverConnectClientThread.start();
                    //将线程对象，放入到一个集合中， 进行管理
                    ManageClientThreads.addClientThread(u.getUserId(), serverConnectClientThread);
                }else { //登陆失败
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    //关闭socket
                    socket.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //如果服务器退出while, 说明服务器不在监听， 因此关闭ServerSocket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
