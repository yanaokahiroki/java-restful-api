package com.restful.api.controller;

import com.restful.api.entity.Product;
import com.restful.api.form.ProductForm;
import com.restful.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * 商品情報Controller クライアントリクエスト、レスポンスを処理
 *
 * @author yanaokahiroki
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
  private final ProductService productService;

  /**
   * 商品情報を登録する
   *
   * @param product 商品情報
   * @return 登録した商品情報
   */
  @PostMapping
  public ResponseEntity<Product> registerProduct(
      @Valid @RequestBody ProductForm product, Locale locale) {
    return new ResponseEntity<>(
        productService.registerProduct(product, locale), HttpStatus.CREATED);
  }

  /**
   * 商品情報を1件取得する
   *
   * @param id 商品ID
   * @return 商品情報
   */
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable int id, Locale locale) {
    return new ResponseEntity<>(productService.getProductById(id, locale), HttpStatus.OK);
  }

  /**
   * 商品情報を全件取得する
   *
   * @return 商品情報List
   */
  @GetMapping
  public ResponseEntity<List<Product>> getAllProduct() {
    return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
  }

  /**
   * 商品情報を更新する
   *
   * @param id 商品ID
   * @param product 更新する商品情報
   * @return 更新した商品情報
   */
  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(
      @PathVariable int id, @RequestBody ProductForm product, Locale locale) {
    return new ResponseEntity<>(productService.updateProduct(id, product, locale), HttpStatus.OK);
  }

  /**
   * 商品情報を削除する
   *
   * @param id 商品ID
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable int id) {
    productService.deleteProduct(id);
  }
}
