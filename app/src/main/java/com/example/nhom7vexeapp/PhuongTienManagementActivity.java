package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PhuongTienManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phuongtien_management);

        // Bấm vào nút Quản lý loại xe
        CardView btnCarType = findViewById(R.id.btnGoToCarType);
        if (btnCarType != null) {
            btnCarType.setOnClickListener(v -> {
                Intent intent = new Intent(this, CarTypeManagementActivity.class);
                startActivity(intent);
            });
        }

        // Nút quay lại trên Toolbar
        if (findViewById(R.id.btnBack) != null) {
            findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        }

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        // Trang chủ
        LinearLayout navHome = findViewById(R.id.nav_home_op_main);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, OperatorMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }

        // Tài xế
        LinearLayout navDriver = findViewById(R.id.nav_driver_op);
        if (navDriver != null) {
            navDriver.setOnClickListener(v -> {
                Intent intent = new Intent(this, QLNhaxeActivity.class);
                startActivity(intent);
            });
        }

        // Chuyến xe
        LinearLayout navTrip = findViewById(R.id.nav_trip_op);
        if (navTrip != null) {
            navTrip.setOnClickListener(v -> {
                Intent intent = new Intent(this, TripListActivity.class);
                startActivity(intent);
            });
        }

        // Tuyến xe
        LinearLayout navRoute = findViewById(R.id.nav_route_op);
        if (navRoute != null) {
            navRoute.setOnClickListener(v -> {
                Intent intent = new Intent(this, QLTuyenxeActivity.class);
                startActivity(intent);
            });
        }
    }
}
