package com.rngay.common.jpa.dao.pager;

import com.rngay.common.jpa.util.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class Pager implements PageInfo, Serializable {

    private final Logger log = LoggerFactory.getLogger(Pager.class);
    public static int DEFAULT_PAGE_SIZE = 10;
    public static int MAX_FETCH_SIZE = 200;
    private int pageNumber;
    private int pageSize;
    private int pageCount;
    private int recordCount;

    public Pager() {
        this.pageNumber = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Pager(int pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }

        this.pageNumber = pageNumber;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Pager(int pageNumber, int pageSize) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }

        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Pager resetPageCount() {
        this.pageCount = -1;
        return this;
    }

    @Override
    public int getPageCount() {
        if (this.pageCount < 0) {
            this.pageCount = (int) Math.ceil((double) this.recordCount / (double) this.pageSize);
        }

        return this.pageCount;
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public Pager setPageNumber(int pn) {
        if (pn > 1 && log.isInfoEnabled()) {
            log.info("");
        }

        this.pageNumber = pn;
        return this;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public Pager setPageSize(int pageSize) {
        this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
        return this.resetPageCount();
    }

    @Override
    public int getRecordCount() {
        return this.recordCount;
    }

    @Override
    public Pager setRecordCount(int var1) {
        this.recordCount = recordCount > 0 ? recordCount : 0;
        this.pageCount = (int) Math.ceil((double) recordCount / (double) this.pageSize);
        return this;
    }

    @Override
    public int getOffset() {
        return this.pageSize * (this.pageNumber - 1);
    }

    @Override
    public boolean isFirst() {
        return this.pageNumber == 1;
    }

    @Override
    public boolean isLast() {
        if (this.pageCount == 0) {
            return true;
        } else {
            return this.pageNumber == this.pageCount;
        }
    }

    @Override
    public boolean hasNext() {
        return !this.isLast();
    }

    @Override
    public boolean hasPrevious() {
        return !this.isFirst();
    }

}
