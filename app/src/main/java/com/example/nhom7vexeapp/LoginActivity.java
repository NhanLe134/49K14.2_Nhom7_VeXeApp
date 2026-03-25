package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    
    private boolean isOperatorMode = false; // Mặc định là Khách hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tvLoginTitle = findViewById(R.id.tvLoginTitle);
        EditText edtUsername = findViewById(R.id.edtUsername);
        EditText edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvSwitchToOperator = findViewById(R.id.tvSwitchToOperator);

        // Sự kiện chuyển đổi giữa Khách hàng và Nhà xe
        tvSwitchToOperator.setOnClickListener(v -> {
            isOperatorMode = !isOperatorMode;
            if (isOperatorMode) {
                tvLoginTitle.setText("ĐĂNG NHẬP NHÀ XE");
                tvSwitchToOperator.setText("Bạn là khách hàng?");
                tvSwitchToOperator.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            } else {
                tvLoginTitle.setText("ĐĂNG NHẬP KHÁCH HÀNG");
                tvSwitchToOperator.setText("Bạn là nhà xe?");
                tvSwitchToOperator.setTextColor(android.graphics.Color.parseColor("#FF5722"));
            }
        });

        btnLogin.setOnClickListener(v -> {
            String user = edtUsername.getText().toString();
            String pass = edtPassword.getText().toString();

            if (isOperatorMode) {
                // LOGIC ĐĂNG NHẬP NHÀ XE
                if (user.equals("admin") && pass.equals("123")) {
                    startActivity(new Intent(LoginActivity.this, OperatorMainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Sai tài khoản nhà xe!", Toast.LENGTH_SHORT).show();
                }
            } else {
                // LOGIC ĐĂNG NHẬP KHÁCH HÀNG
                if (user.equals("user") && pass.equals("123")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Sai tài khoản khách hàng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
