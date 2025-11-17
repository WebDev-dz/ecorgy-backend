package com.example.app.service;

import com.example.app.entity.Product;
import com.example.app.entity.ProductItem;
import com.example.app.entity.Seller;
import com.example.app.repository.ProductItemRepository;
import com.example.app.repository.ProductRepository;
import com.example.app.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {
    
    @Autowired
    private SellerRepository sellerRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductItemRepository productItemRepository;
    
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }
    
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));
    }
    
    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }
    
    public Seller updateSeller(Long id, Seller sellerDetails) {
        Seller seller = getSellerById(id);
        
        seller.setFirstName(sellerDetails.getFirstName());
        seller.setLastName(sellerDetails.getLastName());
        seller.setEmail(sellerDetails.getEmail());
        seller.setBirthDate(sellerDetails.getBirthDate());
        seller.setPassword(sellerDetails.getPassword());
        seller.setPhoneNumber(sellerDetails.getPhoneNumber());
        
        return sellerRepository.save(seller);
    }
    
    public void deleteSeller(Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new RuntimeException("Seller not found with id: " + id);
        }
        sellerRepository.deleteById(id);
    }
    
    public List<ProductItem> getMyProducts(Long sellerId) {
        Seller seller = getSellerById(sellerId);
        List<Product> products = productRepository.findBySellerId(sellerId);
        
        return products.stream()
                .flatMap(product -> productItemRepository.findByProductId(product.getId()).stream())
                .collect(Collectors.toList());
    }
}