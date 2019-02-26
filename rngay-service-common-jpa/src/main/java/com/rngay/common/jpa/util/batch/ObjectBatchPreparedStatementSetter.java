package com.rngay.common.jpa.util.batch;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.util.SetPreparedStatement;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ObjectBatchPreparedStatementSetter<T> extends SetPreparedStatement<T> implements BatchPreparedStatementSetter {

    private List<T> list;
    private int it;
    private Condition cdn;

    public ObjectBatchPreparedStatementSetter() {
    }

    public ObjectBatchPreparedStatementSetter(List<T> list) {
        this.list = list;
        this.it = 0;
    }

    public ObjectBatchPreparedStatementSetter(List<T> list, Condition cdn) {
        this.list = list;
        this.it = 0;
        this.cdn = cdn;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
        T t = this.list.get(i);
        int it = setStatement(t, preparedStatement, this.it);
        if (this.cdn != null) {
            setWhere(preparedStatement, this.cdn, it);
        }
        this.it = 0;
    }

    @Override
    public int getBatchSize() {
        return list.size();
    }

}
