package com.rngay.common.jpa.util;

import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ObjectParameterizedPreparedStatementSetter<T> implements ParameterizedPreparedStatementSetter<T> {

    private T t;

    public ObjectParameterizedPreparedStatementSetter(T t) {
        this.t = t;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, T t) throws SQLException {

    }

}
