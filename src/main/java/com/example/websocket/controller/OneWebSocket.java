package com.example.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 前后端交互实现，自己发消息给自己
 */
@Slf4j
@ServerEndpoint(value="/test/one")
@Component
public class OneWebSocket {

    //记录当前在线人数
    private static AtomicInteger onlineCount = new AtomicInteger();

    /**
     * 链接建立的时候调用的方法
     */
    @OnOpen
    public void OnOpen(Session session){
        onlineCount.incrementAndGet();
        log.info("有新连接加入: {},当前在线人数为: {}",session.getId(),onlineCount.get());
    }


    /**
     * 链接关闭的时候调用
     */
    @OnClose
    public void OnClose(Session session){
        onlineCount.decrementAndGet();
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
        this.SendMessage("Hello, " + message, session);
    }

    @OnError
    public void OnError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    private void SendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：{}", e);
        }
    }
}
