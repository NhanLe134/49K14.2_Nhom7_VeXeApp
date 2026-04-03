package com.example.nhom7vexeapp.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import java.util.List;

public interface ApiService {
    @POST("api/user-auth/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    // Thử dùng đúng tên cột SODIENTHOAI (viết hoa) để Django nhận diện filter
    @GET("api/user-auth/")
    Call<List<CustomerResponse>> checkUserRole(@Query("SODIENTHOAI") String phone);
}
