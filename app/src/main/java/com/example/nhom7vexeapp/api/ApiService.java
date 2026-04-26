package com.example.nhom7vexeapp.api;

import com.example.nhom7vexeapp.models.Loaixe;
import com.example.nhom7vexeapp.models.Trip;
import com.example.nhom7vexeapp.models.Driver;
import com.example.nhom7vexeapp.models.VehicleManaged;
import com.example.nhom7vexeapp.models.NhaXe;
import com.example.nhom7vexeapp.models.Route;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;
import java.util.Map;

public interface ApiService {
    @POST("api/user-auth/") 
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/user-auth/")
    Call<List<CustomerResponse>> getUsers();

    // BỔ SUNG: Hàm đăng ký tài khoản (Dành cho RegisterActivity)
    @POST("api/user-auth/")
    Call<Void> registerAuth(@Body Map<String, String> data);

    @GET("api/taixe/")
    Call<List<Driver>> getDrivers();

    @GET("api/user-auth/{id}/")
    Call<CustomerResponse> getUserAuthDetail(@Path("id") String id);

    @POST("api/nhaxe/")
    Call<Void> createNhaXeProfile(@Body Map<String, String> data);

    @GET("api/nhaxe/{id}/")
    Call<NhaXe> getNhaXeDetail(@Path("id") String id);

    @PUT("api/nhaxe/{id}/")
    Call<Void> updateNhaXeProfile(@Path("id") String id, @Body Map<String, String> data);

    @GET("api/xe/")
    Call<List<VehicleManaged>> getVehicles();

    @PUT("api/xe/{id}/")
    Call<VehicleManaged> updateVehicle(@Path("id") String id, @Body VehicleManaged vehicle);

    @DELETE("api/xe/{id}/")
    Call<Void> deleteVehicle(@Path("id") String id);

    @GET("api/tuyenxe/")
    Call<List<Route>> getRoutes();

    @PUT("api/tuyenxe/{id}/")
    Call<Void> updateRoute(@Path("id") String id, @Body Map<String, String> data);

    @DELETE("api/tuyenxe/{id}/")
    Call<Void> deleteRoute(@Path("id") String id);

    @Multipart
    @POST("api/khachhang/")
    Call<Void> createKhachHangProfile(
            @Part("KhachHangID") RequestBody id,
            @Part("hoTen") RequestBody name,
            @Part("Email") RequestBody email,
            @Part("Ngaysinh") RequestBody dob,
            @Part MultipartBody.Part imageFile
    );

    @GET("api/khachhang/{id}/")
    Call<Map<String, Object>> getKhachHangDetail(@Path("id") String id);

    @DELETE("api/khachhang/{id}/")
    Call<Void> deleteKhachHang(@Path("id") String id);

    @Multipart
    @PUT("api/khachhang/{id}/")
    Call<Void> updateKhachHang(
            @Path("id") String id,
            @Part("hoTen") RequestBody name,
            @Part("Ngaysinh") RequestBody dob,
            @Part MultipartBody.Part imageFile
    );

    @GET("api/chuyenxe/")
    Call<List<Trip>> getTrips();

    @POST("api/chuyenxe/")
    Call<Trip> createTrip(@Body Trip trip);

    @GET("api/loaixe/")
    Call<List<Loaixe>> getLoaixe();

    @PUT("api/loaixe/{id}/")
    Call<Loaixe> updateLoaixe(@Path("id") String id, @Body Loaixe loaixe);
}
