package com.restful.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.Product;
import lombok.Data;

import java.time.LocalDateTime;

/** 商品情報Dto */
@Data
public class ProductDto {
  private int id;

  private String title;

  private String body;

  private int price;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  /**
   * コンストラクタ
   *
   * @param product 商品情報エンティティ
   */
  public ProductDto(Product product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.body = product.getBody();
    this.price = product.getPrice();
    this.createdAt = product.getCreatedAt();
    this.updatedAt = product.getUpdatedAt();
  }
}
