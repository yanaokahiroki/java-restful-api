package com.restful.api.exception;

import com.restful.api.dto.ErrorDetailDto;
import com.restful.api.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * API共通例外ハンドラー
 *
 * @author yanaokahiroki
 */
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
  private final MessageSource messageSource;

  /**
   * リクエストに対して該当する商品が存在しない場合の例外ハンドラー
   *
   * @param ex 例外
   * @param request リクエスト
   */
  @ExceptionHandler(value = ProductNotFoundException.class)
  public ResponseEntity<Object> handleProductNotFound(
      ProductNotFoundException ex, WebRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ErrorResponseDto response = new ErrorResponseDto(status.value(), ex.getMessage());
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
      ProductAlreadyExistsException ex, WebRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorResponseDto response = new ErrorResponseDto(status.value(), ex.getMessage());
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    // 捕捉したバリデーション例外を取得してその1つ1つをDTOに詰めていく
    List<ErrorDetailDto> validatedErrorList =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                fieldError ->
                    new ErrorDetailDto(fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.toList());
    String message = messageSource.getMessage("error.validate", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message, validatedErrorList);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String message = messageSource.getMessage("error.notSupportedMethod", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.notSupportedMediaType", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
      HttpMediaTypeNotAcceptableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.notAcceptableMediaType", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleMissingPathVariable(
      MissingPathVariableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.missingPathVariable", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.missingServletRequestParameter", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.servletRequestBinding", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleConversionNotSupported(
      ConversionNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.notSupportedConversion", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.typeMismatch", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.notReadableMessage", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleHttpMessageNotWritable(
      HttpMessageNotWritableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.notWritableMessage", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.missingServletRequestPart", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleBindException(
      BindException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.bind", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request){
    String message = messageSource.getMessage("error.asyncRequestTimeout", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * CSVファイルのファイル超過した場合の例外ハンドラー
   * 
   * @param ex 例外
   * @param request リクエスト
   */
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<Object> handleMaxUploadSizeExceededException(
      MaxUploadSizeExceededException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String message = messageSource.getMessage("error.maxSizeExceeded", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, headers, status, request);
  }

  /**
   * 404エラー
   *
   * <p>{@inheritDoc}
   */
  @Override
  public ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String message = messageSource.getMessage("error.notFound", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }

  /**
   * 500エラー
   *
   * @param ex 例外
   * @param request リクエスト
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = messageSource.getMessage("error.internal", null, request.getLocale());
    ErrorResponseDto response = new ErrorResponseDto(status.value(), message);
    return super.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
  }
}
