package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class OperatorMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_main);

        // Tìm ImageView Avatar qua ID imgOpProfile đã đặt trong layout
        ImageView btnProfile = findViewById(R.id.imgOpProfile);
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                // Chuyển sang màn hình Hồ sơ nhà xe (OperatorProfileActivity)
                Intent intent = new Intent(OperatorMainActivity.this, OperatorProfileActivity.class);
                startActivity(intent);
            });
        }

        // Bottom Navigation
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        // Trang chủ (Chính là màn này)
        LinearLayout navHome = findViewById(R.id.nav_home_op_main);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                // Đang ở trang chủ rồi, không cần làm gì hoặc reload
            });
        }

        // Phương tiện navigation
        LinearLayout navVehicle = findViewById(R.id.nav_vehicle_op);
        if (navVehicle != null) {
            navVehicle.setOnClickListener(v -> {
                Intent intent = new Intent(OperatorMainActivity.this, PhuongTienManagementActivity.class);
                startActivity(intent);
            });
        }

        // Chuyến xe navigation
        LinearLayout navTrip = findViewById(R.id.nav_trip_op);
        if (navTrip != null) {
            navTrip.setOnClickListener(v -> {
                Intent intent = new Intent(OperatorMainActivity.this, TripListActivity.class);
                startActivity(intent);
            });
        }

        // Tuyến xe navigation
        LinearLayout navRoute = findViewById(R.id.nav_route_op);
        if (navRoute != null) {
            navRoute.setOnClickListener(v -> {
                Intent intent = new Intent(OperatorMainActivity.this, QLTuyenxeActivity.class);
                startActivity(intent);
            });
        }

        // Tài xế navigation (Nếu có màn hình quản lý tài xế, hiện tại chưa thấy rõ activity nào chuyên biệt)
        LinearLayout navDriver = findViewById(R.id.nav_driver_op);
        if (navDriver != null) {
            navDriver.setOnClickListener(v -> {
                // Tạm thời dẫn tới QLNhaxe hoặc thông báo tính năng đang phát triển
                Intent intent = new Intent(OperatorMainActivity.this, QLNhaxeActivity.class);
                startActivity(intent);
            });
        }
    }
}
