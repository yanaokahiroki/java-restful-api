package com.restful.api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * バリデーションメッセージ構成Bean
 *
 * @author yanaokahiroki
 */
@Configuration
@RequiredArgsConstructor
public class ValidationMessageConfiguration {
  private final MessageSource messageSource;

  /**
   * バリデーションメッセージのプロパティファイルを
   * デフォルトのValidateMessageSourceからmessageSourceに変更しBeanとして定義
   */
  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.setValidationMessageSource(messageSource);
    return localValidatorFactoryBean;
  }
}
