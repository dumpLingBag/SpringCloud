package com.rngay.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* 分页包装类
* @Author: pengcheng
* @Date: 2018/12/26
*/
public class PageList<T> implements Serializable {

    private final static int DEFAULT_PAGE_NUMBER = 1;
    private final static int DEFAULT_PAGE_SIZE = 10;

    /**
    * 当前页
    * @Author: pengcheng
    * @Date: 2018/12/26
    */
    private int pageNumber;

    /**
    * 每页显示数量
    * @Author: pengcheng
    * @Date: 2018/12/26
    */
    private int pageSize;

    /**
    * 总记录数
    * @Author: pengcheng
    * @Date: 2018/12/26
    */
    private long totalSize = 0L;

    /**
    * 总页数
    * @Author: pengcheng
    * @Date: 2018/12/26
    */
    private int totalPage;

    /**
    * 存储分页数据
    * @Author: pengcheng
    * @Date: 2018/12/26
    */
    private List<T> list = new ArrayList<>();

    public PageList(){
        this.pageNumber = DEFAULT_PAGE_NUMBER;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public PageList(int pageNumber, long totalSize, int pageSize){
        if (pageNumber < 1){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize < 1){
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageNumber = pageNumber;
        this.totalSize = totalSize;
        this.pageSize = pageSize;
        this.totalPage = getTotalPage();
    }

    public static int getPageNumber(int pageNumber) {
        return pageNumber > 0 ? pageNumber : DEFAULT_PAGE_NUMBER;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public static int getPageSize(int pageSize) {
        return pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        totalPage = (int) (totalSize / pageSize);
        if (totalSize % pageSize != 0){
            totalPage++;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
