package com.example.nhom7vexeapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trip implements Serializable {
    private String id;
    private String routeName;
    private String date;
    private String time;
    private String vehicleType;
    private int seats;
    private String price;
    private String status;
    private Driver assignedDriver;
    private List<Passenger> passengers;

    public Trip(String id, String routeName, String date, String time, String vehicleType, int seats, String price, String status) {
        this.id = id;
        this.routeName = routeName;
        this.date = date;
        this.time = time;
        this.vehicleType = vehicleType;
        this.seats = seats;
        this.price = price;
        this.status = status;
        this.passengers = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Driver getAssignedDriver() { return assignedDriver; }
    public void setAssignedDriver(Driver assignedDriver) { this.assignedDriver = assignedDriver; }
    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
}
