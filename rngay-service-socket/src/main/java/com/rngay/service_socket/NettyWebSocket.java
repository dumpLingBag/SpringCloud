package com.rngay.service_socket;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.util.SerializeUtil;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_socket.contants.RedisKeys;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(prefix = "netty-web-socket")
@Component
public class NettyWebSocket {

    private Logger log = LoggerFactory.getLogger(NettyWebSocket.class);

    public NettyWebSocket() {}

    @Resource
    public void setUserService(PFUserService userService) {
        NettyWebSocket.userService = userService;
    }

    @Resource
    public void setRedisUtil(RedisUtil redisUtil) {
        NettyWebSocket.redisUtil = redisUtil;
    }

    private static RedisUtil redisUtil;
    private static PFUserService userService;
    private static ConcurrentHashMap<String, NettyWebSocket> webSocketSet = new ConcurrentHashMap<>();
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
        String userId = parameterMap.getParameter("userId");
        if (userId != null && !"".equals(userId)) {
            this.userId = userId;
            this.session = session;
            webSocketSet.put(userId, this);
            UAUserDTO user = userService.findById(Integer.parseInt(userId)).getData();
            if (user != null) {
                user.setExpireTime(new Date());
                redisUtil.zadd(RedisKeys.KEY_SET_USER, user.getId(), user);
                redisUtil.set(RedisKeys.getUserKey(userId), user);
            }
            addOnlineCount();
            log.info("用户ID为 -> "+ this.userId +" 的用户加入了，当前在线人数为 -> " + onlineCount);
        }
    }

    /**
     * 当有WebSocket连接关闭时，对该方法进行回调 注入参数的类型:Session
     * @Author pengcheng
     * @Date 2019/4/10
     **/
    @OnClose
    public void onClose(Session session) throws IOException {
        Object user = redisUtil.get(RedisKeys.getUserKey(this.userId));
        if (user != null) {
            redisUtil.zrem(RedisKeys.KEY_SET_USER, user);
            redisUtil.del(RedisKeys.getUserKey(this.userId));
            webSocketSet.remove(this.userId);
            subOnlineCount();
            log.info("有一连接关闭！当前在线人数为 -> " + getOnlineCount());
        }
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
     * 指定用户发送消息, 每次发送消息更新过期时间，30分钟没新消息则删除聊天纪录
     * @Author pengcheng
     * @Date 2019/4/10
     **/
    public boolean sendUser(String message, String sendUserId) {
        try {
            NettyWebSocket nettyWebSocket = webSocketSet.get(sendUserId);
            nettyWebSocket.session.sendText(message);
            redisUtil.zadd(RedisKeys.getMessage(sendUserId), new Date().getTime(), message);
            redisUtil.expire(RedisKeys.getMessage(sendUserId), 60 * 30);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
    * 获取所有在线用户 Id
    * @Author pengcheng
    * @Date 2019/4/19
    **/
    public List<String> getUser() {
        List<String> list = new ArrayList<>();
        for (NettyWebSocket item : webSocketSet.values()) {
            list.add(item.userId);
        }
        return list;
    }

    /**
     * 群发消息
     * @Author pengcheng
     * @Date 2019/4/10
     **/
    public boolean sendAll(String message) {
        if (webSocketSet.size() > 0) {
            for (NettyWebSocket item : webSocketSet.values()) {
                try {
                    item.session.sendText(message);
                } catch (Exception e) {
                    log.warn("ID为" + item.userId + "的用户消息发送失败");
                }
            }
            return true;
        }
        return false;
    }

    public boolean kickOut(String userId) {
        NettyWebSocket remove = webSocketSet.remove(userId);
        return remove == null;
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        NettyWebSocket.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        NettyWebSocket.onlineCount--;
    }

}
