package com.swt.common.datainput;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.swt.common.config.UploadConfig;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class CsvInput implements IDataSource {

  @Autowired
  private UploadConfig config;

  public static final char DEFAULT_SEPARATOR = ',';
  public static final char DEFAULT_QUOTE = '"';
  public static final char DEFAULT_ESCAPE_CHAR = '\0';

  private String filePath;
  private char separator;
  private char quote;
  private char escapeChar;

  private boolean first;
  private boolean hasNext;
  public String nextLine;
  private Map<String, String> next;
  private List<String> header;
  private Reader reader;
  private CSVParser csvParser;
  private LineIterator lineIterator;
  private List<String> targetHeader;

  private ErrorCsvRecord errorRecord;
  private boolean errorOutput;

  private long size;
  private long cursor;
  private long errorDate;
  private List<String> rules;
  private String dateRules;
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private Map<String, String> validateString = new HashMap<>();
  private String[] dateType = config.getDateType();

  public CsvInput(String filePath) {
    this(filePath, DEFAULT_SEPARATOR);
  }

  public CsvInput(String filePath, char separator) {
    this(filePath, separator, DEFAULT_QUOTE);
  }

  public CsvInput(String filePath, char separator, char quote) {
    this(filePath, separator, quote, DEFAULT_ESCAPE_CHAR);
  }

  public CsvInput(String filePath, char separator, char quote, char escapeChar) {
    this.filePath = filePath;
    this.separator = separator;
    this.quote = quote;
    this.escapeChar = escapeChar;

    this.header = new ArrayList<String>();
    first = true;
    hasNext = false;

    validateString.put("int", "整数");
    validateString.put("float", "小数");
    validateString.put("date", "日期");
    validateString.put("sfz", "身份证");
    setErrorOutput(true);
  }

  public void setRules(List<String> rules) {
    this.rules = rules;
  }

  private void setErrorOutput(boolean errorOutput) {
    File file = new File(config.getErrDataDir());
    if (!file.exists()) {
      file.mkdirs();
    }
    this.errorOutput = errorOutput;
  }

  @Override
  public List<String> getHeader() throws IOException {
    if (this.header.size() > 0) {
      return this.header;
    }
    if (first) {
      if (errorOutput) {
        errorRecord = new ErrorCsvRecord(generateErrorFilePath(filePath));
      }
      reader = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
      first = false;
    }
    csvParser =
        new CSVParserBuilder()
            .withEscapeChar(escapeChar)
            .withSeparator(separator)
            .withQuoteChar(quote)
            .build();
    lineIterator = new LineIterator(reader);
    if (lineIterator.hasNext()) {
      String nextLine = lineIterator.nextLine();
      if (nextLine != null && !nextLine.trim().isEmpty()) {
        String[] next = csvParser.parseLine(nextLine);
        this.header.addAll(Arrays.asList(next));
        if (errorOutput) {
          errorRecord.setCsvHeader("\"错误原因\"," + nextLine);
        }
      }
    }
    cursor = 1;
    return this.header;
  }

  @Override
  public boolean hasNext() throws IOException {
    if (first) {
      getHeader();
      first = false;
    }
    this.next = new LinkedHashMap<String, String>();
    do {
      if (lineIterator.hasNext()) {
        String nextLine = lineIterator.nextLine();
        cursor++;
        /* 数据跨多行时，拼接多行，将其视为一行供parseLine()方法处理 */
        while (dataSpanMultiLines(nextLine)) {
          nextLine += "\n" + lineIterator.nextLine();
          cursor++;
        }
        String nextStr;
        String headStr;
        int index;
        if (nextLine != null && !nextLine.trim().isEmpty()) {

          hasNext = true;
          try {
            String[] next = parseLine(nextLine);
            if (next != null && next.length == header.size()) {
              this.nextLine = nextLine;
              for (int i = 0; i < header.size(); i++) {
                headStr = header.get(i);
                nextStr = next[i];
                if (targetHeader != null) {
                  index = targetHeader.indexOf(headStr);
                  if (index > -1) {
                    if (rules != null && !StringUtils.isEmpty(nextStr)) {
                      nextStr = validateError(nextStr, rules.get(index));
                      if (nextStr == null) {
                        errorRecord.addRecord(
                            "\""
                                + headStr
                                + "字段"
                                + validateString.get(rules.get(index))
                                + "校验失败\","
                                + nextLine);
                        this.next.clear();
                        break;
                      }
                    }
                    this.next.put(headStr, nextStr);
                  }
                } else {
                  this.next.put(headStr, nextStr);
                }
              }
            } else {
              if (errorOutput) {
                errorRecord.addRecord("\"字段数不匹配\"," + nextLine);
              }
            }
          } catch (Exception e) {
            if (errorOutput) {
              errorRecord.addRecord("\"该行解析失败\"," + nextLine);
            }
            e.printStackTrace();
          }
        }
      } else {
        hasNext = false;
        close();
        break;
      }
    } while (this.next.size() == 0);
    return hasNext;
  }

  @Override
  public Map<String, String> next() {
    return next;
  }

  @Override
  public void close() {
    if (reader != null) {
      try {
        reader.close();
        reader = null;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (lineIterator != null) {
      lineIterator.close();
      lineIterator = null;
    }
    if (errorOutput) {
      if (errorRecord != null) {
        errorRecord.closeWriter();
        errorRecord = null;
      }
    }
  }

  /**
   * 判断数据是否跨越多行
   *
   * @param nextLine
   * @return
   */
  protected boolean dataSpanMultiLines(String nextLine) {
    if (lineIterator.hasNext() && quote != '\0') {
      if (!nextLine.endsWith("\"") && nextLine.startsWith("\"")) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断数据是否符合校验规则
   *
   * @param nextStr
   * @return
   */
  protected String validateError(String nextStr, String rule) {
    String returnVal = null;
    switch (rule) {
      case "date":
        if (dateRules == null) {
          if (errorDate < 30) {
            for (String dateFormatStr : dateType) {
              returnVal = formatDate(dateFormatStr, nextStr);
              if (returnVal != null) {
                dateRules = dateFormatStr;
                break;
              }
            }
            if (dateRules == null) {
              errorDate++;
            }
          }
        } else {
          returnVal = formatDate(dateRules, nextStr);
        }
        break;
      case "int":
        if (Pattern.matches("^-?[1-9]\\d*$|0", nextStr)) {
          returnVal = nextStr;
        }
        break;
      case "float":
        if (Pattern.matches("^-?[1-9]\\d*\\.?\\d*|-?0\\.\\d*[1-9]\\d*|0$", nextStr)) {
          returnVal = nextStr;
        }
        break;
      case "sfz": // 18位身份证
        if (Pattern.matches(
            "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",
            nextStr)) {
          returnVal = nextStr;
        }
        break;
      default:
        returnVal = nextStr;
    }
    return returnVal;
  }

  public String formatDate(String rule, String nextLine) {
    try {
      Date parse = new SimpleDateFormat(rule).parse(nextLine);
      return simpleDateFormat.format(parse);
    } catch (ParseException e) {
      return null;
    }
  }

  protected String[] parseLine(String line) {
    String[] result = null;
    try {
      result = csvParser.parseLine(line);
    } catch (IOException e) {

    }
    return result;
  }

  public String getNextLine() {
    return nextLine;
  }

  @Override
  public String getFilePath() {
    return new File(filePath).getAbsolutePath();
  }

  @Override
  public long getSize() throws IOException {
    File file = new File(filePath);
    if (!file.exists()) {
      return 0;
    }
    if (size == 0) {
      Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
      LineIterator iter = new LineIterator(reader);
      while (iter.hasNext()) {
        String nextLine = iter.next();
        if (nextLine != null && !nextLine.trim().isEmpty()) {
          size++;
        }
      }
      reader.close();
      iter.close();
      if (size > 0) {
        size -= 1; // 不包括文件头
      }
    }
    return size;
  }

  private String generateErrorFilePath(String originFilePath) {
    String errorFilePath = null;
    File file = new File(originFilePath);
    if (file.isFile()) {
      errorFilePath = config.getErrDataDir() + File.separator + file.getName().replace("tmp_", "");
    }
    return errorFilePath;
  }

  public String getErrorFilePath() {
    return errorRecord.getErrorFilePath();
  }

  public static void rename(String path) {
    File file = new File(path);
    if (file.isDirectory()) {
      String fileName = URLDecoder.decode(file.getName());
      System.out.println(fileName);
      System.out.println(file.renameTo(new File(file.getParent() + File.separator + fileName)));
    }
  }

  public void setTargetHeader(List<String> targetHeader) {
    this.targetHeader = targetHeader;
  }
}
