package com.example.app.repository;

import com.example.app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByWorkerId(Long workerId);
    List<Task> findByClientId(Long clientId);
    List<Task> findByProductId(Long productId);
    List<Task> findByStatus(String status);
}