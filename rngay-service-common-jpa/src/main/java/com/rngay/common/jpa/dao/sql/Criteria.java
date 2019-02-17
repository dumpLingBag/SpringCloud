package com.rngay.common.jpa.dao.sql;

import com.rngay.common.jpa.dao.Condition;
import com.rngay.common.jpa.util.cri.SqlExpressionGroup;

public interface Criteria extends Condition {

    SqlExpressionGroup where();

    OrderBy getOrderBy();

    GroupBy getGroupBy();

}
