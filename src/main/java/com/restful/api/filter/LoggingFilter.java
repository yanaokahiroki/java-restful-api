package com.restful.api.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ログフィルター
 *
 * @author yanaokahiroki
 */
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

  /** {@inheritDoc} */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    filterChain.doFilter(request, response);

    String host = request.getHeader("host");
    MDC.put("host", host != null ? host : "-");

    stopWatch.stop();

    log.info(
        "{}\t{}\t{}\t{}ms",
        request.getMethod(),
        request.getRequestURI(),
        response.getStatus(),
        stopWatch.getTotalTimeMillis());
  }
}
