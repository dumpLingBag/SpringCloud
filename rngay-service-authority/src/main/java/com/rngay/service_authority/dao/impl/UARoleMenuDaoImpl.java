package com.rngay.service_authority.dao.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.dao.UARoleMenuDao;
import com.rngay.service_authority.model.UAOrgRole;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class UARoleMenuDaoImpl implements UARoleMenuDao {

    @Resource
    private Dao dao;

    @Override
    public List<Map<String, Object>> loadMenuByOrgId(Integer orgId) {
        if (orgId > 0) {
            List<UAOrgRole> roles = dao.query(UAOrgRole.class, Cnd.where("checked", "=", 1).and("org_id", "=", orgId));
            if (roles == null || roles.isEmpty()) {
                return null;
            }
            StringBuilder sql = new StringBuilder();
            for (UAOrgRole role : roles) {
                if (sql.length() != 0) {
                    sql.append(" UNION ");
                }
                sql.append(sql(role.getRoleId()));
            }
            return dao.queryForList(sql.toString());
        }
        return dao.query("ua_menu", Cnd.cri().asc("pid").asc("sort"));
    }

    @Override
    public List<Map<String, Object>> loadMenuByUserId(Integer orgId, Integer userId) {
        return null;
    }

    private String sql(Integer roleId) {
        return "SELECT t.id,t.name,t.url,t.icon,t.path,t.component,t.keep_alive,t.auth,t.sort,t.pid FROM (" +
                " SELECT m.* FROM ua_role_menu AS rm, ua_menu AS m WHERE rm.role_id = "+ roleId +" AND rm.menu_id = m.id AND rm.checked = 1" +
                " GROUP BY m.id) AS t";
    }

}
