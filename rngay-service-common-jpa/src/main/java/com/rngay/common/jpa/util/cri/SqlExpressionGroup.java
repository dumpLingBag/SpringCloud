package com.rngay.common.jpa.util.cri;

import com.rngay.common.jpa.util.Maker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SqlExpressionGroup {

    private final Logger log = LoggerFactory.getLogger(SqlExpressionGroup.class);

    private StringBuilder where;
    private List<Object> value;

    public SqlExpressionGroup() {
        this.where = new StringBuilder();
        this.value = new ArrayList<>();
    }

    public SqlExpressionGroup and(String name, String op, Object value) {
        if (null != name && !"".equals(name) && null != op && !"".equals(op) && null != value && !"".equals(value)) {
            this.isWhere();
            if (op.equalsIgnoreCase("IN")) {
                if (value instanceof Collection) {
                    this.append(name, op.toUpperCase(), (List) value);
                } else if (value.getClass().isArray()) {
                    this.append(name, op.toUpperCase(), Arrays.asList((Object[]) value));
                } else {
                    this.where.append(name).append(' ').append(op.toUpperCase()).append(' ').append(value);
                }
            } else if (op.equalsIgnoreCase("LIKE")) {
                if (!value.toString().contains("%"))
                    value = "%" + value + "%";
                this.where.append(name).append(' ').append(op.toUpperCase()).append(' ').append("?");
                this.value.add(value);
            } else {
                this.where.append(name).append(' ').append(op.toUpperCase()).append(' ').append("?");
                this.value.add(value);
            }
        } else {
            log.warn("查询条件为空已过滤，请检查是否需要");
        }
        return this;
    }

    public SqlExpressionGroup andIn(String name, String... names) {
        return this;
    }

    public Maker getSql() {
        Maker maker = new Maker();
        maker.setSqlName(this.where);
        maker.setSqlVal(this.value);
        return maker;
    }

    private void isWhere() {
        if (this.where.length() > 0) {
            this.where.append(" AND ");
        } else {
            this.where.append(" WHERE ");
        }
    }

    private void append(String name, String op, List value) {
        StringBuilder val = new StringBuilder("(");
        List<Object> list = new ArrayList<>();

        for (Object obj : value) {
            val.append("?").append(",");
            list.add(obj);
        }

        val.setCharAt(val.length() - 1, ')');
        this.where.append(name).append(' ').append(op).append(' ').append(val);
        this.value.addAll(list);
    }

}
