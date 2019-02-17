package com.rngay.common.jpa.util;

public interface PageInfo {

    int getPageCount();

    int getPageNumber();

    PageInfo setPageNumber(int var1);

    int getPageSize();

    PageInfo setPageSize(int var1);

    int getRecordCount();

    PageInfo setRecordCount(int var1);

    int getOffset();

    boolean isFirst();

    boolean isLast();

    boolean hasNext();

    boolean hasPrevious();

}
