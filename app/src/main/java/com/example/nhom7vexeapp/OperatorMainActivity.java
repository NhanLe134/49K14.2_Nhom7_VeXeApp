package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OperatorMainActivity extends AppCompatActivity {

    private LinearLayout navHome, navDriver, navVehicle, navTrip, navRoute;
    private ImageView imgOpProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_main);

        initViews();
        setupEvents();
    }

    private void initViews() {
        // Ánh xạ Avatar góc trên bên phải
        imgOpProfile = findViewById(R.id.imgOpProfile);

        // Ánh xạ các nút ở thanh Bottom Navigation
        navHome = findViewById(R.id.nav_home_op_main);
        navDriver = findViewById(R.id.nav_driver_op);
        navVehicle = findViewById(R.id.nav_vehicle_op); // Nút Phương tiện quan trọng của Xù
        navTrip = findViewById(R.id.nav_trip_op);
        navRoute = findViewById(R.id.nav_route_op);
    }

    private void setupEvents() {
        // 1. Khi bấm vào Avatar -> Vào trang Quản lý thông tin nhà xe của Xù
        if (imgOpProfile != null) {
            imgOpProfile.setOnClickListener(v -> {
                Intent intent = new Intent(OperatorMainActivity.this, QLNhaxeActivity.class);
                startActivity(intent);
            });
        }

        // 2. Khi bấm vào tab "Phương tiện" ở navbar -> Vào thẳng trang Quản lý phương tiện
        if (navVehicle != null) {
            navVehicle.setOnClickListener(v -> {
                Intent intent = new Intent(OperatorMainActivity.this, PhuongTienManagementActivity.class);
                startActivity(intent);
            });
        }

        // 3. Khi bấm vào tab "Tuyến xe"
        if (navRoute != null) {
            navRoute.setOnClickListener(v -> {
                Intent intent = new Intent(OperatorMainActivity.this, QLTuyenxeActivity.class);
                startActivity(intent);
            });
        }

        // 4. Các tab khác (Tạm thời hiện thông báo)
        if (navHome != null) {
            navHome.setOnClickListener(v ->
                    Toast.makeText(this, "Bạn đang ở Trang chủ", Toast.LENGTH_SHORT).show());
        }

        if (navDriver != null) {
            navDriver.setOnClickListener(v ->
                    Toast.makeText(this, "Chức năng Quản lý tài xế đang phát triển", Toast.LENGTH_SHORT).show());
        }
    }
}