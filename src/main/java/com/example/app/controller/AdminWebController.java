package com.example.app.controller;

import com.example.app.security.JwtUtils;
import com.example.app.entity.Admin;
import com.example.app.entity.Client;
import com.example.app.entity.Product;
import com.example.app.entity.Review;
import com.example.app.entity.Task;
import com.example.app.entity.Worker;
import com.example.app.service.ProductService;
import com.example.app.service.ReviewService;
import com.example.app.service.SellerService;
import com.example.app.service.TaskService;
import com.example.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SellerService sellerService;

    @ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("activeProducts", productService.getAllProducts().size());
        model.addAttribute("tasksToday", taskService.getAllTasks().size()); // Simplified for now
        // Simplified avg rating
        double avgRating = reviewService.getAllReviews().stream()
                .mapToInt(com.example.app.entity.Review::getRating)
                .average()
                .orElse(0.0);
        model.addAttribute("avgRating", String.format("%.1f", avgRating));

        // Get recent tasks (limit to 5)
        var allTasks = taskService.getAllTasks();
        model.addAttribute("recentTasks", allTasks.subList(0, Math.min(allTasks.size(), 5)));

        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("sellers", sellerService.getAllSellers());
        return "admin/product-form";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute Product product,
            @RequestParam("file-upload") MultipartFile file,
            @RequestParam(value = "sellerId", required = false) Long sellerId) {
        try {
            if (sellerId != null) {
                product.setSeller(sellerService.getSellerById(sellerId));
            } else if (!sellerService.getAllSellers().isEmpty()) {
                // Fallback to first seller if none selected (should be required in UI)
                product.setSeller(sellerService.getAllSellers().get(0));
            }

            productService.createProduct(product, file);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/products/new?error";
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            model.addAttribute("sellers", sellerService.getAllSellers());
            return "admin/product-form";
        } catch (Exception e) {
            return "redirect:/admin/products";
        }
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "admin/tasks";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "admin/reviews";
    }

    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/reviews";
    }

    @GetMapping("/reviews/view/{id}")
    public String viewReview(@PathVariable Long id, Model model) {
        try {
            com.example.app.entity.Review review = reviewService.getReviewById(id);
            model.addAttribute("review", review);
            return "admin/review-detail";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/reviews";
        }
    }

    @GetMapping("/tasks/new")
    public String newTask(Model model) {
        model.addAttribute("task", new com.example.app.entity.Task());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("workers", userService.getAllUsers().stream()
                .filter(u -> u instanceof com.example.app.entity.Worker)
                .toList());
        model.addAttribute("clients", userService.getAllUsers().stream()
                .filter(u -> u instanceof com.example.app.entity.Client)
                .toList());
        return "admin/task-form";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new java.util.HashMap<String, Object>());
        return "admin/user-form";
    }

    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute Task task,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "workerId", required = false) Long workerId,
            @RequestParam(value = "clientId", required = false) Long clientId,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "postalCode", required = false) String postalCode) {
        try {
            if (productId != null) {
                task.setProduct(productService.getProductById(productId));
            }
            if (workerId != null) {
                userService.getUserById(workerId).ifPresent(user -> {
                    if (user instanceof Worker) {
                        task.setWorker((Worker) user);
                    }
                });
            }
            if (clientId != null) {
                userService.getUserById(clientId).ifPresent(user -> {
                    if (user instanceof Client) {
                        task.setClient((Client) user);
                    }
                });
            }

            // Create location
            if (address != null && city != null && postalCode != null) {
                com.example.app.entity.Location location = com.example.app.entity.Location.builder()
                        .street(address)
                        .city(city)
                        .zipCode(postalCode)
                        .country("Algeria") // Default country
                        .latitude(0.0) // Default latitude, should be updated with actual geocoding
                        .longitude(0.0) // Default longitude, should be updated with actual geocoding
                        .build();
                task.setLocation(location);
            }

            taskService.createTask(task);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/tasks/new?error";
        }
        return "redirect:/admin/tasks";
    }

    @PostMapping("/reviews")
    public String saveReview(@ModelAttribute Review review,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "clientId", required = false) Long clientId) {
        try {
            if (productId != null && clientId != null) {
                reviewService.addReview(productId, clientId, review);
            } else {
                return "redirect:/admin/reviews?error=missing_params";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/reviews?error";
        }
        return "redirect:/admin/reviews";
    }

    @PostMapping("/users")
    public String saveUser(@RequestParam("userType") String userType,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "phoneNumber", required = false) Integer phoneNumber,
            @RequestParam(value = "birthDate", required = false) String birthDate) {
        try {
            com.example.app.entity.User user;

            switch (userType.toUpperCase()) {
                case "CLIENT":
                    user = Client.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .password(password)
                            .phoneNumber(phoneNumber)
                            .build();
                    break;
                case "WORKER":
                    user = Worker.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .password(password)
                            .phoneNumber(phoneNumber)
                            .build();
                    break;
                case "ADMIN":
                    user = Admin.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .password(password)
                            .phoneNumber(phoneNumber)
                            .build();
                    break;
                default:
                    return "redirect:/admin/users/new?error=invalid_type";
            }

            userService.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/users/new?error";
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestParam String email, @RequestParam String password,
            HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // 1 day
            response.addCookie(cookie);

            return "redirect:/admin";
        } catch (Exception e) {
            return "redirect:/admin/login?error";
        }
    }
}
