package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhom7vexeapp.adapters.TripAdapter;
import com.example.nhom7vexeapp.models.Trip;
import java.util.ArrayList;
import java.util.List;

public class TripListActivity extends AppCompatActivity implements TripAdapter.OnTripActionListener {
    private RecyclerView rvTrips;
    private TripAdapter adapter;
    public static List<Trip> tripList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        if (tripList.isEmpty()) {
            tripList.add(new Trip("D001", "Huế-Đà Nẵng", "05-02-2025", "6h00", "xe 4 chỗ", 4, "150K", "Chưa hoàn thành"));
            tripList.add(new Trip("D002", "Đà Nẵng-Huế", "05-02-2025", "8h00", "xe 7 chỗ", 7, "180K", "Chưa hoàn thành"));
            tripList.add(new Trip("D003", "Huế-Đà Nẵng", "06-02-2025", "14h00", "xe limousine", 9, "200K", "Chưa hoàn thành"));
        }

        rvTrips = findViewById(R.id.rvTrips);
        rvTrips.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TripAdapter(tripList, this);
        rvTrips.setAdapter(adapter);

        Button btnCreateTrip = findViewById(R.id.btnCreateTrip);
        btnCreateTrip.setOnClickListener(v -> {
            Intent intent = new Intent(TripListActivity.this, CreateTripActivity.class);
            startActivityForResult(intent, 100);
        });

        findViewById(R.id.nav_home).setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onEdit(Trip trip, int position) {
        Intent intent = new Intent(this, CreateTripActivity.class);
        intent.putExtra("editTrip", trip);
        intent.putExtra("position", position);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onClick(Trip trip, int position) {
        Intent intent = new Intent(this, TripDetailActivity.class);
        intent.putExtra("trip", trip);
        intent.putExtra("position", position);
        startActivityForResult(intent, 102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 100) {
                Trip resultTrip = (Trip) data.getSerializableExtra("resultTrip");
                if (resultTrip != null) {
                    tripList.add(0, resultTrip);
                    adapter.notifyItemInserted(0);
                    rvTrips.scrollToPosition(0);
                }
            } else if (requestCode == 101 || requestCode == 102) {
                Trip resultTrip = (Trip) data.getSerializableExtra("resultTrip");
                if (resultTrip == null) resultTrip = (Trip) data.getSerializableExtra("updatedTrip");
                
                int position = data.getIntExtra("position", -1);
                if (resultTrip != null && position != -1) {
                    tripList.set(position, resultTrip);
                    adapter.notifyItemChanged(position);
                }
            }
        }
    }
}
