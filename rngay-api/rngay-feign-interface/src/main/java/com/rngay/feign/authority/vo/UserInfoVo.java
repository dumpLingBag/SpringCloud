package com.rngay.feign.authority.vo;

import com.rngay.feign.authority.RoleDTO;
import com.rngay.feign.user.dto.UaUserDTO;

public class UserInfoVo {

    private UaUserDTO uaUserDTO;
    private RoleDTO roleDTO;

    public UaUserDTO getUaUserDTO() {
        return uaUserDTO;
    }

    public void setUaUserDTO(UaUserDTO uaUserDTO) {
        this.uaUserDTO = uaUserDTO;
    }

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

}
