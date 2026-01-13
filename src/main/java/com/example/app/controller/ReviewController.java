package com.example.app.controller;

import com.example.app.entity.Review;
import com.example.app.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PostMapping
    public ResponseEntity<Review> createReview(
            @RequestParam Long productId,
            @RequestParam Long clientId,
            @RequestBody Review review) {
        try {
            Review createdReview = reviewService.addReview(productId, clientId, review);
            return ResponseEntity.ok(createdReview);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviewsByProduct(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }

    @GetMapping("/client/{clientId}")
    public List<Review> getReviewsByClient(@PathVariable Long clientId) {
        return reviewService.getReviewsByClient(clientId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
