package com.rngay.service_authority.dao.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.dao.UARoleMenuDao;
import com.rngay.service_authority.dao.sql.RoleMenuSql;
import com.rngay.service_authority.model.UAOrgRole;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class RoleMenuDaoImpl implements UARoleMenuDao {

    @Resource
    private Dao dao;
    @Resource
    private RoleMenuSql roleMenuSql;

    @Override
    public List<Map<String, Object>> loadMenuByOrgId(Integer orgId) {
        if (orgId != null && orgId > 0) {
            List<UAOrgRole> roles = dao.query(UAOrgRole.class, Cnd.where("checked", "=", 1).and("org_id", "=", orgId));
            if (roles == null || roles.isEmpty()) {
                return null;
            }
            StringBuilder sql = new StringBuilder();
            for (UAOrgRole role : roles) {
                if (sql.length() != 0) {
                    sql.append(" UNION ");
                }
                sql.append(roleMenuSql.sql(role.getRoleId()));
            }
            return dao.queryForList(sql.toString());
        }
        return dao.query("ua_menu", Cnd.cri().asc("pid").asc("sort"));
    }

    @Override
    public List<Map<String, Object>> loadMenuByUserId(Integer orgId, Integer userId) {
        if (orgId != null && orgId > 0) {
            List<UAOrgRole> orgRoles = dao.query(UAOrgRole.class, Cnd.where("checked", "=", 1).and("org_id", "=", orgId));
            if (orgRoles == null || orgRoles.isEmpty()) {
                return null;
            }
            StringBuilder sqlOrgRole = new StringBuilder();
            for (UAOrgRole role : orgRoles) {
                if (sqlOrgRole.length() != 0) {
                    sqlOrgRole.append(" UNION ");
                }
                sqlOrgRole.append(roleMenuSql.sql(role.getRoleId()));
            }

            String finalSql = "SELECT sm.* FROM ("+ sqlOrgRole.toString() + " AS pm JOIN ("+ roleMenuSql.result(userId) + " AS sm ON sm.id = pm.id";
            return dao.queryForList(finalSql);
        }

        String result = roleMenuSql.result(userId);
        if (result != null) {
            return dao.queryForList(result);
        }
        return null;
    }

}