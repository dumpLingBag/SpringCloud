package com.rngay.common.jpa.dao;

import com.rngay.common.vo.PageList;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.Map;

public interface SqlDao extends JdbcOperations {

    /**
    * table 表中添加单条数据，字段名为 map 的 key，字段值为 map 的 value
    * @Author: pengcheng
    * @Date: 2018/12/5
    */
    int insert(String table, Map<String, Object> map);

    /**
    * table 表中批量添加数据，mapList 中一个 map 为一条记录，字段名为 map 的 key，字段值为 map 的 value
    * @Author: pengcheng
    * @Date: 2018/12/5
    */
    int insert(String table, List<Map<String, Object>> mapList);

    /**
    * 删除 table 表中以 id 为筛选字段，id 为条件的数据
    * @Author: pengcheng
    * @Date: 2018/12/12
    */
    int delete(String table, Integer id);

    /**
    * 删除 table 表中以 column 为筛选字段，value 为条件的数据
    * @Author: pengcheng
    * @Date: 2018/12/12
    */
    int delete(String table, String column, Object value);

    /**
    * 以 primaryKey 作为更新字段，map 里和 primaryKey 相同 key 的 value 作为更新的条件，primaryKey 在
    * map 里应该存在和 primaryKey 相同的 key
    * @Author: pengcheng
    * @Date: 2018/12/12
    */
    int updateMap(String table, String primaryKey, Map<String, Object> map);

    /**
    * map 里面包含更新的值和更新条件，如果 columns 与 map 的 key 相同，则 map 里的 key 为筛选字段，value 为筛选条件
    * 更新除了 columns 与 map 里的 key 相同的数据。
    * @Author: pengcheng
    * @Date: 2018/12/12
    */
    int updateMap(String table, Map<String, Object> map, String...columns);

    /** 
    * 批量更新以 primaryKey 作为更新字段，map 里和 primaryKey 相同 key 的 value 作为更新的条件，primaryKey 在
    * map 里应该存在和 primaryKey 相同的 key
    * @Author: pengcheng 
    * @Date: 2018/12/13 
    */
    int updateMapList(String table, String primaryKey, List<Map<String, Object>> mapList);
    
    /** 
    * 批量更新 map 里面包含更新的值和更新条件，如果 columns 与 map 的 key 相同，则 map 里的 key 为筛选字段，value 为筛选条件
    * 更新除了 columns 与 map 里的 key 相同的数据。
    * @Author: pengcheng 
    * @Date: 2018/12/13 
    */
    int updateMapList(String table, List<Map<String, Object>> mapList, String...columns);

    /**
    * 以 primaryKey 作为更新字段，map 里和 primaryKey 相同 key 的 value 作为更新的条件，primaryKey 在
    * @Author: pengcheng
    * @Date: 2018/12/14
    */
    int insertOrUpdate(String table, String primaryKey, Map<String, Object> map);
    
    /** 
    * 插入和更新 map 里面包含更新的值和更新条件，如果 columns 与 map 的 key 相同，则 map 里的 key 为筛选字段，value 为筛选条件
    * 更新除了 columns 与 map 里的 key 相同的数据。
    * @Author: pengcheng 
    * @Date: 2018/12/14 
    */
    int insertOrUpdate(String table, Map<String, Object> map, String...columns);

    int queryForInt(String sql, Object... args);

    int queryForInt(String sql);

    long queryForLong(String sql, Object... args);

    long queryForLong(String sql);

    Map<String, Object> find(String table, Map<String, Object> colMap);

    Map<String, Object> findById(String table, Integer id);

    List<Map<String, Object>> query(String table, Map<String, Object> colMap);

    PageList<Map<String, Object>> pageList(String sql, Integer currentPage, Integer pageSize, Object... args);

    PageList<Map<String, Object>> pageList(String sql, String countSql, Integer currentPage, Integer pageSize, Object... args);

}
