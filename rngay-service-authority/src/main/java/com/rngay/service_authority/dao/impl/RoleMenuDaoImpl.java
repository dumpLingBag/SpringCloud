package com.rngay.service_authority.dao.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.dao.UARoleMenuDao;
import com.rngay.service_authority.dao.sql.RoleMenuSql;
import com.rngay.service_authority.model.UAOrgRole;
import com.rngay.service_authority.model.UARole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RoleMenuDaoImpl implements UARoleMenuDao {

    @Autowired
    private Dao dao;
    @Autowired
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
                sql.append(roleMenuSql.roleMenu(role.getRoleId()));
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
                sqlOrgRole.append(roleMenuSql.roleMenu(role.getRoleId()));
            }

            String finalSql = "SELECT sm.* FROM (" + sqlOrgRole.toString() + " AS pm JOIN (" + roleMenuSql.result(userId) + " AS sm ON sm.id = pm.id";
            return dao.queryForList(finalSql);
        }

        return dao.queryForList(roleMenuSql.result(userId));
    }

    @Override
    public List<Map<String, Object>> loadMenuByRoleId(Integer roleId) {
        if (roleId == null) return null;

        UARole role = dao.findById(UARole.class, roleId);
        if (role.getOrgId() > 0) {
            List<UAOrgRole> roles = dao.query(UAOrgRole.class, Cnd.where("checked", "=", 1).and("org_id", "=", role.getOrgId()));
            if (roles != null && !roles.isEmpty()) {
                StringBuilder sqlRole = new StringBuilder();
                for (UAOrgRole orgRole : roles) {
                    if (sqlRole.length() != 0) {
                        sqlRole.append(" UNION ");
                    }
                    sqlRole.append(roleMenuSql.roleMenu(orgRole.getRoleId()));
                }

                String finalSql = "SELECT sm.* FROM (" + sqlRole.toString() + ") AS pm JOIN (" + roleMenuSql.roleMenu(roleId) + ") AS sm ON sm.id = pm.id ORDER BY sm.pid, sm.sort";
                return dao.queryForList(finalSql);
            }
        } else {
            return null;
        }

        String sql = "SELECT r_t.* FROM (" + roleMenuSql.roleMenu(roleId) + " r_t ORDER BY r_t.pid, r_t.sort";
        return dao.queryForList(sql);
    }

}
