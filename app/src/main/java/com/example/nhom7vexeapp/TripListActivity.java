package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhom7vexeapp.adapters.TripAdapter;
import com.example.nhom7vexeapp.api.ApiClient;
import com.example.nhom7vexeapp.api.ApiService;
import com.example.nhom7vexeapp.models.Trip;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripListActivity extends AppCompatActivity implements TripAdapter.OnTripActionListener {
    private RecyclerView rvTrips;
    private TripAdapter adapter;
    private List<Trip> tripList = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        apiService = ApiClient.getClient().create(ApiService.class);
        rvTrips = findViewById(R.id.rvTrips);
        rvTrips.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TripAdapter(tripList, this);
        rvTrips.setAdapter(adapter);

        loadTripsFromApi();
        setupBottomNavigation();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        
        Button btnCreateTrip = findViewById(R.id.btnCreateTrip);
        if (btnCreateTrip != null) {
            btnCreateTrip.setOnClickListener(v -> {
                startActivityForResult(new Intent(this, CreateTripActivity.class), 100);
            });
        }
    }

    private void loadTripsFromApi() {
        apiService.getTrips().enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tripList.clear();
                    tripList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override public void onFailure(Call<List<Trip>> call, Throwable t) {}
        });
    }

    private void setupBottomNavigation() {
        // 1. Tab Trang chủ
        View navHome = findViewById(R.id.nav_home_op);
        if (navHome == null) navHome = findViewById(R.id.nav_home_op_main);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                startActivity(new Intent(this, OperatorMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            });
        }

        // 2. Tab Tài xế
        View navDriver = findViewById(R.id.nav_driver_op);
        if (navDriver != null) {
            navDriver.setOnClickListener(v -> {
                startActivity(new Intent(this, QLNhaxeActivity.class));
                finish();
            });
        }

        // 3. Tab Phương tiện
        View navVehicle = findViewById(R.id.nav_vehicle_op);
        if (navVehicle != null) {
            navVehicle.setOnClickListener(v -> {
                startActivity(new Intent(this, QLPhuongTienActivity.class));
                finish();
            });
        }

        // 4. Tab Tuyến xe
        View navRoute = findViewById(R.id.nav_route_op);
        if (navRoute != null) {
            navRoute.setOnClickListener(v -> {
                startActivity(new Intent(this, QLTuyenxeActivity.class));
                finish();
            });
        }

        // Tab Chuyến xe (Hiện tại)
        View navTrip = findViewById(R.id.nav_trip_op);
        if (navTrip != null) {
            navTrip.setOnClickListener(v -> {
                if (rvTrips != null) rvTrips.smoothScrollToPosition(0);
            });
        }
    }

    @Override public void onEdit(Trip trip, int position) {}
    @Override public void onClick(Trip trip, int position) {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) loadTripsFromApi();
    }
}
