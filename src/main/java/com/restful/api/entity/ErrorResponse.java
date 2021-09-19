package com.restful.api.entity;

import lombok.Data;

import java.util.List;

/**
 * エラーレスポンス
 *
 * @author yanaokahiroki
 */
@Data
public class ErrorResponse {
  private int status;
  private String message;
  private List<String> errorList;

  public ErrorResponse(int status, String message){
    this.status = status;
    this.message = message;
  }

  public ErrorResponse(int status, String message, List<String> errorList){
    this.status = status;
    this.message = message;
    this.errorList = errorList;
  }
}
