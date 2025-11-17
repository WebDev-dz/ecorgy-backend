package com.example.app.service;

import com.example.app.entity.Admin;
import com.example.app.entity.Product;
import com.example.app.repository.AdminRepository;
import com.example.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
    }
    
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
    
    public Admin updateAdmin(Long id, Admin adminDetails) {
        Admin admin = getAdminById(id);
        
        admin.setFirstName(adminDetails.getFirstName());
        admin.setLastName(adminDetails.getLastName());
        admin.setEmail(adminDetails.getEmail());
        admin.setBirthDate(adminDetails.getBirthDate());
        admin.setPassword(adminDetails.getPassword());
        admin.setPhoneNumber(adminDetails.getPhoneNumber());
        admin.setPermissions(adminDetails.getPermissions());
        
        return adminRepository.save(admin);
    }
    
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }
    
    public void deleteProduct(Long adminId, Long productId) {
        Admin admin = getAdminById(adminId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        productRepository.delete(product);
    }
}