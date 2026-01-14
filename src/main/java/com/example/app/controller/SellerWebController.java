package com.example.app.controller;

import com.example.app.security.JwtUtils;
import com.example.app.entity.Location;
import com.example.app.entity.Product;
import com.example.app.entity.Seller;
import com.example.app.service.ProductService;
import com.example.app.service.ReviewService;
import com.example.app.service.SellerService;
import com.example.app.service.TaskService;
import com.example.app.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("/seller")
public class SellerWebController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ProductService productService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private LocationService locationService;

    @ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    private Long getCurrentSellerId() {
        // Get authenticated user email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find seller by email
        return sellerService.getAllSellers().stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .map(Seller::getId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
    }

    @GetMapping
    public String dashboard(Model model) {
        try {
            Long sellerId = getCurrentSellerId();
            var stats = sellerService.getSellerStatistics(sellerId);

            model.addAttribute("totalProducts", stats.get("totalProducts"));
            model.addAttribute("avgRating", stats.get("avgRating"));
            model.addAttribute("totalReviews", stats.get("totalReviews"));
            model.addAttribute("activeTasks", stats.get("activeTasks"));

            // Get recent tasks (limit to 5)
            var allTasks = sellerService.getSellerTasks(sellerId);
            model.addAttribute("recentTasks", allTasks.subList(0, Math.min(allTasks.size(), 5)));

            return "seller/dashboard";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login";
        }
    }

    @GetMapping("/products")
    public String products(Model model) {
        try {
            Long sellerId = getCurrentSellerId();
            model.addAttribute("products", sellerService.getSellerProducts(sellerId));
            return "seller/products";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login";
        }
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "seller/product-form";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute Product product,
            @RequestParam("file-upload") MultipartFile file) {
        try {
            Long sellerId = getCurrentSellerId();
            product.setSeller(sellerService.getSellerById(sellerId));
            productService.createProduct(product, file);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/products/new?error";
        }
        return "redirect:/seller/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        try {
            Long sellerId = getCurrentSellerId();
            Product product = productService.getProductById(id);

            // Verify product belongs to seller
            if (!product.getSeller().getId().equals(sellerId)) {
                return "redirect:/seller/products?error=unauthorized";
            }

            model.addAttribute("product", product);
            return "seller/product-form";
        } catch (Exception e) {
            return "redirect:/seller/products";
        }
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        try {
            Long sellerId = getCurrentSellerId();
            Product product = productService.getProductById(id);

            // Verify product belongs to seller
            if (!product.getSeller().getId().equals(sellerId)) {
                return "redirect:/seller/products?error=unauthorized";
            }

            productService.deleteProduct(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/seller/products";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {
        try {
            Long sellerId = getCurrentSellerId();
            model.addAttribute("reviews", sellerService.getSellerReviews(sellerId));
            return "seller/reviews";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login";
        }
    }

    @GetMapping("/reviews/view/{id}")
    public String viewReview(@PathVariable Long id, Model model) {
        try {
            Long sellerId = getCurrentSellerId();
            com.example.app.entity.Review review = reviewService.getReviewById(id);

            // Verify review is for seller's product
            if (!review.getProduct().getSeller().getId().equals(sellerId)) {
                return "redirect:/seller/reviews?error=unauthorized";
            }

            model.addAttribute("review", review);
            return "seller/review-detail";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/reviews";
        }
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        try {
            Long sellerId = getCurrentSellerId();
            model.addAttribute("tasks", sellerService.getSellerTasks(sellerId));
            return "seller/tasks";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login";
        }
    }
 
    @GetMapping("/product-items")
    public String productItems(Model model) {
        try {
            Long sellerId = getCurrentSellerId();
            model.addAttribute("productItems", sellerService.getMyProducts(sellerId));
            return "seller/product-items";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/seller/login";
        }
    }
 
     @GetMapping("/locations")
     public String locations(Model model) {
         try {
             Long sellerId = getCurrentSellerId();
             var taskLocations = sellerService.getSellerTasks(sellerId).stream()
                     .map(com.example.app.entity.Task::getLocation)
                     .filter(java.util.Objects::nonNull)
                     .toList();
 
             var sellerLocations = locationService.getLocationsBySeller(sellerId);
 
             var locations = java.util.stream.Stream.concat(taskLocations.stream(), sellerLocations.stream())
                     .collect(Collectors.collectingAndThen(
                             Collectors.toMap(Location::getId, Function.identity(), (a, b) -> a),
                             map -> map.values().stream()
                                     .sorted(Comparator.comparing(Location::getId))
                                     .collect(Collectors.toCollection(ArrayList::new))));
 
             model.addAttribute("currentSellerId", sellerId);
             model.addAttribute("locations", locations);
             return "seller/locations";
         } catch (Exception e) {
             e.printStackTrace();
             return "redirect:/seller/login";
         }
     }
 
     @GetMapping("/locations/new")
     public String newLocation(Model model) {
         model.addAttribute("location", Location.builder()
                 .latitude(0.0)
                 .longitude(0.0)
                 .country("Algeria")
                 .build());
         return "seller/location-form";
     }
 
     @PostMapping("/locations")
     public String saveLocation(@ModelAttribute Location location) {
         try {
             Long sellerId = getCurrentSellerId();
             location.setSeller(sellerService.getSellerById(sellerId));
 
             if (location.getLatitude() == null) {
                 location.setLatitude(0.0);
             }
             if (location.getLongitude() == null) {
                 location.setLongitude(0.0);
             }
             if (location.getCountry() == null || location.getCountry().isBlank()) {
                 location.setCountry("Algeria");
             }
 
             locationService.createLocation(location);
             return "redirect:/seller/locations";
         } catch (Exception e) {
             e.printStackTrace();
             return "redirect:/seller/locations/new?error";
         }
     }
 
     @GetMapping("/locations/delete/{id}")
     public String deleteLocation(@PathVariable Long id) {
         try {
             Long sellerId = getCurrentSellerId();
             Location location = locationService.getLocationById(id);
 
             if (location.getSeller() == null || !location.getSeller().getId().equals(sellerId)) {
                 return "redirect:/seller/locations?error=unauthorized";
             }
 
             locationService.deleteLocation(id);
             return "redirect:/seller/locations";
         } catch (DataIntegrityViolationException e) {
             return "redirect:/seller/locations?error=inuse";
         } catch (Exception e) {
             e.printStackTrace();
             return "redirect:/seller/locations?error";
         }
     }

    @GetMapping("/login")
    public String login() {
        return "seller/login";
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

            return "redirect:/seller";
        } catch (Exception e) {
            return "redirect:/seller/login?error";
        }
    }
}
