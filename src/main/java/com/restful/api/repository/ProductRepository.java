package com.restful.api.repository;

import com.restful.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * DBとのやり取りをする
 *
 * @author yanaokahiroki
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

  Optional<Product> findByTitleEquals(String title);
}
