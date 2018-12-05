package com.rngay.common.jpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository
public class SQLDaoImpl extends JdbcTemplate implements SQLDao {

    @Override
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public int insert(String table, Map<String, Object> map) {
        if (table == null || "".equals(table) || map == null || map.isEmpty())
            return 0;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        update(connection -> {
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            List<Object> list = new ArrayList<>();
            for (String filed : map.keySet()) {
                if (filed != null && !"".equals(filed)) {
                    append(columns, values, filed);
                    list.add(map.get(filed));
                }
            }

            String sql = "insert into "+ table +"("+ columns.toString() +") values ("+ values.toString() +")";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < list.size(); i++) {
                Object x = list.get(i);
                if(x instanceof Integer){
                    statement.setInt(i+1, (Integer) x);
                }else if(x instanceof Long){
                    statement.setLong(i+1, (Long) x);
                }else if(x instanceof Double){
                    statement.setDouble(i+1, (Double) x);
                }else if(x instanceof Float){
                    statement.setFloat(i+1, (Float) x);
                }else if(x instanceof Short){
                    statement.setShort(i+1, (Short) x);
                }else if(x instanceof String){
                    statement.setString(i+1, (String) x);
                }else if(x instanceof Date){
                    statement.setDate(i+1, (java.sql.Date) x);
                }else if(x instanceof Boolean){
                    statement.setBoolean(i+1, (Boolean) x);
                }else if(x instanceof Byte){
                    statement.setByte(i+1, (Byte) x);
                }else{
                    statement.setObject(i+1, x);
                }
            }
            return statement;
        }, keyHolder);
        Number n = keyHolder.getKey();
        if(n==null)return (Integer) map.get("id");
        return keyHolder.getKey().intValue();
    }

    @Override
    public int insert(String table, List<Map<String, Object>> mapList) {
        if (table == null || "".equals(table) || mapList == null || mapList.isEmpty())
            return 0;
        Set<String> keys = new HashSet<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            if (map != null && !map.isEmpty()) {
                keys.addAll(map.keySet());
                resultList.add(map);
            }
        }

        final List<String> keyList = new ArrayList<>(keys);
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (String key : keyList) {
            append(columns, values, key);
        }

        StringBuilder sql = new StringBuilder("insert into "+ table +"("+ columns.toString() +") values ");
        List<Object> paramList = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            if (map != null && !map.isEmpty()) {
                if(!sql.toString().equals("insert into "+ table +"("+ columns.toString() +") values "))
                    sql.append(",");
                sql.append("(").append(values.toString()).append(")");
                for (String key : keyList) {
                    paramList.add(map.get(key));
                }
            }
        }

        return update(sql.toString(), paramList.toArray());
    }

    private void append(StringBuilder columns, StringBuilder values, String key){
        if (columns.length() > 0){
            columns.append(",").append(key);
            values.append(",?");
        } else {
            columns.append(key);
            values.append("?");
        }
    }

}
