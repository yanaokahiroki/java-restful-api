package com.restful.api.controller;

import com.restful.api.entity.Product;
import com.restful.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品情報Controller
 * クライアントリクエスト、レスポンスを処理
 *
 * @author yanaokahiroki
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  /**
   * 商品情報を登録する
   *
   * @param product 商品情報
   * @return 登録した商品情報
   */
  @PostMapping
  public ResponseEntity<Product> registerProduct(@RequestBody Product product){
    return new ResponseEntity<>(productService.registerProduct(product),HttpStatus.CREATED);
  }

  /**
   * 商品情報を1件取得する
   *
   * @param id 商品ID
   * @return 商品情報
   */
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable int id){
    return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
  }

  /**
   * 商品情報を全件取得する
   *
   * @return 商品情報List
   */
  @GetMapping
  public ResponseEntity<List<Product>> getAllProduct(){
    return new ResponseEntity<>(productService.getAllProduct(),HttpStatus.OK);
  }

  /**
   * 商品情報を更新する
   *
   * @param id 商品ID
   * @param product 更新する商品情報
   * @return 更新した商品情報
   */
  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product){
    return new ResponseEntity<>(productService.updateProduct(id,product),HttpStatus.OK);
  }

  /**
   * 商品情報を削除する
   *
   * @param id 商品ID
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable int id){
    productService.deleteProduct(id);
  }
}
