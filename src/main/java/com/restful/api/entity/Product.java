package com.restful.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 商品テーブルエンティティ
 * 扱うデータを定義
 *
 * @author yanaokahiroki
 */
@Data
@Entity
@JsonIgnoreProperties("hibernateLazyInitializer")
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(nullable = false)
  private String body;

  @Column(nullable = false)
  private int price;

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}
