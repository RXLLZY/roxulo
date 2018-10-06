package com.swt.common.datainput;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * csv Excel统一POI处理类
 *
 * @author chengesheng
 * @date 2012-5-3 下午03:10:23
 * @note PoiHelper
 */
public class CSVAndExcelReader {

  public static String getType(String filePath) {
    if (filePath.contains(".xls")) {
      return "excel文件";
    } else {
      return "csv文件";
    }
  }

  public static long size(String filePath) {
    if (!new File(filePath).exists()) {
      return 0;
    }
    PoiExcelHelper poi = new PoiExcelHelper();
    long size = 0;
    if (isExcel(filePath)) {
      // 查询的行数
      size = poi.size(filePath);
    } else if (isCsv(filePath)) {
      try {
        CsvInput input = new CsvInput(filePath);
        size = input.getSize();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return size;
  }

  /**
   * 获取头
   *
   * @param filePath
   * @return
   */
  public static List<String> getHeader(String filePath) {
    List<String> head = new ArrayList<>(0);
    try {
      if (filePath.endsWith(".xls") || filePath.endsWith(".xlsx")) {
        head = new PoiExcelHelper().readExcel(filePath, 0, "1-1").get(0);
      }
      if (filePath.contains(".csv")) {
        CsvInput input = new CsvInput(filePath);
        head = input.getHeader();
        input.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return head;
  }

  /**
   * 获取所有文件内容
   *
   * @param filePath
   * @return
   */
  public static List<List<String>> getAllContents(String filePath) {
    return getContents(filePath, 1, 0);
  }

  public static boolean isExcel(String filePath) {
    String suffix = filePath.substring(filePath.length() - 4, filePath.length());
    if (suffix.equalsIgnoreCase(".xls")) {
      return true;
    } else {
      suffix = filePath.substring(filePath.length() - 5, filePath.length());
      if (suffix.equalsIgnoreCase(".xlsx")) {
        return true;
      }
    }
    return false;
  }

  public static boolean isCsv(String filePath) {
    String suffix = filePath.substring(filePath.length() - 4, filePath.length());
    if (suffix.equalsIgnoreCase(".csv")) {
      return true;
    } else {
      return false;
    }
  }

  public static List<List<String>> getContents(String filePath, int start, int end) {
    List<List<String>> listContent = new ArrayList();
    if (isExcel(filePath)) {
      PoiExcelHelper poi = new PoiExcelHelper();
      // 查询的行数
      if (end == 0) {
        listContent = poi.readExcel(filePath, 0);
      } else {
        String rows = start + "-" + end;
        listContent = poi.readExcel(filePath, 0, rows);
      }
    } else if (isCsv(filePath)) {
      try {
        CsvInput input = new CsvInput(filePath);
        // 每一列的值
        List<String> line = null;
        int count = 1;
        while (input.hasNext()) {
          line = new ArrayList<>();
          Map<String, String> record = input.next();
          if (count >= start) {
            if (count <= end) {
              Iterator<Map.Entry<String, String>> iter = record.entrySet().iterator();
              while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String value = entry.getValue();

                line.add(value);
              }
              listContent.add(line);
            } else {
              break;
            }
          }
          count++;
        }
        input.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return listContent;
  }
}
