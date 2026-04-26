package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PhuongTienManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phuongtien_management);

        initViews();
        setupBottomNavigation();
    }

    private void initViews() {
        // Nút Back trên Toolbar
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        // 1. Nút "Quản lý Loại xe" (Nút bên trên)
        CardView btnCarType = findViewById(R.id.btnGoToCarType);
        if (btnCarType != null) {
            btnCarType.setOnClickListener(v -> {
                startActivity(new Intent(this, CarTypeManagementActivity.class));
            });
        }

        // 2. NÚT "QUẢN LÝ PHƯƠNG TIỆN" (Nút bên dưới - Dẫn đến danh sách xe thực tế)
        CardView btnManageVehicle = findViewById(R.id.btnManageVehicleMain);
        if (btnManageVehicle != null) {
            btnManageVehicle.setOnClickListener(v -> {
                // Đảm bảo mở đúng QLPhuongTienActivity (màn hình có danh sách và icon thùng rác)
                try {
                    Intent intent = new Intent(this, QLPhuongTienActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Lỗi: Chưa khai báo QLPhuongTienActivity trong Manifest!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupBottomNavigation() {
        // Đồng bộ logic quay về trang chủ
        View navHome = findViewById(R.id.nav_home_op_main);
        if (navHome == null) navHome = findViewById(R.id.nav_home_op);
        
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, OperatorMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });
        }

        // Tab Tuyến xe
        View navRoute = findViewById(R.id.nav_route_op);
        if (navRoute != null) {
            navRoute.setOnClickListener(v -> {
                startActivity(new Intent(this, QLTuyenxeActivity.class));
                finish();
            });
        }
        
        // Tab Chuyến xe
        View navTrip = findViewById(R.id.nav_trip_op);
        if (navTrip != null) {
            navTrip.setOnClickListener(v -> {
                startActivity(new Intent(this, TripListActivity.class));
                finish();
            });
        }
    }
}
