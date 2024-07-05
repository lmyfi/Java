package com.study.qqcommon;

import java.io.Serializable;

// 用户对象
public class User implements Serializable { // 后序 需要将对象输入 socket 中， 所以需要序列化 Serializable  ---IO流知识点

    private static final long serialVersionUID = 1L; //增强兼容性， 可不写
    private String userId;
    private String passwd;

    public User() {
        this.userId = userId;
        this.passwd = passwd;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUserId() {
        return userId;
    }

    public String getPasswd() {
        return passwd;
    }
}
