package com.example.nhom7vexeapp.models;

import com.google.gson.annotations.SerializedName;

public class TripSearchResult extends Trip {
    
    // Đã xóa endTime vì nó đã có ở lớp cha Trip với @SerializedName("GioDen")

    @SerializedName("TenNhaXe")
    private String nhaXeName;

    @SerializedName("GiaVe")
    private String price;

    @SerializedName("LoaiXe")
    private String carType;

    @SerializedName("TenTuyen")
    private String tuyenName;

    @SerializedName("SoChoTrong")
    private Integer soChoTrong;

    public TripSearchResult() {
        super();
    }

    public TripSearchResult(String id, String tuyenXeID, String date, String startTime, String status) {
        super(id, tuyenXeID, date, startTime, status);
    }

    // Nếu Backend trả về TenNhaXe thì lấy, không thì để mặc định
    public String getNhaXeName() { 
        return nhaXeName != null ? nhaXeName : "Nhà xe"; 
    }
    
    public String getPrice() { 
        return price != null ? price : "---"; 
    }
    
    public String getCarType() { 
        return carType != null ? carType : "Xe 4 chỗ"; 
    }
    
    public String getTuyenXeName() {
        return tuyenName != null ? tuyenName : getRouteName();
    }
    
    @Override
    public int getSeats() {
        return soChoTrong != null ? soChoTrong : super.getSeats();
    }
}
