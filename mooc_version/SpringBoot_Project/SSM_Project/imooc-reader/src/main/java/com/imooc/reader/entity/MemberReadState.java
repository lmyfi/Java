package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 会员阅读状态实体
 */
@TableName("member_read_state")
public class MemberReadState {
    @TableId(type = IdType.AUTO)
    private Long rsId;
    private Long bookId;
    private Long memberId;
    private Integer readState;
    private Date createTime;

    public void setRsId(Long rsId) {
        this.rsId = rsId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setReadState(Integer readState) {
        this.readState = readState;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getRsId() {
        return rsId;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Integer getReadState() {
        return readState;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
