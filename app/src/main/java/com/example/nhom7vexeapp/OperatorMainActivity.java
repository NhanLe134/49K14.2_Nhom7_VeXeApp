package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        imgOpProfile = findViewById(R.id.imgOpProfile);
        navHome = findViewById(R.id.nav_home_op_main);
        if (navHome == null) navHome = findViewById(R.id.nav_home_op);
        navDriver = findViewById(R.id.nav_driver_op);
        navVehicle = findViewById(R.id.nav_vehicle_op);
        navTrip = findViewById(R.id.nav_trip_op);
        navRoute = findViewById(R.id.nav_route_op);
    }

    private void setupEvents() {
        if (imgOpProfile != null) {
            imgOpProfile.setOnClickListener(v -> startActivity(new Intent(this, OperatorProfileActivity.class)));
        }

        // TAB PHƯƠNG TIỆN: Dẫn đến màn hình trung gian (2 nút bấm như ảnh bạn gửi)
        if (navVehicle != null) {
            navVehicle.setOnClickListener(v -> {
                startActivity(new Intent(this, PhuongTienManagementActivity.class));
            });
        }

        if (navTrip != null) {
            navTrip.setOnClickListener(v -> startActivity(new Intent(this, TripListActivity.class)));
        }

        if (navRoute != null) {
            navRoute.setOnClickListener(v -> startActivity(new Intent(this, QLTuyenxeActivity.class)));
        }

        if (navDriver != null) {
            navDriver.setOnClickListener(v -> startActivity(new Intent(this, QLNhaxeActivity.class)));
        }

        if (navHome != null) {
            navHome.setOnClickListener(v -> Toast.makeText(this, "Bạn đang ở trang chủ", Toast.LENGTH_SHORT).show());
        }
    }
}
