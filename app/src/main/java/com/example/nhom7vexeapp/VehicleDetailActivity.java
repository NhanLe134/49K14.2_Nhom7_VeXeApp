package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom7vexeapp.models.VehicleManaged;
import com.google.android.material.button.MaterialButton;

public class VehicleDetailActivity extends AppCompatActivity {

    private TextView tvPlate, tvType, tvSeats, tvStatus;
    private ImageView btnBack;
    private MaterialButton btnEdit;
    private VehicleManaged currentVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        initViews();

        // Nhận dữ liệu thực tế từ QLPhuongTienActivity
        if (getIntent() != null && getIntent().hasExtra("vehicle_managed_data")) {
            currentVehicle = (VehicleManaged) getIntent().getSerializableExtra("vehicle_managed_data");
            updateUI();
        }

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                if (currentVehicle != null) {
                    // SỬA LỖI: Kích hoạt chuyển sang màn hình Chỉnh sửa thực tế
                    Intent intent = new Intent(VehicleDetailActivity.this, EditVehicleActivity.class);
                    intent.putExtra("vehicle_managed_data", currentVehicle);
                    startActivityForResult(intent, 500);
                } else {
                    Toast.makeText(this, "Lỗi: Không tìm thấy dữ liệu xe!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateUI() {
        if (currentVehicle != null) {
            if (tvPlate != null) tvPlate.setText(currentVehicle.getBienSoXe());
            if (tvType != null) tvType.setText("Loại: " + currentVehicle.getLoaiXeIDStr());
            if (tvSeats != null) tvSeats.setText(String.valueOf(currentVehicle.getSoGhe() != null ? currentVehicle.getSoGhe() : "N/A"));
            if (tvStatus != null) tvStatus.setText(currentVehicle.getTrangThai());
        }
    }

    private void initViews() {
        tvPlate = findViewById(R.id.tvDetailPlate);
        tvType = findViewById(R.id.tvDetailType);
        tvSeats = findViewById(R.id.tvDetailSeats);
        tvStatus = findViewById(R.id.tvDetailStatus);
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEditVehicle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500 && resultCode == RESULT_OK) {
            // Sau khi chỉnh sửa xong, quay lại thì đóng màn hình chi tiết để thấy sự thay đổi ở danh sách
            finish();
        }
    }
}
