package com.restful.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * ログフィルター
 *
 * @author yanaokahiroki
 */
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

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
