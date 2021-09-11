package com.restful.api.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * リクエストに対して既にDBに同タイトルの商品が存在する場合の例外
 *
 * @author yanaokahiroki
 */
@Slf4j
public class ProductAlreadyExistsException extends RuntimeException{
  public ProductAlreadyExistsException(String message){
    super(message);
    log.warn(message);
  }
}
