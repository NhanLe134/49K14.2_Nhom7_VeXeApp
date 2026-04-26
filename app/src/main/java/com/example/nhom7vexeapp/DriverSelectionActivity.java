package com.example.nhom7vexeapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom7vexeapp.adapters.DriverAdapter;
import com.example.nhom7vexeapp.api.ApiClient;
import com.example.nhom7vexeapp.api.ApiService;
import com.example.nhom7vexeapp.models.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverSelectionActivity extends AppCompatActivity implements DriverAdapter.OnDriverListener {

    private static final String TAG = "DriverSelect";
    private RecyclerView rvDrivers;
    private DriverAdapter adapter;
    private List<Driver> driverList = new ArrayList<>();
    private String opUid, tripId;
    private ApiService apiService;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_selection);

        tripId = getIntent().getStringExtra("tripId");
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        opUid = pref.getString("op_uid", "NX00001");

        apiService = ApiClient.getClient().create(ApiService.class);
        initViews();

        btnBack.setOnClickListener(v -> finish());
        loadDriversWithDetails();
    }

    private void initViews() {
        rvDrivers = findViewById(R.id.rvDrivers);
        btnBack = findViewById(R.id.btnBack);
        rvDrivers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DriverAdapter(driverList, this);
        rvDrivers.setAdapter(adapter);
    }

    private void loadDriversWithDetails() {
        apiService.getChiTietTaiXe().enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> resCT) {
                Map<String, String> nameMap = new HashMap<>();
                if (resCT.isSuccessful() && resCT.body() != null) {
                    for (Map<String, Object> ct : resCT.body()) {
                        String id = findVal(ct, "Taixe", "TaiXeID");
                        String name = findVal(ct, "HoTen", "hoten");
                        if (!id.isEmpty()) nameMap.put(id, name);
                    }
                }

                apiService.getDriversRaw().enqueue(new Callback<List<Map<String, Object>>>() {
                    @Override
                    public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> resTX) {
                        if (resTX.isSuccessful() && resTX.body() != null) {
                            driverList.clear();
                            for (Map<String, Object> tx : resTX.body()) {
                                String nxe = findVal(tx, "Nhaxe", "nhaxe");
                                if (nxe.isEmpty() || nxe.equalsIgnoreCase(opUid)) {
                                    String id = findVal(tx, "TaiXeID", "id");
                                    String phone = findVal(tx, "SoDienThoai", "phone");
                                    String name = nameMap.getOrDefault(id, "Tài xế mới");
                                    if (!id.isEmpty()) {
                                        driverList.add(new Driver(id, name, phone, "", "1 Tô Hữu, Huế", "1 Hùng Vương, Đà Nẵng"));
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {}
                });
            }
            @Override public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {}
        });
    }

    private String findVal(Map<String, Object> map, String... keys) {
        if (map == null) return "";
        for (String k : keys) {
            for (Map.Entry<String, Object> e : map.entrySet()) {
                if (e.getKey().equalsIgnoreCase(k) && e.getValue() != null) return e.getValue().toString();
            }
        }
        return "";
    }

    @Override
    public void onDriverClick(Driver driver) {
        showConfirmDialog(driver);
    }

    @Override public void onDriverDelete(Driver driver) {}

    private void showConfirmDialog(Driver driver) {
        View view = getLayoutInflater().inflate(R.layout.dialog_confirm_custom, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvMsg = view.findViewById(R.id.tvDialogMessage);
        tvMsg.setText("Bạn có muốn phân công tài xế " + driver.getName() + "?");

        view.findViewById(R.id.btnCancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            dialog.dismiss();
            handleAssignment(driver);
        });
        dialog.show();
    }

    private void handleAssignment(Driver driver) {
        if (tripId == null || tripId.isEmpty()) return;

        Map<String, Object> data = new HashMap<>();
        data.put("ChuyenXeID", tripId);
        data.put("Taixe", driver.getId()); // Key chuẩn cho server: Taixe

        apiService.updateTripRaw(tripId, data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showSuccessDialog();
                } else {
                    Log.e(TAG, "Fail code: " + response.code());
                    Toast.makeText(DriverSelectionActivity.this, "Lỗi cập nhật server!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DriverSelectionActivity.this, "Lỗi mạng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSuccessDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_success_custom, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        new Handler().postDelayed(() -> {
            if (dialog.isShowing()) dialog.dismiss();
            setResult(RESULT_OK);
            finish();
        }, 1500);
    }
}
