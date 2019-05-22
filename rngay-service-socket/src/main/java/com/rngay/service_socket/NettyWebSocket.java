package com.rngay.service_socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rngay.common.cache.RedisUtil;
import com.rngay.feign.socket.dto.ContentDTO;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_socket.contants.RedisKeys;
import com.rngay.service_socket.util.MessageSortUtil;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(prefix = "netty-web-socket")
@Component
public class NettyWebSocket {

    private Logger logger = LoggerFactory.getLogger(NettyWebSocket.class);

    public NettyWebSocket() {
    }

    @Autowired
    public void setUserService(PFUserService userService) {
        NettyWebSocket.userService = userService;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        NettyWebSocket.redisUtil = redisUtil;
    }

    private static RedisUtil redisUtil;
    private static PFUserService userService;
    private static ConcurrentHashMap<String, NettyWebSocket> webSocket = new ConcurrentHashMap<>();

    private String userId = "";
    private Session session;

    /**
     * 当有新的WebSocket连接进入时，对该方法进行回调 注入参数的类型:Session、HttpHeaders、ParameterMap
     *
     * @Author pengcheng
     * @Date 2019/4/10
     **/
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        String userId = parameterMap.getParameter("userId");
        if (userId != null && !"".equals(userId)) {
            this.userId = userId;
            this.session = session;
            boolean socket = webSocket.containsKey(userId);
            if (!socket) {
                webSocket.put(userId, this);
                UAUserDTO user = userService.findById(Integer.parseInt(userId)).getData();
                if (user != null) {
                    user.setExpireTime(new Date());
                    redisUtil.zadd(RedisKeys.KEY_SET_USER, user.getId(), user);
                    redisUtil.set(RedisKeys.getUserKey(userId), user);
                }
                logger.info("用户ID为 -> " + userId + " 的用户加入了，当前在线人数为 -> " + getOnlineCount());
            }
        }
    }

    /**
     * 当有WebSocket连接关闭时，对该方法进行回调 注入参数的类型:Session
     *
     * @Author pengcheng
     * @Date 2019/4/10
     **/
    @OnClose
    public void onClose(Session session) throws IOException {
        Object user = redisUtil.get(RedisKeys.getUserKey(this.userId));
        if (user != null) {
            redisUtil.zrem(RedisKeys.KEY_SET_USER, user);
            redisUtil.del(RedisKeys.getUserKey(this.userId));
        }
        webSocket.remove(this.userId);
        logger.info("有一连接关闭！当前在线人数为 -> " + getOnlineCount());
    }

    /**
     * 当有WebSocket抛出异常时，对该方法进行回调 注入参数的类型:Session、Throwable
     *
     * @Author pengcheng
     * @Date 2019/4/10
     **/
    @OnError
    public void onError(Session session, Throwable throwable) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        String message = "{\"to\":\"0\",\"fm\":\"0\",\"fmTo\":\"0\",\"content\":\"消息发送异常\"," +
                "\"createTime\":\""+format+"\",\"smsType\":\"0\"}";
        session.sendText(message);
        throwable.printStackTrace();
    }

    /**
     * 当接收到字符串消息时，对该方法进行回调 注入参数的类型:Session、String
     *
     * @Author pengcheng
     * @Date 2019/4/10
     **/
    @OnMessage
    public void OnMessage(String message) {
        logger.info("收到新的消息 -> " + message);
        if (message != null && !"".equals(message)) {
            JSONObject object = JSONObject.parseObject(message);
            ContentDTO contentDTO = JSON.toJavaObject(object, ContentDTO.class);
            if (contentDTO != null) {
                List<Integer> sort = MessageSortUtil.sort(contentDTO.getTo(), contentDTO.getFm());
                NettyWebSocket nettyWebSocket = webSocket.get(contentDTO.getTo());
                if (nettyWebSocket == null) {
                    redisUtil.zadd(RedisKeys.getCacheMessage(String.valueOf(sort.get(0)), String.valueOf(sort.get(1))), new Date().getTime(), contentDTO);
                } else {
                    redisUtil.zadd(RedisKeys.getMessage(String.valueOf(sort.get(0)), String.valueOf(sort.get(1))), new Date().getTime(), contentDTO);
                    redisUtil.expire(RedisKeys.getMessage(String.valueOf(sort.get(0)), String.valueOf(sort.get(1))),60 * 60 * 24 * 30);
                    nettyWebSocket.session.sendText(message);
                }
            }
        }
    }

    /**
     * 群发消息
     *
     * @Author pengcheng
     * @Date 2019/4/10
     **/
    public boolean sendAll(String message) {
        if (webSocket.size() > 0) {
            for (NettyWebSocket item : webSocket.values()) {
                try {
                    item.session.sendText(message);
                } catch (Exception e) {
                    logger.warn("ID为" + item.userId + "的用户消息发送失败");
                }
            }
            return true;
        }
        return false;
    }

    public boolean kickOut(String userId) {
        try {
            Object user = redisUtil.get(RedisKeys.getUserKey(userId));
            if (user != null) {
                redisUtil.del(RedisKeys.getUserKey(userId));
                redisUtil.zrem(RedisKeys.KEY_SET_USER, user);
                webSocket.remove(userId);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static int getOnlineCount() {
        return webSocket.size();
    }

}
