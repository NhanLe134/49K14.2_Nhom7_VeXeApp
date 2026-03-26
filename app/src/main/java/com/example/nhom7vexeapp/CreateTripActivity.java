package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nhom7vexeapp.models.Trip;

public class CreateTripActivity extends AppCompatActivity {
    private EditText etRoute, etDate, etTime, etVehicle, etPrice;
    private Button btnSave;
    private Trip editTrip;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        etRoute = findViewById(R.id.etRoute);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etVehicle = findViewById(R.id.etVehicle);
        etPrice = findViewById(R.id.etPrice);
        btnSave = findViewById(R.id.btnSave);

        editTrip = (Trip) getIntent().getSerializableExtra("editTrip");
        position = getIntent().getIntExtra("position", -1);

        if (editTrip != null) {
            etRoute.setText(editTrip.getRouteName());
            etDate.setText(editTrip.getDate());
            etTime.setText(editTrip.getTime());
            etVehicle.setText(editTrip.getVehicleType());
            etPrice.setText(editTrip.getPrice());
            btnSave.setText("Cập nhật");
        }

        btnSave.setOnClickListener(v -> {
            String route = etRoute.getText().toString();
            String date = etDate.getText().toString();
            String time = etTime.getText().toString();
            String vehicle = etVehicle.getText().toString();
            String price = etPrice.getText().toString();

            Trip resultTrip = new Trip(
                editTrip != null ? editTrip.getId() : "D" + System.currentTimeMillis() % 1000,
                route, date, time, vehicle, 4, price, "Chưa hoàn thành"
            );

            Intent intent = new Intent();
            intent.putExtra("resultTrip", resultTrip);
            intent.putExtra("updatedTrip", resultTrip);
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
