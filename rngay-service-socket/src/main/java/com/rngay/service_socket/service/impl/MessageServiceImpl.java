package com.rngay.service_socket.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.common.vo.PageList;
import com.rngay.feign.socket.dto.ContentDTO;
import com.rngay.feign.socket.dto.MessageList;
import com.rngay.feign.socket.dto.UserMessage;
import com.rngay.service_socket.model.UserMessageInfo;
import com.rngay.service_socket.model.UserMessageList;
import com.rngay.service_socket.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private Dao sqlDao;

    @Override
    public List<MessageList> openMessage(String userId) {
        String sqlList = "select uml.*,am.nick_name,am.avatar_path to_avatar_path,am.user_name," +
                "(select avatar_path from au_member au where au.id = uml.from_user_id) from_avatar_path," +
                "(select count(id) from user_message_info uio where uio.sms_status <> 50" +
                " and uml.user_info_id = uio.user_info_id and uio.to_user_id = uml.from_user_id) un_read\n" +
                " from user_message_list uml inner join au_member am on uml.to_user_id = am.id" +
                " where uml.from_user_id = ? and uml.is_delete = 0";
        List<MessageList> mapList = sqlDao.query(sqlList, BeanPropertyRowMapper.newInstance(MessageList.class), userId);
        if (mapList.isEmpty()) {
            return mapList;
        }
        List<String> userInfoIds = new ArrayList<>();
        for (MessageList map : mapList) {
            if (map.getUserInfoId() != null) {
                userInfoIds.add(map.getUserInfoId());
            }
        }
        StringBuilder param = new StringBuilder();
        for (int i = 0; i < userInfoIds.size(); i++) {
            if (i != userInfoIds.size() - 1) {
                param.append("'").append(userInfoIds.get(i)).append("'").append(",");
            } else {
                param.append("'").append(userInfoIds.get(i)).append("'");
            }
        }
        //获取最新信息
        String sqlInfo = "SELECT umi.*,(CASE \n" +
                "WHEN DATEDIFF(create_time,NOW()) = 0 THEN DATE_FORMAT(create_time,'%H:%i')" +
                "WHEN DATEDIFF(create_time,NOW()) = -1 THEN '昨天'" +
                "WHEN DATEDIFF(create_time,NOW()) < 0 AND DATEDIFF(create_time,NOW()) > -7 THEN " +
                "(CASE \n" +
                "DAYOFWEEK(create_time) WHEN 1 THEN '星期日' WHEN 2 THEN '星期一' WHEN 3 THEN '星期二' WHEN 4 THEN '星期三' WHEN 5 THEN '星期四' WHEN 6 THEN '星期五' WHEN 7 THEN '星期六' ELSE NULL\n" +
                "END)" +
                "WHEN DATEDIFF(create_time,NOW()) < -7 THEN DATE_FORMAT(create_time,'%Y/%c/%e')" +
                "ELSE NULL\n" +
                "END" +
                ") date_time FROM user_message_info umi WHERE\n" +
                "umi.id IN\n" +
                "(SELECT MAX(id) FROM user_message_info \n" +
                "WHERE user_info_id\n" +
                "IN(" + param + ")\n" +
                "AND from_is_delete <> ? AND to_is_delete <> ? \n" +
                "GROUP BY user_info_id)\n" +
                "ORDER BY umi.id DESC";
        List<UserMessage> infoList = sqlDao.query(sqlInfo, BeanPropertyRowMapper.newInstance(UserMessage.class), userId, userId);
        Map<String, Object> map = new HashMap<>();
        if (!infoList.isEmpty()) {
            for (int i = 0; i < infoList.size(); i++) {
                map.put(infoList.get(i).getUserInfoId(),infoList.get(i));
                map.put(infoList.get(i).getUserInfoId() + "_sort", i);
            }
        }
        //录入信息
        for (MessageList messageMap : mapList) {
            messageMap.setUserMessage((UserMessage) map.get(messageMap.getUserInfoId()));
            messageMap.setSort((Integer) map.get(messageMap.getUserInfoId() + "_sort"));
        }
        //排序
        mapList.sort(Comparator.comparing(MessageList::getSort));
        return mapList;
    }

    @Override
    public Integer saveMessage(ContentDTO messageInfo) {
        UserMessageList fm = sqlDao.find(UserMessageList.class, Cnd.where("from_user_id", "=", messageInfo.getFromUserId()).and("to_user_id", "=", messageInfo.getToUserId()));
        if (fm == null) {
            Date date = new Date();
            sqlDao.insert(messageInfo);
            sqlDao.update("insert into user_message_list(from_user_id, to_user_id, user_info_id, create_time, update_time) values(?,?,?,?,?)",
                    messageInfo.getFromUserId(), messageInfo.getToUserId(), messageInfo.getUserInfoId(), date, date);
        } else {
            if (String.valueOf(fm.getIsDelete()).equals("1")) {
                sqlDao.update("update user_message_list set is_delete = 0 where from_user_id = ? and to_user_id = ?",
                        messageInfo.getFromUserId(), messageInfo.getToUserId());
            }
        }
        UserMessageList to = sqlDao.find(UserMessageList.class, Cnd.where("from_user_id", "=", messageInfo.getToUserId()).and("to_user_id", "=", messageInfo.getFromUserId()));
        if (to == null) {
            Date date = new Date();
            sqlDao.update("insert into user_message_list(from_user_id, to_user_id, user_info_id, create_time, update_time) values(?,?,?,?,?)",
                    messageInfo.getToUserId(), messageInfo.getFromUserId(), messageInfo.getUserInfoId(), date, date);
        } else {
            if (String.valueOf(to.getIsDelete()).equals("1")) {
                sqlDao.update("update user_message_list set is_delete = 0 where from_user_id = ? and to_user_id = ?",
                        messageInfo.getToUserId(), messageInfo.getFromUserId());
            }
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        sqlDao.update(con -> {
            String sql;
            if (messageInfo.getSmsType().name().equals("IMAGE")) {
                sql = "insert into user_message_info(user_info_id, from_user_id, to_user_id, content, sms_status, sms_type, create_time, time_interval, message_id, width, height)" +
                        " values (?,?,?,?,?,?,?,?,?,?,?)";
            } else {
                sql = "insert into user_message_info(user_info_id, from_user_id, to_user_id, content, sms_status, sms_type, create_time, time_interval, message_id)" +
                        " values (?,?,?,?,?,?,?,?,?)";
            }
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, messageInfo.getUserInfoId());
            statement.setInt(2, messageInfo.getFromUserId());
            statement.setInt(3, messageInfo.getToUserId());
            statement.setString(4, messageInfo.getContent());
            statement.setInt(5, messageInfo.getSmsStatus());
            statement.setString(6, messageInfo.getSmsType().name());
            statement.setObject(7, messageInfo.getCreateTime());
            statement.setString(8, messageInfo.getTimeInterval());
            statement.setString(9, messageInfo.getMessageId());
            if (messageInfo.getSmsType().name().equals("IMAGE")) {
                statement.setFloat(10, messageInfo.getWidth());
                statement.setFloat(11, messageInfo.getHeight());
            }
            return statement;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            return key.intValue();
        }
        return null;
    }

    @Override
    public PageList<ContentDTO> getUserContent(ContentDTO contentDTO) {
        String sql = "SELECT umi.id,umi.content,umi.create_time,umi.width,umi.height,umi.from_user_id," +
                "(CASE WHEN DATEDIFF(create_time,NOW()) = 0 THEN sms_status ELSE NULL END) sms_status" +
                ",umi.sms_type,umi.to_user_id,umi.user_info_id " +
                dataInterval() +
                "FROM user_message_info umi \n" +
                "WHERE umi.user_info_id = ? ORDER BY umi.id DESC";
        return null;
    }

    @Override
    public PageList<ContentDTO> getToUserContent(String userInfoId) {
        String sql = "SELECT umi.id,umi.content,umi.create_time,umi.from_user_id," +
                "(CASE WHEN DATEDIFF(create_time,NOW()) = 0 THEN sms_status ELSE NULL END) sms_status" +
                ",umi.sms_type,umi.to_user_id,umi.user_info_id" +
                dataInterval() +
                "FROM user_message_info umi WHERE umi.user_info_id = ? ORDER BY umi.id DESC";
        return null;
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

    @Override
    public Integer getLong(String md5encrypt) {
        String sql = "select count(*) from user_message_list where user_info_id = ?";
        return null;
    }

    @Override
    public Integer alreadyRead(String md5encrypt, Integer fromUserId) {
        String sql = "update user_message_info set sms_status = 50 where user_info_id = ? and sms_status <> 20 and from_user_id <> ?";
        return sqlDao.update(sql, md5encrypt, fromUserId);
    }

    @Override
    public Map<String, Object> getPhoto(String userId) {

        String sql = "select id,avatar_path,user_name,nick_name from au_member where id = ?";
        return sqlDao.queryForMap(sql, userId);
    }

    @Override
    public Integer readId(Integer id, Integer smsStatus) {
        String sql = "update user_message_info set sms_status = ? where id = ?";
        return sqlDao.update(sql, smsStatus, id);
    }

}
