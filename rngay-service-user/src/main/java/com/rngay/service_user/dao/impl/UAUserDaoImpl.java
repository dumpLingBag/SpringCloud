package com.rngay.service_user.dao.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.common.jpa.dao.util.cri.SimpleCriteria;
import com.rngay.common.util.CryptUtil;
import com.rngay.common.vo.PageList;
import com.rngay.feign.user.dto.*;
import com.rngay.service_user.dao.UAUserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UAUserDaoImpl implements UAUserDao {

    @Resource
    private Dao dao;

    @Override
    public UAUserDTO findUserById(Integer id) {
        return dao.findById(UAUserDTO.class, id);
    }

    @Override
    public UAUserDTO findUser(String account, String password) {
        return dao.find(UAUserDTO.class, Cnd.where("account","=", account).and("password","=", password));
    }

    @Override
    public UAUserDTO findByAccount(String account) {
        return dao.find(UAUserDTO.class, Cnd.where("account","=", account));
    }

    @Override
    public UAUserDTO findByMobile(String mobile) {
        return dao.find(UAUserDTO.class, Cnd.where("mobile","=", mobile));
    }

    @Override
    public int insertUser(UASaveUserDTO userDTO) {
        return dao.insert(userDTO);
    }

    @Override
    public int updateUser(UAUpdateUserDTO userDTO) {
        return dao.update(userDTO);
    }

    @Override
    public PageList<UAUserDTO> pageList(UAUserPageListDTO pageListDTO) {
        SimpleCriteria cri = new SimpleCriteria();
        if (StringUtils.isNotEmpty(pageListDTO.getAccount())){
            cri.where().and("account","like","%"+pageListDTO.getAccount()+"%");
        }
        if (StringUtils.isNotEmpty(pageListDTO.getName())){
            cri.where().and("name","like","%"+pageListDTO.getName()+"%");
        }
        if (pageListDTO.getEnable() != null){
            cri.where().and("enable","=", pageListDTO.getEnable());
        }
        return dao.paginate(UAUserDTO.class, pageListDTO.getCurrentPage(), pageListDTO.getPageSize(), cri);
    }

    @Override
    public int reset(Integer id) {
        UAUserDTO userDTO = new UAUserDTO();
        userDTO.setId(id);
        userDTO.setPassword(BCrypt.hashpw(CryptUtil.MD5encrypt("123456"), BCrypt.gensalt(12)));
        return dao.update(userDTO);
    }

    @Override
    public int enable(Integer id, Integer enable) {
        UAUserDTO userDTO = new UAUserDTO();
        userDTO.setEnable(enable);
        userDTO.setId(id);
        return dao.update(userDTO);
    }

    @Override
    public int updatePassword(UpdatePassword password) {
        UAUserDTO userDTO = new UAUserDTO();
        userDTO.setId(password.getUserId());
        userDTO.setPassword(password.getPassword());
        return dao.update(userDTO);
    }

    @Override
    public int delete(Integer id) {
        return dao.delete(UAUserDTO.class, id);
    }
}
