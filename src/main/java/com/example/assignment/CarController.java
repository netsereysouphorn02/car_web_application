package com.example.assignment;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private Map<String, Integer> cars = new HashMap<>();

    @PostMapping("/add")
    public ResponseEntity<String> addCar(@RequestBody Map<String, String> carDetails) {
        String carName = carDetails.get("name");
        int wheels = Integer.parseInt(carDetails.get("wheels"));
        cars.put(carName, wheels);
        return ResponseEntity.ok().body("Car information updated successfully.");
    }

    @GetMapping("/{carName}")
    public ResponseEntity<?> getCarDetails(@PathVariable String carName) {
        if (cars.containsKey(carName)) {
            int wheels = cars.get(carName);
            return ResponseEntity.ok().body(new CarDetails(carName, wheels));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    @GetMapping("/filter")
    public List<String> filterCarsByWheels(@RequestParam int wheels) {
        List<String> filteredCars = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cars.entrySet()) {
            if (entry.getValue() == wheels) {
                filteredCars.add(entry.getKey());
            }
        }
        return filteredCars;
    }

    @GetMapping("/all")
    public List<String> getAllCars() {
        return new ArrayList<>(cars.keySet());
    }

    @PostMapping("/shutdown")
    public void shutdownServer() {
        // Logic to shut down the server
        System.exit(0);
    }
}



