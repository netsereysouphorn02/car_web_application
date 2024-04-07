package com.example.assignment;

public class CarDetails {
    private String name;
    private int wheels;

    // Constructor
    public CarDetails(String name, int wheels) {
        this.name = name;
        this.wheels = wheels;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWheels() {
        return wheels;
    }

    public void setWheels(int wheels) {
        this.wheels = wheels;
    }
}
