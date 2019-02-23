package com.rngay.common.jpa.util;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ObjectBatchPreparedStatementSetter<T> implements BatchPreparedStatementSetter {

    private List<T> list;
    private static int it;

    public ObjectBatchPreparedStatementSetter() {
    }

    public ObjectBatchPreparedStatementSetter(List<T> list) {
        this.list = list;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
        preparedStatement.setObject(it++, list.get(i));
    }

    @Override
    public int getBatchSize() {
        return list.size();
    }

}
