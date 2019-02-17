package com.rngay.common.jpa.dao.sql.impl;

import com.rngay.common.exception.BaseException;
import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.dao.sql.SqlMake;
import com.rngay.common.jpa.util.Maker;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

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
    public Maker makeUpdate(Object obj) {
        return this.makeJoinUpdate(obj, null);
    }

    @Override
    public Maker makeUpdate(Object obj, Condition cnd) {
        return this.makeJoinUpdate(obj, cnd);
    }

    @Override
    public Maker makeDelete(Class<?> clazz, long id) {
        return this.makeDelete(clazz, Cnd.where("id","=", id));
    }

    @Override
    public Maker makeDelete(Class<?> clazz, Condition cnd) {
        return this.makeJoinDelete(tableName(clazz), cnd);
    }

    @Override
    public Maker makeDelete(String tableName, long id) {
        return this.makeJoinDelete(tableName, Cnd.where("id","=", id));
    }

    @Override
    public Maker makeDelete(String tableName, Condition cnd) {
        return this.makeJoinDelete(tableName, cnd);
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

}
