package com.example.nhom7vexeapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhom7vexeapp.R;
import com.example.nhom7vexeapp.models.Driver;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {
    private List<Driver> driverList;
    private OnDriverClickListener listener;

    public interface OnDriverClickListener {
        void onDriverClick(Driver driver);
    }

    public DriverAdapter(List<Driver> driverList, OnDriverClickListener listener) {
        this.driverList = driverList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver, parent, false);
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        Driver driver = driverList.get(position);
        holder.tvName.setText(driver.getName());
        holder.tvPhone.setText("SĐT: " + driver.getPhone());
        
        // Mocking pickup/dropoff for the card look
        holder.tvPickup.setText("Điểm đón: 1 Tố Hữu, Thành Phố Huế");
        holder.tvDropoff.setText("Điểm trả: 1 Hùng Vương, Đà Nẵng");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDriverClick(driver);
            }
        });
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    static class DriverViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvPickup, tvDropoff;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvDriverName);
            tvPhone = itemView.findViewById(R.id.tvDriverPhone);
            tvPickup = itemView.findViewById(R.id.tvPickup);
            tvDropoff = itemView.findViewById(R.id.tvDropoff);
        }
    }
}
