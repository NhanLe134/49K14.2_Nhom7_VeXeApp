package com.example.nhom7vexeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;

public class CustomerRegisterActivity extends AppCompatActivity {

    private LinearLayout layoutStepPhone;
    private ScrollView layoutStepForm;
    private EditText edtPhoneInput, edtFullName, edtDob;
    private TextView tvFixedPhone, tvFileName, tvHeaderTitle;
    private TextView tvErrorFullName, tvErrorDob, tvErrorFile;
    private MaterialButton btnVerifyPhone, btnFinish, btnCancel, btnSelectFile;
    private String verifiedPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        initViews();

        findViewById(R.id.btnBack).setOnClickListener(v -> handleCancel());

        btnVerifyPhone.setOnClickListener(v -> {
            String phone = edtPhoneInput.getText().toString().trim();
            if (phone.length() < 10) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại hợp lệ", Toast.LENGTH_SHORT).show();
            } else {
                verifiedPhone = phone;
                showOtpDialog();
            }
        });

        edtDob.setOnClickListener(v -> showDatePicker());

        btnFinish.setOnClickListener(v -> {
            if (validateForm()) {
                saveUserData(); // Lưu thông tin tài khoản theo SĐT đã xác thực
                Toast.makeText(this, "Đăng ký thành công cho SĐT: " + verifiedPhone, Toast.LENGTH_LONG).show();
                
                // Quay về màn hình đăng nhập
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("registeredPhone", verifiedPhone);
                startActivity(intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(v -> handleCancel());
    }

    private void initViews() {
        layoutStepPhone = findViewById(R.id.layoutStepPhone);
        layoutStepForm = findViewById(R.id.layoutStepForm);
        edtPhoneInput = findViewById(R.id.edtPhoneInput);
        btnVerifyPhone = findViewById(R.id.btnVerifyPhone);

        edtFullName = findViewById(R.id.edtFullName);
        edtDob = findViewById(R.id.edtDob);
        tvFixedPhone = findViewById(R.id.tvFixedPhone);
        tvFileName = findViewById(R.id.tvFileName);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);

        tvErrorFullName = findViewById(R.id.tvErrorFullName);
        tvErrorDob = findViewById(R.id.tvErrorDob);
        tvErrorFile = findViewById(R.id.tvErrorFile);

        btnFinish = findViewById(R.id.btnFinish);
        btnCancel = findViewById(R.id.btnCancel);
        btnSelectFile = findViewById(R.id.btnSelectFile);
    }

    private void saveUserData() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        
        // Lưu thông tin theo Key chứa SĐT để không bị ghi đè bởi người dùng khác
        String nameKey = "name_" + verifiedPhone;
        String dobKey = "dob_" + verifiedPhone;
        
        editor.putString(nameKey, edtFullName.getText().toString().trim());
        editor.putString(dobKey, edtDob.getText().toString().trim());
        
        // Cập nhật SĐT cuối cùng vừa đăng ký thành công
        editor.putString("lastRegisteredPhone", verifiedPhone);
        editor.apply();
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

        setupOtpEntry(otp1, otp2);
        setupOtpEntry(otp2, otp3);
        setupOtpEntry(otp3, otp4);
        setupOtpEntry(otp4, otp5);
        setupOtpEntry(otp5, otp6);

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
            
            if (code.equals("123456")) {
                dialog.dismiss();
                moveToForm();
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

    private void moveToForm() {
        layoutStepPhone.setVisibility(View.GONE);
        layoutStepForm.setVisibility(View.VISIBLE);
        tvHeaderTitle.setText("Đăng ký tài khoản khách hàng");
        // Hiển thị SĐT đã xác thực lên Form và không cho sửa
        tvFixedPhone.setText(verifiedPhone);
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, y, m, d) -> {
            edtDob.setText(String.format("%02d/%02d/%d", d, m + 1, y));
            tvErrorDob.setVisibility(View.GONE);
            edtDob.setBackgroundResource(R.drawable.bg_input_white);
        }, year, month, day);
        
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DAY_OF_MONTH, -1);
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        
        datePickerDialog.show();
    }

    private boolean validateForm() {
        boolean isValid = true;
        if (edtFullName.getText().toString().trim().isEmpty()) {
            edtFullName.setBackgroundResource(R.drawable.bg_input_error);
            tvErrorFullName.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            edtFullName.setBackgroundResource(R.drawable.bg_input_white);
            tvErrorFullName.setVisibility(View.GONE);
        }

        if (edtDob.getText().toString().trim().isEmpty()) {
            edtDob.setBackgroundResource(R.drawable.bg_input_error);
            tvErrorDob.setVisibility(View.VISIBLE);
            isValid = false;
        }
        return isValid;
    }

    private void handleCancel() {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn muốn hủy đăng ký?")
                .setPositiveButton("Đồng ý", (dialog, which) -> finish())
                .setNegativeButton("Không", null)
                .show();
    }
}
