package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("test") //说明实体对应哪张表
//对应数据库中的那个表，就填入相应的表名
public class Test {
    @TableId(type = IdType.AUTO)
    @TableField("id")  //属性对应的表中的字段名
    private Integer id;
    @TableField("content")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
