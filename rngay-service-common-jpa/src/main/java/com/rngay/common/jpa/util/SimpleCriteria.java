package com.rngay.common.jpa.util;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.dao.sql.Criteria;
import com.rngay.common.jpa.dao.sql.GroupBy;
import com.rngay.common.jpa.dao.sql.OrderBy;
import com.rngay.common.jpa.util.cri.GroupBySet;
import com.rngay.common.jpa.util.cri.OrderBySet;
import com.rngay.common.jpa.util.cri.SqlExpressionGroup;

public class SimpleCriteria implements Criteria, OrderBy, GroupBy {

    private SqlExpressionGroup where;
    private OrderBySet orderBy;
    private GroupBySet groupBy;

    public SimpleCriteria() {
        this.where = new SqlExpressionGroup();
        this.orderBy = new OrderBySet();
        this.groupBy = new GroupBySet();
    }

    @Override
    public GroupBy groupBy(String... names) {
        this.groupBy = new GroupBySet(names);
        return this;
    }

    @Override
    public GroupBy having(Condition cnd) {
        this.groupBy.having(cnd);
        return this;
    }

    @Override
    public OrderBy asc(String name) {
        return this.orderBy.asc(name);
    }

    @Override
    public OrderBy desc(String name) {
        return this.orderBy.desc(name);
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

    @Override
    public SqlExpressionGroup where() {
        return this.where;
    }

    @Override
    public OrderBy getOrderBy() {
        return this.orderBy;
    }


    @Override
    public GroupBy getGroupBy() {
        return this.groupBy;
    }

}
