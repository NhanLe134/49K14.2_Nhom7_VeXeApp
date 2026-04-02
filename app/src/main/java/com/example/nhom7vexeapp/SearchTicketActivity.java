package com.example.nhom7vexeapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SearchTicketActivity extends AppCompatActivity {

    private EditText edtDate, edtTime; // Thêm edtTime
    private Spinner spOrigin, spDestination;
    private Button btnSearchTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ticket);

        initViews();
        setupCityData();

        // 1. TỰ ĐỘNG ĐIỀN NGÀY GIỜ HIỆN TẠI KHI MỞ APP
        setCurrentDateTime();

        // 2. Sự kiện chọn ngày
        edtDate.setOnClickListener(v -> openDatePicker());

        // 3. Sự kiện chọn giờ (Mới thêm)
        edtTime.setOnClickListener(v -> openTimePicker());

        // 4. Nút Tìm kiếm
        btnSearchTicket.setOnClickListener(v -> performSearch());
    }

    private void initViews() {
        edtDate = findViewById(R.id.edtDate);
        edtTime = findViewById(R.id.edtTime); // Ánh xạ ô giờ
        spOrigin = findViewById(R.id.spOrigin);
        spDestination = findViewById(R.id.spDestination);
        btnSearchTicket = findViewById(R.id.btnSearchTicket);
    }

    private void setCurrentDateTime() {
        Calendar c = Calendar.getInstance();

        // Định dạng ngày: 02/04/2026
        String currentDate = String.format(Locale.getDefault(), "%02d/%02d/%d",
                c.get(Calendar.DAY_OF_MONTH), (c.get(Calendar.MONTH) + 1), c.get(Calendar.YEAR));
        edtDate.setText(currentDate);

        // Định dạng giờ: 12:30
        String currentTime = String.format(Locale.getDefault(), "%02d:%02d",
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        edtTime.setText(currentTime);
    }

    private void openDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) -> {
            edtDate.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", d, (m + 1), y));
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void openTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(this, (view, h, m) -> {
            edtTime.setText(String.format(Locale.getDefault(), "%02d:%02d", h, m));
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    private void setupCityData() {
        List<String> cities = new ArrayList<>();
        cities.add("Đà Nẵng"); cities.add("Huế"); cities.add("Hội An");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrigin.setAdapter(adapter);
        spDestination.setAdapter(adapter);
        spDestination.setSelection(1);
    }

    private void performSearch() {
        String origin = spOrigin.getSelectedItem().toString();
        String destination = spDestination.getSelectedItem().toString();
        String date = edtDate.getText().toString();
        String time = edtTime.getText().toString(); // Lấy giờ từ ô nhập

        if (origin.equals(destination)) {
            Toast.makeText(this, "Nơi đi và đến phải khác nhau!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, VeResultsActivity.class);
        intent.putExtra("ORIGIN_KEY", origin);
        intent.putExtra("DESTINATION_KEY", destination);
        intent.putExtra("DATE_KEY", date);
        intent.putExtra("TIME_KEY", time); // Gửi giờ sang màn hình sau
        startActivity(intent);
    }
}