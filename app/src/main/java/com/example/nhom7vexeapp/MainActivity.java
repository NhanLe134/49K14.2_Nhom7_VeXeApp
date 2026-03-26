package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView btnProfile = findViewById(R.id.btnProfile);
        
        btnProfile.setOnClickListener(v -> {
            SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                // Nếu đã đăng nhập, vào trang Thông tin khách hàng
                Intent intent = new Intent(MainActivity.this, CustomerProfileActivity.class);
                startActivity(intent);
            } else {
                // Nếu chưa, vào trang Đăng nhập
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
