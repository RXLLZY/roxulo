package com.swt.modules.calculatelibrary.entity.rest;

import java.util.ArrayList;
import java.util.List;

/** <b>兼容JDYS web接口使用</b> */
public class Result {

  private List<RowMeta> rowMetaList = new ArrayList<RowMeta>();
  private List<String[]> data = new ArrayList<String[]>();

  public List<RowMeta> getRowMetaList() {
    return rowMetaList;
  }

  public void setRowMetaList(List<RowMeta> rowMetaList) {
    this.rowMetaList = rowMetaList;
  }

  public List<String[]> getData() {
    return data;
  }

  public void setData(List<String[]> data) {
    this.data = data;
  }
}
