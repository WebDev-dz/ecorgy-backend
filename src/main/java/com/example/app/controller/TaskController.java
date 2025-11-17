package com.example.app.controller;

import com.example.app.entity.Task;
import com.example.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/worker/{workerId}")
    public List<Task> getTasksByWorker(@PathVariable Long workerId) {
        return taskRepository.findByWorkerId(workerId);
    }
    
    @GetMapping("/client/{clientId}")
    public List<Task> getTasksByClient(@PathVariable Long clientId) {
        return taskRepository.findByClientId(clientId);
    }
    
    @GetMapping("/product/{productId}")
    public List<Task> getTasksByProduct(@PathVariable Long productId) {
        return taskRepository.findByProductId(productId);
    }
    
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskRepository.findByStatus(status);
    }
    
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setCreatedAt(new Date());
        return taskRepository.save(task);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setConfirmedAt(taskDetails.getConfirmedAt());
            task.setAchievedAt(taskDetails.getAchievedAt());
            task.setProduct(taskDetails.getProduct());
            task.setWorker(taskDetails.getWorker());
            task.setClient(taskDetails.getClient());
            task.setLocation(taskDetails.getLocation());
            
            Task updatedTask = taskRepository.save(task);
            return ResponseEntity.ok(updatedTask);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}