package com.restful.api.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "setting")
public class ContentTypeConfig {
  /** content-type */
  private String contentType;

  /** 拡張子 */
  private String extension;
}
