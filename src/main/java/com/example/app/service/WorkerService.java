package com.example.app.service;

import com.example.app.entity.Worker;
import com.example.app.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
    
    @Autowired
    private WorkerRepository workerRepository;
    
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }
    
    public Worker getWorkerById(Long id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with id: " + id));
    }
    
    public Worker createWorker(Worker worker) {
        return workerRepository.save(worker);
    }
    
    public Worker updateWorker(Long id, Worker workerDetails) {
        Worker worker = getWorkerById(id);
        
        worker.setFirstName(workerDetails.getFirstName());
        worker.setLastName(workerDetails.getLastName());
        worker.setEmail(workerDetails.getEmail());
        worker.setBirthDate(workerDetails.getBirthDate());
        worker.setPassword(workerDetails.getPassword());
        worker.setPhoneNumber(workerDetails.getPhoneNumber());
        
        return workerRepository.save(worker);
    }
    
    public void deleteWorker(Long id) {
        if (!workerRepository.existsById(id)) {
            throw new RuntimeException("Worker not found with id: " + id);
        }
        workerRepository.deleteById(id);
    }
    
    public void reportTaskProgress(Long workerId) {
        Worker worker = getWorkerById(workerId);
        // Business logic for reporting task progress
        // This could involve updating task statuses, sending notifications, etc.
        worker.reportTaskProgress();
    }
}