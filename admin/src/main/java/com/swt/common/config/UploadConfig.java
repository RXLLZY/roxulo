package com.swt.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by HuGuowei on 18-6-14.
 *
 * <p>Say something when you want to bibi.<br>
 */
@ConfigurationProperties(prefix = "map.file")
@Configuration
public class UploadConfig {
  private String localDir;
  private String hdfsDir;
  private String[] dateType;
  private String errDataDir;
  private String validDataDir;

  public String getLocalDir() {
    return localDir;
  }

  public void setLocalDir(String localDir) {
    this.localDir = localDir;
  }

  public String getHdfsDir() {
    return hdfsDir;
  }

  public void setHdfsDir(String hdfsDir) {
    this.hdfsDir = hdfsDir;
  }

  public String[] getDateType() {
    return dateType;
  }

  public void setDateType(String[] dateType) {
    this.dateType = dateType;
  }

  public String getErrDataDir() {
    return errDataDir;
  }

  public void setErrDataDir(String errDataDir) {
    this.errDataDir = errDataDir;
  }

  public String getValidDataDir() {
    return validDataDir;
  }

  public void setValidDataDir(String validDataDir) {
    this.validDataDir = validDataDir;
  }
}
