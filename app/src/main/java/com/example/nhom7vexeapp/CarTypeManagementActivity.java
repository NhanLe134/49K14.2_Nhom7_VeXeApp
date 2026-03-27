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
        setupBottomNav(); // Thêm phần điều hướng dưới cùng
    }

    private void initViews() {

        rvCarTypes = findViewById(R.id.rvCarTypes);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupEvents() {
        // KÍCH HOẠT NÚT BACK: Bấm là về lại màn hình Quản lý Phương tiện
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                // finish() sẽ đóng màn hình hiện tại và lộ ra màn hình nằm ngay dưới nó
                finish();
            });
        }
    }

    private void setupRecyclerView() {
        carTypeList = new ArrayList<>();
        // Giả lập dữ liệu như cũ của Xù
        carTypeList.add(new CarType("Loại xe A", 4, "150.000 đ", "27/03/2026", 0xFF0098D6));
        carTypeList.add(new CarType("Loại xe B", 7, "200.000 đ", "26/03/2026", 0xFF4CAF50));
        carTypeList.add(new CarType("Loại xe C", 9, "Chưa thiết lập", "Chưa cập nhật", 0xFF9C27B0));

        adapter = new CarTypeAdapter(carTypeList, this);
        rvCarTypes.setLayoutManager(new LinearLayoutManager(this));
        rvCarTypes.setAdapter(adapter);
    }

    private void setupBottomNav() {
        // Ánh xạ các nút ở thanh Bottom Nav (Xù nhớ kiểm tra ID trong XML nhé)
        LinearLayout navHome = findViewById(R.id.nav_home_op);
        LinearLayout navVehicle = findViewById(R.id.nav_vehicle_op);

        // Bấm vào Trang chủ -> Về màn hình chính nhà xe
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, QLNhaxeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }

        // Bấm vào Phương tiện -> Về lại màn hình chọn trung gian
        if (navVehicle != null) {
            navVehicle.setOnClickListener(v -> {
                finish(); // Vì màn hình trung gian đang nằm ngay dưới nên chỉ cần finish
            });
        }
    }
}