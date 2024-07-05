package com.study.qqclient.sevice;

import com.study.qqcommon.Message;
import com.study.qqcommon.MessageType;

import java.io.*;

/**
 * 该类/对象完成 文件传输服务
 */
public class FileClientService {

    /**
     *
     * @param src 源文件
     * @param dest  把文件传输到对方的那个目录
     * @param senderId 发送用户id
     * @param getterId  接受用户Id
     */
    public void sendFileToOne(String src, String dest, String senderId, String getterId ) {

        //读取src文件 ---》 message 对象
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);

        //需要将文件读取
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int)new File(src).length()];

        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes); //将src文件读取到程序的自己数组
            //将文件对应的字节数组设置成message
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //提示信息
        System.out.println("\n" + senderId + "给" + getterId + " 发送文件" + src + "到对方的电脑目录" + dest);

        //发送

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
