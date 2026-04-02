package com.example.nhom7vexeapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhom7vexeapp.adapters.PassengerAdapter;
import com.example.nhom7vexeapp.models.Driver;
import com.example.nhom7vexeapp.models.Passenger;
import com.example.nhom7vexeapp.models.Trip;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class TripDetailActivity extends AppCompatActivity {

    private TextView tvRouteName, tvTime, tvTotalSeats, tvAvailableSeats, tvStatus;
    private MaterialButton btnAssign;
    private RecyclerView rvPassengers;
    private Trip trip;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        initViews();
        loadData();
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

    private void loadData() {
        trip = (Trip) getIntent().getSerializableExtra("trip");
        position = getIntent().getIntExtra("position", -1);

        if (trip != null) {
            tvRouteName.setText("Tuyến xe: " + trip.getRouteName());
            tvTime.setText("Giờ xuất phát: " + trip.getTime());
            tvTotalSeats.setText("Tổng số ghế: " + trip.getSeats());
            tvAvailableSeats.setText("Ghế trống: " + (trip.getSeats() - (trip.getPassengers() != null ? trip.getPassengers().size() : 0)));
            tvStatus.setText(trip.getStatus());

            // Logic: if driver assigned, change button text
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
                    // Logic for showing route (mocked for now)
                }
            });

            setupPassengerList();
        }
    }

    private void setupPassengerList() {
        List<Passenger> passengers = trip.getPassengers();
        if (passengers == null || passengers.isEmpty()) {
            // Mock passengers for demo
            passengers = new ArrayList<>();
            passengers.add(new Passenger("Phan Thị Quỳnh Trâm", "0123456789", "1 Tố Hữu, TP Huế", "1 Hùng Vương, Đà Nẵng", "A1"));
            passengers.add(new Passenger("Lê Văn Hùng", "0123456789", "25 Bà Triệu, TP Huế", "1 Tố Hữu, Đà Nẵng", "A2"));
            passengers.add(new Passenger("Vương Tuệ Nhi", "0123456789", "1 Nguyễn Huệ, TP Huế", "25 Phan Thanh, Đà Nẵng", "A3"));
            trip.setPassengers(passengers);
        }

        rvPassengers.setLayoutManager(new LinearLayoutManager(this));
        rvPassengers.setAdapter(new PassengerAdapter(passengers));
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
        tvMsg.setText("Phân công tài xế thành công");

        dialog.show();

        new android.os.Handler().postDelayed(() -> {
            dialog.dismiss();
            trip.setAssignedDriver(driver);
            btnAssign.setText("Hiển thị lộ trình chuyến");
            
            // Return updated trip to list
            Intent intent = new Intent();
            intent.putExtra("updatedTrip", trip);
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
        }, 1500);
    }

    private void setupBottomNavigation() {
        findViewById(R.id.nav_home_op_main).setOnClickListener(v -> {
            Intent intent = new Intent(this, OperatorMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        // ... (other nav clicks)
    }
}
