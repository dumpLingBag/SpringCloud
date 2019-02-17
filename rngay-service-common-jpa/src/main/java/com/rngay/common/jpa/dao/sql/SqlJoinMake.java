package com.rngay.common.jpa.dao.sql;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.util.Maker;

public interface SqlJoinMake {

    Maker makeJoinQuery(String tableName, Object cnd);

    Maker makeJoinUpdate(Object obj, Condition cnd);

    Maker makeJoinDelete(String tableName, Condition cnd);

    Maker makeJoinInsert(Object obj);

}
