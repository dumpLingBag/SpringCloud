package com.rngay.common.jpa.dao.sql;

public interface OrderBy {

    OrderBy asc(String var1);

    OrderBy desc(String var1);

    OrderBy orderBy(String var1, String var2);

}
