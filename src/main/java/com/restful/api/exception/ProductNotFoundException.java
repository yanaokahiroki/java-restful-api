package com.restful.api.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * リクエストに対して該当する商品が存在しない場合の例外
 *
 * @author yanaokahiroki
 */
@Slf4j
public class ProductNotFoundException extends RuntimeException{
  public ProductNotFoundException(String message){
    super(message);
    log.warn(message);
  }
}
