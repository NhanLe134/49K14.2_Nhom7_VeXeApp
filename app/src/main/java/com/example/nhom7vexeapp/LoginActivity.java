package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private boolean isOperatorMode = false;
    private LinearLayout layoutCustomerLogin, layoutOperatorLogin;
    private TextView tvLoginTitle, tvSwitchMode, tvRegisterCustomer, tvRegisterOperator;
    private EditText edtPhoneLogin, edtUsername, edtPassword;
    private Button btnLoginCustomer, btnLoginOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        tvSwitchMode.setOnClickListener(v -> {
            isOperatorMode = !isOperatorMode;
            updateUI();
        });

        tvRegisterCustomer.setOnClickListener(v -> {
            startActivity(new Intent(this, CustomerRegisterActivity.class));
        });

        tvRegisterOperator.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        btnLoginCustomer.setOnClickListener(v -> handleCustomerLogin());
        btnLoginOperator.setOnClickListener(v -> handleOperatorLogin());
        
        // Nhận SĐT vừa đăng ký xong (nếu có)
        String registeredPhone = getIntent().getStringExtra("registeredPhone");
        if (registeredPhone != null) {
            edtPhoneLogin.setText(registeredPhone);
        }
    }

    private void initViews() {
        layoutCustomerLogin = findViewById(R.id.layoutCustomerLogin);
        layoutOperatorLogin = findViewById(R.id.layoutOperatorLogin);
        tvLoginTitle = findViewById(R.id.tvLoginTitle);
        tvSwitchMode = findViewById(R.id.tvSwitchMode);
        tvRegisterCustomer = findViewById(R.id.tvRegisterCustomer);
        tvRegisterOperator = findViewById(R.id.tvRegisterOperator);
        edtPhoneLogin = findViewById(R.id.edtPhoneLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLoginCustomer = findViewById(R.id.btnLoginCustomer);
        btnLoginOperator = findViewById(R.id.btnLoginOperator);
    }

    private void updateUI() {
        if (isOperatorMode) {
            tvLoginTitle.setText("Đăng nhập");
            layoutCustomerLogin.setVisibility(View.GONE);
            layoutOperatorLogin.setVisibility(View.VISIBLE);
            tvSwitchMode.setText("Bạn là khách hàng?");
            tvSwitchMode.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        } else {
            tvLoginTitle.setText("Đăng nhập");
            layoutCustomerLogin.setVisibility(View.VISIBLE);
            layoutOperatorLogin.setVisibility(View.GONE);
            tvSwitchMode.setText("Bạn là nhà xe?");
            tvSwitchMode.setTextColor(android.graphics.Color.parseColor("#FF5722"));
        }
    }

    private void handleCustomerLogin() {
        String phone = edtPhoneLogin.getText().toString().trim();
        if (phone.length() >= 10) {
            SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            
            String name, dob;
            
            // Xử lý thông tin theo SĐT yêu cầu
            if (phone.equals("0799376815")) {
                name = "Huy Phong";
                dob = "03/10/2005";
            } else {
                // Kiểm tra xem SĐT này đã đăng ký qua App chưa
                name = pref.getString("name_" + phone, "Nguyễn Văn An");
                dob = pref.getString("dob_" + phone, "20/11/2004");
            }
            
            editor.putBoolean("isLoggedIn", true);
            editor.putString("customerPhone", phone);
            editor.putString("customerName", name);
            editor.putString("customerDob", dob);
            editor.apply();

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Vui lòng nhập số điện thoại hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleOperatorLogin() {
        String user = edtUsername.getText().toString();
        String pass = edtPassword.getText().toString();

        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUser = pref.getString("op_user", "admin");
        String savedPass = pref.getString("op_pass", "123");

        if (user.equals(savedUser) && pass.equals(savedPass)) {
            startActivity(new Intent(LoginActivity.this, OperatorMainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Sai tài khoản nhà xe!", Toast.LENGTH_SHORT).show();
        }
    }
}
