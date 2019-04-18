package com.rngay.common.jpa.dao.sql;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.util.Maker;

public interface SqlJoinMake {

    <T> String tableName(Class<T> tClass);

    Maker makeJoinQuery(String tableName, Object cnd, String... fields);

    Maker makeJoinCount(String tableName, Condition cdn, String field);

    Maker makeJoinUpdate(Object obj, Condition cdn, boolean isNull);

    Maker makeJoinDelete(String tableName, Condition cdn, Integer batchNumber);

    Maker makeJoinInsert(Object obj);

    Maker makeJoinPager(String tableName, int pageNumber, int pageSize, Condition cdn);

}
