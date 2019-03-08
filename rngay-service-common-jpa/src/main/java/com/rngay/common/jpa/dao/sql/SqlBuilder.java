package com.rngay.common.jpa.dao.sql;

public interface SqlBuilder {

    StringBuilder query(String tableName, String... fields);

    StringBuilder query(String tableName);

    StringBuilder count(String tableName, String field);

    StringBuilder count(String tableName);

    StringBuilder insert(String tableName);

    StringBuilder insert(String tableName, String fields, String value);

    StringBuilder delete(String tableName);

    StringBuilder update(String tableName);

}
