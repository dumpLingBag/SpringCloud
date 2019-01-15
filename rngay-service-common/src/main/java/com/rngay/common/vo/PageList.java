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

    /**
    * 当前页
    * @Author: pengcheng
    * @Date: 2018/12/26
    */
    private int currentPage = 1;

    /**
    * 每页显示数量
    * @Author: pengcheng
    * @Date: 2018/12/26
    */
    private int pageSize = 10;

    /**
    * 总记录数
    * @Author: pengcheng
    * @Date: 2018/12/26
    */
    private Long totalSize = 0L;

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

    public PageList(){}

    public PageList(Integer currentPage, Long totalSize, Integer pageSize){
        if (currentPage == null){
            currentPage = 1;
        }
        if (pageSize == null){
            pageSize = this.pageSize;
        }
        this.currentPage = currentPage;
        this.totalSize = totalSize;
        this.pageSize = pageSize;
    }

    public static Integer getCurrentPage(Integer currentPage) {
        return currentPage != null && currentPage >= 0 ? currentPage : 1;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public static Integer getPageSize(Integer pageSize) {
        return pageSize != null && pageSize > 0 ? pageSize : 10;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
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
