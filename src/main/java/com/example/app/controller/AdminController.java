package com.example.app.controller;

import com.example.app.entity.Admin;
import com.example.app.entity.Product;
import com.example.app.repository.AdminRepository;
import com.example.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminRepository.save(admin);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin adminDetails) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            admin.setFirstName(adminDetails.getFirstName());
            admin.setLastName(adminDetails.getLastName());
            admin.setEmail(adminDetails.getEmail());
            admin.setBirthDate(adminDetails.getBirthDate());
            admin.setPassword(adminDetails.getPassword());
            admin.setPhoneNumber(adminDetails.getPhoneNumber());
            admin.setPermissions(adminDetails.getPermissions());
            
            Admin updatedAdmin = adminRepository.save(admin);
            return ResponseEntity.ok(updatedAdmin);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{adminId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long adminId, @PathVariable Long productId) {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        Optional<Product> productOptional = productRepository.findById(productId);
        
        if (adminOptional.isPresent() && productOptional.isPresent()) {
            productRepository.deleteById(productId);
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}