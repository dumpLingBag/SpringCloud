package com.rngay.service_authority.dao.sql;

import com.rngay.common.jpa.dao.Dao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RoleMenuSql {

    @Resource
    private Dao dao;

    public String sqlUrl(String sql) {
        return "SELECT url.url FROM ("+ sql + ") AS m, ua_menu_url AS menuUrl, ua_url AS url "+
                "WHERE m.id = menuUrl.menu_id AND menuUrl.checked = 1 AND url.id = menuUrl.url_id";
    }

    public String roleMenu(Integer roleId) {
        return "SELECT t.id,t.name,t.icon,t.path,t.component,t.keep_alive,t.auth,t.sort,t.pid FROM (" +
                " SELECT m.* FROM ua_role_menu AS rm, ua_menu AS m WHERE rm.role_id = "+ roleId +" AND rm.menu_id = m.id AND rm.checked = 1" +
                " GROUP BY m.id) AS t";
    }

    private String userRole() {
        return "SELECT role_id FROM ua_user_role WHERE checked = 1 AND user_id = ?" +
                " UNION SELECT dr.role_id FROM ua_department_user AS du, ua_department_role AS dr WHERE du.checked = 1" +
                " AND dr.checked = 1 AND dr.department_id = du.department_id AND du.user_id=?";
    }

    public String result(Integer userId) {
        List<Integer> userRoleIds = dao.queryForList(userRole(), Integer.class, userId, userId);
        if (userRoleIds.isEmpty()) {
            return null;
        }
        StringBuilder sqlUserRole = new StringBuilder();
        for (Integer id : userRoleIds) {
            if (sqlUserRole.length() != 0) {
                sqlUserRole.append(" UNION ");
            }
            sqlUserRole.append(roleMenu(id));
        }
        return sqlUserRole.toString();
    }

}
