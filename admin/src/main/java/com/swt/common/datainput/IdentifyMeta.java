package com.swt.common.datainput;

import com.swt.common.config.UploadConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class IdentifyMeta {

  private final UploadConfig config;

  public IdentifyMeta(UploadConfig config) {
    this.config = config;
  }

  public TableModel identifyDataMeta(String fileName) {
    String filePath = config.getLocalDir() + File.separator + fileName;
    TableModel resultTable = new TableModel();
    List<List<String>> listExcelContent = CSVAndExcelReader.getContents(filePath, 0, 50);
    if (null == listExcelContent || listExcelContent.size() < 1) {
      return resultTable;
    }
    // 把原始名字传给后边的识别模块
    // 无文件
    List<String> originalFieldName = CSVAndExcelReader.getHeader(filePath);
    if (null == originalFieldName || originalFieldName.size() < 1) {
      return resultTable;
    }
    // 找空字段
    boolean hasEmptyFieldName = false;
    for (String fieldName : originalFieldName) {
      if (fieldName.trim().isEmpty()) {
        hasEmptyFieldName = true;
        break;
      }
    }
    if (hasEmptyFieldName) {
      List<String> oldNameList = new ArrayList<String>();
      oldNameList.add("has some empty field name");
      DataHeadList dhl = new DataHeadList();
      dhl.setOld_name(oldNameList);
      resultTable.setData_head(dhl);
      return resultTable;
    }
    // 找重复字段名
    boolean hasSameFieldName = hasSame(originalFieldName);
    if (hasSameFieldName) {
      List<String> oldNameList = new ArrayList<String>();
      oldNameList.add("has same field name");
      DataHeadList dhl = new DataHeadList();
      dhl.setOld_name(oldNameList);
      resultTable.setData_head(dhl);
      return resultTable;
    }

    DataHeadList dhl = new DataHeadList();
    dhl.setOld_name(originalFieldName);
    dhl.setNew_name(originalFieldName);
    dhl.setNew_comment(originalFieldName);
    dhl.setSrcFileName(fileName);
    resultTable.setData_head(dhl);

    return resultTable;
  }

  private static boolean hasSame(List<? extends Object> list) {
    if (null == list) {
      return false;
    }
    return list.size() != new HashSet<Object>(list).size();
  }
}
