package com.example.nhom7vexeapp.api;

import com.example.nhom7vexeapp.models.Loaixe;
import com.example.nhom7vexeapp.models.Trip;
import com.example.nhom7vexeapp.models.TaixeModel;
import com.example.nhom7vexeapp.models.ChiTietTaiXeModel;
import com.example.nhom7vexeapp.models.UserModel;
import com.example.nhom7vexeapp.models.NhaXe;
import com.example.nhom7vexeapp.models.Route;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // --- USER AUTH ---
    @POST("api/user-auth/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/user-auth/")
    Call<List<UserModel>> getUsers(@Query("Method") String method);

    @GET("api/user-auth/{id}/")
    Call<CustomerResponse> getUserAuthDetail(@Path("id") String id);

    @POST("api/user-auth/")
    Call<Void> registerAuth(@Body Map<String, String> data);

    @POST("api/user-auth/")
    Call<UserModel> createUser(@Query("Method") String method, @Body UserModel user);

    @PUT("api/user-auth/{id}/")
    Call<UserModel> updateUser(@Path("id") String id, @Body UserModel user);

    @DELETE("api/user-auth/{id}/")
    Call<Void> deleteUser(@Path("id") String id);

    // --- QUẢN LÝ NHÀ XE ---
    @GET("api/nhaxe/{id}/")
    Call<NhaXe> getNhaXeDetail(@Path("id") String id);

    @PUT("api/nhaxe/{id}/")
    Call<Void> updateNhaXeProfile(@Path("id") String id, @Body Map<String, String> data);

    @PATCH("api/nhaxe/{id}/")
    Call<Void> patchNhaXeProfile(@Path("id") String id, @Body Map<String, String> data);

    @POST("api/nhaxe/")
    Call<Void> createNhaXeProfile(@Body Map<String, String> data);

    // --- TÀI XẾ & CHI TIẾT TÀI XẾ ---
    @GET("api/taixe/")
    Call<List<Map<String, Object>>> getDriversRaw();

    @GET("api/taixe/")
    Call<List<TaixeModel>> getTaixeList(@Query("Method") String method);

    @POST("api/taixe/")
    Call<TaixeModel> createTaixe(@Query("Method") String method, @Body TaixeModel taixe);

    @PUT("api/taixe/{id}/")
    Call<TaixeModel> updateTaixe(@Path("id") String id, @Body TaixeModel taixe);

    @DELETE("api/taixe/{id}/")
    Call<Void> deleteTaixe(@Path("id") String id);

    @GET("api/chitiettaixe/")
    Call<List<Map<String, Object>>> getChiTietTaiXe();

    @GET("api/chitiettaixe/")
    Call<List<ChiTietTaiXeModel>> getChiTietTaiXeList(@Query("Method") String method);

    @POST("api/chitiettaixe/")
    Call<ChiTietTaiXeModel> createChiTietTaiXe(@Query("Method") String method, @Body ChiTietTaiXeModel chiTiet);

    @PUT("api/chitiettaixe/{id}/")
    Call<ChiTietTaiXeModel> updateChiTietTaiXe(@Path("id") String id, @Body ChiTietTaiXeModel chiTiet);

    // --- CHUYẾN XE ---
    @GET("api/chuyenxe/")
    Call<List<Trip>> getTrips();

    @POST("api/chuyenxe/")
    Call<Trip> createTrip(@Body Trip trip);

    @POST("api/chuyenxe/")
    Call<Void> createTripRaw(@Body Map<String, Object> data);

    @PUT("api/chuyenxe/{id}/")
    Call<Void> updateTrip(@Path("id") String id, @Body Trip trip);

    @PUT("api/chuyenxe/{id}/")
    Call<Void> updateTripRaw(@Path("id") String id, @Body Map<String, Object> data);

    @PATCH("api/chuyenxe/{id}/")
    Call<Void> patchTrip(@Path("id") String id, @Body Map<String, Object> data);

    // --- VÉ XE ---
    @GET("api/ve/")
    Call<List<Map<String, Object>>> getTicketsByTrip(@Query("ChuyenXe") String tripId);

    @PATCH("api/ve/{id}/")
    Call<Void> patchTicket(@Path("id") String id, @Body Map<String, Object> data);

    // --- TUYẾN XE ---
    @GET("api/tuyenxe/")
    Call<List<Map<String, Object>>> getRoutes();

    @PUT("api/tuyenxe/{id}/")
    Call<Void> updateRoute(@Path("id") String id, @Body Map<String, String> data);

    // --- LOẠI XE ---
    @GET("api/loaixe/")
    Call<List<Loaixe>> getLoaixe();

    @PUT("api/loaixe/{id}/")
    Call<Loaixe> updateLoaixe(@Path("id") String id, @Body Loaixe loaixe);

    // --- GHẾ NGỒI ---
    @POST("api/ghengoi/")
    Call<Void> createGheNgoi(@Body Map<String, Object> data);

    // --- QUẢN LÝ KHÁCH HÀNG ---
    @GET("api/khachhang/")
    Call<List<Map<String, Object>>> getKhachHangList();

    @GET("api/khachhang/{id}/")
    Call<Map<String, Object>> getKhachHangDetail(@Path("id") String id);

    @POST("api/khachhang/")
    Call<Void> createKhachHangProfile(@Body Map<String, String> data);

    @PUT("api/khachhang/{id}/")
    Call<Void> updateKhachHangProfile(@Path("id") String id, @Body Map<String, String> data);

    @DELETE("api/khachhang/{id}/")
    Call<Void> deleteKhachHang(@Path("id") String id);

    // --- PHƯƠNG TIỆN (XE) ---
    @GET("api/xe/")
    Call<List<Map<String, Object>>> getVehicles();
}
