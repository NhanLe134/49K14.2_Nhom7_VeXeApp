package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom7vexeapp.adapters.CarTypeAdapter;
import com.example.nhom7vexeapp.models.CarType;

import java.util.ArrayList;
import java.util.List;

public class CarTypeManagementActivity extends AppCompatActivity {

    private RecyclerView rvCarTypes;
    private CarTypeAdapter adapter;
    private List<CarType> carTypeList;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type_management);

        initViews();
        setupRecyclerView();
        setupEvents();
        setupBottomNav(); 
    }

    private void initViews() {
        rvCarTypes = findViewById(R.id.rvCarTypes);
        btnBack = findViewById(R.id.btnBack);
        if (btnBack == null) btnBack = findViewById(R.id.btnProfile); // In layout it's btnProfile but acting as back/profile
    }

    private void setupEvents() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                finish();
            });
        }
    }

    private void setupRecyclerView() {
        carTypeList = new ArrayList<>();
        carTypeList.add(new CarType("Loại xe A", 4, "150.000 đ", "27/03/2026", 0xFF0098D6));
        carTypeList.add(new CarType("Loại xe B", 7, "200.000 đ", "26/03/2026", 0xFF4CAF50));
        carTypeList.add(new CarType("Loại xe C", 9, "Chưa thiết lập", "Chưa cập nhật", 0xFF9C27B0));

        adapter = new CarTypeAdapter(carTypeList, this);
        rvCarTypes.setLayoutManager(new LinearLayoutManager(this));
        rvCarTypes.setAdapter(adapter);
    }

    private void setupBottomNav() {
        LinearLayout navHome = findViewById(R.id.nav_home_op);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, OperatorMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }

        LinearLayout navVehicle = findViewById(R.id.nav_vehicle_op);
        if (navVehicle != null) {
            navVehicle.setOnClickListener(v -> {
                Intent intent = new Intent(this, PhuongTienManagementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }

        LinearLayout navTrip = findViewById(R.id.nav_trip_op);
        if (navTrip != null) {
            navTrip.setOnClickListener(v -> {
                Intent intent = new Intent(this, TripListActivity.class);
                startActivity(intent);
            });
        }

        LinearLayout navRoute = findViewById(R.id.nav_route_op);
        if (navRoute != null) {
            navRoute.setOnClickListener(v -> {
                Intent intent = new Intent(this, QLTuyenxeActivity.class);
                startActivity(intent);
            });
        }

        LinearLayout navDriver = findViewById(R.id.nav_driver_op);
        if (navDriver != null) {
            navDriver.setOnClickListener(v -> {
                Intent intent = new Intent(this, QLNhaxeActivity.class);
                startActivity(intent);
            });
        }
    }
}
