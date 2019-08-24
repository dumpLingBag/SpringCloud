package com.rngay.service_socket.service;

import com.rngay.common.vo.PageList;
import com.rngay.feign.socket.dto.ContentDTO;
import com.rngay.feign.socket.dto.MessageList;

import java.util.List;
import java.util.Map;

public interface MessageService {

    List<MessageList> openMessage(String userId);

    Integer saveMessage(ContentDTO messageInfo);

    PageList<ContentDTO> getUserContent(ContentDTO contentDTO);

    PageList<ContentDTO> getToUserContent(String userInfoId);

    Integer getLong(String md5encrypt);

    Integer alreadyRead(String md5encrypt, Integer fromUserId);

    Map<String, Object> getPhoto(String userId);

    Integer readId(Integer id, Integer smsStatus);

}
