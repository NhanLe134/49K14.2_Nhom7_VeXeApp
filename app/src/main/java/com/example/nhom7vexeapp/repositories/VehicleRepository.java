package com.example.nhom7vexeapp.repositories;

import com.example.nhom7vexeapp.models.VehicleManaged;
import com.example.nhom7vexeapp.api.ApiClient;
import com.example.nhom7vexeapp.api.ApiService;
import java.util.List;
import retrofit2.Call;

public class VehicleRepository {
    private ApiService apiService;

    public VehicleRepository() {
        if (ApiClient.getClient() != null) {
            this.apiService = ApiClient.getClient().create(ApiService.class);
        }
    }

    public Call<List<VehicleManaged>> getAllVehicles() {
        return apiService.getVehicles(); // Sử dụng endpoint từ ApiService chung
    }

    public Call<VehicleManaged> updateVehicle(String id, VehicleManaged vehicle) {
        return apiService.updateVehicle(id, vehicle);
    }

    public Call<Void> deleteVehicle(String id) {
        return apiService.deleteVehicle(id);
    }
}
