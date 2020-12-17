package com.example.websocket.controller;


import com.alibaba.fastjson.JSON;
import com.example.websocket.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value="/test/p2p")
@Component
public class P2PWebSocket {

    //记录当前在线人数
    private static final AtomicInteger onlineCount = new AtomicInteger();

    //存放在线客户端
    private static final Map<String, Session> client = new ConcurrentHashMap<>();

    /**
     * 建立连接的时候调用
     */
    @OnOpen
    public void OnOpen(Session session){
        onlineCount.incrementAndGet();
        client.put(session.getId(),session);
        log.info("有新连接加入: {},当前在线人数为: {}",session.getId(),onlineCount.get());
    }

    /**
     * 断开连接的时候调用
     */
    @OnClose
    public void OnClose(Session session){
        onlineCount.decrementAndGet();
        client.remove(session.getId());
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 发送消息的时候调用
     * @param message 要发送的消息
     * @param session session信息
     *
     */
    @OnMessage
    public void OnMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        this.SendMessage(message, session);
    }

    @OnError
    public void OnError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息的函数
     * @param message
     * @param fromSession
     */
    private void SendMessage(String message, Session fromSession){
        Message receive = JSON.parseObject(message,Message.class);
        for(Map.Entry<String, Session> sessionEntry : client.entrySet()){
            Session toSession = sessionEntry.getValue();
            if(receive.getToUser().equals(toSession.getId())){
                log.info("客户端[{}]发送给客户端[{}],消息内容为：{}",
                        fromSession.getId(),toSession.getId(),receive.getContent());
                toSession.getAsyncRemote().sendText(receive.getContent());
                return ;
            }
        }

        //也有可能session是空的
        fromSession.getAsyncRemote().sendText("不存在该id用户");
    }
}
