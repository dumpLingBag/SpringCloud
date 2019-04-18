package com.rngay.common.jpa.util;

import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class ObjectRowMapper<T> implements RowMapper<T> {

    private Class<T> tClass;

    public ObjectRowMapper(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {
        T t = null;

        try {
            t = tClass.newInstance();

            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field: fields) {
                int column = resultSet.findColumn(field.getName());
                if (column > 0) {
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);

                    setFieldVal(t, field, resultSet);

                    field.setAccessible(accessible);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return t;
    }

    private void setFieldVal(T t, Field field, ResultSet rs) throws IllegalAccessException, SQLException {
        Class<?> type = field.getType();
        if (type == int.class || type == Integer.class) {
            field.set(t, rs.getInt(field.getName()));
        } else if (type == String.class) {
            field.set(t, rs.getString(field.getName()));
        } else if (type == Date.class || type == java.sql.Date.class || type == Timestamp.class) {
            field.set(t, rs.getTimestamp(field.getName()));
        } else if (type == boolean.class || type == Boolean.class) {
            field.set(t, rs.getBoolean(field.getName()));
        } else if (type == float.class) {
            field.set(t, rs.getFloat(field.getName()));
        } else if (type == double.class) {
            field.set(t, rs.getDouble(field.getName()));
        } else if (type == BigDecimal.class) {
            field.set(t, rs.getBigDecimal(field.getName()));
        } else if (type == short.class || type == Short.class) {
            field.set(t, field.getShort(field.getName()));
        } else if (type == long.class) {
            field.set(t, rs.getLong(field.getName()));
        }
    }

}
