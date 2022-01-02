package com.restful.api.service;

import com.restful.api.dto.ProductDto;
import com.restful.api.entity.Product;
import com.restful.api.exception.ProductAlreadyExistsException;
import com.restful.api.exception.ProductNotFoundException;
import com.restful.api.form.ProductForm;
import com.restful.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

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
  private final MessageSource messageSource;

  /**
   * 商品情報を登録する
   *
   * @param productForm 商品情報
   * @param locale 国
   * @return 登録した商品情報
   */
  public ProductDto registerProduct(ProductForm productForm, Locale locale) {
    if (existsTitle(productForm)) {
      throw new ProductAlreadyExistsException(
          messageSource.getMessage(
              "error.product.already.exist", new String[] {productForm.getTitle()}, locale));
    }
    Product product = new Product();
    product.setTitle(productForm.getTitle());
    product.setBody(productForm.getBody());
    product.setPrice(productForm.getPrice());
    Product registeredProduct = productRepository.save(product);
    return new ProductDto(registeredProduct);
  }

  /**
   * 商品情報を1件取得する リクエストされた商品IDの商品がDBに存在しない場合には例外をスロー
   *
   * @param id 商品ID
   * @param locale 国
   * @return 商品情報
   */
  public ProductDto getProductById(int id, Locale locale) {
    Product product = findProductById(id, locale);
    return new ProductDto(product);
  }

  /**
   * 商品情報を全件取得する
   *
   * @return 商品情報List
   */
  public List<ProductDto> getAllProduct() {
    List<Product> productList = productRepository.findAll();
    return productList.stream().map(ProductDto::new).toList();
  }

  /**
   * 商品情報を商品名で取得する
   *
   * @param title 商品名
   * @return titleに部分一致する商品情報List
   */
  public List<ProductDto> getProductByTitle(String title) {
    List<Product> productList = productRepository.findProductByTitleContains(title);
    return productList.stream().map(ProductDto::new).toList();
  }

  /**
   * 商品情報を更新する
   *
   * @param id 商品ID
   * @param targetProduct 更新する商品情報
   * @param locale 国
   * @return 更新した商品情報
   */
  public ProductDto updateProduct(int id, ProductForm targetProduct, Locale locale) {
    Product product = findProductById(id, locale);
    product.setTitle(targetProduct.getTitle());
    product.setBody(targetProduct.getBody());
    product.setPrice(targetProduct.getPrice());
    Product updatedProduct = productRepository.saveAndFlush(product);
    return new ProductDto(updatedProduct);
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
   * 商品IDで商品を取得する
   *
   * @param id 商品ID
   * @param locale 国
   * @return 商品情報エンティティ
   */
  private Product findProductById(int id, Locale locale) {
    return productRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ProductNotFoundException(
                    messageSource.getMessage(
                        "error.product.notFound", new String[] {String.valueOf(id)}, locale)));
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
