package com.rngay.common.jpa.dao.sql;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.util.Maker;

import java.util.Map;

public interface SqlMake {

    Maker makeQuery(String tableName, long id);

    Maker makeQuery(String tableName);

    Maker makeQuery(Class<?> clazz);

    Maker makeQuery(Class<?> clazz, long id);

    Maker makeQuery(String tableName, Condition cnd);

    Maker makeQuery(Class<?> clazz, Condition cnd);

    Maker makeQuery(String tableName, String sql);

    Maker makeQuery(Class<?> clazz, String sql);

    Maker makeUpdate(Object obj);

    Maker makeUpdate(Object obj, Condition cnd);

    Maker makeDelete(Class<?> clazz, long id);

    Maker makeDelete(Class<?> clazz, Condition cnd);

    Maker makeDelete(String tableName, long id);

    Maker makeDelete(String tableName, Condition cnd);

    Maker makeInsert(String tableName, Map<String, Object> map);

    Maker makeInsert(Class<?> clazz, Map<String, Object> map);

    Maker makeInsert(Object obj);

}
