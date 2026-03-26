package com.example.nhom7vexeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PhanHoiActivity extends AppCompatActivity {

    private TextView tabPending, tabReviewed;
    private View tabIndicator;
    private LinearLayout layoutFeedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_hoi);

        // Ánh xạ View
        tabPending = findViewById(R.id.tab_pending);
        tabReviewed = findViewById(R.id.tab_reviewed);
        tabIndicator = findViewById(R.id.tab_indicator);
        layoutFeedbackList = findViewById(R.id.layout_feedback_list);

        // Mặc định hiển thị tab Chờ đánh giá
        showPendingFeedback();

        // Sự kiện click tab
        tabPending.setOnClickListener(v -> showPendingFeedback());
        tabReviewed.setOnClickListener(v -> showReviewedFeedback());
    }

    private void showPendingFeedback() {
        updateTabUI(tabPending);
        layoutFeedbackList.removeAllViews();

        // Giả lập dữ liệu "Chờ đánh giá" theo hình ảnh
        addFeedbackItem("13", "04/2026", "13:45 - 16:45", "Khang Limousine", "Huế - Đà Nẵng", "Đã hoàn thành", "#27AE60");
        addFeedbackItem("06", "03/2026", "13:45 - 16:45", "Khang Limousine", "Đà Nẵng - Huế", "Đã hoàn thành", "#27AE60");
        addFeedbackItem("20", "02/2026", "13:45 - 16:45", "Khang Limousine", "Huế - Đà Nẵng", "Đang đón khách", "#F1C40F");
    }

    private void showReviewedFeedback() {
        updateTabUI(tabReviewed);
        layoutFeedbackList.removeAllViews();
        // Bạn có thể thêm dữ liệu "Đã đánh giá" ở đây nếu có hình ảnh thiết kế
    }

    private void addFeedbackItem(String day, String monthYear, String time, String company, String route, String status, String statusColor) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.item_phan_hoi, layoutFeedbackList, false);

        ((TextView) itemView.findViewById(R.id.tvDay)).setText(day);
        ((TextView) itemView.findViewById(R.id.tvMonthYear)).setText(monthYear);
        ((TextView) itemView.findViewById(R.id.tvTimeRange)).setText(time);
        ((TextView) itemView.findViewById(R.id.tvBusCompany)).setText(company);
        ((TextView) itemView.findViewById(R.id.tvRoute)).setText(route);
        
        TextView tvStatus = itemView.findViewById(R.id.tvStatus);
        tvStatus.setText(status);
        tvStatus.setTextColor(Color.parseColor(statusColor));

        layoutFeedbackList.addView(itemView);
    }

    private void updateTabUI(TextView selectedTab) {
        tabPending.setTextColor(Color.parseColor("#888888"));
        tabReviewed.setTextColor(Color.parseColor("#888888"));

        selectedTab.setTextColor(Color.parseColor("#000000"));

        tabIndicator.post(() -> {
            int width = tabPending.getWidth();
            if (selectedTab == tabPending) tabIndicator.setTranslationX(0);
            else if (selectedTab == tabReviewed) tabIndicator.setTranslationX(width);
        });
    }
}
