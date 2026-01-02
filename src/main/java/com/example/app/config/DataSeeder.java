package com.example.app.config;

import com.example.app.entity.*;
import com.example.app.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

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

    public DataSeeder(UserRepository userRepository,
                      AdminRepository adminRepository,
                      SellerRepository sellerRepository,
                      ClientRepository clientRepository,
                      WorkerRepository workerRepository,
                      ProductRepository productRepository,
                      ProductItemRepository productItemRepository,
                      TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.sellerRepository = sellerRepository;
        this.clientRepository = clientRepository;
        this.workerRepository = workerRepository;
        this.productRepository = productRepository;
        this.productItemRepository = productItemRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        Admin admin = Admin.builder()
                .firstName("Admin")
                .lastName("User")
                .email("admin@ecorgy.com")
                .password("admin123")
                .permissions(Arrays.asList("MANAGE_USERS", "DELETE_PRODUCT"))
                .build();
        admin = adminRepository.save(admin);

        Seller seller = Seller.builder()
                .firstName("Sally")
                .lastName("Seller")
                .email("seller@ecorgy.com")
                .password("seller123")
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
                .password("client123")
                .location(clientLocation)
                .build();
        client = clientRepository.save(client);

        Worker worker = Worker.builder()
                .firstName("Wally")
                .lastName("Worker")
                .email("worker@ecorgy.com")
                .password("worker123")
                .build();
        worker = workerRepository.save(worker);

        Product product = Product.builder()
                .serialNumber("SN-0001")
                .name("Eco Widget")
                .description("A sustainable, eco-friendly widget for demo purposes.")
                .seller(seller)
                .build();
        product = productRepository.save(product);

        ProductItem item = ProductItem.builder()
                .price(199.99)
                .product(product)
                .seller(seller)
                .build();
        item = productItemRepository.save(item);

        Task task = Task.builder()
                .description("Install Eco Widget at client location")
                .status("CREATED")
                .createdAt(new Date())
                .product(product)
                .worker(worker)
                .client(client)
                .location(clientLocation)
                .build();
        taskRepository.save(task);
    }
}

