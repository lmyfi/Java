package com.imooc.mall.utils;

import com.imooc.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5Utils {

    /**
     * md5加密
     * @param strValue 要加密的数据
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5str(String strValue) throws NoSuchAlgorithmException {
        //获取MD5工具
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        /**
         * strValue.getBytes() 将字符串以字节方式获取，因为加密算法需要字节数据类型
         * md5.digest() MD5加密方法
         *Base64.encodeBase64String将按字节获取的数据进行base64转码，为了便于存储
         * Constant.SALT盐值
         */
        return Base64.encodeBase64String(md5.digest((strValue+Constant.SALT).getBytes()));
    }

    //测试
    public static void main(String[] args) {
        String md5str = null;
        try {
            md5str = getMD5str("1234");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(md5str);
    }
}
