package com.example.nhom7vexeapp.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trip implements Serializable {
    @SerializedName("ChuyenXeID")
    private String id;

    @SerializedName("Xe")
    private String xeID;

    @SerializedName("TuyenXe")
    private String tuyenXeID;

    @SerializedName("Taixe")
    private String taiXeID;

    @SerializedName("NgayKhoiHanh")
    private String date;

    @SerializedName("GioDi")
    private String startTime;

    @SerializedName("GioDen")
    private String endTime;

    @SerializedName("TrangThai")
    private String status;

    // Các trường hỗ trợ quản lý (giữ lại để không lỗi code của bạn bè)
    private int seats = 4; 
    private Driver assignedDriver;
    private List<Passenger> passengers = new ArrayList<>();

    // No-arg constructor for GSON
    public Trip() {
    }

    public Trip(String id, String tuyenXeID, String date, String startTime, String status) {
        this.id = id;
        this.tuyenXeID = tuyenXeID;
        this.date = date;
        this.startTime = startTime;
        this.status = status;
    }

    // Getters an toàn
    public String getId() { return id != null ? id : ""; }
    public String getXeID() { return xeID != null ? xeID : ""; }
    public String getTuyenXeID() { return tuyenXeID != null ? tuyenXeID : ""; }
    public String getDate() { return date != null ? date : ""; }
    public String getTime() { return startTime != null ? startTime : "00:00"; }
    public String getEndTime() { return endTime != null ? endTime : ""; }
    public String getStatus() { return status != null ? status : "-"; }
    
    public String getRouteName() { return "Tuyến: " + getTuyenXeID(); }
    public String getVehicleType() { return "Xe: " + getXeID(); }
    
    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }
    
    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
    
    public Driver getAssignedDriver() { return assignedDriver; }
    public void setAssignedDriver(Driver driver) { this.assignedDriver = driver; }
}
