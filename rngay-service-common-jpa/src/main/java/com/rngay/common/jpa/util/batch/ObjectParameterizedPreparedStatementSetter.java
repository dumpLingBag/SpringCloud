package com.rngay.common.jpa.util.batch;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.util.SetPreparedStatement;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ObjectParameterizedPreparedStatementSetter<T> extends SetPreparedStatement<T> implements ParameterizedPreparedStatementSetter<T> {

    private int it;
    private Condition cdn;

    public ObjectParameterizedPreparedStatementSetter() {
        this.it = 0;
    }

    public ObjectParameterizedPreparedStatementSetter(Condition cdn) {
        this.cdn = cdn;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, T t) throws SQLException {
        int it = setStatement(t, preparedStatement, this.it);
        if (cdn != null) {
            setWhere(preparedStatement, this.cdn, it);
        }
        this.it = 0;
    }

}
