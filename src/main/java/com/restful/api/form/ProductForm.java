package com.restful.api.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 商品情報フォーム
 *
 * @author yanaokahiroki
 */
@Data
public class ProductForm {
  @NotBlank(message = "{product.title.required}")
  @Size(max = 50, message = "{product.title.max}")
  private String title;

  @NotBlank(message = "{product.body.required}")
  @Size(max = 100, message = "{product.body.max}")
  private String body;

  @NotNull(message = "{product.price.required}")
  @Min(value = 1, message = "{product.price.min}")
  @Max(value = 1_000_000, message = "{product.price.max}")
  private int price;
}
