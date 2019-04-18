package com.rngay.common.jpa.util;

import java.util.List;

public class Maker {

    private StringBuilder sqlName;

    private List<Object> sqlVal;

    public StringBuilder getSqlName() {
        return sqlName;
    }

    public void setSqlName(StringBuilder sqlName) {
        this.sqlName = sqlName;
    }

    public List<Object> getSqlVal() {
        return sqlVal;
    }

    public void setSqlVal(List<Object> sqlVal) {
        this.sqlVal = sqlVal;
    }
}
