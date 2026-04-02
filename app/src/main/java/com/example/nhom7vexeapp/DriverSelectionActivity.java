package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhom7vexeapp.adapters.DriverAdapter;
import com.example.nhom7vexeapp.models.Driver;
import java.util.ArrayList;
import java.util.List;

public class DriverSelectionActivity extends AppCompatActivity implements DriverAdapter.OnDriverClickListener {

    private RecyclerView rvDrivers;
    private DriverAdapter adapter;
    private List<Driver> driverList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_selection);

        rvDrivers = findViewById(R.id.rvDrivers);
        rvDrivers.setLayoutManager(new LinearLayoutManager(this));

        driverList = new ArrayList<>();
        driverList.add(new Driver("TX001", "Huy Phong", "0123456789"));
        driverList.add(new Driver("TX002", "Thanh Nhã", "0987654321"));

        adapter = new DriverAdapter(driverList, this);
        rvDrivers.setAdapter(adapter);

        setupBottomNavigation();
    }

    @Override
    public void onDriverClick(Driver driver) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có muốn phân công tài xế " + driver.getName() + " cho chuyến xe này?")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("selectedDriver", driver);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void setupBottomNavigation() {
        LinearLayout navHome = findViewById(R.id.nav_home_op_main);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, OperatorMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }
        // ... apply same pattern for other nav items if needed
    }
}
