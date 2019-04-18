package com.rngay.common.jpa.dao.sql;

import com.rngay.common.jpa.dao.Condition;

public interface GroupBy extends OrderBy {

    GroupBy groupBy(String... var1);

    GroupBy having(Condition var1);

}
