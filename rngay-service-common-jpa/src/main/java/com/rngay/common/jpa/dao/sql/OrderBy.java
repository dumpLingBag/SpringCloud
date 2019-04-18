package com.rngay.common.jpa.dao.sql;

import com.rngay.common.jpa.dao.Condition;

public interface OrderBy extends Condition {

    OrderBy asc(String var1);

    OrderBy desc(String var1);

    OrderBy orderBy(String var1, String var2);

}
