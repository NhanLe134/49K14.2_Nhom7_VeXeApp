package com.example.nhom7vexeapp.api;

import com.example.nhom7vexeapp.models.Loaixe;
import com.example.nhom7vexeapp.models.Trip;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;
import java.util.List;

public interface ApiService {
    @POST("api/user-auth/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/user-auth/")
    Call<List<CustomerResponse>> getUsers();

    @GET("api/chuyenxe/")
    Call<List<Trip>> getTrips();

    @POST("api/chuyenxe/")
    Call<Trip> createTrip(@Body Trip trip);

    @GET("api/loaixe/")
    Call<List<Loaixe>> getLoaixe();

    // Thêm API để cập nhật loại xe (sử dụng PUT và truyền LoaixeID)
    @PUT("api/loaixe/{id}/")
    Call<Loaixe> updateLoaixe(@Path("id") String id, @Body Loaixe loaixe);
}
