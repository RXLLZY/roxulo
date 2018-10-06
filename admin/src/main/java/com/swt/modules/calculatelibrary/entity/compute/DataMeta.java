package com.swt.modules.calculatelibrary.entity.compute;

import java.io.Serializable;

public class DataMeta implements Serializable {
  private static final long serialVersionUID = -4165785719042710236L;
  private int pageId;
  private String previousId;
  private String fieldName;
  private String fieldComment;

  public int getPageId() {
    return pageId;
  }

  public void setPageId(int pageId) {
    this.pageId = pageId;
  }

  public String getPreviousId() {
    return previousId;
  }

  public void setPreviousId(String previousId) {
    this.previousId = previousId;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldComment() {
    return fieldComment;
  }

  public void setFieldComment(String fieldComment) {
    this.fieldComment = fieldComment;
  }
}
