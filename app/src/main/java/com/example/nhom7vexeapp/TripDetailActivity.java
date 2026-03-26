package com.example.nhom7vexeapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nhom7vexeapp.models.Trip;

public class TripDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        Trip trip = (Trip) getIntent().getSerializableExtra("trip");
        if (trip != null) {
            ((TextView) findViewById(R.id.tvTripId)).setText(trip.getId());
            ((TextView) findViewById(R.id.tvRouteName)).setText(trip.getRouteName());
            ((TextView) findViewById(R.id.tvTimeDate)).setText(trip.getTime() + " " + trip.getDate());
            ((TextView) findViewById(R.id.tvVehicle)).setText(trip.getVehicleType());
            ((TextView) findViewById(R.id.tvStatus)).setText(trip.getStatus());
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
