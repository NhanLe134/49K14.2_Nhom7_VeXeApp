package com.example.nhom7vexeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom7vexeapp.api.ApiClient;
import com.example.nhom7vexeapp.api.ApiService;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRegisterActivity extends AppCompatActivity {

    private LinearLayout layoutStepPhone;
    private ScrollView layoutStepForm;
    private EditText edtPhoneInput, edtFullName, edtDob;
    private TextView tvFixedPhone, tvFileName, tvHeaderTitle;
    private MaterialButton btnVerifyPhone, btnFinish, btnCancel, btnSelectFile;
    private String verifiedPhone = "";
    private String selectedImageBase64 = ""; // Biến lưu chuỗi ảnh thật
    private boolean isOtpVerified = false;

    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        selectedImageBase64 = encodeImageToBase64(uri);
                        tvFileName.setText("Đã chọn ảnh thành công");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        initViews();

        btnVerifyPhone.setOnClickListener(v -> {
            String phone = edtPhoneInput.getText().toString().trim();
            if (phone.length() >= 10) { verifiedPhone = phone; showOtpDialog(); }
        });

        edtDob.setOnClickListener(v -> showDatePicker());
        btnSelectFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        btnFinish.setOnClickListener(v -> { if (validateForm()) handleRegister(); });
        btnCancel.setOnClickListener(v -> finish());
    }

    private String encodeImageToBase64(Uri imageUri) {
        try {
            android.graphics.Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
            bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 50, outputStream);
            byte[] byteArray = outputStream.toByteArray();
            return "data:image/jpeg;base64," + android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
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
        btnFinish = findViewById(R.id.btnFinish);
        btnCancel = findViewById(R.id.btnCancel);
        btnSelectFile = findViewById(R.id.btnSelectFile);
    }

    private void handleRegister() {
        String randomUserID = "KH" + (10000 + new Random().nextInt(89999));
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Map<String, String> authData = new HashMap<>();
        authData.put("UserID", randomUserID);
        authData.put("TenDangNhap", verifiedPhone);
        authData.put("MatKhau", "123");
        authData.put("SoDienThoai", verifiedPhone);
        authData.put("Vaitro", "KhachHang");

        apiService.registerAuth(authData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Map<String, String> profileData = new HashMap<>();
                    profileData.put("KhachHangID", randomUserID); 
                    profileData.put("TenKhachHang", edtFullName.getText().toString().trim());
                    profileData.put("NgaySinh", edtDob.getText().toString().trim());
                    profileData.put("SoDienThoai", verifiedPhone);
                    profileData.put("Email", verifiedPhone + "@gmail.com");
                    profileData.put("AnhDaiDienURL", selectedImageBase64); // GỬI ẢNH THẬT (BASE64)

                    apiService.createKhachHangProfile(profileData).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> res) {
                            if (res.isSuccessful()) {
                                Toast.makeText(CustomerRegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                try {
                                    String error = res.errorBody() != null ? res.errorBody().string() : "Lỗi Profile";
                                    Toast.makeText(CustomerRegisterActivity.this, "Lỗi bảng Khách hàng: " + error, Toast.LENGTH_LONG).show();
                                } catch (Exception e) { e.printStackTrace(); }
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(CustomerRegisterActivity.this, "Lỗi kết nối Profile!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(CustomerRegisterActivity.this, "Lỗi tạo tài khoản!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CustomerRegisterActivity.this, "Lỗi kết nối Server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showOtpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_otp, null);
        builder.setView(view);
        Button btnVerifyOtp = view.findViewById(R.id.btnVerifyOtp);
        btnVerifyOtp.setVisibility(View.VISIBLE);
        AlertDialog dialog = builder.create();
        dialog.show();
        btnVerifyOtp.setOnClickListener(v -> { isOtpVerified = true; dialog.dismiss(); moveToForm(); });
    }

    private void moveToForm() {
        layoutStepPhone.setVisibility(View.GONE);
        layoutStepForm.setVisibility(View.VISIBLE);
        tvFixedPhone.setText(verifiedPhone);
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) -> edtDob.setText(d + "/" + (m + 1) + "/" + y), 
            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private boolean validateForm() { return !edtFullName.getText().toString().trim().isEmpty(); }
}
