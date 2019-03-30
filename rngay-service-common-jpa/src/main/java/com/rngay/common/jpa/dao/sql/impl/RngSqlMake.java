package com.rngay.common.jpa.dao.sql.impl;

import com.rngay.common.exception.BaseException;
import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.dao.sql.SqlMake;
import com.rngay.common.jpa.util.Maker;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
* 调用具体 SQL 实现
* @Author pengcheng
* @Date 2019/3/7
**/
@Component
public class RngSqlMake extends RngSqlJoinMake implements SqlMake {

    @Override
    public Maker makeQuery(String tableName, long id) {
        return this.makeJoinQuery(tableName, Cnd.where("id","=", id));
    }

    @Override
    public Maker makeQuery(String tableName) {
        return this.makeJoinQuery(tableName, null);
    }

    @Override
    public Maker makeQuery(Class<?> clazz) {
        return this.makeJoinQuery(tableName(clazz), null);
    }

    @Override
    public Maker makeQuery(Class<?> clazz, long id) {
        return this.makeQuery(clazz, Cnd.where("id","=", id));
    }

    @Override
    public Maker makeQuery(String tableName, Condition cnd) {
        return this.makeJoinQuery(tableName, cnd);
    }

    @Override
    public Maker makeQuery(Class<?> clazz, Condition cnd) {
        return this.makeJoinQuery(tableName(clazz), cnd);
    }

    @Override
    public Maker makeQuery(String tableName, String sql) {
        return this.makeJoinQuery(tableName, sql);
    }

    @Override
    public Maker makeQuery(Class<?> clazz, String sql) {
        return this.makeQuery(tableName(clazz), sql);
    }

    @Override
    public Maker makeQuery(String tableName, long id, String... fields) {
        return this.makeJoinQuery(tableName, id, fields);
    }

    @Override
    public Maker makeQuery(String tableName, String... fields) {
        return this.makeJoinQuery(tableName, null, fields);
    }

    @Override
    public Maker makeQuery(Class<?> clazz, String... fields) {
        return this.makeJoinQuery(tableName(clazz), null, fields);
    }

    @Override
    public Maker makeQuery(Class<?> clazz, long id, String... fields) {
        return this.makeJoinQuery(tableName(clazz), id, fields);
    }

    @Override
    public Maker makeQuery(String tableName, Condition cdn, String... fields) {
        return this.makeJoinQuery(tableName, cdn, fields);
    }

    @Override
    public Maker makeQuery(Class<?> clazz, Condition cdn, String... fields) {
        return this.makeJoinQuery(tableName(clazz), cdn, fields);
    }

    @Override
    public Maker makeQuery(String tableName, String sql, String... fields) {
        return this.makeJoinQuery(tableName, sql, fields);
    }

    @Override
    public Maker makeQuery(Class<?> clazz, String sql, String... fields) {
        return this.makeJoinQuery(tableName(clazz), sql, fields);
    }

    @Override
    public Maker makeCount(Class<?> clazz) {
        return this.makeJoinCount(tableName(clazz), null, null);
    }

    @Override
    public Maker makeCount(String tableName) {
        return this.makeJoinCount(tableName, null, null);
    }

    @Override
    public Maker makeCount(Class<?> clazz, Condition cdn) {
        return this.makeJoinCount(tableName(clazz), cdn, null);
    }

    @Override
    public Maker makeCount(String tableName, Condition cdn) {
        return this.makeJoinCount(tableName, cdn, null);
    }

    @Override
    public Maker makeCount(Class<?> clazz, String field) {
        return this.makeJoinCount(tableName(clazz),null, field);
    }

    @Override
    public Maker makeCount(String tableName, String field) {
        return this.makeJoinCount(tableName, null, field);
    }

    @Override
    public Maker makeCount(Class<?> clazz, Condition cdn, String field) {
        return this.makeJoinCount(tableName(clazz), cdn, field);
    }

    @Override
    public Maker makeCount(String tableName, Condition cdn, String field) {
        return this.makeJoinCount(tableName, cdn, field);
    }

    @Override
    public Maker makeUpdate(Object obj, boolean isNUll) {
        return this.makeJoinUpdate(obj, null, isNUll);
    }

    @Override
    public Maker makeUpdate(Object obj, Condition cnd, boolean isNull) {
        return this.makeJoinUpdate(obj, cnd, isNull);
    }

    @Override
    public Maker makeUpdate(Map<String, Object> map, String tableName, boolean isNull) {
        if (null == tableName || "".equals(tableName)) {
            throw new BaseException(500, "不存在数据库表!");
        }

        map.put(".table", tableName);
        return this.makeJoinUpdate(map, null, isNull);
    }

    @Override
    public Maker makeUpdate(Map<String, Object> map, String tableName, Condition cdn, boolean isNull) {
        if (null == tableName || "".equals(tableName)) {
            throw new BaseException(500, "不存在数据库表!");
        }

        map.put(".table", tableName);
        return this.makeJoinUpdate(map, cdn, isNull);
    }

    @Override
    public Maker makeDelete(Class<?> clazz, long id) {
        return this.makeDelete(clazz, Cnd.where("id","=", id));
    }

    @Override
    public Maker makeDelete(Class<?> clazz, Condition cnd) {
        return this.makeJoinDelete(tableName(clazz), cnd, null);
    }

    @Override
    public Maker makeDelete(String tableName, long id) {
        return this.makeJoinDelete(tableName, Cnd.where("id","=", id), null);
    }

    @Override
    public Maker makeDelete(String tableName, Condition cnd) {
        return this.makeJoinDelete(tableName, cnd, null);
    }

    @Override
    public Maker makeInsert(String tableName, Map<String, Object> map) {
        if (null == tableName || "".equals(tableName)) {
            throw new BaseException(500, "不存在数据库表!");
        }

        map.put(".table", tableName);
        return this.makeJoinInsert(map);
    }

    @Override
    public Maker makeInsert(Class<?> clazz, Map<String, Object> map) {
        String tableName = tableName(clazz);
        return this.makeInsert(tableName, map);
    }

    @Override
    public Maker makeInsert(Object obj) {
        return this.makeJoinInsert(obj);
    }

    @Override
    public Maker makePager(String tableName, int pageNumber, int pageSize) {
        return this.makeJoinPager(tableName, pageNumber, pageSize, null);
    }

    @Override
    public Maker makePager(String tableName, int pageNumber, int pageSize, Condition cdn) {
        return this.makeJoinPager(tableName, pageNumber, pageSize, cdn);
    }

    @Override
    public Maker makePager(Class<?> clazz, int pageNumber, int pageSize) {
        return this.makeJoinPager(tableName(clazz), pageNumber, pageSize, null);
    }

    @Override
    public Maker makePager(Class<?> clazz, int pageNumber, int pageSize, Condition cdn) {
        return this.makeJoinPager(tableName(clazz), pageNumber, pageSize, cdn);
    }

}