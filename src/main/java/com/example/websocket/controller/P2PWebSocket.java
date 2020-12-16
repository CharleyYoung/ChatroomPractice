package com.example.websocket.controller;


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
        log.info("有新连接加入广播: {},当前在线人数为: {}",session.getId(),onlineCount.get());
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

    private void SendMessage(String message, Session fromSession){

    }
}
