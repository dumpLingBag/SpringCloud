package com.rngay.common.jpa.dao.util.cri;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.dao.sql.Criteria;
import com.rngay.common.jpa.dao.sql.GroupBy;
import com.rngay.common.jpa.util.Maker;

public class GroupBySet extends OrderBySet implements GroupBy {

    private String[] names;
    private Condition having;

    public GroupBySet() {
    }

    public GroupBySet(String... names) {
        this.names = names;
    }

    @Override
    public GroupBy groupBy(String... names) {
        this.names = names;
        return this;
    }

    @Override
    public GroupBy having(Condition cnd) {
        this.having = cnd;
        return this;
    }

    public Maker getGroupBy() {
        if (null != this.names && this.names.length != 0) {
            Maker maker = new Maker();
            StringBuilder group = new StringBuilder(" GROUP BY ");

            for (String name : this.names) {
                group.append(name);
                group.append(",");
            }

            group.setCharAt(group.length() - 1, ' ');
            if (null != this.having) {
                group.append("HAVING ");
                maker = ((Criteria) this.having).where().getSql();
                group.append(maker.getSqlName().toString().substring(7));
            }
            maker.setSqlName(group);
            return maker;
        }
        return null;
    }

}
