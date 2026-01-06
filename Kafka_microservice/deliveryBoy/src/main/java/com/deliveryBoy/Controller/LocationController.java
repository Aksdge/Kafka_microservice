package com.deliveryBoy.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.kafkaService.kafkaService;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private kafkaService kafkaService;

    @PostMapping("/update")
    public ResponseEntity<String> updateLocation(@RequestBody LocationRequest request) {
        if (request == null || request.getLocation() == null || request.getLocation().isBlank()) {
            return ResponseEntity.badRequest().body("location is required");
        }

        String location = request.getLocation();
        this.kafkaService.updateLocation(location);
        return ResponseEntity.ok("Location updated successfully");
    }
}
