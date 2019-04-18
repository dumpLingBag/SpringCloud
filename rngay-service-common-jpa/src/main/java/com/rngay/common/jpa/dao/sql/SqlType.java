package com.rngay.common.jpa.dao.sql;

public enum SqlType {
    SELECT,
    DELETE,
    TRUNCATE,
    UPDATE,
    INSERT,
    CREATE,
    DROP,
    RUN,
    ALTER,
    EXEC,
    CALL,
    OTHER;

    private SqlType() {
    }
}
