package com.swt.common.utils;

import java.io.Serializable;
import java.util.List;

public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 5352430458666644260L;

    private PageInfo pageInfo;
    private List<T> list;
    private Integer totalCount = 0;

    public synchronized PageInfo getPageInfo() {
        if (pageInfo == null) {
            pageInfo = new PageInfo();
        }

        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPageCount() {
        return (int) Math.ceil((double) this.getTotalCount() / this.getPageInfo().getPageSize());
    }

}
