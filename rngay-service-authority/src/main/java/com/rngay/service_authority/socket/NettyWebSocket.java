package com.rngay.service_authority.socket;

import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(prefix = "netty-web-socket")
@Component
public class NettyWebSocket {

    private Logger log = LoggerFactory.getLogger(NettyWebSocket.class);

    private static CopyOnWriteArraySet<NettyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();
    private static int onlineCount = 0;

    private String userId = "";
    private Session session;

    /**
    * 当有新的WebSocket连接进入时，对该方法进行回调 注入参数的类型:Session、HttpHeaders、ParameterMap
    * @Author pengcheng
    * @Date 2019/4/10
    **/
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        this.userId = parameterMap.getParameter("userId");
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        log.info("用户ID为 -> "+ this.userId +"的用户加入了，当前在线人数为 -> " + onlineCount);
    }

    /**
    * 当有WebSocket连接关闭时，对该方法进行回调 注入参数的类型:Session
    * @Author pengcheng
    * @Date 2019/4/10
    **/
    @OnClose
    public void onClose(Session session) throws IOException {
        webSocketSet.remove(this);
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为 -> " + getOnlineCount());
    }

    /**
    * 当有WebSocket抛出异常时，对该方法进行回调 注入参数的类型:Session、Throwable
    * @Author pengcheng
    * @Date 2019/4/10
    **/
    @OnError
    public void onError(Session session, Throwable throwable) {
        session.sendText("消息出现异常");
        throwable.printStackTrace();
    }

    /**
    * 当接收到字符串消息时，对该方法进行回调 注入参数的类型:Session、String
    * @Author pengcheng
    * @Date 2019/4/10
    **/
    @OnMessage
    public void OnMessage(Session session, String message) {
        log.info("收到新的消息 -> " + message);
        session.sendText("Hello Netty!");
    }

    /**
    * 指定用户发送消息
    * @Author pengcheng
    * @Date 2019/4/10
    **/
    public void sendUser(String message, String sendUserId) {
        for (NettyWebSocket item : webSocketSet) {
            if (item.userId.equals(sendUserId)) {
                item.session.sendText(message);
            }
        }
    }

    /**
    * 群发消息
    * @Author pengcheng
    * @Date 2019/4/10
    **/
    public void sendAll(String message) {
        for (NettyWebSocket item : webSocketSet) {
            item.session.sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        NettyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        NettyWebSocket.onlineCount--;
    }

}
