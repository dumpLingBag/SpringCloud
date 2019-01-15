package com.rngay.service_user.dao.impl;

import com.rngay.common.jpa.dao.SqlDao;
import com.rngay.common.vo.PageList;
import com.rngay.feign.user.dto.UAUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;
import com.rngay.service_user.dao.UAUserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UAUserDaoImpl implements UAUserDao {

    @Resource
    private SqlDao sqlDao;

    @Override
    public Map<String, Object> findUserById(Integer id) {
        return sqlDao.queryForMap("select * from ua_user where id = ?", id);
    }

    @Override
    public Map<String, Object> findUser(String account, String password) {
        return sqlDao.queryForMap("select * from ua_user where account = ? and password = ?", account, password);
    }

    @Override
    public int insertUser(Map<String, Object> map) {
        return sqlDao.insert("ua_user", map);
    }

    @Override
    public int updateUser(Map<String, Object> map) {
        return sqlDao.updateMap("ua_user", "id", map);
    }

    @Override
    public PageList<Map<String, Object>> pageList(UAUserPageListDTO pageListDTO) {
        String sql = "select * from ua_user u where 1 = 1";
        List<Object> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(pageListDTO.getAccount())){
            sql += " and u.account like ?";
            list.add("%"+pageListDTO.getAccount()+"%");
        }
        if (StringUtils.isNotEmpty(pageListDTO.getName())){
            sql += " and u.name like ?";
            list.add("%"+pageListDTO.getName()+"%");
        }
        if (pageListDTO.getEnable() != null){
            sql += " and u.enable = ?";
            list.add(pageListDTO.getEnable());
        }
        return sqlDao.pageList(sql, pageListDTO.getCurrentPage(), pageListDTO.getPageSize(), list.toArray());
    }

    @Override
    public int reset(Integer id) {
        return sqlDao.update("update ua_user set password = 123456 where id = ?", id);
    }

    @Override
    public int enable(Integer id, Integer enable) {
        return sqlDao.update("update ua_user set enable = ? where id = ?", enable, id);
    }

    @Override
    public int updatePassword(UpdatePassword password) {
        return sqlDao.update("update up_user set password = ? where id = ?", password.getUserId(), password.getPassword());
    }
}
