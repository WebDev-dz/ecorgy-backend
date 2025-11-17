package com.example.app.service;

import com.example.app.entity.ProductItem;
import com.example.app.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductItemService {
    
    @Autowired
    private ProductItemRepository productItemRepository;
    
    public List<ProductItem> getAllProductItems() {
        return productItemRepository.findAll();
    }
    
    public ProductItem getProductItemById(Long id) {
        return productItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductItem not found with id: " + id));
    }
    
    public List<ProductItem> getProductItemsByProduct(Long productId) {
        return productItemRepository.findByProductId(productId);
    }
    
    public ProductItem createProductItem(ProductItem productItem) {
        return productItemRepository.save(productItem);
    }
    
    public ProductItem updateProductItem(Long id, ProductItem productItemDetails) {
        ProductItem productItem = getProductItemById(id);
        
        productItem.setPrice(productItemDetails.getPrice());
        productItem.setProduct(productItemDetails.getProduct());
        
        return productItemRepository.save(productItem);
    }
    
    public void deleteProductItem(Long id) {
        if (!productItemRepository.existsById(id)) {
            throw new RuntimeException("ProductItem not found with id: " + id);
        }
        productItemRepository.deleteById(id);
    }
}