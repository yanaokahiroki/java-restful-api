package com.restful.api.entity;

import lombok.Data;

/**
 * エラーレスポンス
 *
 * @author yanaokahiroki
 */
@Data
public class ErrorResponse {
  private int status;
  private String message;

  public ErrorResponse(int status, String message){
    this.status = status;
    this.message = message;
  }
}
