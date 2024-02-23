package com.restful.api.dto;

import java.util.List;
import lombok.Data;

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

  /**
   * エラーレスポンスコンストラクタ
   *
   * @param status HTTPステータス
   * @param message レスポンスメッセージ
   */
  public ErrorResponseDto(int status, String message) {
    this.status = status;
    this.message = message;
  }

  /**
   * エラーレスポンスコンストラクタ
   *
   * @param status HTTPステータス
   * @param message レスポンスメッセージ
   * @param errorList エラー詳細List
   */
  public ErrorResponseDto(int status, String message, List<ErrorDetailDto> errorList) {
    this.status = status;
    this.message = message;
    this.errorList = errorList;
  }
}
