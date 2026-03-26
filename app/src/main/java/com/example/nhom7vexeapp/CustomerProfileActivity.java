package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class CustomerProfileActivity extends AppCompatActivity {

    private TextView tvName, tvPhone, tvDob;
    private ImageView btnBack, btnEdit;
    private MaterialButton btnLogout;
    private LinearLayout navHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        initViews();
        loadData();

        // Nút back ở trên cùng
        btnBack.setOnClickListener(v -> finish());

        // Nút Trang chủ ở thanh menu dưới cùng
        navHome.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            Intent intent = new Intent(CustomerProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerProfileActivity.this, EditCustomerProfileActivity.class);
            startActivityForResult(intent, 300);
        });
    }

    private void initViews() {
        tvName = findViewById(R.id.tvProfileName);
        tvPhone = findViewById(R.id.tvProfilePhone);
        tvDob = findViewById(R.id.tvProfileDob);
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);
        navHome = findViewById(R.id.nav_home_profile);
    }

    private void loadData() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        tvName.setText(pref.getString("customerName", "Nguyễn Văn An"));
        tvPhone.setText(pref.getString("customerPhone", "0916441979"));
        tvDob.setText(pref.getString("customerDob", "20/11/2004"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == RESULT_OK) {
            loadData(); // Reload updated data
        }
    }
}
