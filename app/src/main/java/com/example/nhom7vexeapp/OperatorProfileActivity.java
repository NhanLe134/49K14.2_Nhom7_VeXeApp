package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nhom7vexeapp.api.ApiClient;
import com.example.nhom7vexeapp.api.ApiService;
import com.example.nhom7vexeapp.models.NhaXe;
import com.google.android.material.button.MaterialButton;

import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperatorProfileActivity extends AppCompatActivity {

    private TextView tvOpNameHeader, tvOpNameDetail, tvOpRep, tvOpAddress, tvOpPhone, tvOpEmail;
    private MaterialButton btnEdit, btnLogout;
    private ImageView btnBack, imgOpBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_profile);

        initViews();
        
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String opUid = pref.getString("op_uid", "");
        String fallbackName = pref.getString("op_user", "Nhà xe");

        if (opUid.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        loadOperatorDataFromDB(opUid, fallbackName);
        setupBottomNavigation();

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(this, EditOperatorProfileActivity.class);
                startActivityForResult(intent, 100);
            });
        }
        if (btnLogout != null) btnLogout.setOnClickListener(v -> handleLogout());
    }

    private void initViews() {
        tvOpNameHeader = findViewById(R.id.tvOpBusName);
        tvOpNameDetail = findViewById(R.id.tvOpNameDetail);
        tvOpRep = findViewById(R.id.tvOpRep);
        tvOpAddress = findViewById(R.id.tvOpAddress);
        tvOpPhone = findViewById(R.id.tvOpPhone);
        tvOpEmail = findViewById(R.id.tvOpEmail);
        btnEdit = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogoutOp);
        btnBack = findViewById(R.id.btnBack);
        imgOpBanner = findViewById(R.id.imgOpBanner); 
    }

    private void loadOperatorDataFromDB(String opUid, String fallbackName) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getNhaXeDetail(opUid).enqueue(new Callback<NhaXe>() {
            @Override
            public void onResponse(Call<NhaXe> call, Response<NhaXe> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NhaXe nhaXe = response.body();
                    
                    String tenNhaXe = (nhaXe.getBusName() == null || nhaXe.getBusName().isEmpty()) ? fallbackName : nhaXe.getBusName();
                    
                    if (tvOpNameHeader != null) tvOpNameHeader.setText(tenNhaXe);
                    if (tvOpNameDetail != null) tvOpNameDetail.setText(tenNhaXe);
                    
                    if (tvOpRep != null) tvOpRep.setText("Chưa cập nhật"); // API chưa có trường này
                    if (tvOpAddress != null) tvOpAddress.setText(nhaXe.getAddress());
                    if (tvOpPhone != null) tvOpPhone.setText(nhaXe.getPhone());
                    if (tvOpEmail != null) tvOpEmail.setText(nhaXe.getEmail());

                    String imgUrl = nhaXe.getBannerUrl();
                    if (imgUrl != null && !imgUrl.isEmpty() && imgOpBanner != null) {
                        Glide.with(OperatorProfileActivity.this)
                                .load(imgUrl)
                                .placeholder(R.drawable.banner_nhaxe)
                                .error(R.drawable.banner_nhaxe)
                                .into(imgOpBanner);
                    }
                }
            }

            @Override
            public void onFailure(Call<NhaXe> call, Throwable t) {
                if (tvOpNameHeader != null) tvOpNameHeader.setText(fallbackName);
            }
        });
    }


    private String findValue(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            if (map.containsKey(key) && map.get(key) != null && !map.get(key).toString().equals("null")) {
                return map.get(key).toString();
            }
            // Thử tìm kiểu viết thường hoàn toàn
            for (String actualKey : map.keySet()) {
                if (actualKey.equalsIgnoreCase(key) && map.get(actualKey) != null) {
                    return map.get(actualKey).toString();
                }
            }
        }
        return "";
    }

    private void handleLogout() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        pref.edit().clear().apply();
        startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    private void setupBottomNavigation() {
        View navHome = findViewById(R.id.nav_home_op);
        if (navHome != null) navHome.setOnClickListener(v -> {
            startActivity(new Intent(this, OperatorMainActivity.class));
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            loadOperatorDataFromDB(pref.getString("op_uid", ""), pref.getString("op_user", ""));
        }
    }
}
