package com.example.app.config;

import com.example.app.entity.*;
import com.example.app.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final SellerRepository sellerRepository;
    private final ClientRepository clientRepository;
    private final WorkerRepository workerRepository;
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final TaskRepository taskRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      AdminRepository adminRepository,
                      SellerRepository sellerRepository,
                      ClientRepository clientRepository,
                      WorkerRepository workerRepository,
                      ProductRepository productRepository,
                      ProductItemRepository productItemRepository,
                      TaskRepository taskRepository,
                      ReviewRepository reviewRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.sellerRepository = sellerRepository;
        this.clientRepository = clientRepository;
        this.workerRepository = workerRepository;
        this.productRepository = productRepository;
        this.productItemRepository = productItemRepository;
        this.taskRepository = taskRepository;
        this.reviewRepository = reviewRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        // --- Users ---

        Admin admin = Admin.builder()
                .firstName("Admin")
                .lastName("User")
                .email("admin@ecorgy.com")
                .password(passwordEncoder.encode("admin123"))
                .permissions(Arrays.asList("MANAGE_USERS", "DELETE_PRODUCT"))
                .build();
        adminRepository.save(admin);

        Seller seller = Seller.builder()
                .firstName("Sally")
                .lastName("Seller")
                .email("seller@ecorgy.com")
                .password(passwordEncoder.encode("seller123"))
                .build();
        seller = sellerRepository.save(seller);

        Location clientLocation = Location.builder()
                .latitude(36.7525)
                .longitude(3.04197)
                .street("1 Example St")
                .city("Algiers")
                .zipCode("16000")
                .country("Algeria")
                .build();

        Client client = Client.builder()
                .firstName("Clara")
                .lastName("Client")
                .email("client@ecorgy.com")
                .password(passwordEncoder.encode("client123"))
                .location(clientLocation)
                .build();
        client = clientRepository.save(client);

        Worker worker = Worker.builder()
                .firstName("Wally")
                .lastName("Worker")
                .email("worker@ecorgy.com")
                .password(passwordEncoder.encode("worker123"))
                .build();
        worker = workerRepository.save(worker);
        
        // --- Products & Related Data ---
        
        List<Product> products = new ArrayList<>();
        
        // Product 1
        Product product1 = Product.builder()
                .serialNumber("SN-0001")
                .name("Eco Solar Panel")
                .description("High efficiency solar panel for residential use. Reduces carbon footprint.")
                .seller(seller)
                .imageUrl("https://example.com/images/solar-panel.jpg")
                .build();
        products.add(productRepository.save(product1));

        // Product 2
        Product product2 = Product.builder()
                .serialNumber("SN-0002")
                .name("Bamboo Water Bottle")
                .description("Reusable water bottle made from sustainable bamboo.")
                .seller(seller)
                .imageUrl("https://example.com/images/bamboo-bottle.jpg")
                .build();
        products.add(productRepository.save(product2));

        // Product 3
        Product product3 = Product.builder()
                .serialNumber("SN-0003")
                .name("Organic Cotton T-Shirt")
                .description("100% organic cotton t-shirt, fair trade certified.")
                .seller(seller)
                .imageUrl("https://example.com/images/cotton-tshirt.jpg")
                .build();
        products.add(productRepository.save(product3));
        
        // Product 4
        Product product4 = Product.builder()
                .serialNumber("SN-0004")
                .name("Recycled Paper Notebook")
                .description("Notebook made from 100% post-consumer recycled paper.")
                .seller(seller)
                .imageUrl("https://example.com/images/notebook.jpg")
                .build();
        products.add(productRepository.save(product4));
        
        // Product 5
        Product product5 = Product.builder()
                .serialNumber("SN-0005")
                .name("Biodegradable Phone Case")
                .description("Phone case that composts in your backyard.")
                .seller(seller)
                .imageUrl("https://example.com/images/phone-case.jpg")
                .build();
        products.add(productRepository.save(product5));

        // --- Product Items (Prices) ---
        
        createProductItem(product1, seller, 299.99);
        createProductItem(product2, seller, 24.50);
        createProductItem(product3, seller, 19.99);
        createProductItem(product4, seller, 5.99);
        createProductItem(product5, seller, 15.00);

        // --- Reviews ---
        
        createReview(product1, client, 5, "Amazing product! Saved so much on electricity.");
        createReview(product1, client, 4, "Good quality, but installation was tricky.");
        createReview(product2, client, 5, "Love the design and feel.");
        createReview(product3, client, 3, "Shrunk a bit after wash.");
        createReview(product5, client, 5, "Fits perfectly and feels good to help the planet.");

        // --- Tasks ---
        
        createTask("Install Eco Solar Panel at client roof", "PENDING", product1, worker, client, clientLocation);
        createTask("Deliver Bamboo Bottles bulk order", "IN_PROGRESS", product2, worker, client, clientLocation);
    }
    
    private void createProductItem(Product product, Seller seller, Double price) {
        ProductItem item = ProductItem.builder()
                .price(price)
                .product(product)
                .seller(seller)
                .build();
        productItemRepository.save(item);
    }

    private void createReview(Product product, Client client, Integer rating, String comment) {
        Review review = Review.builder()
                .rating(rating)
                .comment(comment)
                .product(product)
                .client(client)
                .createdAt(new Date())
                .build();
        reviewRepository.save(review);
    }
    
    private void createTask(String description, String status, Product product, Worker worker, Client client, Location location) {
         Task task = Task.builder()
                .description(description)
                .status(status)
                .createdAt(new Date())
                .product(product)
                .worker(worker)
                .client(client)
                .location(location)
                .build();
        taskRepository.save(task);
    }
}
