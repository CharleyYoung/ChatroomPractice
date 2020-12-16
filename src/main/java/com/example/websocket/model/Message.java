package com.example.websocket.model;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * 消息体
 */
public class Message {
    @JSONField(name = "fromUser")
    private String fromUser;

    @JSONField(name = "toUser")
    private String toUser;

    @JSONField(name = "content")
    private String content;

    

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
