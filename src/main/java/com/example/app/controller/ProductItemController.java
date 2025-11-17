package com.example.app.controller;

import com.example.app.entity.ProductItem;
import com.example.app.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-items")
public class ProductItemController {
    
    @Autowired
    private ProductItemRepository productItemRepository;
    
    @GetMapping
    public List<ProductItem> getAllProductItems() {
        return productItemRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductItem> getProductItemById(@PathVariable Long id) {
        Optional<ProductItem> productItem = productItemRepository.findById(id);
        return productItem.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/product/{productId}")
    public List<ProductItem> getProductItemsByProduct(@PathVariable Long productId) {
        return productItemRepository.findByProductId(productId);
    }
    
    @PostMapping
    public ProductItem createProductItem(@RequestBody ProductItem productItem) {
        return productItemRepository.save(productItem);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductItem> updateProductItem(@PathVariable Long id, @RequestBody ProductItem productItemDetails) {
        Optional<ProductItem> productItemOptional = productItemRepository.findById(id);
        
        if (productItemOptional.isPresent()) {
            ProductItem productItem = productItemOptional.get();
            productItem.setPrice(productItemDetails.getPrice());
            productItem.setProduct(productItemDetails.getProduct());
            
            ProductItem updatedProductItem = productItemRepository.save(productItem);
            return ResponseEntity.ok(updatedProductItem);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductItem(@PathVariable Long id) {
        if (productItemRepository.existsById(id)) {
            productItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}