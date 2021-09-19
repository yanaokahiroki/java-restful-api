package com.restful.api.form;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * 商品情報フォーム
 *
 * @author yanaokahiroki
 */
@Data
public class ProductForm {
  @NotBlank(message = "必須項目です。")
  @Size(max = 50, message = "50文字以下で入力してください。")
  private String title;

  @NotBlank(message = "必須項目です。")
  @Size(max = 100, message = "100文字以下で入力してください。")
  private String body;

  @NotNull(message = "必須項目です。")
  @Min(value = 1, message = "1円以上で入力してください。")
  @Max(value = 1_000_000, message = "100万円以下で入力してください。")
  private int price;
}
