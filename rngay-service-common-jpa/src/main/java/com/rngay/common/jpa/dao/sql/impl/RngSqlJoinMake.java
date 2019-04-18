package com.rngay.common.jpa.dao.sql.impl;

import com.rngay.common.exception.BaseException;
import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.dao.sql.Criteria;
import com.rngay.common.jpa.dao.sql.SqlJoinMake;
import com.rngay.common.jpa.dao.util.cri.GroupBySet;
import com.rngay.common.jpa.dao.util.cri.OrderBySet;
import com.rngay.common.jpa.util.Maker;
import com.rngay.common.util.HumpUtil;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
* SQL 的具体实现
* @Author pengcheng
* @Date 2019/3/7
**/
public class RngSqlJoinMake extends RngSqlBuilder implements SqlJoinMake {

    @Override
    public <T> String tableName(Class<T> tClass) {
        String name = tClass.getAnnotation(Table.class).name();
        if (!"".equals(name)) {
            return name;
        }
        throw new BaseException(500, "不存在表名!");
    }

    @Override
    public Maker makeJoinQuery(String tableName, Object cnd, String... fields) {
        if (null == tableName || "".equals(tableName)) {
            throw new BaseException(500, "表名为空!");
        }

        Maker maker = new Maker();
        StringBuilder sqlN = query(tableName, fields);

        if (null != cnd) {
            if (cnd instanceof Condition) {
                cdn(maker, sqlN, (Condition) cnd);
            } else {
                if (cnd instanceof Integer) {
                    List<Object> list = new ArrayList<>();
                    list.add(cnd);
                    maker.setSqlName(sqlN.append(" WHERE id = ?"));
                    maker.setSqlVal(list);
                } else {
                    String sql = String.valueOf(cnd);
                    if (sql.contains("WHERE") || sql.contains("where")) {
                        sqlN.append(' ').append(cnd);
                    } else {
                        sqlN.append(' ').append("WHERE").append(cnd);
                    }
                    maker.setSqlName(sqlN);
                }
            }
        } else {
            maker.setSqlName(sqlN);
        }

        return maker;
    }

    @Override
    public Maker makeJoinCount(String tableName, Condition cdn, String field) {
        if (null == tableName || "".equals(tableName)) {
            throw new BaseException(500, "不存在更新实体表!");
        }

        Maker maker = new Maker();
        StringBuilder sqlN = count(tableName, field);
        if (null != cdn) {
            Criteria criteria = (Criteria) cdn;
            Maker sql = criteria.where().getSql();
            if (null != sql.getSqlName() && sql.getSqlName().length() != 0) {
                sqlN.append(sql.getSqlName());
            }
            maker.setSqlVal(sql.getSqlVal());
        }
        maker.setSqlName(sqlN);

        return maker;
    }

    @Override
    public Maker makeJoinUpdate(Object obj, Condition cnd, boolean isNull) {
        if (null == obj) {
            throw new NullPointerException("不存在要更新的数据!");
        }

        Maker maker = new Maker();
        List<Object> val = new ArrayList<>();
        boolean isId = false;
        String id = null;
        if (obj instanceof Map) {
            Map<?, ?> map = (Map)obj;
            if (null == cnd) {
                if (null == map.get("id")) {
                    throw new BaseException(500, "主键不存在!");
                }
            }
            if (null == map.get(".table") || "".equals(map.get(".table"))) {
                throw new BaseException(500, ".table不存在!");
            }
            StringBuilder sqlN = update((String) map.get(".table"));
            StringBuilder where = new StringBuilder();
            for (Map.Entry<?, ?> m : map.entrySet()) {
                if (!"id".equals(m.getKey()) && !".table".equals(m.getKey())) {
                    where.append(HumpUtil.humpToLine(String.valueOf(m.getKey()))).append("=").append("?").append(",");
                    val.add(m.getValue());
                }
            }
            if (where.length() > 0) {
                where.setCharAt(where.length() - 1, ' ');
                if (null == cnd) {
                    sqlN.append(where).append("WHERE ").append("id").append("=").append("?");
                    val.add(map.get("id"));
                } else {
                    Maker sql = ((Criteria) cnd).where().getSql();
                    sqlN.append(where).append(sql.getSqlName());
                    val.addAll(sql.getSqlVal());
                }
                maker.setSqlName(sqlN);
                maker.setSqlVal(val);
            } else {
                throw new BaseException(500, "不存在更新信息!");
            }
        } else {
            String tableName= tableName(obj.getClass());
            if (null == tableName || "".equals(tableName)) {
                throw new BaseException(500, "不存在表名");
            }
            StringBuilder sqlN = update(tableName);
            StringBuilder sql = new StringBuilder();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                boolean idAnn = field.isAnnotationPresent(Id.class);
                try {
                    if (idAnn) {
                        isId = true;
                        Object o = field.get(obj);
                        if (null != o && !"".equals(o)) {
                            id = String.valueOf(o);
                        }
                    } else {
                        Object o = field.get(obj);
                        if (isNull) {
                            sql.append(HumpUtil.humpToLine(field.getName())).append("=").append("?").append(",");
                            val.add(o);
                        } else {
                            if (null != o && !"".equals(o)) {
                                sql.append(HumpUtil.humpToLine(field.getName())).append("=").append("?").append(",");
                                val.add(o);
                            }
                        }
                    }
                    field.setAccessible(accessible);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new BaseException(500, "安全权限异常");
                }
            }

            sql.setCharAt(sql.length() - 1, ' ');
            if (null == cnd) {
                if (isId) {
                    sqlN.append(sql).append("WHERE id=?");
                    val.add(id);
                } else {
                    throw new BaseException(500, "不存在主键!");
                }
            } else {
                Maker sqlV = ((Criteria) cnd).where().getSql();
                sqlN.append(sql).append(sqlV.getSqlName());
                val.addAll(sqlV.getSqlVal());
            }

            maker.setSqlName(sqlN);
            maker.setSqlVal(val);
        }

        return maker;
    }

    @Override
    public Maker makeJoinDelete(String tableName, Condition cnd, Integer batchNumber) {
        if (null == tableName || "".equals(tableName)) {
            throw new BaseException(500, "表名为空!");
        }

        if (null != cnd) {
            Maker maker = new Maker();
            StringBuilder sqlN = delete(tableName);
            Criteria cri = (Criteria) cnd;
            Maker sql = cri.where().getSql();
            if (null != sql.getSqlName() && sql.getSqlName().length() != 0) {
                sqlN.append(sql.getSqlName());
            }
            maker.setSqlName(sqlN);
            maker.setSqlVal(sql.getSqlVal());

            return maker;
        } else {
            throw new BaseException(500, "不存在删除条件!");
        }
    }

    @Override
    public Maker makeJoinInsert(Object obj) {
        if (null == obj) {
            throw new BaseException(500, "不存在要更新的数据!");
        }

        Maker maker = new Maker();
        if (obj.getClass().isArray()) {
            throw new BaseException(500, "该方式不支持批量操作!");
        } else {
            if (obj instanceof Map) {
                Map<?, ?> map = (Map)obj;
                if (null == map.get(".table") || "".equals(map.get(".table"))) {
                    throw new BaseException(500, "不存在要更新的数据表!");
                }

                StringBuilder sqlN = new StringBuilder();
                StringBuilder sqlV = new StringBuilder();
                List<Object> ob = new ArrayList<>();
                if (!map.isEmpty()) {
                    for (Map.Entry<?, ?> m : map.entrySet()) {
                        if (!".table".equals(m.getKey())) {
                            if (m.getValue() != null && !"".equals(m.getValue())) {
                                sqlN.append(HumpUtil.humpToLine(String.valueOf(m.getKey()))).append(",");
                                sqlV.append("?").append(",");
                                ob.add(m.getValue());
                            }
                        }
                    }

                    sqlN.setCharAt(sqlN.length() - 1, ' ');
                    sqlV.setCharAt(sqlV.length() - 1, ' ');

                    StringBuilder val = insert((String) map.get(".table"), sqlN.toString().trim(), sqlV.toString().trim());
                    maker.setSqlName(val);
                    maker.setSqlVal(ob);
                }
            } else {
                String tableName = tableName(obj.getClass());
                if (null == tableName || "".equals(tableName)) {
                    throw new BaseException(500, "不存在要更新的数据库表!");
                }

                StringBuilder sqlN = new StringBuilder();
                StringBuilder sqlV = new StringBuilder();
                List<Object> ob = new ArrayList<>();
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    boolean id = field.isAnnotationPresent(Id.class);
                    if (!id) {
                        try {
                            Object o = field.get(obj);
                            if (null != o && !"".equals(o)) {
                                sqlN.append(HumpUtil.humpToLine(field.getName())).append(",");
                                sqlV.append("?").append(",");
                                ob.add(o);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            throw new BaseException(500, "安全权限异常");
                        }
                    }
                }

                sqlN.setCharAt(sqlN.length() - 1, ' ');
                sqlV.setCharAt(sqlV.length() - 1, ' ');

                StringBuilder insert = insert(tableName, sqlN.toString().trim(), sqlV.toString().trim());
                maker.setSqlName(insert);
                maker.setSqlVal(ob);
            }
        }
        return maker;
    }

    @Override
    public Maker makeJoinPager(String tableName, int pageNumber, int pageSize, Condition cdn) {
        if (null == tableName || "".equals(tableName)) {
            throw new BaseException(500, "表名为空!");
        }

        Maker maker = new Maker();
        StringBuilder sqlN = query(tableName);
        if (cdn != null) {
            cdn(maker, sqlN, cdn);
        } else {
            maker.setSqlName(sqlN);
        }
        maker.setSqlName(maker.getSqlName().append(' ').append("LIMIT").append(' ').append((pageNumber - 1) * pageSize).append(",").append(pageSize));

        return maker;
    }

    private void cdn(Maker maker, StringBuilder sqlN, Condition cdn) {
        Criteria criteria = (Criteria) cdn;
        Maker sql = criteria.where().getSql();
        if (null != sql.getSqlName() && sql.getSqlName().length() != 0) {
            sqlN.append(sql.getSqlName());
        }
        GroupBySet groupBy = (GroupBySet) criteria.getGroupBy();
        if (null != groupBy.getGroupBy()) {
            sqlN.append(groupBy.getGroupBy().getSqlName());
            sql.getSqlVal().addAll(groupBy.getGroupBy().getSqlVal());
        }
        OrderBySet orderBy = (OrderBySet) criteria.getOrderBy();
        if (null != orderBy.getNames() && !"".equals(orderBy.getNames())) {
            sqlN.append(orderBy.getNames());
        }
        maker.setSqlName(sqlN);
        maker.setSqlVal(sql.getSqlVal());
    }

}
