package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom7vexeapp.adapters.VehicleManagedAdapter;
import com.example.nhom7vexeapp.models.VehicleManaged;
import com.example.nhom7vexeapp.viewmodels.VehicleViewModel;

import java.util.ArrayList;
import java.util.List;

public class QLPhuongTienActivity extends AppCompatActivity {

    private RecyclerView rvVehicles;
    private VehicleManagedAdapter adapter;
    private List<VehicleManaged> vehicleList = new ArrayList<>();
    private ProgressBar progressBar;
    private VehicleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_phuong_tien);

        viewModel = new ViewModelProvider(this).get(VehicleViewModel.class);

        initViews();
        setupRecyclerView();
        setupObservers();

        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String nhaXeId = pref.getString("op_uid", ""); 

        if (nhaXeId.isEmpty() || nhaXeId.length() < 7) {
            nhaXeId = "NX00001"; 
        }

        viewModel.fetchVehicles(nhaXeId);
    }

    private void initViews() {
        rvVehicles = findViewById(R.id.rvVehicles);
        progressBar = findViewById(R.id.progressBarVehicles);
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        if (rvVehicles != null) {
            rvVehicles.setLayoutManager(new LinearLayoutManager(this));
            // ĐỒNG BỘ LẠI LISTENER: Sử dụng onShowDetail khớp với Adapter
            adapter = new VehicleManagedAdapter(vehicleList, this, new VehicleManagedAdapter.OnVehicleActionListener() {
                @Override
                public void onDelete(VehicleManaged vehicle, int position) {
                    viewModel.deleteVehicle(vehicle.getXeID());
                }

                @Override
                public void onShowDetail(VehicleManaged vehicle) {
                    // KÍCH HOẠT MỞ MÀN HÌNH CHI TIẾT
                    Intent intent = new Intent(QLPhuongTienActivity.this, VehicleDetailActivity.class);
                    intent.putExtra("vehicle_managed_data", vehicle);
                    startActivity(intent);
                }
            });
            rvVehicles.setAdapter(adapter);
        }
    }

    private void setupObservers() {
        viewModel.vehicleList.observe(this, newList -> {
            if (newList != null) {
                vehicleList.clear();
                vehicleList.addAll(newList);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.isLoading.observe(this, isLoading -> {
            if (progressBar != null) progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.errorMessage.observe(this, msg -> {
            if (msg != null) Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        });

        viewModel.isActionSuccess.observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Đã cập nhật Database thành công!", Toast.LENGTH_SHORT).show();
                recreate();
            }
        });
    }
}
