package com.example.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_item_seq")
    @SequenceGenerator(name = "product_item_seq", sequenceName = "PRODUCT_ITEM_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(nullable = false)
    private Double price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}