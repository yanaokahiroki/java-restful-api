package com.restful.api.service;

import com.restful.api.entity.Product;
import com.restful.api.exception.ProductAlreadyExistsException;
import com.restful.api.exception.ProductNotFoundException;
import com.restful.api.form.ProductForm;
import com.restful.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 商品情報に関するビジネスロジック
 *
 * @author yanaokahiroki
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  /**
   * 商品情報を登録する
   *
   * @param productForm 商品情報
   * @return 登録した商品情報
   */
  public Product registerProduct(ProductForm productForm) {
    if (existsTitle(productForm)) {
      throw new ProductAlreadyExistsException("商品名：" + productForm.getTitle() + "は既に存在しています。");
    }
    Product product = new Product();
    product.setTitle(productForm.getTitle());
    product.setBody(productForm.getBody());
    product.setPrice(productForm.getPrice());
    return productRepository.save(product);
  }

  /**
   * 商品情報を1件取得する リクエストされた商品IDの商品がDBに存在しない場合には例外をスロー
   *
   * @param id 商品ID
   * @return 商品情報
   */
  public Product getProductById(int id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ProductNotFoundException("ID：" + id + "の商品は存在しません。"));
  }

  /**
   * 商品情報を全件取得する
   *
   * @return 商品情報List
   */
  public List<Product> getAllProduct() {
    return productRepository.findAll();
  }

  /**
   * 商品情報を更新する
   *
   * @param id 商品ID
   * @param updatedProduct 更新する商品情報
   * @return 更新した商品情報
   */
  public Product updateProduct(int id, ProductForm updatedProduct) {
    Product product = getProductById(id);
    product.setTitle(updatedProduct.getTitle());
    product.setBody(updatedProduct.getBody());
    product.setPrice(updatedProduct.getPrice());
    return productRepository.save(product);
  }

  /**
   * 商品情報を削除する
   *
   * @param id 商品ID
   */
  public void deleteProduct(int id) {
    productRepository.deleteById(id);
  }

  /**
   * 既にDBに登録済みのtitleかどうか判定する
   *
   * @param product 商品
   * @return DBに既に登録済みの商品titleであるならtrue
   */
  private boolean existsTitle(ProductForm product) {
    return productRepository.findByTitleEquals(product.getTitle()).isPresent();
  }
}
