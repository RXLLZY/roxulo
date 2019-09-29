package com.swt.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties({"records", "total", "size", "current", "pages", "ascs", "descs", "optimizeCountSql", "isSearchCount", "searchCount"})
public class PageData<T> extends Page<T> {
    private static final Long DEFAULT_PAGE_NO = 1L;
    private static final Long DEFAULT_PAGE_SIZE = 10L;
    private static final String DESC = "desc";
    private static final String ASC = "asc";
    private PageInfo pageInfo;

    public PageData() {
        super(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE);
        this.pageInfo = new PageInfo(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE);
    }

    public PageData(PageInfo pageInfo) {
        super(pageInfo.getPageNo(), pageInfo.getPageSize());
        if(!Objects.isNull(pageInfo.getSidx())){
            if(DESC.equalsIgnoreCase(pageInfo.getOrder())){
                super.setDesc();
            }else{
                super.setAsc(pageInfo.getSidx());
            }
        }
        this.pageInfo = pageInfo;
    }

    public PageData(int pageNo, int pageSize) {
        super((long)pageNo, (long)pageSize);
        this.pageInfo = new PageInfo((long)pageNo, (long)pageSize);
    }

    public List<T> getList() {
        return super.getRecords();
    }

    public Long getTotalCount() {
        return super.getTotal();
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public Integer getTotalPageCount() {
        return (int)Math.ceil((double)this.getTotalCount() / (double)this.getPageInfo().getPageSize());
    }
}
