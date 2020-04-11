package com.rngay.service_socket;

import com.rngay.service_socket.util.SocketSmsMapUtil;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;

@ServerEndpoint(prefix = "netty-web-socket")
@Component
public class NettyWebSocket {

    private Logger logger = LoggerFactory.getLogger(NettyWebSocket.class);

    private String userId;
    private Session session;

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        String userId = parameterMap.getParameter("userId");
        if (StringUtils.isNotEmpty(userId)) {
            this.userId = userId;
            this.session = session;
            SocketSmsMapUtil.put(userId, this);
            this.session.sendText("{\"readyState\":\"1\"}");
        } else {
            this.session.sendText("{\"readyState\":\"3\"}");
        }
    }

    @OnClose
    public void onClose() throws IOException {
        SocketSmsMapUtil.remove(this.userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        session.sendText("{\"readyState\":\"4\"}");
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        logger.info("收到消息：" + message);
        if (message != null && message.equals("socket:rngay:ping")) {

            session.sendText("socket:rngay:pong");
        }
    }

    private String dataInterval() {
        return ",(CASE \n" +
                "WHEN DATEDIFF(time_interval,NOW()) = 0 THEN DATE_FORMAT(time_interval,'%H:%i')" +
                "WHEN DATEDIFF(time_interval,NOW()) = -1 THEN DATE_FORMAT(time_interval,'昨天 %H:%i')" +
                "WHEN DATEDIFF(time_interval,NOW()) < 0 AND DATEDIFF(time_interval,NOW()) > -7 THEN " +
                "(CASE \n" +
                "DAYOFWEEK(time_interval)" +
                "WHEN 1 THEN DATE_FORMAT(time_interval,'星期日 %H:%i')" +
                "WHEN 2 THEN DATE_FORMAT(time_interval,'星期一 %H:%i')" +
                "WHEN 3 THEN DATE_FORMAT(time_interval,'星期二 %H:%i')" +
                "WHEN 4 THEN DATE_FORMAT(time_interval,'星期三 %H:%i')" +
                "WHEN 5 THEN DATE_FORMAT(time_interval,'星期四 %H:%i')" +
                "WHEN 6 THEN DATE_FORMAT(time_interval,'星期五 %H:%i')" +
                "WHEN 7 THEN DATE_FORMAT(time_interval,'星期六 %H:%i') ELSE NULL\n" +
                "END)" +
                "WHEN DATEDIFF(time_interval,NOW()) < -7 THEN DATE_FORMAT(time_interval,'%Y年%c月%e日 %H:%i')" +
                "ELSE NULL\n" +
                "END) time_interval ";
    }

}
