package com.rngay.common.jpa.dao;

import com.rngay.common.jpa.dao.sql.Criteria;
import com.rngay.common.jpa.dao.sql.GroupBy;
import com.rngay.common.jpa.dao.sql.OrderBy;
import com.rngay.common.jpa.util.SimpleCriteria;
import com.rngay.common.jpa.util.cri.SqlExpressionGroup;

public class Cnd implements Criteria, OrderBy, GroupBy {

    protected SimpleCriteria cri;

    public static Cnd where(String name, String op, Object value) {
        return new Cnd(name, op, value);
    }

    public Cnd and(String name, String op, Object value) {
        this.cri.where().and(name, op, value);
        return this;
    }

    protected Cnd(String name, String op, Object value) {
        this();
        this.cri.where().and(name, op, value);
    }

    protected Cnd() {
        this.cri = new SimpleCriteria();
    }

    @Override
    public SqlExpressionGroup where() {
        return this.cri.where();
    }

    @Override
    public OrderBy getOrderBy() {
        return this.cri.getOrderBy();
    }

    @Override
    public GroupBy getGroupBy() {
        return this.cri.getGroupBy();
    }

    @Override
    public GroupBy groupBy(String... names) {
        this.cri.groupBy(names);
        return this;
    }

    @Override
    public GroupBy having(Condition cnd) {
        this.cri.having(cnd);
        return this;
    }

    @Override
    public OrderBy asc(String name) {
        this.cri.asc(name);
        return this;
    }

    @Override
    public OrderBy desc(String name) {
        this.cri.desc(name);
        return this;
    }

    @Override
    public OrderBy orderBy(String name, String dir) {
        if ("asc".equalsIgnoreCase(dir)) {
            this.asc(name);
        } else {
            this.desc(name);
        }

        return this;
    }
}
