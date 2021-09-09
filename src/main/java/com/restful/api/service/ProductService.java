package com.restful.api.service;

import com.restful.api.entity.Product;
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
   * 商品情報を1件取得する
   * @param id 商品ID
   * @return 商品情報
   */
  public Product getProductById(int id){
    return productRepository.getById(id);
  }

  /**
   * 商品情報を全件取得する
   *
   * @return 商品情報List
   */
  public List<Product> getAllProduct(){
    return productRepository.findAll();
  }

  /**
   * 商品情報を登録する
   *
   * @param product 商品情報
   * @return 登録した商品情報
   */
  public Product registerProduct(Product product){
    return productRepository.save(product);
  }

  /**
   * 商品情報を更新する
   *
   * @param id 商品ID
   * @param updatedProduct 更新する商品情報
   * @return 更新した商品情報
   */
  public Product updateProduct(int id,Product updatedProduct){
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
  public void deleteProduct(int id){
    productRepository.deleteById(id);
  }


}
