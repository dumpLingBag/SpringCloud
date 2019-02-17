package com.rngay.common.jpa.util.cri;

import com.rngay.common.jpa.dao.sql.OrderBy;

public class OrderBySet implements OrderBy {

    private StringBuilder names;

    public OrderBySet() {
        this.names = new StringBuilder();
    }

    @Override
    public OrderBy asc(String name) {
        if (this.names.length() > 0) {
            this.names.append(",").append(name).append(" ASC");
        } else {
            this.names.append(" ORDER BY ").append(name).append(" ASC");
        }
        return this;
    }

    @Override
    public OrderBy desc(String name) {
        if (this.names.length() > 0) {
            this.names.append(",").append(name).append(" DESC");
        } else {
            this.names.append(" ORDER BY ").append(name).append(" DESC");
        }
        return this;
    }

    @Override
    public OrderBy orderBy(String name, String dir) {
        if (this.names.length() > 0) {
            this.names.append(",").append(name).append(' ').append(dir);
        } else {
            this.names.append(" ORDER BY ").append(name).append(' ').append(dir);
        }
        return this;
    }

    public String getNames() {
        return this.names.toString();
    }

}
