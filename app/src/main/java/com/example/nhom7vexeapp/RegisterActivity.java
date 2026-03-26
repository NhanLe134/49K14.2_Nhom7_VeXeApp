package com.example.nhom7vexeapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtOperatorName, edtRepName, edtUsername, edtPassword, edtConfirmPassword, edtAddress, edtPhone;
    private TextView tvErrorOperatorName, tvErrorRepName, tvErrorUsername, tvErrorPassword, tvErrorConfirmPassword, tvErrorAddress, tvErrorPhone;
    private Button btnRegister, btnCancel, btnVerifyPhone;
    private boolean isOtpVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());

        // Nút hoàn thành ban đầu mờ và không bấm được
        btnRegister.setEnabled(false);
        btnRegister.setAlpha(0.5f);

        btnVerifyPhone.setOnClickListener(v -> {
            if (edtPhone.getText().toString().trim().length() < 10) {
                showError(edtPhone, tvErrorPhone);
                tvErrorPhone.setText("Vui lòng nhập số điện thoại hợp lệ trước khi xác nhận.");
            } else {
                showOtpDialog();
            }
        });

        btnRegister.setOnClickListener(v -> {
            if (validateForm()) {
                saveAccount();
                Toast.makeText(this, "Đăng ký tài khoản nhà xe thành công!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void initViews() {
        edtOperatorName = findViewById(R.id.edtOperatorName);
        edtRepName = findViewById(R.id.edtRepName);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);

        tvErrorOperatorName = findViewById(R.id.tvErrorOperatorName);
        tvErrorRepName = findViewById(R.id.tvErrorRepName);
        tvErrorUsername = findViewById(R.id.tvErrorUsername);
        tvErrorPassword = findViewById(R.id.tvErrorPassword);
        tvErrorConfirmPassword = findViewById(R.id.tvErrorConfirmPassword);
        tvErrorAddress = findViewById(R.id.tvErrorAddress);
        tvErrorPhone = findViewById(R.id.tvErrorPhone);

        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancel);
        btnVerifyPhone = findViewById(R.id.btnVerifyPhone);
    }

    private void showOtpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_otp, null);
        builder.setView(view);

        EditText otp1 = view.findViewById(R.id.otp1);
        EditText otp2 = view.findViewById(R.id.otp2);
        EditText otp3 = view.findViewById(R.id.otp3);
        EditText otp4 = view.findViewById(R.id.otp4);
        EditText otp5 = view.findViewById(R.id.otp5);
        EditText otp6 = view.findViewById(R.id.otp6);
        TextView tvOtpError = view.findViewById(R.id.tvOtpError);
        Button btnVerifyOtp = view.findViewById(R.id.btnVerifyOtp);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Tự động chuyển ô khi nhập
        setupOtpEntry(otp1, otp2);
        setupOtpEntry(otp2, otp3);
        setupOtpEntry(otp3, otp4);
        setupOtpEntry(otp4, otp5);
        setupOtpEntry(otp5, otp6);
        
        // Khi nhập ô cuối cùng thì hiện nút xác nhận
        otp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) btnVerifyOtp.setVisibility(View.VISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnVerifyOtp.setOnClickListener(v -> {
            String code = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() +
                          otp4.getText().toString() + otp5.getText().toString() + otp6.getText().toString();

            if (code.equals("123456")) { // Mã OTP giả định là 123456
                isOtpVerified = true;
                btnRegister.setEnabled(true);
                btnRegister.setAlpha(1.0f);
                btnVerifyPhone.setText("Đã xác nhận");
                btnVerifyPhone.setEnabled(false);
                Toast.makeText(this, "Xác thực OTP thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                tvOtpError.setVisibility(View.VISIBLE);
                tvOtpError.setText("Mã OTP không đúng. Vui lòng thử lại.");
            }
        });
    }

    private void setupOtpEntry(EditText current, EditText next) {
        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) next.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private boolean validateForm() {
        boolean isValid = true;
        resetErrors();

        if (edtOperatorName.getText().toString().trim().isEmpty()) {
            showError(edtOperatorName, tvErrorOperatorName);
            isValid = false;
        }
        if (edtRepName.getText().toString().trim().isEmpty()) {
            showError(edtRepName, tvErrorRepName);
            isValid = false;
        }
        if (edtUsername.getText().toString().trim().isEmpty()) {
            showError(edtUsername, tvErrorUsername);
            isValid = false;
        }
        if (edtPassword.getText().toString().trim().isEmpty()) {
            showError(edtPassword, tvErrorPassword);
            isValid = false;
        }
        if (!edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())) {
            showError(edtConfirmPassword, tvErrorConfirmPassword);
            tvErrorConfirmPassword.setText("Mật khẩu nhập lại không khớp.");
            isValid = false;
        }
        if (edtAddress.getText().toString().trim().isEmpty()) {
            showError(edtAddress, tvErrorAddress);
            isValid = false;
        }
        
        if (!isOtpVerified) {
            Toast.makeText(this, "Vui lòng xác nhận số điện thoại bằng OTP.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void showError(EditText editText, TextView errorText) {
        editText.setBackgroundResource(R.drawable.bg_input_error);
        errorText.setVisibility(View.VISIBLE);
    }

    private void resetErrors() {
        edtOperatorName.setBackgroundResource(R.drawable.bg_input_white);
        edtRepName.setBackgroundResource(R.drawable.bg_input_white);
        edtUsername.setBackgroundResource(R.drawable.bg_input_white);
        edtPassword.setBackgroundResource(R.drawable.bg_input_white);
        edtConfirmPassword.setBackgroundResource(R.drawable.bg_input_white);
        edtAddress.setBackgroundResource(R.drawable.bg_input_white);
        edtPhone.setBackgroundResource(R.drawable.bg_input_white);

        tvErrorOperatorName.setVisibility(View.GONE);
        tvErrorRepName.setVisibility(View.GONE);
        tvErrorUsername.setVisibility(View.GONE);
        tvErrorPassword.setVisibility(View.GONE);
        tvErrorConfirmPassword.setVisibility(View.GONE);
        tvErrorAddress.setVisibility(View.GONE);
        tvErrorPhone.setVisibility(View.GONE);
    }

    private void saveAccount() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("op_user", edtUsername.getText().toString());
        editor.putString("op_pass", edtPassword.getText().toString());
        editor.apply();
    }
}
