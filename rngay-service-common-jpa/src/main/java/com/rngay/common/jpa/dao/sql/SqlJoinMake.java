package com.rngay.common.jpa.dao.sql;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.util.Maker;

public interface SqlJoinMake {

    Maker makeJoinQuery(String tableName, Object cnd);

    Maker makeJoinCount(String tableName, Condition cdn);

    Maker makeJoinUpdate(Object obj, Condition cdn);

    Maker makeJoinDelete(String tableName, Condition cdn);

    Maker makeJoinInsert(Object obj);

    Maker makeJoinPager(String tableName, int pageNumber, int pageSize, Condition cdn);

}
