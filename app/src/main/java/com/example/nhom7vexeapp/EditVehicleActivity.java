package com.example.nhom7vexeapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.nhom7vexeapp.models.VehicleManaged;
import com.example.nhom7vexeapp.viewmodels.VehicleViewModel;
import com.google.android.material.button.MaterialButton;

public class EditVehicleActivity extends AppCompatActivity {

    private TextView tvPlate, tvType, tvSeats, tvStatusDisplay;
    private LinearLayout layoutStatus;
    private MaterialButton btnSave, btnCancel;
    private VehicleManaged currentVehicle;
    private VehicleViewModel viewModel;
    private String selectedStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        viewModel = new ViewModelProvider(this).get(VehicleViewModel.class);

        initViews();
        loadData();
        setupEvents();
        setupObservers();
    }

    private void initViews() {
        tvPlate = findViewById(R.id.tvEditPlate);
        tvType = findViewById(R.id.tvEditType);
        tvSeats = findViewById(R.id.tvEditSeats);
        tvStatusDisplay = findViewById(R.id.tvStatusDisplay);
        layoutStatus = findViewById(R.id.layoutStatus);
        btnSave = findViewById(R.id.btnSaveEdit);
        btnCancel = findViewById(R.id.btnCancelEdit);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void loadData() {
        if (getIntent() != null && getIntent().hasExtra("vehicle_managed_data")) {
            currentVehicle = (VehicleManaged) getIntent().getSerializableExtra("vehicle_managed_data");
            if (currentVehicle != null) {
                tvPlate.setText(currentVehicle.getBienSoXe());
                tvType.setText("Loại: " + currentVehicle.getLoaiXeIDStr());
                tvSeats.setText(String.valueOf(currentVehicle.getSoGhe() != null ? currentVehicle.getSoGhe() : "N/A"));
                selectedStatus = currentVehicle.getTrangThai();
                tvStatusDisplay.setText(selectedStatus);
            }
        }
    }

    private void setupEvents() {
        layoutStatus.setOnClickListener(v -> showCustomStatusDialog());

        btnCancel.setOnClickListener(v -> showConfirmDialog("Bạn có thông tin chỉnh sửa chưa lưu,\nxác nhận hủy?", this::finish));

        btnSave.setOnClickListener(v -> {
            showConfirmDialog("Bạn có thông tin chỉnh sửa chưa lưu,\nxác nhận lưu?", () -> {
                if (currentVehicle != null) {
                    currentVehicle.setTrangThai(selectedStatus);
                    viewModel.updateVehicle(currentVehicle.getXeID(), currentVehicle);
                }
            });
        });
    }

    private void setupObservers() {
        viewModel.isActionSuccess.observe(this, success -> {
            if (success) {
                showSuccessPopup();
            }
        });
    }

    private void showConfirmDialog(String message, Runnable onConfirm) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_cancel);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView tvMsg = dialog.findViewById(R.id.tvDialogMessage);
        tvMsg.setText(message);

        dialog.findViewById(R.id.btnNo).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.btnYes).setOnClickListener(v -> {
            dialog.dismiss();
            onConfirm.run();
        });

        dialog.show();
    }

    private void showSuccessPopup() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView tvMsg = dialog.findViewById(R.id.tvMessage);
        tvMsg.setText("Cập nhật thông tin Phương tiện thành công");

        dialog.show();

        new Handler().postDelayed(() -> {
            dialog.dismiss();
            setResult(RESULT_OK);
            finish();
        }, 1500);
    }

    private void showCustomStatusDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_status_dropdown);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.findViewById(R.id.status_active).setOnClickListener(v -> {
            selectedStatus = "Đang hoạt động";
            tvStatusDisplay.setText(selectedStatus);
            dialog.dismiss();
        });

        dialog.findViewById(R.id.status_maintenance).setOnClickListener(v -> {
            selectedStatus = "Bảo trì";
            tvStatusDisplay.setText(selectedStatus);
            dialog.dismiss();
        });

        dialog.findViewById(R.id.status_stopped).setOnClickListener(v -> {
            selectedStatus = "Dừng hoạt động";
            tvStatusDisplay.setText(selectedStatus);
            dialog.dismiss();
        });

        dialog.show();
    }
}
