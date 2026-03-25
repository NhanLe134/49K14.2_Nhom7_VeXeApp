package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Nút Profile ở góc trên bên phải
        ImageView btnProfile = findViewById(R.id.btnProfile);
        
        btnProfile.setOnClickListener(v -> {
            // Khách hàng bấm vào đây sẽ dẫn đến trang Đăng nhập dành cho nhà xe
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
