package com.restful.api.configuration;

import com.restful.api.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * ログフィルター用設定<br>
 * {@link com.restful.api.filter.LoggingFilter}
 *
 * @author yanaokahiroki
 */
@Configuration
public class FilterConfig {
  /** ログフィルターBean */
  @Bean
  public FilterRegistrationBean<LoggingFilter> filterRegistrationBean() {
    FilterRegistrationBean<LoggingFilter> bean = new FilterRegistrationBean<>(new LoggingFilter());
    bean.addUrlPatterns("/api/v1/*");
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }
}
