package com.restful.api.exception;

/**
 * リクエストに対して該当する商品が存在しない場合の例外
 *
 * @author yanaokahiroki
 */
public class ProductNotFoundException extends RuntimeException{
  public ProductNotFoundException(String message){
    super(message);
  }
}
