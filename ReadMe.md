# Spring Boot + WebSocket  

这个项目是用于自己练手实现 spring boot整合websocket，日后可能会将项目发展成一个聊天室项目，以后再说吧  

## 目前的版本  

目前已经实现了与前端页面与后端的调整，包括自己给自己发信息、websocket实现群发  

## 目前遇到的问题  

在2020/12/18提交的版本中，想尝试将广播改造成使用用户名的形式，而不依靠WebSocket的session id，但是改造失败了。JS无法获取index.html中用thymeleaf标签标注的`<p>`内的值，目前没有什么好的解决思路  
