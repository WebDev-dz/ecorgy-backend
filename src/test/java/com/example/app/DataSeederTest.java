package com.example.app;

import com.example.app.entity.Admin;
import com.example.app.entity.Product;
import com.example.app.entity.Task;
import com.example.app.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class DataSeederTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void seedExecutedAndCreatedSampleData() {
        assertTrue(userRepository.count() >= 4);
        assertTrue(adminRepository.count() >= 1);
        assertTrue(sellerRepository.count() >= 1);
        assertTrue(clientRepository.count() >= 1);
        assertTrue(workerRepository.count() >= 1);

        assertTrue(productRepository.count() >= 1);
        assertTrue(productItemRepository.count() >= 1);
        assertTrue(taskRepository.count() >= 1);

        assertTrue(userRepository.existsByEmail("admin@ecorgy.com"));
        Admin admin = adminRepository.findAll().get(0);
        assertNotNull(admin.getPermissions());
        assertFalse(admin.getPermissions().isEmpty());

        Product product = productRepository.findAll().get(0);
        assertNotNull(product.getSeller());

        Task task = taskRepository.findAll().get(0);
        assertNotNull(task.getClient());
        assertNotNull(task.getWorker());
        assertNotNull(task.getProduct());
        assertNotNull(task.getLocation());
    }
}
