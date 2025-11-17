package com.example.app.controller;

import com.example.app.entity.Location;
import com.example.app.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    
    @Autowired
    private LocationRepository locationRepository;
    
    @GetMapping
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location locationDetails) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        
        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();
            location.setLatitude(locationDetails.getLatitude());
            location.setLongitude(locationDetails.getLongitude());
            location.setStreet(locationDetails.getStreet());
            location.setCity(locationDetails.getCity());
            location.setZipCode(locationDetails.getZipCode());
            location.setCountry(locationDetails.getCountry());
            
            Location updatedLocation = locationRepository.save(location);
            return ResponseEntity.ok(updatedLocation);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}