package com.rngay.service_socket.service.impl;

import com.riicy.socket.dao.SQLDao;
import com.riicy.socket.service.SysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysMessageServiceImpl implements SysMessageService {

    @Autowired
    private SQLDao sqlDao;

    @Override
    public List<Map<String, Object>> getSysInfo(String userId) {
        return sqlDao.queryForList("select * from sys_message_info where status = 1 and user_id = ?", userId);
    }
}
