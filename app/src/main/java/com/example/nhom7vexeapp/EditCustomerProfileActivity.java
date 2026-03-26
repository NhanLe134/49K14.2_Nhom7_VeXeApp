package com.example.nhom7vexeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;

public class EditCustomerProfileActivity extends AppCompatActivity {

    private EditText edtName, edtDob;
    private TextView tvPhone, tvErrorName, tvErrorDob;
    private MaterialButton btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_profile);

        initViews();
        loadCurrentData();

        findViewById(R.id.btnBack).setOnClickListener(v -> showCancelConfirmation());
        btnCancel.setOnClickListener(v -> showCancelConfirmation());

        edtDob.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> {
            if (validateForm()) {
                saveUpdatedData();
                showSuccessPopup();
            }
        });
    }

    private void initViews() {
        edtName = findViewById(R.id.edtEditFullName);
        edtDob = findViewById(R.id.edtEditDob);
        tvPhone = findViewById(R.id.tvEditPhone);
        tvErrorName = findViewById(R.id.tvErrorEditName);
        tvErrorDob = findViewById(R.id.tvErrorEditDob);
        btnSave = findViewById(R.id.btnSaveEdit);
        btnCancel = findViewById(R.id.btnCancelEdit);
    }

    private void loadCurrentData() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        edtName.setText(pref.getString("customerName", "Nguyễn Văn An"));
        tvPhone.setText(pref.getString("customerPhone", "0916441979"));
        edtDob.setText(pref.getString("customerDob", "20/11/2004"));
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
        if (edtName.getText().toString().trim().isEmpty()) {
            edtName.setBackgroundResource(R.drawable.bg_input_error);
            tvErrorName.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            edtName.setBackgroundResource(R.drawable.bg_input_white);
            tvErrorName.setVisibility(View.GONE);
        }

        if (edtDob.getText().toString().trim().isEmpty()) {
            edtDob.setBackgroundResource(R.drawable.bg_input_error);
            tvErrorDob.setVisibility(View.VISIBLE);
            isValid = false;
        }
        return isValid;
    }

    private void saveUpdatedData() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("customerName", edtName.getText().toString().trim());
        editor.putString("customerDob", edtDob.getText().toString().trim());
        editor.apply();
    }

    private void showSuccessPopup() {
        new AlertDialog.Builder(this)
                .setTitle("Thành công")
                .setMessage("Cập nhật thông tin khách hàng thành công")
                .setPositiveButton("Đóng", (dialog, which) -> {
                    setResult(RESULT_OK);
                    finish();
                })
                .show();
    }

    private void showCancelConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có chắc muốn hủy thao tác này?")
                .setPositiveButton("Đồng ý", (dialog, which) -> finish())
                .setNegativeButton("Không", null)
                .show();
    }
}
