package com.swt.modules.calculatelibrary.entity;

public class DataField {
  private String table;
  private String tableComment;
  private String field;
  private String fieldComment;
  private String fieldType;
  private int isIdNumber;

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getTableComment() {
    return tableComment;
  }

  public void setTableComment(String tableComment) {
    this.tableComment = tableComment;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getFieldComment() {
    return fieldComment;
  }

  public void setFieldComment(String fieldComment) {
    this.fieldComment = fieldComment;
  }

  public String getFieldType() {
    return fieldType;
  }

  public void setFieldType(String fieldType) {
    this.fieldType = fieldType;
  }

  public int getIsIdNumber() {
    return isIdNumber;
  }

  public void setIsIdNumber(int isIdNumber) {
    this.isIdNumber = isIdNumber;
  }
}
