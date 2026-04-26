package com.example.nhom7vexeapp.viewmodels;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.nhom7vexeapp.models.VehicleManaged;
import com.example.nhom7vexeapp.repositories.VehicleRepository;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleViewModel extends ViewModel {
    private VehicleRepository repository;
    public MutableLiveData<List<VehicleManaged>> vehicleList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> isActionSuccess = new MutableLiveData<>();

    public VehicleViewModel() {
        this.repository = new VehicleRepository();
    }

    public void fetchVehicles(String nhaXeId) {
        isLoading.setValue(true);
        if (repository == null) return;

        repository.getAllVehicles().enqueue(new Callback<List<VehicleManaged>>() {
            @Override
            public void onResponse(Call<List<VehicleManaged>> call, Response<List<VehicleManaged>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<VehicleManaged> allVehicles = response.body();
                    List<VehicleManaged> filteredList = new ArrayList<>();
                    
                    for (VehicleManaged v : allVehicles) {
                        // SỬA LỖI: Sử dụng hàm getNhaXeIDStr() đúng với Model mới
                        String vehicleNhaXeId = v.getNhaXeIDStr();
                        if (vehicleNhaXeId != null && nhaXeId != null) {
                            if (vehicleNhaXeId.trim().equalsIgnoreCase(nhaXeId.trim())) {
                                filteredList.add(v);
                            }
                        }
                    }
                    vehicleList.setValue(filteredList);
                    
                    if (filteredList.isEmpty()) {
                        errorMessage.setValue("Nhà xe hiện chưa có phương tiện nào.");
                    }
                } else {
                    errorMessage.setValue("Lỗi từ server Render: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<VehicleManaged>> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối Render (Cold Start): Thử lại sau 30s.");
            }
        });
    }

    public void updateVehicle(String id, VehicleManaged vehicle) {
        isLoading.setValue(true);
        if (repository == null) return;

        repository.updateVehicle(id, vehicle).enqueue(new Callback<VehicleManaged>() {
            @Override
            public void onResponse(Call<VehicleManaged> call, Response<VehicleManaged> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) isActionSuccess.setValue(true);
                else errorMessage.setValue("Cập nhật thất bại.");
            }
            @Override public void onFailure(Call<VehicleManaged> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối.");
            }
        });
    }

    public void deleteVehicle(String id) {
        isLoading.setValue(true);
        if (repository == null) return;

        repository.deleteVehicle(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) isActionSuccess.setValue(true);
                else errorMessage.setValue("Xóa thất bại.");
            }
            @Override public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối.");
            }
        });
    }
}
