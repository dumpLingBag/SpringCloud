package com.rngay.common.jpa.dao.sql.impl;

import com.rngay.common.jpa.dao.sql.SqlBuilder;
import com.rngay.common.jpa.dao.sql.SqlType;

import java.util.Arrays;
import java.util.Collection;
/**
* 主要是实现 SQL 头部
* @Author pengcheng
* @Date 2019/3/7
**/
public class RngSqlBuilder implements SqlBuilder {

    @Override
    public StringBuilder query(String tableName, String... fields) {
        String f = Arrays.toString(fields).replace("[", "").replace("]", "");
        f = f.length() == 0 ? "*" : f;
        return new StringBuilder(String.valueOf(SqlType.SELECT)).append(' ').append(f).append(' ').append("FROM").append(' ').append(tableName);
    }

    @Override
    public StringBuilder query(String tableName) {
        return query(tableName, "*");
    }

    @Override
    public StringBuilder count(String tableName, String field) {
        field = field == null || "".equals(field) ? "*" : field;
        return new StringBuilder(String.valueOf(SqlType.SELECT)).append(' ').append("COUNT").append("(").append(field)
                .append(")").append(' ').append("FROM").append(' ').append(tableName);
    }

    @Override
    public StringBuilder count(String tableName) {
        return count(tableName, "*");
    }

    @Override
    public StringBuilder insert(String tableName) {
        return new StringBuilder(String.valueOf(SqlType.INSERT)).append(' ').append("INTO").append(' ').append(tableName).append(' ');
    }

    @Override
    public StringBuilder insert(String tableName, String fields, String value) {
        return insert(tableName).append("(").append(fields).append(")").append(" VALUES ").append("(").append(value).append(")");
    }

    @Override
    public StringBuilder delete(String tableName) {
        return new StringBuilder(String.valueOf(SqlType.DELETE)).append(" FROM ").append(tableName);
    }

    @Override
    public StringBuilder update(String tableName) {
        return new StringBuilder(String.valueOf(SqlType.UPDATE)).append(' ').append(tableName).append(" SET ");
    }

}
