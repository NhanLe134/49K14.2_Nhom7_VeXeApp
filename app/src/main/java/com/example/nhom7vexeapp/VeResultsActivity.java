
package com.example.nhom7vexeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom7vexeapp.adapters.VeResultAdapter;
import com.example.nhom7vexeapp.models.VeResultModel;

import java.util.ArrayList;
import java.util.List;

public class VeResultsActivity extends AppCompatActivity {

    private RecyclerView rvVeResults;
    private VeResultAdapter adapter;
    private TextView tvRouteTitle, tvDateTitle;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ve_results);

        // 1. Ánh xạ View từ XML
        initViews();

        // 2. Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String origin = intent.getStringExtra("ORIGIN_KEY");
        String destination = intent.getStringExtra("DESTINATION_KEY");
        String date = intent.getStringExtra("DATE_KEY");

        // --- KHẮC PHỤC LỖI VĂNG APP ---
        // Nếu chạy trực tiếp màn hình này (không qua Tìm kiếm), dữ liệu sẽ bị null.
        // Chúng ta gán dữ liệu giả để App không bị Crash.
        if (origin == null) origin = "Đà Nẵng";
        if (destination == null) destination = "Huế";
        if (date == null) date = "02/04/2026";

        // 3. Hiển thị thông tin lên Header
        if (tvRouteTitle != null) {
            tvRouteTitle.setText(origin + " - " + destination);
        }
        if (tvDateTitle != null) {
            tvDateTitle.setText(date);
        }

        // 4. Xử lý nút quay lại
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 5. Thiết lập danh sách vé
        setupRecyclerView();
    }

    private void initViews() {
        rvVeResults = findViewById(R.id.rvVeResults);
        tvRouteTitle = findViewById(R.id.tvRouteTitle);
        tvDateTitle = findViewById(R.id.tvDateTitle);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupRecyclerView() {
        // Kiểm tra rvVeResults có tồn tại trong XML không trước khi dùng
        if (rvVeResults == null) return;

        rvVeResults.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu mẫu giống Figma
        List<VeResultModel> list = new ArrayList<>();
        list.add(new VeResultModel("07:00", "09:30", "Nhà xe Thành Hải", "130.000", "3 chỗ trống", "Xe 4 chỗ"));
        list.add(new VeResultModel("07:30", "10:00", "Nhà xe Long Hùng", "180.000", "4 chỗ trống", "Xe 7 chỗ"));
        list.add(new VeResultModel("08:00", "11:00", "Đà Nẵng - Huế", "155.000", "6 chỗ trống", "Xe Limousine"));
        list.add(new VeResultModel("09:00", "12:00", "Nhà xe Kim Chi", "140.000", "2 chỗ trống", "Xe 4 chỗ"));

        // Khởi tạo Adapter
        adapter = new VeResultAdapter(list, item -> {
            // Xử lý khi nhấn nút "Chọn chỗ"
            try {
                Intent nextIntent = new Intent(VeResultsActivity.this, DatVeActivity.class);
                nextIntent.putExtra("selected_nha_xe", item.getNhaXeName());
                nextIntent.putExtra("selected_price", item.getPrice());
                startActivity(nextIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Chưa tạo màn hình DatVeActivity nè Xù ơi!", Toast.LENGTH_SHORT).show();
            }
        });

        rvVeResults.setAdapter(adapter);
    }
}