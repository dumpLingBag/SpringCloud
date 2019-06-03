package com.rngay.service_socket.service;

import com.rngay.common.vo.PageList;
import com.rngay.feign.socket.dto.ContentDTO;

import java.util.List;
import java.util.Map;

public interface MessageService {

    List<Map<String, Object>> openMessage(String userId);

    Integer saveMessage(ContentDTO messageInfo);

    PageList<Map<String, Object>> getUserContent(ContentDTO contentDTO);

    PageList<Map<String, Object>> getToUserContent(String userInfoId);

}
