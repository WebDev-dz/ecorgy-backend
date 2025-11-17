package com.example.app.controller;

import com.example.app.entity.Seller;
import com.example.app.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {
    
    @Autowired
    private SellerRepository sellerRepository;
    
    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) {
        Optional<Seller> seller = sellerRepository.findById(id);
        return seller.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerRepository.save(seller);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody Seller sellerDetails) {
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        
        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            seller.setFirstName(sellerDetails.getFirstName());
            seller.setLastName(sellerDetails.getLastName());
            seller.setEmail(sellerDetails.getEmail());
            seller.setBirthDate(sellerDetails.getBirthDate());
            seller.setPassword(sellerDetails.getPassword());
            seller.setPhoneNumber(sellerDetails.getPhoneNumber());
            
            Seller updatedSeller = sellerRepository.save(seller);
            return ResponseEntity.ok(updatedSeller);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        if (sellerRepository.existsById(id)) {
            sellerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}