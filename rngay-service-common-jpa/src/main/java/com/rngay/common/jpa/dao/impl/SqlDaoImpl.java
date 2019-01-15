package com.rngay.common.jpa.dao.impl;

import com.rngay.common.jpa.dao.SqlDao;
import com.rngay.common.jpa.util.AppendKey;
import com.rngay.common.vo.PageList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository
public class SqlDaoImpl extends JdbcTemplate implements SqlDao {

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
                    AppendKey.append(columns, values, filed);
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
                    java.util.Date d = (java.util.Date) x;
                    statement.setTimestamp(i+1, new Timestamp(d.getTime()));
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
        if(keyHolder.getKey() == null)
            return (Integer) map.get("id");
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
            AppendKey.append(columns, values, key);
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

    @Override
    public int delete(String table, Integer id) {
        return update("delete from "+ table +" where id = ?", id);
    }

    @Override
    public int delete(String table, String column, Object value) {
        return update("delete from "+ table +" where "+ column +" = ?", value);
    }

    @Override
    public int updateMap(String table, String primaryKey, Map<String, Object> map) {
        Map<String, Object> toMap = new HashMap<>(map);
        toMap.remove("create_time");

        if (table == null || primaryKey == null || map.isEmpty())
            return 0;
        int index = 0;
        Object[] array = new Object[toMap.size()];
        StringBuilder setKeyVal = new StringBuilder();
        for (String key : toMap.keySet()) {
            if (!key.equals(primaryKey)) {
                if (setKeyVal.toString().equals("")) {
                    setKeyVal.append(key).append(" = ?");
                } else {
                    setKeyVal.append(",").append(key).append(" = ? ");
                }
                array[index] = map.get(key);
                index++;
            }
        }
        array[index] = map.get(primaryKey);

        return update("update "+ table +" set "+ setKeyVal +" where "+ primaryKey +" = ?", array);
    }

    @Override
    public int updateMap(String table, Map<String, Object> map, String... columns) {
        Map<String, Object> toMap = new HashMap<>(map);
        toMap.remove("create_time");
        toMap.remove("password");

        if (table == null || columns == null || columns.length <= 0 || map.isEmpty())
            return 0;
        Set<String> columnSet = new HashSet<>();
        Collections.addAll(columnSet, columns);

        if (toMap.keySet().containsAll(columnSet)) {
            List<Object> setList = new ArrayList<>();
            List<Object> whereList = new ArrayList<>();
            StringBuilder setString = new StringBuilder();
            StringBuilder whereString = new StringBuilder();
            for (String key : toMap.keySet()) {
                if (columnSet.contains(key)) {
                    if (whereString.length() <= 0) {
                        whereString.append(key).append(" = ? ");
                    } else {
                        whereString.append(" and ").append(key).append(" = ? ");
                    }
                    whereList.add(map.get(key));
                } else {
                    if (setString.length() <= 0) {
                        setString.append(key).append(" = ? ");
                    } else {
                        setString.append(",").append(key).append(" = ? ");
                    }
                    setList.add(map.get(key));
                }
            }
            setList.addAll(whereList);
            if (!setList.isEmpty()) {
                Object[] array = new Object[setList.size()];
                for (int i = 0; i < setList.size(); i++) {
                    array[i] = setList.get(i);
                }
                return update("update "+ table +" set "+ setString +" where "+ whereString, array);
            }
        }
        return 0;
    }

    @Override
    public int updateMapList(String table, String primaryKey, List<Map<String, Object>> mapList) {
        if (table == null || primaryKey == null || mapList == null || mapList.isEmpty())
            return 0;
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            if (map != null && !map.isEmpty()) {
                list.add(map);
            }
        }

        int sum = 0;
        if (!list.isEmpty()) {
            for (Map<String, Object> map : list) {
                sum += updateMap(table, primaryKey, map);
            }
        }

        return sum > 0 ? sum : 0;
    }

    @Override
    public int updateMapList(String table, List<Map<String, Object>> mapList, String... columns) {
        if (table == null || columns == null || columns.length <= 0 || mapList == null || mapList.isEmpty())
            return 0;
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            if (map != null && !map.isEmpty()) {
                list.add(map);
            }
        }

        int sum = 0;
        if (!list.isEmpty()) {
            for (Map<String, Object> map : list) {
                sum += updateMap(table, map, columns);
            }
        }

        return sum > 0 ? sum : 0;
    }

    @Override
    public int insertOrUpdate(String table, String primaryKey, Map<String, Object> map) {
        long count = queryForLong("select count(*) from "+ table +" where "+ primaryKey + " = ?", map.get(primaryKey));
        if (count > 0){
            return updateMap(table, primaryKey, map);
        } else {
            return insert(table, map);
        }
    }

    @Override
    public int insertOrUpdate(String table, Map<String, Object> map, String... columns) {
        int update = updateMap(table, map, columns);
        if (update > 0) return update;
        int insert = insert(table, map);
        if (insert > 0) return insert;
        return 0;
    }

    @Override
    public int queryForInt(String sql, Object... args) throws DataAccessException {
        Integer result = queryForObject(sql, Integer.class, args);
        if(result==null)
            return 0;
        return result;
    }

    @Override
    public int queryForInt(String sql) throws DataAccessException {
        Integer result = queryForObject(sql, Integer.class);
        if(result==null)
            return 0;
        return result;
    }

    @Override
    public long queryForLong(String sql, Object... args) throws DataAccessException {
        Long result = queryForObject(sql, Long.class, args);
        if(result==null)
            return 0;
        return result;
    }

    @Override
    public long queryForLong(String sql) throws DataAccessException {
        Long result = queryForObject(sql, Long.class);
        if(result==null)
            return 0;
        return result;
    }

    @Override
    public Map<String, Object> find(String table, Map<String, Object> colMap) {
        StringBuilder column = new StringBuilder();
        List<Object> value = new ArrayList<>();
        entry(column, value, colMap);
        return queryForMap("select * from "+ table + column, value.toArray());
    }

    @Override
    public Map<String, Object> findById(String table, Integer id) {
        return queryForMap("select * from "+ table + " where id = ?", id);
    }

    @Override
    public List<Map<String, Object>> query(String table, Map<String, Object> colMap) {
        StringBuilder column = new StringBuilder();
        List<Object> value = new ArrayList<>();
        entry(column, value, colMap);
        return queryForList("select * from "+ table + column, value.toArray());
    }

    @Override
    public PageList<Map<String, Object>> pageList(String sql, Integer currentPage, Integer pageSize, Object... args) {
        String countSql = "select count(*) from ("+sql+") as alias_name";
        return pageList(sql, countSql, currentPage, pageSize, args);
    }

    @Override
    public PageList<Map<String, Object>> pageList(String sql, String countSql, Integer currentPage, Integer pageSize, Object... args) {
        if (currentPage == null){
            currentPage = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        sql = sql + " limit " + (currentPage - 1) * pageSize + "," + pageSize;
        long totalSize = queryForLong(countSql, args);
        List<Map<String, Object>> list = queryForList(sql, args);
        PageList<Map<String, Object>> pageList = new PageList<>(currentPage, totalSize, pageSize);
        pageList.setList(list);
        return pageList;
    }

    private void entry(StringBuilder column, List<Object> value, Map<String, Object> colMap){
        for (Map.Entry<String, Object> entry : colMap.entrySet()) {
            if (column.length() > 0){
                column.append(" and ").append(entry.getKey()).append(" = ").append("?");
                value.add(entry.getValue());
            } else {
                column.append(" where ").append(entry.getKey()).append(" = ").append("?");
                value.add(entry.getValue());
            }
        }
    }

}
