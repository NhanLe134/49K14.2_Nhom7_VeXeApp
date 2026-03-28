package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Ánh xạ các thành phần giao diện
        LinearLayout navTickets = findViewById(R.id.nav_tickets);
        LinearLayout navFeedback = findViewById(R.id.nav_feedback);
        ImageView btnProfile = findViewById(R.id.btnProfile);
        
        // Xử lý sự kiện click cho nút Vé của tôi (Quản lý vé)
        if (navTickets != null) {
            navTickets.setOnClickListener(v -> {
                checkLoginAndNavigate(QLVeXeActivity.class);
            });
        }

        // Xử lý sự kiện click cho nút Đánh giá (Phản hồi)
        if (navFeedback != null) {
            navFeedback.setOnClickListener(v -> {
                checkLoginAndNavigate(PhanHoiActivity.class);
            });
        }

        // Xử lý sự kiện click cho nút Profile
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                if (isLoggedIn) {
                    // Nếu đã đăng nhập, chuyển đến màn hình Profile cá nhân
                    startActivity(new Intent(MainActivity.this, CustomerProfileActivity.class));
                } else {
                    // Nếu chưa đăng nhập, chuyển đến màn hình Login
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }
    }

    /**
     * Kiểm tra trạng thái đăng nhập trước khi chuyển sang chức năng yêu cầu tài khoản
     */
    private void checkLoginAndNavigate(Class<?> targetActivity) {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Đã đăng nhập -> cho phép truy cập
            Intent intent = new Intent(MainActivity.this, targetActivity);
            startActivity(intent);
        } else {
            // Chưa đăng nhập -> hiển thị thông báo
            showLoginRequiredDialog();
        }
    }

    /**
     * Hiển thị Dialog yêu cầu đăng nhập
     */
    private void showLoginRequiredDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Yêu cầu đăng nhập")
                .setMessage("Bạn cần đăng nhập để thực hiện chức năng này.")
                .setPositiveButton("Đăng nhập", (dialog, which) -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Để sau", (dialog, which) -> dialog.dismiss())
                .setCancelable(true)
                .show();
    }
}
