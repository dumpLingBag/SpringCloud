package com.rngay.common.jpa.util.batch;

import com.rngay.common.jpa.util.SetPreparedStatement;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ObjectParameterizedPreparedStatementSetter<T> extends SetPreparedStatement<T> implements ParameterizedPreparedStatementSetter<T> {

    private int it;
    private boolean boo = false;

    public ObjectParameterizedPreparedStatementSetter() {
        this.it = 0;
    }

    public ObjectParameterizedPreparedStatementSetter(boolean boo) {
        this.boo = boo;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, T t) throws SQLException {
        setStatement(t, preparedStatement, it, this.boo);
        this.it = 0;
    }

}
