package com.study.qqclient.sevice;

import java.util.HashMap;

//该类管理客户端连接到服务端的线程
public class ManageClientConnectServerThread {
    //把多个线程放到一个HashMap集合， key 就是用户Id, value 就是线程
    public static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    //将某个线程加到集合
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId,clientConnectServerThread);
    }
    //通过userId 可以得到对应线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }
}