package com.restful.api.exception;

import com.restful.api.controller.ProductController;
import com.restful.api.entity.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * API共通例外ハンドラー
 *
 * @author yanaokahiroki
 */
@Slf4j
@RestControllerAdvice(assignableTypes = ProductController.class)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
          Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request){
    ErrorResponse errorResponse = new ErrorResponse(status.value(), ex.getMessage());
    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
  }

  /**
   * リクエストに対して該当する商品が存在しない場合の例外ハンドラー
   *
   * @param ex 例外
   * @param request リクエスト
   */
  @ExceptionHandler(value = ProductNotFoundException.class)
  protected ResponseEntity<Object> handleProductNotFound(ProductNotFoundException ex, WebRequest request){
    HttpStatus status = HttpStatus.NOT_FOUND;
    ErrorResponse response = new ErrorResponse(status.value(), ex.getMessage());
    return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * 500エラー
   *
   * @param ex 例外
   * @param request リクエスト
   */
  @ExceptionHandler(value = Exception.class)
  protected ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorResponse response = new ErrorResponse(status.value(), "サーバー内でエラーが発生しています。");
    return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }
}
