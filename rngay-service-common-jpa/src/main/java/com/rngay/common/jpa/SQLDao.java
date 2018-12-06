package com.rngay.common.jpa;

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

}
