package com.rngay.common.jpa.dao;

import com.rngay.common.vo.PageList;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.Map;

public interface Dao extends JdbcOperations {

    <T> int insert(T var1);

    int insert(Map<String, Object> var1, String var2);

    int insert(Class<?> var1, Map<String, Object> var2);

    <T> int[] batchInsert(List<T> list);

    <T> int[][] batchInsert(List<T> list, int batch);

    int update(Object var1);

    int update(Object var1, Condition var2);

    int update(Object var1, boolean isNull);

    int update(Object var1, Condition var2, boolean isNull);

    <T> int[] batchUpdate(List<T> var1);

    <T> int[] batchUpdate(List<T> var1, Condition var2);

    <T> List<T> query(Class<T> var1);

    <T> List<T> query(Class<T> var1, Condition var2);

    List<Map<String, Object>> query(String var1);

    List<Map<String, Object>> query(String var1, Condition var2);

    <T> List<T> query(Class<T> var1, String... fields);

    <T> List<T> query(Class<T> var1, Condition var2, String... fields);

    List<Map<String, Object>> query(String var1, String... fields);

    List<Map<String, Object>> query(String var1, Condition var2, String... fields);

    long count(Class<?> var1);

    long count(String var1);

    long count(Class<?> var1, Condition var2);

    long count(String var1, Condition var2);

    long count(Class<?> var1, String field);

    long count(String var1, String field);

    long count(Class<?> var1, Condition var2, String field);

    long count(String var1, Condition var2, String field);

    int delete(Class<?> var1, long var2);

    int delete(Class<?> var1, Condition var2);

    <T> T findById(Class<T> var1, long var2);

    <T> T find(Class<T> var1, Condition var2);

    Map<String, Object> findById(String var1, long var2);

    Map<String, Object> find(String var1, Condition var2);

    <T> String queryForString(Class<T> var1, Condition var2, String field);

    <T> List<String> queryForList(Class<T> var1, Condition var2, String field);

    <T> T queryForObject(String tableName, Class<T> clazz, Condition cdn, String field);

    <T> List<T> queryForList(String tableName, Class<T> clazz, Condition cdn, String field);

    <T> PageList<T> paginate(Class<T> var1, int pageNumber, int pageSize);

    PageList<Map<String, Object>> paginate(String var1, int pageNumber, int pageSize);

    <T> PageList<T> paginate(Class<T> var1, int pageNumber, int pageSize, Condition var2);

    PageList<Map<String, Object>> paginate(String var1, int pageNumber, int pageSize, Condition var2);

}
