package com.restful.api.repository;

import com.restful.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DBとのやり取りをする
 *
 * @author yanaokahiroki
 */
public interface ProductRepository extends JpaRepository<Product, Integer> { }
