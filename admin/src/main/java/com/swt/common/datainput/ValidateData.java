package com.swt.common.datainput;

import com.swt.common.config.ESConfig;
import com.swt.common.config.UploadConfig;
import com.swt.common.utils.ConfigConstant;
import com.swt.common.utils.HDFSUtils;
import com.swt.modules.calculatelibrary.dao.DataResourceMapper;
import com.swt.modules.calculatelibrary.entity.DataField;
import com.swt.modules.calculatelibrary.entity.DataResource;
import com.swt.modules.calculatelibrary.entity.TableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ValidateData {
  private final Logger logger = Logger.getLogger(ValidateData.class.getName());
  private final UploadConfig uploadConfig;
  private final ESConfig esConfig;
  private final HDFSUtils hdfs;
  private final DataResourceMapper dsMapper;
  private final int MAX_COMMIT = 5000; // 每次提交的最大数量
  private final int ES_EACH_COMMIT_SIZE = 120000; // 每次提交的最大数量

  @Autowired
  public ValidateData(
          UploadConfig uploadConfig, ESConfig esConfig, HDFSUtils hdfs, DataResourceMapper dsMapper) {
    this.uploadConfig = uploadConfig;
    this.esConfig = esConfig;
    this.hdfs = hdfs;
    this.dsMapper = dsMapper;
  }

  public ValidationReturnModel ValidateAndSaveData(GuessMetaModel gmmIn) {
    ValidationReturnModel vrmRetValue = new ValidationReturnModel();
    long insertNum = 0L;
    long invalidNum = 0L;

    DataHeadList insertDataHead = gmmIn.getData().getData_head();
    String fileName = gmmIn.getFilename();
    List<String> listOldName = insertDataHead.getOld_name(); // 每行只取选中的字段

    // 查询符合表头的的es数据
    String esIndexName = getModel(listOldName);
    esIndexName =
        "".equalsIgnoreCase(esIndexName)
            ? "add_" + LocalDateTime.now().format(ConfigConstant.time).toLowerCase()
            : esIndexName;
    insertDataHead.setEs_name(esIndexName);

    // 校验数据并生成CSV
    String finalFileName = fileName.substring(4, fileName.length()); // 去掉前面生成 tmp_,生成上传到hdfs的csv文件名
    String hdfsFilePath = uploadConfig.getHdfsDir() + File.separator + finalFileName;
    insertDataHead.setHdfsPath(hdfsFilePath);
    // 保存到数据库并获得fileID
    Integer fileID = insertDataResourseInDB(insertDataHead);
    try {
      long size =
          dataFormatVerification(fileName, listOldName, finalFileName, insertDataHead.getRules());
      if (size > 0) {
        // 传到hdfs
        String srcFile = uploadConfig.getValidDataDir() + File.separator + finalFileName;
        hdfs.put2HDFS(srcFile, hdfsFilePath);
      }
      CsvInput errorInput =
          new CsvInput(uploadConfig.getErrDataDir() + File.separator + finalFileName);
      invalidNum = errorInput.getSize();
      vrmRetValue.setInput_num(insertNum);
      vrmRetValue.setErr_num(invalidNum);
      vrmRetValue.setFileID(fileID);
      // 向字段信息表中添加字段信息
      if (size > 0 && insertNum > 0) {
        insertTableFieldInDB(insertDataHead, fileID + "");
      } else {
        throw new IllegalArgumentException();
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "自定义数据上传失败", e);
      removeErrorFile(fileID);
    }
    return vrmRetValue;
  }

  /**
   * @param uploadFilePath 上传的文件的路径
   * @param targetHeader 选中的字段
   * @param finalFileName 目标文件名
   * @param rules 字段类型
   * @throws Exception 文件不存在
   */
  private long dataFormatVerification(
      String uploadFilePath, List<String> targetHeader, String finalFileName, List<String> rules)
      throws Exception {
    File sourceFile = new File(uploadConfig.getLocalDir() + File.separator + uploadFilePath);
    String tarPath = uploadConfig.getValidDataDir() + File.separator + finalFileName;
    if (!sourceFile.exists()) {
      throw new FileNotFoundException("文件" + sourceFile.getAbsolutePath() + "不存在！");
    }
    CsvInput input = new CsvInput(sourceFile.getAbsolutePath());
    input.setRules(rules);
    input.setTargetHeader(targetHeader);
    CsvWriter output =
        new CsvWriter()
            .withOutputPath(tarPath)
            .withDelimiter(",")
            .withQuote("\"")
            .withTrim(true)
            .withHeader(targetHeader);
    Map<String, String> next;
    while (input.hasNext()) {
      next = input.next();
      output.write(next);
    }
    output.close();
    input.close();
    return CSVAndExcelReader.size(tarPath);
  }

  private void insertTableFieldInDB(DataHeadList insertDataHead, String fileID) {
    String hdfsPath = insertDataHead.getHdfsPath();
    List<String> rules = insertDataHead.getRules();
    List<String> oldNameList = insertDataHead.getOld_name();
    List<String> newNameList = insertDataHead.getNew_name();
    for (int i = 0; i < oldNameList.size(); i++) {
      DataField df = new DataField();
      df.setTable(insertDataHead.getHdfsPath());
      df.setTableComment(insertDataHead.getTable_comment());
      df.setField(oldNameList.get(i));
      df.setFieldComment(newNameList.get(i));
      if ("date".equals(rules.get(i))) {
        df.setFieldType(rules.get(i));
        df.setIsIdNumber(2);
      } else if ("sfz".equals(rules.get(i))) {
        df.setFieldType("string");
        df.setIsIdNumber(1);
      } else {
        df.setFieldType(rules.get(i));
        df.setIsIdNumber(0);
      }
      dsMapper.addField(df);
    }
  }

  /**
   * 删除数据源
   *
   * <p>删除增加了数据源，但是数据导入失败的数据源
   *
   * @param fileID 数据源id
   */
  private void removeErrorFile(int fileID) {
    try {
      dsMapper.deleteDataResource(fileID);
    } catch (Exception ignore) {
    }
  }

  /**
   * 添加数据源到数据库
   *
   * @param insertDataHead 数据源描述信息
   * @return 数据源id
   */
  private Integer insertDataResourseInDB(DataHeadList insertDataHead) {
    DataResource ds = new DataResource();
    ds.setName(insertDataHead.getTable_comment());
    ds.setType("自有数据");
    ds.setDivision("自有数据");
    ds.setTable_name(insertDataHead.getHdfsPath());
    ds.setTable_name_preview(insertDataHead.getEs_name());
    ds.setType_id(9);
    ds.setCreator(insertDataHead.getCreator());
    dsMapper.addDataResource(ds);
    return ds.getData_resourse_id();
  }

  /**
   * 获取es字段类型
   *
   * @param oldNameList 字段
   * @return 索引名称
   */
  private String getModel(List<String> oldNameList) {
    // 获取tablename和tablenamepreview  其中tablenamepreview为es所用
    Map<String, List<String>> colMap = new HashMap<>();
    List<String> conformList = new ArrayList<>(); // 符合模版的es名
    List<TableName> nameList = dsMapper.privateDs();
    for (TableName table : nameList) {
      colMap.put(table.getTableNamePreview(), dsMapper.cols(table.getTableName()));
    }
    String esName = "";
    // 查询匹配的模版
    for (Entry<String, List<String>> entry : colMap.entrySet()) {
      if (oldNameList.containsAll(entry.getValue())) {
        if (conformList.size() < entry.getValue().size()) {
          conformList = entry.getValue();
          esName = entry.getKey();
        }
      }
    }
    return esName;
  }
}
