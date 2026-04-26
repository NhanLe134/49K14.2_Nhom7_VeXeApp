package com.example.nhom7vexeapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom7vexeapp.adapters.PassengerAdapter;
import com.example.nhom7vexeapp.api.ApiClient;
import com.example.nhom7vexeapp.api.ApiService;
import com.example.nhom7vexeapp.models.Driver;
import com.example.nhom7vexeapp.models.Passenger;
import com.example.nhom7vexeapp.models.Trip;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailActivity extends AppCompatActivity {

    private TextView tvRouteName, tvTime, tvTotalSeats, tvAvailableSeats, tvStatus;
    private MaterialButton btnAssign;
    private RecyclerView rvPassengers;
    private Trip trip;
    private int position;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        apiService = ApiClient.getClient().create(ApiService.class);

        initViews();
        loadInitialData();
        setupBottomNavigation();
    }

    private void initViews() {
        tvRouteName = findViewById(R.id.tvRouteName);
        tvTime = findViewById(R.id.tvTime);
        tvTotalSeats = findViewById(R.id.tvTotalSeats);
        tvAvailableSeats = findViewById(R.id.tvAvailableSeats);
        tvStatus = findViewById(R.id.tvStatus);
        btnAssign = findViewById(R.id.btnAssign);
        rvPassengers = findViewById(R.id.rvPassengers);
    }

    private void loadInitialData() {
        trip = (Trip) getIntent().getSerializableExtra("trip");
        position = getIntent().getIntExtra("position", -1);

        if (trip != null) {
            updateUI();
            // Lấy thêm thông tin chi tiết từ API nếu cần (ví dụ hành khách thực tế)
            fetchTripDetailsFromServer(trip.getId());
        }
    }

    private void updateUI() {
        tvRouteName.setText("Tuyến xe: " + trip.getRouteName());
        tvTime.setText("Giờ xuất phát: " + trip.getTime());
        tvTotalSeats.setText("Tổng số ghế: " + trip.getSeats());
        
        int occupied = (trip.getPassengers() != null) ? trip.getPassengers().size() : 0;
        tvAvailableSeats.setText("Ghế trống: " + (trip.getSeats() - occupied));
        tvStatus.setText(trip.getStatus());

        if (trip.getAssignedDriver() != null) {
            btnAssign.setText("Hiển thị lộ trình chuyến");
        } else {
            btnAssign.setText("Phân công tài xế");
        }

        btnAssign.setOnClickListener(v -> {
            if (trip.getAssignedDriver() == null) {
                Intent intent = new Intent(this, DriverSelectionActivity.class);
                startActivityForResult(intent, 500);
            } else {
                Toast.makeText(this, "Tính năng xem lộ trình đang được cập nhật", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchTripDetailsFromServer(String tripId) {
        // Giả sử Backend có API lấy danh sách hành khách đã đặt vé cho chuyến này
        // Ở đây chúng ta sẽ lấy danh sách vé từ bảng Ve lọc theo ChuyenXeID
        // Tạm thời để rỗng danh sách nếu chưa có API hành khách riêng biệt
        setupPassengerList(new ArrayList<>());
    }

    private void setupPassengerList(List<Passenger> passengers) {
        if (passengers == null) passengers = new ArrayList<>();
        trip.setPassengers(passengers);
        
        rvPassengers.setLayoutManager(new LinearLayoutManager(this));
        rvPassengers.setAdapter(new PassengerAdapter(passengers));
        
        // Cập nhật lại số ghế trống sau khi có danh sách hành khách thật
        tvAvailableSeats.setText("Ghế trống: " + (trip.getSeats() - passengers.size()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500 && resultCode == RESULT_OK && data != null) {
            Driver driver = (Driver) data.getSerializableExtra("selectedDriver");
            if (driver != null) {
                showSuccessDialog(driver);
            }
        }
    }

    private void showSuccessDialog(Driver driver) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView tvMsg = dialog.findViewById(R.id.tvMessage);
        if (tvMsg != null) tvMsg.setText("Phân công tài xế thành công");

        dialog.show();

        new android.os.Handler().postDelayed(() -> {
            dialog.dismiss();
            trip.setAssignedDriver(driver);
            btnAssign.setText("Hiển thị lộ trình chuyến");
            
            Intent intent = new Intent();
            intent.putExtra("updatedTrip", trip);
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
        }, 1500);
    }

    private void setupBottomNavigation() {
        View navHome = findViewById(R.id.nav_home_op_main);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, OperatorMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }
    }
}
