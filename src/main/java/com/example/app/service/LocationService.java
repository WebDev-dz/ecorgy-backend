package com.example.app.service;

import com.example.app.entity.Location;
import com.example.app.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    
    @Autowired
    private LocationRepository locationRepository;
    
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
    
    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }
    
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }
    
    public Location updateLocation(Long id, Location locationDetails) {
        Location location = getLocationById(id);
        
        location.setLatitude(locationDetails.getLatitude());
        location.setLongitude(locationDetails.getLongitude());
        location.setStreet(locationDetails.getStreet());
        location.setCity(locationDetails.getCity());
        location.setZipCode(locationDetails.getZipCode());
        location.setCountry(locationDetails.getCountry());
        
        return locationRepository.save(location);
    }
    
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        locationRepository.deleteById(id);
    }
}