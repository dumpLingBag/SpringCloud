package com.rngay.common.jpa.dao;

import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.Map;

public interface SQLDao extends JdbcOperations {

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
    * 以 primaryKey 为更新条件，修改 table 表中对应记录，map 的 key (除主键外) 做为要更新的字段
    * 对应的 value 为更新字段的值
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

}
