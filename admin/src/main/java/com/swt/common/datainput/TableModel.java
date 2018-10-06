package com.swt.common.datainput;

import java.io.Serializable;
import java.util.List;

public class TableModel implements Serializable {
  private static final long serialVersionUID = -6173396145907868348L;

  private DataHeadList data_head;
  private List<List<String>> data_body;

  public DataHeadList getData_head() {
    return data_head;
  }

  public void setData_head(DataHeadList data_head) {
    this.data_head = data_head;
  }

  public List<List<String>> getData_body() {
    return data_body;
  }

  public void setData_body(List<List<String>> data_body) {
    this.data_body = data_body;
  }
}
