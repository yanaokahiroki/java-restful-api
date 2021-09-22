package com.restful.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * エラー詳細Dto
 *
 * @author yanaokahiroki
 */
@Data
@AllArgsConstructor
public class ErrorDetailDto {
  private String key;
  private String detail;
}
