package com.restful.api.dto;

import lombok.Data;

import java.util.List;

/**
 * エラーレスポンス
 *
 * @author yanaokahiroki
 */
@Data
public class ErrorResponseDto {
  private int status;
  private String message;
  private List<ErrorDetailDto> errorList;

  public ErrorResponseDto(int status, String message){
    this.status = status;
    this.message = message;
  }

  public ErrorResponseDto(int status, String message, List<ErrorDetailDto> errorList){
    this.status = status;
    this.message = message;
    this.errorList = errorList;
  }
}
