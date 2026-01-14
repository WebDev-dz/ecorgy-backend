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

    public List<Product> getSellerProducts(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    public List<com.example.app.entity.Review> getSellerReviews(Long sellerId) {
        List<Product> products = productRepository.findBySellerId(sellerId);
        return products.stream()
                .flatMap(product -> product.getReviews() != null ? product.getReviews().stream()
                        : java.util.stream.Stream.empty())
                .collect(Collectors.toList());
    }

    public List<com.example.app.entity.Task> getSellerTasks(Long sellerId) {
        List<Product> products = productRepository.findBySellerId(sellerId);
        return products.stream()
                .flatMap(product -> product.getTasks() != null ? product.getTasks().stream()
                        : java.util.stream.Stream.empty())
                .collect(Collectors.toList());
    }

    public java.util.Map<String, Object> getSellerStatistics(Long sellerId) {
        List<Product> products = getSellerProducts(sellerId);
        List<com.example.app.entity.Review> reviews = getSellerReviews(sellerId);
        List<com.example.app.entity.Task> tasks = getSellerTasks(sellerId);

        double avgRating = reviews.stream()
                .mapToInt(com.example.app.entity.Review::getRating)
                .average()
                .orElse(0.0);

        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("totalProducts", products.size());
        stats.put("avgRating", String.format("%.1f", avgRating));
        stats.put("totalReviews", reviews.size());
        stats.put("activeTasks", tasks.stream().filter(t -> !"Completed".equals(t.getStatus())).count());

        return stats;
    }
}