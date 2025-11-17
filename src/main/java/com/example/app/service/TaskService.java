package com.example.app.service;

import com.example.app.entity.Task;
import com.example.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }
    
    public List<Task> getTasksByWorker(Long workerId) {
        return taskRepository.findByWorkerId(workerId);
    }
    
    public List<Task> getTasksByClient(Long clientId) {
        return taskRepository.findByClientId(clientId);
    }
    
    public List<Task> getTasksByProduct(Long productId) {
        return taskRepository.findByProductId(productId);
    }
    
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }
    
    public Task createTask(Task task) {
        task.setCreatedAt(new Date());
        return taskRepository.save(task);
    }
    
    public Task updateTask(Long id, Task taskDetails) {
        Task task = getTaskById(id);
        
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setConfirmedAt(taskDetails.getConfirmedAt());
        task.setAchievedAt(taskDetails.getAchievedAt());
        task.setProduct(taskDetails.getProduct());
        task.setWorker(taskDetails.getWorker());
        task.setClient(taskDetails.getClient());
        task.setLocation(taskDetails.getLocation());
        
        return taskRepository.save(task);
    }
    
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}