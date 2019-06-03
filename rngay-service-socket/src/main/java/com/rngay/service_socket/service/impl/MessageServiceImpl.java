package com.rngay.service_socket.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.impl.SqlDao;
import com.rngay.common.vo.PageList;
import com.rngay.feign.socket.dto.ContentDTO;
import com.rngay.service_socket.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private SqlDao sqlDao;

    @Override
    public List<Map<String, Object>> openMessage(String userId) {
        String sqlList = "select uml.*,am.nick_name,am.avatar_path,am.user_name,(select count(id)" +
                " from user_message_info uio where uio.sms_status = 1 and uml.user_info_id = uio.user_info_id ) un_read" +
                " from user_message_list uml inner join au_member am on uml.to_user_id = am.id where uml.from_user_id = ?";
        List<Map<String, Object>> mapList = sqlDao.queryForList(sqlList, userId);
        for (Map<String, Object> map : mapList) {
            String sqlInfo = "select umi.* from user_message_info umi where umi.user_info_id = ? order by umi.create_time asc limit 1";
            List<Map<String, Object>> info = sqlDao.queryForList(sqlInfo, map.get("user_info_id"));
            map.put("userMessage", info);
        }
        return mapList;
    }

    @Override
    public Integer saveMessage(ContentDTO messageInfo) {
        Map<String, Object> fm = sqlDao.queryForMap("select id from user_message_list where from_user_id = ? and to_user_id = ?",
                messageInfo.getFromUserId(), messageInfo.getToUserId());
        if (fm.isEmpty()) {
            Date date = new Date();
            sqlDao.update("insert into user_message_list(from_user_id, to_user_id, user_info_id, create_time, update_time) values(?,?,?,?,?)",
                    messageInfo.getFromUserId(), messageInfo.getToUserId(), messageInfo.getUserInfoId(), date, date);
        }
        Map<String, Object> to = sqlDao.queryForMap("select id from user_message_list where from_user_id = ? and to_user_id = ?",
                messageInfo.getToUserId(), messageInfo.getFromUserId());
        if (to.isEmpty()) {
            Date date = new Date();
            sqlDao.update("insert into user_message_list(from_user_id, to_user_id, user_info_id, create_time, update_time) values(?,?,?,?,?)",
                    messageInfo.getToUserId(), messageInfo.getFromUserId(), messageInfo.getUserInfoId(), date, date);
        }
        return sqlDao.update("insert into user_message_info(user_info_id, from_user_id, to_user_id, content, sms_status, sms_type, create_time) values (?,?,?,?,?,?,?)",
                messageInfo.getUserInfoId(), messageInfo.getFromUserId(), messageInfo.getToUserId(), messageInfo.getContent(), messageInfo.getSmsStatus(), messageInfo.getSmsType().name(), new Date());
    }

    @Override
    public PageList<Map<String, Object>> getUserContent(ContentDTO contentDTO) {
        String sql = "select * from user_message_info where user_info_id = ? order by create_time asc";
        return sqlDao.paginate("user_message_info", contentDTO.getCurrentPage(), contentDTO.getPageSize(), Cnd.where("user_info_id","=",contentDTO.getUserInfoId()));
        //return sqlDao.getPageList(sql, contentDTO.getCurrentPage(), contentDTO.getPageSize(), null, contentDTO.getUserInfoId());
    }

    @Override
    public PageList<Map<String, Object>> getToUserContent(String userInfoId) {
        String sql = "select * from user_message_info where user_info_id = ? order by create_time asc";
        return sqlDao.paginate("user_message_info", 1, 10, Cnd.where("user_info_id","=", userInfoId));
        //return sqlDao.getPageList(sql, 1, 10, null, userInfoId);
    }

}
