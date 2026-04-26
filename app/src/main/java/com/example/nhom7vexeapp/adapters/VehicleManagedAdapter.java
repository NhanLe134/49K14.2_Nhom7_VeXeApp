package com.example.nhom7vexeapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhom7vexeapp.R;
import com.example.nhom7vexeapp.models.VehicleManaged;
import java.util.List;

public class VehicleManagedAdapter extends RecyclerView.Adapter<VehicleManagedAdapter.VehicleViewHolder> {

    private List<VehicleManaged> vehicleList;
    private Context context;
    private OnVehicleActionListener listener;

    public interface OnVehicleActionListener {
        void onDelete(VehicleManaged vehicle, int position);
        void onShowDetail(VehicleManaged vehicle); // Đổi tên cho rõ nghĩa
    }

    public VehicleManagedAdapter(List<VehicleManaged> vehicleList, Context context, OnVehicleActionListener listener) {
        this.vehicleList = vehicleList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phuong_tien, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        VehicleManaged vehicle = vehicleList.get(position);
        
        holder.tvIndex.setText(String.valueOf(position + 1));
        holder.tvPlate.setText(vehicle.getBienSoXe());
        holder.tvType.setText("Loại: " + vehicle.getLoaiXeIDStr());
        holder.tvStatus.setText(vehicle.getTrangThai());

        // Màu sắc trạng thái
        if ("Bảo trì".equalsIgnoreCase(vehicle.getTrangThai())) {
            holder.tvStatus.setTextColor(Color.parseColor("#FF9800"));
        } else if ("Hoạt động".equalsIgnoreCase(vehicle.getTrangThai())) {
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#9E9E9E"));
        }

        // Nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(vehicle, holder.getAdapterPosition());
        });

        // NHẤN VÀO TOÀN BỘ DÒNG XE ĐỂ XEM CHI TIẾT
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onShowDetail(vehicle);
        });
    }

    @Override
    public int getItemCount() {
        return (vehicleList != null) ? vehicleList.size() : 0;
    }

    static class VehicleViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex, tvPlate, tvType, tvStatus;
        ImageView btnDelete;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tvVehicleIndex);
            tvPlate = itemView.findViewById(R.id.tvVehiclePlate);
            tvType = itemView.findViewById(R.id.tvVehicleType);
            tvStatus = itemView.findViewById(R.id.tvVehicleStatus);
            btnDelete = itemView.findViewById(R.id.btnDeleteVehicle);
        }
    }
}
