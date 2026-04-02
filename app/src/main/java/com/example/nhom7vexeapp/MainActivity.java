package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1. Giữ cấu trúc khai báo biến rõ ràng của Xù
    private ImageView btnProfile;
    private LinearLayout navHome, navSearch, navTickets, navFeedback;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo SharedPreferences (Dùng chung cho cả nhóm)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        initViews();    // Ánh xạ linh kiện
        setupEvents();   // Cài đặt sự kiện bấm nút
    }

    private void initViews() {
        btnProfile = findViewById(R.id.btnProfile);
        navHome = findViewById(R.id.nav_home);
        navSearch = findViewById(R.id.nav_search);
        navTickets = findViewById(R.id.nav_tickets);
        navFeedback = findViewById(R.id.nav_feedback);
    }

    private void setupEvents() {
        // 1. Nút Profile (Avatar) - Logic chung của cả 2 bên
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                if (isLoggedIn) {
                    startActivity(new Intent(MainActivity.this, CustomerProfileActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }

        // 2. Nút Tìm kiếm vé (Tính năng riêng của Xù - Cho phép vào không cần login)
        if (navSearch != null) {
            navSearch.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, SearchTicketActivity.class));
            });
        }

        // 3. Nút Vé của tôi (Dùng hàm kiểm tra của bạn nhóm)
        if (navTickets != null) {
            navTickets.setOnClickListener(v -> checkLoginAndNavigate(QLVeXeActivity.class));
        }

        // 4. Nút Đánh giá (Dùng hàm kiểm tra của bạn nhóm)
        if (navFeedback != null) {
            navFeedback.setOnClickListener(v -> checkLoginAndNavigate(PhanHoiActivity.class));
        }

        // 5. Nút Trang chủ
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                // Đang ở trang chủ, có thể thêm hiệu ứng cuộn lên đầu trang
            });
        }
    }

    /**
     * Hàm kiểm tra đăng nhập (Logic quan trọng từ bản dev của bạn nhóm)
     */
    private void checkLoginAndNavigate(Class<?> targetActivity) {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Đã đăng nhập -> Cho đi tiếp
            Intent intent = new Intent(MainActivity.this, targetActivity);
            startActivity(intent);
        } else {
            // Chưa đăng nhập -> Hiện Dialog yêu cầu
            showLoginRequiredDialog();
        }
    }

    /**
     * Hiển thị Dialog (Logic từ bản dev của bạn nhóm)
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