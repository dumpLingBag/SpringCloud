package com.rngay.common.jpa.dao;

import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.Map;

public interface Dao extends JdbcOperations {

    <T> int insert(T var1);

    int insert(Map<String, Object> var1, String var2);

    int insert(Class<?> var1, Map<String, Object> var2);

    int update(Object var1);

    int update(Object var1, Condition var2);

    <T> List<T> query(Class<T> var1);

    <T> List<T> query(Class<T> var1, Condition var2);

    List<Map<String, Object>> query(String var1);

    List<Map<String, Object>> query(String var1, Condition var2);

    int delete(Class<?> var1, long var2);

    int delete(Class<?> var1, Condition var2);

    <T> T findById(Class<T> var1, long var2);

    <T> T find(Class<T> var1, Condition var2);

    Map<String, Object> findById(String var1, long var2);

    Map<String, Object> find(String var1, Condition var2);

    <T> Page<T> paginate(Class<T> var1, int currentPage, int pageSize, Condition var2);

}
