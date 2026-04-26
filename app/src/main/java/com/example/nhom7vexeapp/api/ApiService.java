package com.example.nhom7vexeapp.api;

import com.example.nhom7vexeapp.models.BookingRequest;
import com.example.nhom7vexeapp.models.KhachHang;
import com.example.nhom7vexeapp.models.Loaixe;
import com.example.nhom7vexeapp.models.Seat;
import com.example.nhom7vexeapp.models.Trip;
import com.example.nhom7vexeapp.models.TripResponse;
import com.example.nhom7vexeapp.models.TripSearchResult;
import com.example.nhom7vexeapp.api.CustomerResponse;
import com.example.nhom7vexeapp.api.LoginResponse;
import com.example.nhom7vexeapp.TicketModel;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;
import java.util.Map;

public interface ApiService {

    // --- 1. AUTH & LOGIN ---
    @POST("api/user-auth/") 
    Call<LoginResponse> login(@Body com.example.nhom7vexeapp.models.LoginRequest loginRequest);

    @GET("api/user-auth/")
    Call<List<CustomerResponse>> getUsers();

    @GET("api/user-auth/{id}/")
    Call<CustomerResponse> getUserAuthDetail(@Path("id") String id);

    @POST("api/user-auth/")
    Call<Void> registerAuth(@Body Map<String, String> data);

    @POST("api/user-auth/")
    Call<KhachHang> loginWithMap(@Body Map<String, String> body);


    // --- 2. QUẢN LÝ CHUYẾN XE ---
    @GET("api/chuyenxe/")
    Call<List<TripSearchResult>> getChuyenXe(); 

    @GET("api/chuyenxe/")
    Call<List<Trip>> getTrips();

    @POST("api/chuyenxe/")
    Call<Trip> createTrip(@Body Trip trip);


    // --- 3. ĐẶT VÉ & GHẾ NGỒI ---
    @GET("api/ghengoi/")
    Call<List<Seat>> getSeatsByTrip(@Query("ChuyenXe") String chuyenXeId);

    @POST("api/dat-ve/")
    Call<Void> bookTicket(@Body BookingRequest bookingRequest);


    // --- 4. QUẢN LÝ KHÁCH HÀNG ---
    @POST("api/khachhang/")
    Call<Void> createKhachHangProfile(@Body Map<String, String> data);

    @GET("api/khachhang/{id}/")
    Call<Map<String, Object>> getKhachHangDetail(@Path("id") String id);

    @DELETE("api/khachhang/{id}/")
    Call<Void> deleteKhachHang(@Path("id") String id);

    @PUT("api/khachhang/{id}/")
    Call<Void> updateKhachHang(@Path("id") String id, @Body Map<String, String> data);

    @POST("api/khachhang/")
    Call<KhachHang> register(@Body Map<String, String> body);

    @GET("api/khachhang/{id}/")
    Call<KhachHang> getProfile(@Path("id") String id);

    @PUT("api/khachhang/{id}/")
    Call<KhachHang> updateProfile(@Path("id") String id, @Body KhachHang khachHang);

    @GET("api/khachhang/{id}/lich-su-ve/")
    Call<List<TicketModel>> getTicketHistory(@Path("id") String id);


    // --- 5. QUẢN LÝ NHÀ XE ---
    @POST("api/nhaxe/")
    Call<Void> createNhaXeProfile(@Body Map<String, String> data);

    @GET("api/nhaxe/{id}/")
    Call<Map<String, Object>> getNhaXeDetail(@Path("id") String id);

    @PUT("api/nhaxe/{id}/")
    Call<Void> updateNhaXeProfile(@Path("id") String id, @Body Map<String, String> data);


    // --- 6. QUẢN LÝ LOẠI XE ---
    @GET("api/loaixe/")
    Call<List<Loaixe>> getLoaixe();

    @PUT("api/loaixe/{id}/")
    Call<Loaixe> updateLoaixe(@Path("id") String id, @Body Loaixe loaixe);
}
