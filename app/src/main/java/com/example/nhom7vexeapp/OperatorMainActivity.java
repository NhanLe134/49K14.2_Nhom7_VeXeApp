package com.example.nhom7vexeapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OperatorMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Giao diện tạm thời cho Nhà xe
        TextView textView = new TextView(this);
        textView.setText("CHÀO MỪNG NHÀ XE\nĐây là màn hình quản lý dành cho nhà xe.");
        textView.setTextSize(20);
        textView.setPadding(50, 50, 50, 50);
        
        setContentView(textView);
    }
}
