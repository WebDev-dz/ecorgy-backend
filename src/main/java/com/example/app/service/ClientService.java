package com.example.app.service;

import com.example.app.entity.Client;
import com.example.app.entity.Task;
import com.example.app.repository.ClientRepository;
import com.example.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }
    
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
    
    public Client updateClient(Long id, Client clientDetails) {
        Client client = getClientById(id);
        
        client.setFirstName(clientDetails.getFirstName());
        client.setLastName(clientDetails.getLastName());
        client.setEmail(clientDetails.getEmail());
        client.setBirthDate(clientDetails.getBirthDate());
        client.setPassword(clientDetails.getPassword());
        client.setPhoneNumber(clientDetails.getPhoneNumber());
        client.setLocation(clientDetails.getLocation());
        
        return clientRepository.save(client);
    }
    
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
    
    public Task createTask(Long clientId, Task task) {
        Client client = getClientById(clientId);
        task.setClient(client);
        return taskRepository.save(task);
    }
    
    public List<Task> getClientTasks(Long clientId) {
        return taskRepository.findByClientId(clientId);
    }
}