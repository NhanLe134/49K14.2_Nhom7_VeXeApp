package com.example.nhom7vexeapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// QUAN TRỌNG: Chỉ import R của dự án mình thôi, xóa cái android.os.Build... đi nhé
import com.example.nhom7vexeapp.R;
import com.example.nhom7vexeapp.models.VeResultModel;

import java.util.List;

public class VeResultAdapter extends RecyclerView.Adapter<VeResultAdapter.ViewHolder> {

    private List<VeResultModel> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onSelectClick(VeResultModel item);
    }

    public VeResultAdapter(List<VeResultModel> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Hết đỏ ở layout nè
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ve_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VeResultModel item = list.get(position);

        if (item != null) {
            holder.tvNhaXe.setText(item.getNhaXeName());
            holder.tvPrice.setText(item.getPrice() + "đ");
            holder.tvSeats.setText(item.getEmptySeats());
            holder.tvCar.setText(item.getCarType());

            // Dữ liệu giả - Hết đỏ ở drawable nè
            holder.tvTime.setText("07:00 - 09:30");
            holder.imgNhaXe.setImageResource(R.drawable.kh_home_1);

            holder.btnSelect.setOnClickListener(v -> {
                if (listener != null) listener.onSelectClick(item);
            });
        }
    }

    @Override
    public int getItemCount() { return list != null ? list.size() : 0; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvNhaXe, tvPrice, tvSeats, tvCar;
        ImageView imgNhaXe;
        Button btnSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Hết đỏ ở các ID nè
            tvTime = itemView.findViewById(R.id.tvTimeResult);
            tvNhaXe = itemView.findViewById(R.id.tvNhaXeName);
            tvPrice = itemView.findViewById(R.id.tvPriceResult);
            tvSeats = itemView.findViewById(R.id.tvEmptySeats);
            tvCar = itemView.findViewById(R.id.tvCarType);
            btnSelect = itemView.findViewById(R.id.btnSelectSeat);
            imgNhaXe = itemView.findViewById(R.id.imgNhaXe);

            // Xù có thể mở ra nếu trong XML đã đặt ID cho mấy cái này
            // tvRating = itemView.findViewById(R.id.tvRating);
            // tvConfirmStatus = itemView.findViewById(R.id.tvConfirmStatus);
        }
    }
}