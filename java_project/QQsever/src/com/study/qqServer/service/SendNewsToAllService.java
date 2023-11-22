package com.study.qqServer.service;

import com.study.qqcommon.Message;
import com.study.qqcommon.MessageType;
import com.study.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class SendNewsToAllService implements Runnable{
    @Override
    public void run() {

        //多次使用推送服务,添加循环
        while (true) {
            System.out.println("请输入服务器要推送的新闻/消息[输入exit表示退出推送服务线程]");
            String news = Utility.readString(100);
            //判断是否退出
            if (news.equals("exit")) {
                break;
            }
            //构建一个信息， 群发信息
            Message message = new Message();
            message.setSender("服务器");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("服务器推送消息给所有人说: " + news);

            //遍历当前所有的通信线程， 得到线程， 并发送message

            HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();

            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onlineUserId = iterator.next().toString();

                try {
                    ObjectOutputStream oos = new ObjectOutputStream(hm.get(onlineUserId).getSocket().getOutputStream());
                    oos.writeObject(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
