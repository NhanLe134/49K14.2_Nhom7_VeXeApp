package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PhuongTienManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phuongtien_management);

        // Bấm vào nút Quản lý loại xe
        CardView btnCarType = findViewById(R.id.btnGoToCarType);
        btnCarType.setOnClickListener(v -> {
            Intent intent = new Intent(this, CarTypeManagementActivity.class);
            startActivity(intent);
        });

        // Nút quay lại trên Toolbar
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}