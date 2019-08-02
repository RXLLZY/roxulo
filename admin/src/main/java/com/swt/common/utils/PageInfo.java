package com.swt.common.utils;

import java.io.Serializable;

public class PageInfo implements Serializable {

    private static final long serialVersionUID = -2438150216480179472L;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int pageNo = 1;

    public PageInfo() {
    }
    public PageInfo(int page, int pageSize) {
        this.pageIndex = page;
        this.pageSize = pageSize;
    }

    /**
     * 修复datagrid查询结果为空时，pageIndex传0的问题
     *
     * @return
     */
    public int getStart() {
        if (pageIndex == 0) {
            return pageIndex * pageSize;
        } else {
            return (pageIndex - 1) * pageSize;
        }
    }

    public int getEnd() {
        return pageIndex * pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int page) {
        this.pageIndex = page;
    }

    public int getPageSize() {
        if (this.pageSize > 0) {
            return pageSize;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.setPageIndex(pageNo);
        this.pageNo = pageNo;
    }
}
