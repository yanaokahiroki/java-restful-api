package com.restful.api.exception;

import com.restful.api.entity.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * API共通例外ハンドラー
 *
 * @author yanaokahiroki
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
  /**
   * リクエストに対して該当する商品が存在しない場合の例外ハンドラー
   *
   * @param ex 例外
   * @param request リクエスト
   */
  @ExceptionHandler(value = ProductNotFoundException.class)
  public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException ex, WebRequest request){
    HttpStatus status = HttpStatus.NOT_FOUND;
    ErrorResponse response = new ErrorResponse(status.value(), ex.getMessage());
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * リクエストに対して既にDBに同タイトルの商品が存在する場合の例外ハンドラー
   *
   * @param ex 例外
   * @param request リクエスト
   */
  @ExceptionHandler(value = ProductAlreadyExistsException.class)
  public ResponseEntity<Object> handleProductAlreadyExistsException(
          ProductAlreadyExistsException ex, WebRequest request){
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorResponse response = new ErrorResponse(status.value(), ex.getMessage());
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * 404エラー
   *
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleNoHandlerFoundException(
          NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
    ErrorResponse response = new ErrorResponse(status.value(), "存在しないURLです。");
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * 500エラー
   *
   * @param ex 例外
   * @param request リクエスト
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorResponse response = new ErrorResponse(status.value(), "サーバー内でエラーが発生しています。");
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }
}
