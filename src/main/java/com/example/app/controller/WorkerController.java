package com.example.app.controller;

import com.example.app.entity.Worker;
import com.example.app.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {
    
    @Autowired
    private WorkerRepository workerRepository;
    
    @GetMapping
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorkerById(@PathVariable Long id) {
        Optional<Worker> worker = workerRepository.findById(id);
        return worker.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Worker createWorker(@RequestBody Worker worker) {
        return workerRepository.save(worker);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Worker> updateWorker(@PathVariable Long id, @RequestBody Worker workerDetails) {
        Optional<Worker> workerOptional = workerRepository.findById(id);
        
        if (workerOptional.isPresent()) {
            Worker worker = workerOptional.get();
            worker.setFirstName(workerDetails.getFirstName());
            worker.setLastName(workerDetails.getLastName());
            worker.setEmail(workerDetails.getEmail());
            worker.setBirthDate(workerDetails.getBirthDate());
            worker.setPassword(workerDetails.getPassword());
            worker.setPhoneNumber(workerDetails.getPhoneNumber());
            
            Worker updatedWorker = workerRepository.save(worker);
            return ResponseEntity.ok(updatedWorker);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        if (workerRepository.existsById(id)) {
            workerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}