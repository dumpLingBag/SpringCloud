package com.rngay.common.jpa.dao.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.common.jpa.dao.sql.SqlMake;
import com.rngay.common.jpa.dao.sql.impl.RngSqlMake;
import com.rngay.common.jpa.util.Maker;
import com.rngay.common.jpa.util.batch.ObjectBatchPreparedStatementSetter;
import com.rngay.common.jpa.util.batch.ObjectParameterizedPreparedStatementSetter;
import com.rngay.common.vo.PageList;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.Id;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class SqlDao extends JdbcTemplate implements Dao {

    private SqlMake sqlMake;

    public SqlDao() {
        this.sqlMake = new RngSqlMake();
    }

    @Override
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public <T> int insert(T var1) {
        Maker maker = sqlMake.makeInsert(var1);
        return insert(maker);
    }

    @Override
    public int insert(Map<String, Object> var1, String var2) {
        Maker maker = sqlMake.makeInsert(var2, var1);
        return insert(maker);
    }

    @Override
    public int insert(Class<?> var1, Map<String, Object> var2) {
        Maker maker = sqlMake.makeInsert(var1, var2);
        return insert(maker);
    }

    private int insert(Maker maker) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        update(connection -> {
            PreparedStatement statement = connection.prepareStatement(maker.getSqlName().toString(), Statement.RETURN_GENERATED_KEYS);
            List<Object> sqlVal = maker.getSqlVal();
            int i = 0;
            for (Object aSqlVal : sqlVal) {
                statement.setObject(i = i + 1, aSqlVal);
            }
            return statement;
        }, keyHolder);
        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : 0;
    }

    @Override
    public <T> int[] batchInsert(List<T> list) {
        Maker maker = sqlMake.makeInsert(list.get(0));
        return batchUpdate(maker.getSqlName().toString(), new ObjectBatchPreparedStatementSetter<>(list));
    }

    @Override
    public int[] batchInsert(List<Map<String, Object>> list, String tableName) {
        Maker maker = sqlMake.makeInsert(tableName, list.get(0));
        return batchUpdate(maker.getSqlName().toString(), new ObjectBatchPreparedStatementSetter<>(list));
    }

    @Override
    public <T> int[][] batchInsert(List<T> list, int batchSize) {
        Maker maker = sqlMake.makeInsert(list.get(0));
        return batchUpdate(maker.getSqlName().toString(), list, batchSize, new ObjectParameterizedPreparedStatementSetter<>());
    }

    @Override
    public int[][] batchInsert(List<Map<String, Object>> list, String tableName, int batchSize) {
        Maker maker = sqlMake.makeInsert(tableName, list.get(0));
        return batchUpdate(maker.getSqlName().toString(), list, batchSize, new ObjectParameterizedPreparedStatementSetter<>());
    }

    @Override
    public <T> int insertOrUpdate(T var1) {
        if (var1 == null) {
            return 0;
        } else {
            try {
                Field id = var1.getClass().getDeclaredField("id");
                if (id != null) {
                    Object o = id.get(var1);
                    if (o != null && !"".equals(o)) {
                        boolean b = id.isAnnotationPresent(Id.class);
                        if (b) {
                            Maker maker = sqlMake.makeUpdate(var1, false);
                            return update(maker.getSqlName().toString(), toArray(maker.getSqlVal()));
                        } else {
                            return 0;
                        }
                    } else {
                        Maker maker = sqlMake.makeInsert(var1);
                        return insert(maker);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int insertOrUpdate(List<Map<String, Object>> var1, String tableName) {
        if (var1.isEmpty()) {
            return 0;
        } else {
            int i = 0;
            List<Map<String, Object>> addList = new ArrayList<>();
            for (Map<String, Object> map : var1) {
                Maker maker = sqlMake.makeUpdate(map, tableName, Cnd.where("id", "=", map.get("id")),false);
                int id = update(maker.getSqlName().toString(), maker.getSqlVal().toArray());
                if (id <= 0) {
                    addList.add(map);
                } else {
                    i += id;
                }
            }
            if (!addList.isEmpty()) {
                Maker maker = sqlMake.makeInsert(tableName, addList.get(0));
                batchUpdate(maker.getSqlName().toString(), new ObjectBatchPreparedStatementSetter<>(addList));
                i += addList.size();
            }
            return i;
        }
    }

    @Override
    public <T> int insertOrUpdate(List<T> var1) {
        if (var1.isEmpty()) {
            return 0;
        } else {

        }
        return 0;
    }

    @Override
    public int update(Object var1) {
        return this.update(var1, false);
    }

    @Override
    public int update(Object var1, Condition var2) {
        return this.update(var1, var2, false);
    }

    @Override
    public int update(Object var1, boolean isNull) {
        Maker maker = sqlMake.makeUpdate(var1, isNull);
        return update(maker.getSqlName().toString(), toArray(maker.getSqlVal()));
    }

    @Override
    public int update(Object var1, Condition var2, boolean isNull) {
        Maker maker = sqlMake.makeUpdate(var1, var2, isNull);
        return update(maker.getSqlName().toString(), toArray(maker.getSqlVal()));
    }

    /**
     * 批量更新只支持接收实体集合按id实现批量更新
     * */
    @Override
    public <T> int[] batchUpdate(List<T> var1) {
        Maker maker = sqlMake.makeUpdate(var1.get(0), false);
        return batchUpdate(maker.getSqlName().toString(), new ObjectBatchPreparedStatementSetter<>(var1,true));
    }

    @Override
    public <T> int[][] batchUpdate(List<T> var1, int batchSize) {
        Maker maker = sqlMake.makeUpdate(var1.get(0), false);
        return batchUpdate(maker.getSqlName().toString(), var1, batchSize, new ObjectParameterizedPreparedStatementSetter<>(true));
    }

    @Override
    public <T> List<T> query(Class<T> var1) {
        return query(var1, "");
    }

    @Override
    public <T> List<T> query(Class<T> var1, Condition var2) {
        return query(var1, var2, "");
    }

    @Override
    public List<Map<String, Object>> query(String var1) {
        return query(var1, "");
    }

    @Override
    public List<Map<String, Object>> query(String var1, Condition var2) {
        return query(var1, var2, "");
    }

    @Override
    public <T> List<T> query(Class<T> var1, String... fields) {
        Maker maker = sqlMake.makeQuery(var1, fields);
        return query(maker.getSqlName().toString(), BeanPropertyRowMapper.newInstance(var1), toArray(maker.getSqlVal()));
    }

    @Override
    public <T> List<T> query(Class<T> var1, Condition var2, String... fields) {
        Maker maker = sqlMake.makeQuery(var1, var2, fields);
        return query(maker.getSqlName().toString(), BeanPropertyRowMapper.newInstance(var1), toArray(maker.getSqlVal()));
    }

    @Override
    public List<Map<String, Object>> query(String var1, String... fields) {
        Maker maker = sqlMake.makeQuery(var1, fields);
        return queryForList(maker.getSqlName().toString(), toArray(maker.getSqlVal()));
    }

    @Override
    public List<Map<String, Object>> query(String var1, Condition var2, String... fields) {
        Maker maker = sqlMake.makeQuery(var1, var2, fields);
        return queryForList(maker.getSqlName().toString(), toArray(maker.getSqlVal()));
    }

    @Override
    public long count(Class<?> var1) {
        return this.count(var1, "");
    }

    @Override
    public long count(String var1) {
        return this.count(var1, "");
    }

    @Override
    public long count(Class<?> var1, Condition var2) {
        return this.count(var1, var2, "");
    }

    @Override
    public long count(String var1, Condition var2) {
        return this.count(var1, var2, "");
    }

    @Override
    public long count(Class<?> var1, String field) {
        Maker maker = sqlMake.makeCount(var1, field);
        Long count = queryForObject(maker.getSqlName().toString(), long.class);
        return count != null && count > 0 ? count : 0;
    }

    @Override
    public long count(String var1, String field) {
        Maker maker = sqlMake.makeCount(var1, field);
        Long count = queryForObject(maker.getSqlName().toString(), long.class);
        return count != null && count > 0 ? count : 0;
    }

    @Override
    public long count(Class<?> var1, Condition var2, String field) {
        Maker maker = sqlMake.makeCount(var1, var2, field);
        Long count = queryForObject(maker.getSqlName().toString(), long.class, toArray(maker.getSqlVal()));
        return count != null && count > 0 ? count : 0;
    }

    @Override
    public long count(String var1, Condition var2, String field) {
        Maker maker = sqlMake.makeCount(var1, var2, field);
        Long count = queryForObject(maker.getSqlName().toString(), long.class, toArray(maker.getSqlVal()));
        return count != null && count > 0 ? count : 0;
    }

    @Override
    public int delete(Class<?> var1, long var2) {
        Maker maker = sqlMake.makeDelete(var1, var2);
        return update(maker.getSqlName().toString(), toArray(maker.getSqlVal()));
    }

    @Override
    public int delete(Class<?> var1, Condition var2) {
        Maker maker = sqlMake.makeDelete(var1, var2);
        return update(maker.getSqlName().toString(), toArray(maker.getSqlVal()));
    }

    @Override
    public <T> int[] batchDelete(List<T> var1) {
        return new int[0];
    }

    @Override
    public <T> T findById(Class<T> var1, long var2) {
        Maker maker = sqlMake.makeQuery(var1, var2);
        List<T> query = query(maker.getSqlName().toString(), BeanPropertyRowMapper.newInstance(var1), toArray(maker.getSqlVal()));
        return query.size() > 0 ? query.get(0) : null;
    }

    @Override
    public <T> T find(Class<T> var1, Condition var2) {
        Maker maker = sqlMake.makeQuery(var1, var2);
        List<T> query = query(maker.getSqlName().toString(), BeanPropertyRowMapper.newInstance(var1), toArray(maker.getSqlVal()));
        return query.size() > 0 ? query.get(0) : null;
    }

    @Override
    public Map<String, Object> findById(String var1, long var2) {
        Maker maker = sqlMake.makeQuery(var1, var2);
        return queryForMap(maker.getSqlName().toString(), toArray(maker.getSqlVal()));
    }

    @Override
    public Map<String, Object> find(String var1, Condition var2) {
        Maker maker = sqlMake.makeQuery(var1, var2);
        return queryForMap(maker.getSqlName().toString(), toArray(maker.getSqlVal()));
    }

    @Override
    public <T> String queryForString(Class<T> var1, Condition var2, String field) {
        Maker maker = sqlMake.makeQuery(var1, var2, field);
        List<String> list = queryForList(maker.getSqlName().toString(), String.class, toArray(maker.getSqlVal()));
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public <T> List<String> queryForList(Class<T> var1, Condition var2, String field) {
        Maker maker = sqlMake.makeQuery(var1, var2, field);
        return queryForList(maker.getSqlName().toString(), String.class, toArray(maker.getSqlVal()));
    }

    @Override
    public <T> T queryForObject(String tableName, Class<T> clazz, Condition cdn, String field) {
        Maker maker = sqlMake.makeQuery(tableName, cdn, field);
        List<T> list = queryForList(maker.getSqlName().toString(), clazz, toArray(maker.getSqlVal()));
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public <T> List<T> queryForList(String tableName, Class<T> clazz, Condition cdn, String field) {
        Maker maker = sqlMake.makeQuery(tableName, cdn, field);
        return queryForList(maker.getSqlName().toString(), clazz, toArray(maker.getSqlVal()));
    }


    @Override
    public <T> PageList<T> paginate(Class<T> var1, int pageNumber, int pageSize) {
        long totalSize = this.count(var1);
        if (totalSize <= 0) {
            return null;
        }

        int totalPage = totalPage(totalSize, pageSize);
        pageNumber = pageNumber > totalPage ? 1 : pageNumber;

        Maker pager = sqlMake.makePager(var1, pageNumber, pageSize);
        List<T> list = query(pager.getSqlName().toString(), BeanPropertyRowMapper.newInstance(var1));

        PageList<T> pageList = new PageList<>(pageNumber, totalSize, pageSize);
        pageList.setList(list);

        return pageList;
    }

    @Override
    public PageList<Map<String, Object>> paginate(String var1, int pageNumber, int pageSize) {
        long totalSize = this.count(var1);
        if (totalSize <= 0) {
            return null;
        }

        int totalPage = totalPage(totalSize, pageSize);
        pageNumber = pageNumber > totalPage ? 1 : pageNumber;

        Maker pager = sqlMake.makePager(var1, pageNumber, pageSize);
        List<Map<String, Object>> list = queryForList(pager.getSqlName().toString());

        PageList<Map<String, Object>> pageList = new PageList<>(pageNumber, totalSize, totalPage);
        pageList.setList(list);

        return pageList;
    }

    @Override
    public <T> PageList<T> paginate(Class<T> var1, int pageNumber, int pageSize, Condition var2) {
        long totalSize = this.count(var1, var2);
        if (totalSize <= 0) {
            return null;
        }

        int totalPage = totalPage(totalSize, pageSize);
        pageNumber = pageNumber > totalPage ? 1 : pageNumber;

        Maker pager = sqlMake.makePager(var1, pageNumber, pageSize, var2);
        
        List<T> list = query(pager.getSqlName().toString(), BeanPropertyRowMapper.newInstance(var1), toArray(pager.getSqlVal()));

        PageList<T> pageList = new PageList<>(pageNumber, totalSize, pageSize);
        pageList.setList(list);

        return pageList;
    }

    @Override
    public PageList<Map<String, Object>> paginate(String var1, int pageNumber, int pageSize, Condition var2) {
        long totalSize = this.count(var1, var2);
        if (totalSize <= 0) {
            return null;
        }

        int totalPage = totalPage(totalSize, pageSize);
        pageNumber = pageNumber > totalPage ? 1 : pageNumber;

        Maker pager = sqlMake.makePager(var1, pageNumber, pageSize);
        List<Map<String, Object>> list = queryForList(pager.getSqlName().toString(), toArray(pager.getSqlVal()));

        PageList<Map<String, Object>> pageList = new PageList<>(pageNumber, totalSize, pageSize);
        pageList.setList(list);

        return pageList;
    }

    private int totalPage(long totalSize, int pageSize) {
        int totalPage;

        if (totalSize % pageSize == 0) {
            totalPage = (int) (totalSize / pageSize);
        } else {
            totalPage = (int) (totalSize / pageSize) + 1;
        }

        return totalPage;
    }

    private Object[] toArray(List<Object> list) {
        return list != null && !list.isEmpty() ? list.toArray() : null;
    }

}
