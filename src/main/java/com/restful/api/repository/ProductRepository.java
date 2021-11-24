package com.restful.api.repository;

import com.restful.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * DBとのやり取りをする
 *
 * @author yanaokahiroki
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

  Optional<Product> findByTitleEquals(String title);

  /**
   * 商品名にtitleが含まれている商品情報を取得する
   *
   * @param title 商品名
   * @return 商品情報List
   */
  List<Product> findProductByTitleContains(String title);
}
