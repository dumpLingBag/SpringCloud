package com.rngay.common.jpa.util;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.dao.sql.Criteria;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SetPreparedStatement<T> {

    protected int setStatement(T t, PreparedStatement ps, int it, boolean boo) throws SQLException {
        if (t instanceof Map) {
            Map<?, ?> map = (Map)t;
            for (Map.Entry<?, ?> m : map.entrySet()) {
                if (!".table".equals(m.getKey())) {
                    if (m.getValue() != null && !"".equals(m.getValue())) {
                        ps.setObject(it = it + 1, m.getValue());
                    }
                }
            }
        } else {
            Field[] fields = t.getClass().getDeclaredFields();
            try {
                Object id = null;
                for (Field field : fields) {
                    boolean b = field.isAnnotationPresent(Id.class);
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);

                    Object o = field.get(t);
                    if (!b) {
                        if (o != null && !"".equals(o)) {
                            boolean anEnum = field.getType().isEnum();
                            ps.setObject(it = it + 1, anEnum ? ((Enum) o).name() : o);
                        }
                    } else {
                        id = o;
                    }

                    field.setAccessible(accessible);
                }
                if (boo) {
                    if (id != null && !"".equals(id)) {
                        ps.setObject(it = it + 1, id);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return it;
    }

    /**
     * 好像没什么用，先留着
     * */
    protected void setWhere(PreparedStatement ps, Condition cdn, int it) throws SQLException {
        Criteria cri = (Criteria) cdn;
        Maker sql = cri.where().getSql();
        List<Object> sqlVal = sql.getSqlVal();
        for (Object val : sqlVal) {
            ps.setObject(it = it + 1, val);
        }
    }

    /*private int field(T t, PreparedStatement ps, Field field, int it) throws SQLException {
        try {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            Object o = field.get(t);
            if (o != null && !"".equals(o)) {
                ps.setObject(it = it + 1, o);
            }

            field.setAccessible(accessible);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return it;
    }*/

}
