package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nhom7vexeapp.api.ApiClient;
import com.example.nhom7vexeapp.api.ApiService;
import com.example.nhom7vexeapp.api.CustomerResponse;
import com.google.android.material.button.MaterialButton;

import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerProfileActivity extends AppCompatActivity {

    private TextView tvName, tvPhone, tvDob;
    private ImageView btnBack, btnEdit, imgAvatar;
    private MaterialButton btnLogout;
    private LinearLayout navHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        initViews();
        
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String customerUid = pref.getString("customerUid", "");

        if (customerUid.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        loadAllData(customerUid);

        btnBack.setOnClickListener(v -> finish());
        navHome.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        });

        btnEdit.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, EditCustomerProfileActivity.class), 300);
        });
    }

    private void initViews() {
        tvName = findViewById(R.id.tvProfileName);
        tvPhone = findViewById(R.id.tvProfilePhone);
        tvDob = findViewById(R.id.tvProfileDob);
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);
        navHome = findViewById(R.id.nav_home_profile);
        imgAvatar = findViewById(R.id.imgProfileAvatar);
    }

    private void loadAllData(String uid) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // 1. LẤY TÊN VÀ NGÀY SINH TỪ BẢNG KHACHHANG
        apiService.getKhachHangDetail(uid).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> data = response.body();
                    tvName.setText(findValue(data, "TenKhachHang", "tenkhachhang"));
                    tvDob.setText(findValue(data, "NgaySinh", "ngaysinh"));

                    String imgUrl = findValue(data, "AnhDaiDienURL", "anhdaidienurl");
                    if (!imgUrl.isEmpty() && imgAvatar != null) {
                        Glide.with(CustomerProfileActivity.this).load(imgUrl).placeholder(R.drawable.logo).into(imgAvatar);
                    }
                }
            }
            @Override public void onFailure(Call<Map<String, Object>> call, Throwable t) {}
        });

        // 2. LẤY SĐT TỪ BẢNG AUTH (user-auth)
        apiService.getUserAuthDetail(uid).enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvPhone.setText(response.body().getSdt());
                }
            }
            @Override public void onFailure(Call<CustomerResponse> call, Throwable t) {}
        });
    }

    private String findValue(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            if (map.containsKey(key) && map.get(key) != null && !map.get(key).toString().equals("null")) {
                return map.get(key).toString();
            }
        }
        return "Chưa cập nhật";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == RESULT_OK) {
            SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            loadAllData(pref.getString("customerUid", ""));
        }
    }
}
