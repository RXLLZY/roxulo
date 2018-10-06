package com.swt.common.datainput;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel统一POI处理类（针对2003以前和2007以后两种格式的兼容处理）
 *
 * @author chengesheng
 * @date 2012-5-3 下午03:10:23
 * @note PoiHelper
 */
public class PoiExcelHelper {
  public static final String SEPARATOR = ",";
  public static final String CONNECTOR = "-";

  /** 获取文件行数 */
  public int size(String filePath) {
    int i = 0;
    List<String> sheetList = new ArrayList<String>(0);
    try {
      Workbook wb =
          WorkbookFactory.create(
              new FileInputStream(new File(filePath))); // 这种方式 Excel 2003/2007/2010 都是可以处理的
      Sheet sheet = wb.getSheetAt(0);
      i = sheet.getLastRowNum();
      wb.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return i;
  }
  /** 获取sheet列表 */
  public static List<String> getSheetList(String filePath) {
    int i = 0;
    ArrayList<String> sheetList = new ArrayList<String>(50);
    if (filePath.endsWith(".xls")) { // 03
      try {
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
        int num = wb.getNumberOfSheets();
        while (i < num) {
          sheetList.add(wb.getSheetName(i));
          i++;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (filePath.endsWith(".xlsx")) { // excel 07
      try {
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
        int num = wb.getNumberOfSheets();
        while (i < num) {
          sheetList.add(wb.getSheetName(i));
          i++;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return sheetList;
  }

  /** 读取Excel文件数据 读取该sheet的所有的数据 */
  public static List<List<String>> readExcel(String filePath, int sheetIndex) {
    return readExcel(filePath, sheetIndex, "1-", "1-");
  }

  /** 读取Excel文件数据 行数输入 格式 num - num 例如 8-10 列数从1到最后 */
  public List<List<String>> readExcel(String filePath, int sheetIndex, String rows) {
    return readExcel(filePath, sheetIndex, rows, "1-");
  }

  /** 读取Excel文件数据 行数从1开始到最后 列数输入 格式 num - num 例如 8-10 */
  public List<List<String>> readExcel(String filePath, int sheetIndex, String[] columns) {
    return readExcel(filePath, sheetIndex, "1-", columns);
  }

  /** 读取Excel文件数据 */
  public static List<List<String>> readExcel(
      String filePath, int sheetIndex, String rows, String columns) {
    List<List<String>> dataList = new ArrayList<List<String>>();
    if (filePath.endsWith(".xls")) { // 03
      try {
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        dataList = readExcel(sheet, rows, getColumnNumber(sheet, columns));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (filePath.endsWith(".xlsx")) { // excel 07
      try {
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
        XSSFSheet sheet = wb.getSheetAt(sheetIndex);
        dataList = readExcel(sheet, rows, getColumnNumber(sheet, columns));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return dataList;
  }

  /** 读取Excel文件数据 */
  public List<List<String>> readExcel(
      String filePath, int sheetIndex, String rows, String[] columns) {
    int[] cols = getColumnNumber(columns);

    return readExcel(filePath, sheetIndex, rows, cols);
  }

  /** 读取Excel文件数据 */
  public List<List<String>> readExcel(String filePath, int sheetIndex, String rows, int[] cols) {
    List<List<String>> dataList = new ArrayList<List<String>>();
    if (filePath.endsWith(".xls")) { // 03
      try {
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        dataList = readExcel(sheet, rows, cols);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (filePath.endsWith(".xlsx")) { // excel 07
      try {
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
        XSSFSheet sheet = wb.getSheetAt(sheetIndex);

        dataList = readExcel(sheet, rows, cols);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return dataList;
  }

  /** 读取Excel文件内容 */
  protected static List<List<String>> readExcel(Sheet sheet, String rows, int[] cols) {
    List<List<String>> dataList = new ArrayList<List<String>>();
    // 处理行信息，并逐行列块读取数据
    String[] rowList = rows.split(SEPARATOR);
    for (String rowStr : rowList) {
      if (rowStr.contains(CONNECTOR)) {
        String[] rowArr = rowStr.trim().split(CONNECTOR);
        int start = Integer.parseInt(rowArr[0]) - 1;
        int end;
        if (rowArr.length == 1) {
          end = sheet.getLastRowNum();
        } else {
          end = Integer.parseInt(rowArr[1].trim()) - 1;
          if (end > sheet.getLastRowNum()) {
            end = sheet.getLastRowNum();
          }
        }
        dataList.addAll(getRowsValue(sheet, start, end, cols));
      } else {
        dataList.add(getRowValue(sheet, Integer.parseInt(rowStr) - 1, cols));
      }
    }
    return dataList;
  }

  /** 获取连续行、列数据 */
  protected List<List<String>> getRowsValue(
      Sheet sheet, int startRow, int endRow, int startCol, int endCol) {
    if (endRow < startRow || endCol < startCol) {
      return null;
    }

    List<List<String>> data = new ArrayList<List<String>>();
    for (int i = startRow; i <= endRow; i++) {
      data.add(getRowValue(sheet, i, startCol, endCol));
    }
    return data;
  }

  /** 获取连续行、不连续列数据 */
  private static List<List<String>> getRowsValue(
      Sheet sheet, int startRow, int endRow, int[] cols) {
    if (endRow < startRow) {
      return null;
    }

    List<List<String>> data = new ArrayList<List<String>>();
    for (int i = startRow; i <= endRow; i++) {
      data.add(getRowValue(sheet, i, cols));
    }
    return data;
  }

  /** 获取行连续列数据 */
  private List<String> getRowValue(Sheet sheet, int rowIndex, int startCol, int endCol) {
    if (endCol < startCol) {
      return null;
    }

    Row row = sheet.getRow(rowIndex);
    ArrayList<String> rowData = new ArrayList<String>();
    for (int i = startCol; i <= endCol; i++) {
      rowData.add(getCellValue(row, i));
    }
    return rowData;
  }

  /** 获取行不连续列数据 */
  private static List<String> getRowValue(Sheet sheet, int rowIndex, int[] cols) {
    Row row = sheet.getRow(rowIndex);
    ArrayList<String> rowData = new ArrayList<String>();
    for (int colIndex : cols) {
      rowData.add(getCellValue(row, colIndex));
    }
    return rowData;
  }

  /**
   * 获取单元格内容
   *
   * @param row
   * @param column a excel column string like 'A', 'C' or "AA".
   * @return
   */
  protected String getCellValue(Row row, String column) {
    return getCellValue(row, getColumnNumber(column));
  }

  /**
   * 获取单元格内容
   *
   * @param row
   * @param col a excel column index from 0 to 65535
   * @return
   */
  private static String getCellValue(Row row, int col) {
    if (row == null) {
      return "";
    }
    Cell cell = row.getCell(col);
    return getCellValue(cell);
  }

  /**
   * 获取单元格内容
   *
   * @param cell
   * @return
   */
  private static String getCellValue(Cell cell) {
    if (cell == null) {
      return "";
    }
    String value = cell.toString().trim();
    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
      if (DateUtil.isCellDateFormatted(cell)) {
        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()); // 日期型
      }
    }
    // String value = cell.toString().trim();
    try {
      // This step is used to prevent Integer string being output with
      // '.0'.
      Float.parseFloat(value);
      value = value.replaceAll("\\.0$", "");
      value = value.replaceAll("\\.0+$", "");
      return value;
    } catch (NumberFormatException ex) {
      return value;
    }
  }

  /**
   * Change excel column letter to integer number
   *
   * @param columns column letter of excel file, like A,B,AA,AB
   * @return
   */
  private int[] getColumnNumber(String[] columns) {
    int[] cols = new int[columns.length];
    for (int i = 0; i < columns.length; i++) {
      cols[i] = getColumnNumber(columns[i]);
    }
    return cols;
  }

  /**
   * Change excel column letter to integer number
   *
   * @param column column letter of excel file, like A,B,AA,AB
   * @return
   */
  private int getColumnNumber(String column) {
    int length = column.length();
    short result = 0;
    for (int i = 0; i < length; i++) {
      char letter = column.toUpperCase().charAt(i);
      int value = letter - 'A' + 1;
      result += value * Math.pow(26, length - i - 1);
    }
    return result - 1;
  }

  /**
   * Change excel column string to integer number array
   *
   * @param sheet excel sheet
   * @param columns column letter of excel file, like A,B,AA,AB
   * @return
   */
  protected static int[] getColumnNumber(Sheet sheet, String columns) {
    // 拆分后的列为动态，采用List暂存
    ArrayList<Integer> result = new ArrayList<Integer>();
    String[] colList = columns.split(SEPARATOR);
    for (String colStr : colList) {
      if (colStr.contains(CONNECTOR)) {
        String[] colArr = colStr.trim().split(CONNECTOR);
        int start = Integer.parseInt(colArr[0]) - 1;
        int end;
        if (colArr.length == 1) {
          end = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum() - 1;
        } else {
          end = Integer.parseInt(colArr[1].trim()) - 1;
        }
        for (int i = start; i <= end; i++) {
          result.add(i);
        }
      } else {
        result.add(Integer.parseInt(colStr) - 1);
      }
    }

    // 将List转换为数组
    int len = result.size();
    int[] cols = new int[len];
    for (int i = 0; i < len; i++) {
      cols[i] = result.get(i).intValue();
    }

    return cols;
  }

  /**
   * remove掉 表头为空的列 author：史熙
   *
   * @param listContent
   * @return
   */
  public static List<List<String>> removeBlankHeadCol(List<List<String>> listContent) {
    List<List<String>> returnValue = new ArrayList<>();
    try {
      if (listContent.size() < 1) {
        return returnValue;
      } else if (listContent.get(0).size() < 2) { // 只有一列的返回空
        return returnValue;
      } else {
        while (true) { // remove掉只有一列的表头,例 [语音话单，，，，，，]
          if (listContent.get(0).get(1).isEmpty()) {
            listContent.remove(0);
          } else {
            break;
          }
        }
        List<Integer> listHeadNum = new ArrayList<>();
        List<String> listOriginalHead = listContent.get(0);
        List<String> listNewHead = new ArrayList<>();
        for (int i = 0; i < listOriginalHead.size(); i++) {
          String head = listOriginalHead.get(i);
          if (null != head && !head.trim().isEmpty() && !"null".equals(head.toLowerCase())) {
            listNewHead.add(head);
            listHeadNum.add(i);
          }
        }
        returnValue.add(listNewHead);
        for (int j = 1; j < listContent.size(); j++) {
          List<String> listRow = new ArrayList<>();
          for (int n = 0; n < listHeadNum.size(); n++) {
            listRow.add(listContent.get(j).get(n));
          }
          returnValue.add(listRow);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      returnValue = listContent;
    }
    return returnValue;
  }

  /** 获取sheet列表 */
  public static List<String> getSheetList(String filePath, InputStream inputStream) {
    int i = 0;
    ArrayList<String> sheetList = new ArrayList<String>(50);
    if (filePath.endsWith(".xls")) { // 03
      try {
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        int num = wb.getNumberOfSheets();
        while (i < num) {
          sheetList.add(wb.getSheetName(i));
          i++;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (filePath.endsWith(".xlsx")) { // excel 07
      try {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        int num = wb.getNumberOfSheets();
        while (i < num) {
          sheetList.add(wb.getSheetName(i));
          i++;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return sheetList;
  }

  /** 读取Excel文件数据 读取该sheet的所有的数据 */
  public static List<List<String>> readExcel(
      String filePath, InputStream inputStream, int sheetIndex) {
    return readExcel(filePath, inputStream, sheetIndex, "1-", "1-");
  }

  /** 读取Excel文件数据 行数输入 格式 num - num 例如 8-10 列数从1到最后 */
  public static List<List<String>> readExcel(
      String filePath, InputStream inputStream, int sheetIndex, String rows) {
    return readExcel(filePath, inputStream, sheetIndex, rows, "1-");
  }

  /** 读取Excel文件数据 行数从1开始到最后 列数输入 格式 num - num 例如 8-10 */
  public List<List<String>> readExcel(
      String filePath, InputStream inputStream, int sheetIndex, String[] columns) {
    return readExcel(filePath, inputStream, sheetIndex, "1-", columns);
  }

  /** 读取Excel文件数据 */
  public static List<List<String>> readExcel(
      String filePath, InputStream inputStream, int sheetIndex, String rows, String columns) {
    List<List<String>> dataList = new ArrayList<List<String>>();
    if (filePath.endsWith(".xls")) { // 03
      try {
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        dataList = readExcel(sheet, rows, getColumnNumber(sheet, columns));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (filePath.endsWith(".xlsx")) { // excel 07
      try {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(sheetIndex);
        dataList = readExcel(sheet, rows, getColumnNumber(sheet, columns));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return dataList;
  }

  /** 读取Excel文件数据 */
  public List<List<String>> readExcel(
      String filePath, InputStream inputStream, int sheetIndex, String rows, String[] columns) {
    int[] cols = getColumnNumber(columns);

    return readExcel(filePath, inputStream, sheetIndex, rows, cols);
  }

  /** 读取Excel文件数据 */
  public List<List<String>> readExcel(
      String filePath, InputStream inputStream, int sheetIndex, String rows, int[] cols) {
    List<List<String>> dataList = new ArrayList<List<String>>();
    if (filePath.endsWith(".xls")) { // 03
      try {
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);

        dataList = readExcel(sheet, rows, cols);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (filePath.endsWith(".xlsx")) { // excel 07
      try {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(sheetIndex);

        dataList = readExcel(sheet, rows, cols);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return dataList;
  }

  /**
   * excel转csv
   *
   * @param io
   * @param path 路径
   * @param csvFileName 新文件
   * @throws IOException
   * @throws InvalidFormatException
   */
  public void parseToCSVFile(InputStream io, String path, String csvFileName)
      throws IOException, InvalidFormatException {
    File dirFile = new File(path);
    if (!dirFile.exists()) dirFile.mkdirs();
    File file = new File(path + File.separator + csvFileName);
    if (file.exists()) file.delete();
    Workbook wb = WorkbookFactory.create(io);
    Sheet sheet = wb.getSheetAt(0);
    int count = 1;
    int size = sheet.getLastRowNum();
    int commit = 5000;
    List<String> validData = new ArrayList<>(5000);
    String rows = "";
    while (count < size) {
      rows = count + "-" + (count + commit - 1);
      validData = getCSVLine(sheet, rows, getColumnNumber(sheet, "1-"));
      ;
      FileUtils.writeLines(file, "utf-8", validData, true);
      // 清空list
      validData.clear();
      count += commit;
    }
    io.close();
  }

  private String parseListToCSVLine(List<String> strList) {
    String listStr = JSONObject.toJSONString(strList);
    return listStr.substring(1, listStr.length() - 1);
  }

  private List<String> getCSVLine(Sheet sheet, String rows, int[] cols) {
    String[] rowArr = rows.trim().split(CONNECTOR);
    int start = Integer.parseInt(rowArr[0]) - 1;
    int end;
    if (rowArr.length == 1) {
      end = sheet.getLastRowNum();
    } else {
      end = Integer.parseInt(rowArr[1].trim()) - 1;
      if (end > sheet.getLastRowNum()) {
        end = sheet.getLastRowNum();
      }
    }
    List<String> dataList = new ArrayList<>();
    for (int i = start; i <= end; i++) {
      dataList.add(parseListToCSVLine(getRowValue(sheet, i, cols)));
    }
    return dataList;
  }
}
