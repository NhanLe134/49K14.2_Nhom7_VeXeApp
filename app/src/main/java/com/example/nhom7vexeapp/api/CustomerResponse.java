package com.example.nhom7vexeapp.api;

import com.google.gson.annotations.SerializedName;

public class CustomerResponse {
    @SerializedName(value = "TenDangNhap", alternate = {"tendangnhap", "ten_khach_hang", "username"})
    private String TenDangNhap;
    
    @SerializedName(value = "Vaitro", alternate = {"vaitro", "vai_tro", "VAITRO"})
    private String Vaitro;
    
    @SerializedName(value = "SoDienThoai", alternate = {"sodienthoai", "sdt", "phone"})
    private String SoDienThoai;

    @SerializedName(value = "NgaySinh", alternate = {"ngaysinh", "ngay_sinh", "dob"})
    private String NgaySinh;

    // Các Getter khớp chính xác với LoginActivity đang gọi
    public String getVaitro() { return Vaitro; }
    public String getSdt() { return SoDienThoai; }
    public String getTenKhachHang() { return TenDangNhap != null ? TenDangNhap : "Khách hàng"; }
    public String getNgaySinh() { return NgaySinh != null ? NgaySinh : "Chưa cập nhật"; }
}
