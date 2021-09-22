package com.restful.api.exception;

/**
 * リクエストに対して既にDBに同タイトルの商品が存在する場合の例外
 *
 * @author yanaokahiroki
 */
public class ProductAlreadyExistsException extends RuntimeException {
  public ProductAlreadyExistsException(String message) {
    super(message);
  }
}
