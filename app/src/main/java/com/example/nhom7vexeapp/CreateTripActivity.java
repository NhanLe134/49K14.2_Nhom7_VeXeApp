package com.example.nhom7vexeapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom7vexeapp.models.Trip;

import java.util.Calendar;

public class CreateTripActivity extends AppCompatActivity {
    private Spinner spRoute, spTime, spVehicle;
    private EditText etDate;
    private TextView tvSeats, tvPrice, tvFormTitle;
    private LinearLayout layoutInfo;
    private Button btnSave, btnCancel;
    
    private String selectedRoute, selectedTime, selectedVehicle;
    private int selectedSeats;
    private String selectedPrice;

    private Trip editTrip;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        initViews();
        setupSpinners();
        setupDatePicker();
        setupListeners();

        editTrip = (Trip) getIntent().getSerializableExtra("editTrip");
        position = getIntent().getIntExtra("position", -1);

        if (editTrip != null) {
            populateFields();
        }
    }

    private void initViews() {
        spRoute = findViewById(R.id.spRoute);
        spTime = findViewById(R.id.spTime);
        spVehicle = findViewById(R.id.spVehicle);
        etDate = findViewById(R.id.etDate);
        tvSeats = findViewById(R.id.tvSeats);
        tvPrice = findViewById(R.id.tvPrice);
        tvFormTitle = findViewById(R.id.tvFormTitle); // Added to layout if possible, or just use a generic one
        layoutInfo = findViewById(R.id.layoutInfo);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupSpinners() {
        // Route Spinner
        String[] routes = {"Chọn nơi xuất phát", "Huế-Đà Nẵng", "Đà Nẵng-Huế"};
        ArrayAdapter<String> routeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, routes);
        routeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRoute.setAdapter(routeAdapter);

        // Time Spinner (4h-22h)
        String[] times = new String[20];
        times[0] = "Chọn giờ xuất phát";
        for (int i = 4; i <= 22; i++) {
            times[i - 3] = i + "h00";
        }
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTime.setAdapter(timeAdapter);

        // Vehicle Spinner
        String[] vehicles = {"Chọn loại xe", "xe 4 chỗ", "xe 7 chỗ", "xe limousine"};
        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vehicles);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVehicle.setAdapter(vehicleAdapter);
    }

    private void populateFields() {
        if (tvFormTitle != null) tvFormTitle.setText("FORM CHỈNH SỬA THÔNG TIN CHUYẾN XE");
        
        // Populate Route
        setSpinnerSelection(spRoute, editTrip.getRouteName());
        
        // Populate Date
        etDate.setText(editTrip.getDate());
        
        // Populate Time
        setSpinnerSelection(spTime, editTrip.getTime());
        
        // Populate Vehicle
        setSpinnerSelection(spVehicle, editTrip.getVehicleType());
        
        // The selection listeners will handle selectedSeats and selectedPrice
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void setupDatePicker() {
        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
                String date = String.format("%02d/%02d/%d", dayOfMonth, month1 + 1, year1);
                etDate.setText(date);
            }, year, month, day);

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
    }

    private void setupListeners() {
        spRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoute = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVehicle = parent.getItemAtPosition(position).toString();
                if (position == 0) {
                    layoutInfo.setVisibility(View.GONE);
                } else {
                    layoutInfo.setVisibility(View.VISIBLE);
                    if (position == 1) { // 4 chỗ
                        selectedSeats = 4;
                        selectedPrice = "150K";
                    } else if (position == 2) { // 7 chỗ
                        selectedSeats = 7;
                        selectedPrice = "180K";
                    } else { // limousine
                        selectedSeats = 9;
                        selectedPrice = "200K";
                    }
                    tvSeats.setText("Số ghế: " + selectedSeats);
                    tvPrice.setText("Giá vé: " + selectedPrice);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnSave.setOnClickListener(v -> validateAndSave());
        
        btnCancel.setOnClickListener(v -> finish());
    }

    private void validateAndSave() {
        String date = etDate.getText().toString();

        if (selectedRoute.equals("Chọn nơi xuất phát") || date.isEmpty() || 
            selectedTime.equals("Chọn giờ xuất phát") || selectedVehicle.equals("Chọn loại xe")) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        showSuccessDialog();
    }

    private void showSuccessDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView tvMsg = dialog.findViewById(R.id.tvMessage);
        String successMsg = (editTrip == null) ? "Tạo chuyến xe thành công" : "Cập nhật chuyến xe thành công";
        tvMsg.setText(successMsg);

        dialog.show();

        // Delay and finish
        new android.os.Handler().postDelayed(() -> {
            dialog.dismiss();
            
            Trip resultTrip = new Trip(
                editTrip != null ? editTrip.getId() : "D" + (int)(Math.random() * 900 + 100),
                selectedRoute, etDate.getText().toString(), selectedTime, 
                selectedVehicle, selectedSeats, selectedPrice, 
                editTrip != null ? editTrip.getStatus() : "Chưa hoàn thành"
            );

            Intent intent = new Intent();
            intent.putExtra("resultTrip", resultTrip);
            intent.putExtra("updatedTrip", resultTrip);
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            finish();
        }, 1500);
    }
}
