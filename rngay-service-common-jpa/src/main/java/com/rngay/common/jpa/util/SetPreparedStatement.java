package com.rngay.common.jpa.util;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.dao.sql.Criteria;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetPreparedStatement<T> {

    protected int setStatement(T t, PreparedStatement ps, int it) throws SQLException {
        Field[] fields = t.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                boolean b = field.isAnnotationPresent(Id.class);
                if (!b) {
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);

                    Object o = field.get(t);
                    if (o != null && !"".equals(o)) {
                        it = it + 1;
                        ps.setObject(it, o);
                    }

                    field.setAccessible(accessible);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return it;
    }

    protected void setWhere(PreparedStatement ps, Condition cdn, int it) {
        Criteria cri = (Criteria)cdn;
        Maker sql = cri.where().getSql();
        String trim = sql.getSqlName().toString().trim();

    }

    public static void main(String[] args) {
        String a = " name = ? and age = ?";
        System.out.println(a.trim());
    }

}
