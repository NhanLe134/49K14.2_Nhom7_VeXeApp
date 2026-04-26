package com.example.nhom7vexeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom7vexeapp.api.ApiClient;
import com.example.nhom7vexeapp.api.ApiService;
import com.example.nhom7vexeapp.utils.IdGenerator;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRegisterActivity extends AppCompatActivity {

    private LinearLayout layoutStepPhone;
    private ScrollView layoutStepForm;
    private EditText edtPhoneInput, edtFullName, edtDob, edtEmail;
    private TextView tvFixedPhone, tvFileName, tvHeaderTitle;
    private String verifiedPhone = "";
    private Uri selectedImageUri = null;
    private String dateForApi = ""; 

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                selectedImageUri = uri;
                if (tvFileName != null) tvFileName.setText("Đã chọn ảnh đại diện");
            }
        });

        initViews();
        setupEvents();
    }

    private void initViews() {
        layoutStepPhone = findViewById(R.id.layoutStepPhone);
        layoutStepForm = findViewById(R.id.layoutStepForm);
        edtPhoneInput = findViewById(R.id.edtPhoneInput);
        edtFullName = findViewById(R.id.edtFullName);
        edtDob = findViewById(R.id.edtDob);
        edtEmail = findViewById(R.id.edtEmailInput);
        tvFixedPhone = findViewById(R.id.tvFixedPhone);
        tvFileName = findViewById(R.id.tvFileName);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
    }

    private void setupEvents() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnVerifyPhone).setOnClickListener(v -> {
            String phone = edtPhoneInput.getText().toString().trim();
            if (phone.length() >= 10) {
                verifiedPhone = phone;
                showOtpDialog();
            } else {
                Toast.makeText(this, "SĐT không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        if (edtDob != null) edtDob.setOnClickListener(v -> showDatePicker());

        findViewById(R.id.btnSelectFile).setOnClickListener(v -> pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build()));

        findViewById(R.id.btnFinish).setOnClickListener(v -> {
            if (validateForm()) {
                saveToDatabaseAndFinish();
            }
        });

        View btnCancel = findViewById(R.id.btnCancel);
        if (btnCancel != null) btnCancel.setOnClickListener(v -> finish());
    }

    private void saveToDatabaseAndFinish() {
        String newId = IdGenerator.generateKhachHangID(this);
        
        RequestBody idBody = RequestBody.create(MediaType.parse("text/plain"), newId);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), edtFullName.getText().toString().trim());
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), edtEmail.getText().toString().trim());
        RequestBody dobBody = RequestBody.create(MediaType.parse("text/plain"), dateForApi);

        MultipartBody.Part imagePart = null;
        if (selectedImageUri != null) {
            File file = uriToFile(selectedImageUri);
            if (file != null) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                imagePart = MultipartBody.Part.createFormData("anhDaiDienURL", file.getName(), requestFile);
            }
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.createKhachHangProfile(idBody, nameBody, emailBody, dobBody, imagePart).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CustomerRegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(CustomerRegisterActivity.this, "Lỗi Server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CustomerRegisterActivity.this, "Lỗi kết nối Render", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File uriToFile(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getCacheDir(), "temp_avatar.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            return file;
        } catch (Exception e) { return null; }
    }

    private void showOtpDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_otp, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        dialog.show();
        Button btnVerifyOtp = view.findViewById(R.id.btnVerifyOtp);
        btnVerifyOtp.setOnClickListener(v -> {
            dialog.dismiss();
            moveToForm();
        });
    }

    private void moveToForm() {
        layoutStepPhone.setVisibility(View.GONE);
        layoutStepForm.setVisibility(View.VISIBLE);
        if (tvHeaderTitle != null) tvHeaderTitle.setText("Đăng ký tài khoản khách hàng");
        tvFixedPhone.setText(verifiedPhone);
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) -> {
            edtDob.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", d, m + 1, y));
            dateForApi = String.format(Locale.getDefault(), "%d-%02d-%02d", y, m + 1, d);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private boolean validateForm() {
        return !edtFullName.getText().toString().isEmpty() && !dateForApi.isEmpty() && !edtEmail.getText().toString().isEmpty();
    }
}
