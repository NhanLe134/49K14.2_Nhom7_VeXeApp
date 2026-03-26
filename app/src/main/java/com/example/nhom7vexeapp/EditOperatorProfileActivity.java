package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class EditOperatorProfileActivity extends AppCompatActivity {

    private EditText edtName, edtRep, edtAddress, edtPhone;
    private TextView tvErrorName;
    private MaterialButton btnSave, btnCancel;
    private LinearLayout navHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_operator_profile);

        initViews();
        loadCurrentData();

        findViewById(R.id.btnBack).setOnClickListener(v -> showCancelConfirmation());
        btnCancel.setOnClickListener(v -> showCancelConfirmation());

        // Nút Home ở Bottom Nav
        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, OperatorMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnSave.setOnClickListener(v -> {
            if (validateForm()) {
                saveUpdatedData();
                showSuccessPopup();
            }
        });
    }

    private void initViews() {
        edtName = findViewById(R.id.edtEditOpName);
        edtRep = findViewById(R.id.edtEditOpRep);
        edtAddress = findViewById(R.id.edtEditOpAddress);
        edtPhone = findViewById(R.id.edtEditOpPhone);
        tvErrorName = findViewById(R.id.tvErrorOpName);
        btnSave = findViewById(R.id.btnSaveEditOp);
        btnCancel = findViewById(R.id.btnCancelEditOp);
        navHome = findViewById(R.id.navHomeEditProfile);
    }

    private void loadCurrentData() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        edtName.setText(pref.getString("op_name", "Nhà xe Đà Nẵng-Huế"));
        edtRep.setText(pref.getString("op_rep", "Tôn Thất Huy Phong"));
        edtAddress.setText(pref.getString("op_address", "K36/12 Lưu Quang Thuận, Đà Nẵng"));
        edtPhone.setText(pref.getString("op_phone", "0905509767"));
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

        if (edtRep.getText().toString().trim().isEmpty() || 
            edtAddress.getText().toString().trim().isEmpty() || 
            edtPhone.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    private void saveUpdatedData() {
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("op_name", edtName.getText().toString().trim());
        editor.putString("op_rep", edtRep.getText().toString().trim());
        editor.putString("op_address", edtAddress.getText().toString().trim());
        editor.putString("op_phone", edtPhone.getText().toString().trim());
        editor.apply();
    }

    private void showSuccessPopup() {
        new AlertDialog.Builder(this)
                .setTitle("Thành công")
                .setMessage("Cập nhật thông tin nhà xe thành công")
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
