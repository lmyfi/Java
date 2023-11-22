package com.study.qqclient.view;

import com.study.qqclient.sevice.FileClientService;
import com.study.qqclient.sevice.MessageClientService;
import com.study.qqclient.sevice.UserClientService;
import com.study.qqclient.utils.Utility;
import com.study.qqcommon.MessageType;

import java.io.File;

public class QQview {

    private boolean loop = true; // 控制是否像是菜单
    private String key = " "; //接受用户的键盘输入
    private UserClientService userClientService = new UserClientService();//对象是用户登陆服务/注册用户
    private MessageClientService messageClientService = new MessageClientService();//对象用户私聊/群聊
    private FileClientService fileClientService = new FileClientService();//该对象用于传输文件
    public static void main(String[] args) {
        new QQview().mainMenu();
       System.out.println("退出系统" );
    }
    //显示主菜单
    public void mainMenu() {
        while (loop) {
            System.out.println("===========欢迎登陆网络通信系统===========");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择:  " );
            key = Utility.readString(1);
            //根据用户输入， 来处理不同逻辑
            switch (key) {
                case "1":
                    System.out.print("请输入你的用户名: ");
                    String userId = Utility.readString(50);
                    System.out.print("请输入你的用户密码: ");
                    String pwd = Utility.readString(50);
                    if (userClientService.checkUser(userId, pwd)) { //二级菜单
                        System.out.println("======欢迎 （ 用户 " + userId + "登陆成功)" +"======="+ "\r\n");
                        //进入到二级菜单
                        while (loop) {
                            System.out.println("===========网络通信系统二级菜单欢迎（用户 " + userId + ") ========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择:  ");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1" :
                                    System.out.println(" 在线列表 ");
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("请输入相对大家说的话");
                                    String s = Utility.readString(50);
                                    //调用一个方法， 将对象封装成message对象， 发送给服务端
                                    messageClientService.sendMessageToAll(s, userId);
                                    break;
                                case "3":
                                    //编写方法，发送 message对象
                                    System.out.println("请输入想聊天的用户名（在线）:");
                                    String getterId = Utility.readString(50);
                                    System.out.println("请输入想说的话: ");
                                    String content = Utility.readString(100);
                                    //编写一个方法， 将消息发送给服务端
                                    messageClientService.sendMessageToOne(content, userId, getterId);
                                    System.out.println(" 私聊消息");
                                    break;
                                case "4":
                                    System.out.print("请输入你想把文件发送给的用户（在线用户）:");
                                    getterId = Utility.readString(50);
                                    System.out.print("请输入发送文件的完整路径（形式 d:\\xxx.jpg ）");
                                    String src = Utility.readString(100);
                                    System.out.print("请输入把文件发送到用户的什么位置（形式 d:\\xxx.jpg）");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src, dest, userId, getterId);
                                    break;
                                case "9" :
                                    //调用退出系统方法
                                    userClientService.logout();
                                    loop = false;
                                    break;
                        }
                        }
                    }else {
                        System.out.println("======登陆失败=====" + "\r\n");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }

        }
    }

}
