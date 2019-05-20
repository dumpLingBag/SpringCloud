package com.rngay.service_authority.dao.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.dao.UAUserRoleDao;
import com.rngay.service_authority.dao.sql.RoleMenuSql;
import com.rngay.service_authority.model.UAOrgRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleDaoImpl implements UAUserRoleDao {

    @Autowired
    private Dao dao;
    @Autowired
    private RoleMenuSql roleMenuSql;

    @Override
    public List<String> loadUrlByOrgId(Integer orgId) {
        if (orgId != null && orgId > 0) {
            List<UAOrgRole> orgRoles = dao.query(UAOrgRole.class, Cnd.where("checked", "=", 1).and("org_id", "=", orgId));
            if (orgRoles == null || orgRoles.isEmpty()) {
                return null;
            }
            StringBuilder sql = new StringBuilder();
            for (UAOrgRole orgRole : orgRoles) {
                if (sql.length() != 0) {
                    sql.append(" UNION ");
                }
                sql.append(roleMenuSql.roleMenu(orgRole.getRoleId()));
            }
            return dao.queryForList(roleMenuSql.sqlUrl(sql.toString()), String.class);
        }
        return dao.queryForList("SELECT url FROM ua_url", String.class);
    }

    @Override
    public List<String> loadUrlByUserId(Integer orgId, Integer userId) {
        if (orgId != null && orgId > 0) {
            List<UAOrgRole> orgRoles = dao.query(UAOrgRole.class, Cnd.where("checked", "=", 1).and("org_id", "=", orgId));
            if (orgRoles == null || orgRoles.isEmpty()) {
                return null;
            }
            StringBuilder sql = new StringBuilder();
            for (UAOrgRole orgRole : orgRoles) {
                if (sql.length() != 0) {
                    sql.append(" UNION ");
                }
                sql.append(roleMenuSql.roleMenu(orgRole.getRoleId()));
            }
            String finalSql = "SELECT sm.* FROM (" + sql.toString() + " AS pm JOIN (" + roleMenuSql.result(userId) + " AS sm ON sm.id = pm.id";
            return dao.queryForList(roleMenuSql.sqlUrl(finalSql), String.class);
        }
        return dao.queryForList(roleMenuSql.sqlUrl(roleMenuSql.result(userId)), String.class);
    }

}
