package com.rngay.common.jpa.dao.sql;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.util.Maker;

import java.util.Map;

public interface SqlMake {

    Maker makeQuery(String tableName, long id);

    Maker makeQuery(String tableName);

    Maker makeQuery(Class<?> clazz);

    Maker makeQuery(Class<?> clazz, long id);

    Maker makeQuery(String tableName, Condition cdn);

    Maker makeQuery(Class<?> clazz, Condition cdn);

    Maker makeQuery(String tableName, String sql);

    Maker makeQuery(Class<?> clazz, String sql);

    Maker makeQuery(String tableName, long id, String... fields);

    Maker makeQuery(String tableName, String... fields);

    Maker makeQuery(Class<?> clazz, String... fields);

    Maker makeQuery(Class<?> clazz, long id, String... fields);

    Maker makeQuery(String tableName, Condition cdn, String... fields);

    Maker makeQuery(Class<?> clazz, Condition cdn, String... fields);

    Maker makeQuery(String tableName, String sql, String... fields);

    Maker makeQuery(Class<?> clazz, String sql, String... fields);

    Maker makeCount(Class<?> clazz);

    Maker makeCount(String tableName);

    Maker makeCount(Class<?> clazz, Condition cdn);

    Maker makeCount(String tableName, Condition cdn);

    Maker makeCount(Class<?> clazz, String field);

    Maker makeCount(String tableName, String field);

    Maker makeCount(Class<?> clazz, Condition cdn, String field);

    Maker makeCount(String tableName, Condition cdn, String field);

    Maker makeUpdate(Object obj);

    Maker makeUpdate(Object obj, Condition cdn);

    Maker makeDelete(Class<?> clazz, long id);

    Maker makeDelete(Class<?> clazz, Condition cdn);

    Maker makeDelete(String tableName, long id);

    Maker makeDelete(String tableName, Condition cdn);

    Maker makeInsert(String tableName, Map<String, Object> map);

    Maker makeInsert(Class<?> clazz, Map<String, Object> map);

    Maker makeInsert(Object obj);

    Maker makePager(String tableName, int pageNumber, int pageSize);
    
    Maker makePager(String tableName, int pageNumber, int pageSize, Condition cdn);

    Maker makePager(Class<?> clazz, int pageNumber, int pageSize);

    Maker makePager(Class<?> clazz, int pageNumber, int pageSize, Condition cdn);

}
