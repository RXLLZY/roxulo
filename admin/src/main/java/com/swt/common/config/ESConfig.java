package com.swt.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by HuGuowei on 18-6-10.
 *
 * <p>Say something when you want to bibi.<br>
 */
@Configuration
@ConfigurationProperties(prefix = "map.es")
public class ESConfig {
  private String host = "http://localhost";
  private Integer port = 9200;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }
}
