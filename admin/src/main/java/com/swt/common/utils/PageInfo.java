package com.swt.common.utils;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PageInfo implements Serializable {
    private static final long serialVersionUID = -2438150216480179472L;
    private static final Long DEFAULT_PAGE_NO = 1L;
    private static final Long DEFAULT_PAGE_SIZE = 10L;

    @ApiModelProperty(value = "页码", example = "1", hidden = false)
    private Long pageNo;

    @ApiModelProperty(value = "每页条数", example = "10", hidden = false)
    private Long pageSize;

    @ApiModelProperty(value = "升降序(asc,desc)", example = "asc", hidden = false)
    private String order;

    @ApiModelProperty(value = "排序字段", example = "", hidden = false)
    private String[] sidx;

    public PageInfo() {
        this.pageNo = DEFAULT_PAGE_NO;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public PageInfo(long pageNo, long pageSize) {
        this.pageNo = DEFAULT_PAGE_NO;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public long getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String[] getSidx() {
        return sidx;
    }

    public void setSidx(String[] sidx) {
        this.sidx = sidx;
    }
}
