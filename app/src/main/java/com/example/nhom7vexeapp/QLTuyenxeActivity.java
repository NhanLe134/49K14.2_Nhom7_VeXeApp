package com.example.nhom7vexeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhom7vexeapp.adapters.RouteAdapter;
import com.example.nhom7vexeapp.api.ApiClient;
import com.example.nhom7vexeapp.api.ApiService;
import com.example.nhom7vexeapp.models.Route;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLTuyenxeActivity extends AppCompatActivity implements RouteAdapter.OnRouteActionListener {

    private RecyclerView rvRoutes;
    private RouteAdapter adapter;
    private List<Route> routeList = new ArrayList<>();
    private ApiService apiService;
    private String opUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_tuyenxe);

        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        opUid = pref.getString("op_uid", "");
        apiService = ApiClient.getClient().create(ApiService.class);

        initViews();
        setupRecyclerView();
        fetchRoutesFromApi();
        setupNavigation();
    }

    private void initViews() {
        rvRoutes = findViewById(R.id.rvRoutes);
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        adapter = new RouteAdapter(routeList, this);
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));
        rvRoutes.setAdapter(adapter);
    }

    private void fetchRoutesFromApi() {
        if (opUid == null || opUid.isEmpty()) return;
        apiService.getRoutes().enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    routeList.clear();
                    for (Route r : response.body()) {
                        if (opUid.equals(r.getNhaXeId())) routeList.add(r);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override public void onFailure(Call<List<Route>> call, Throwable t) {}
        });
    }

    private void setupNavigation() {
        // TAB TRANG CHỦ
        View navHome = findViewById(R.id.nav_home_op);
        if (navHome == null) navHome = findViewById(R.id.nav_home_op_main);
        if (navHome != null) navHome.setOnClickListener(v -> {
            startActivity(new Intent(this, OperatorMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        });

        // TAB TÀI XẾ
        View navDriver = findViewById(R.id.nav_driver_op);
        if (navDriver != null) navDriver.setOnClickListener(v -> {
            startActivity(new Intent(this, QLNhaxeActivity.class));
            finish();
        });

        // TAB PHƯƠNG TIỆN (SỬA LỖI: Đảm bảo dẫn vào QLPhuongTienActivity)
        View navVehicle = findViewById(R.id.nav_vehicle_op);
        if (navVehicle != null) navVehicle.setOnClickListener(v -> {
            Intent intent = new Intent(QLTuyenxeActivity.this, QLPhuongTienActivity.class);
            startActivity(intent);
            finish(); // Đóng màn hình Tuyến xe để mở Phương tiện
        });

        // TAB CHUYẾN XE
        View navTrip = findViewById(R.id.nav_trip_op);
        if (navTrip != null) navTrip.setOnClickListener(v -> {
            startActivity(new Intent(this, TripListActivity.class));
            finish();
        });
    }

    @Override public void onEdit(Route route) {}
    @Override public void onDelete(Route route) {}
    @Override public void onStatusChange(Route route) {}
}
