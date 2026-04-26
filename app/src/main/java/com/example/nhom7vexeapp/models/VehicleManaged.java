package com.example.nhom7vexeapp.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Map;

/**
 * Model khớp tuyệt đối 100% với class Xe(models.Model) trong Django
 */
public class VehicleManaged implements Serializable {
    @SerializedName("XeID") // Khớp với XeID trong Django
    private String XeID;

    @SerializedName("BienSoXe") // Khớp với BienSoXe trong Django
    private String BienSoXe;

    @SerializedName("TrangThai") // Khớp với TrangThai trong Django
    private String TrangThai;

    @SerializedName("SoGhe") // Khớp với SoGhe trong Django
    private Integer SoGhe;

    @SerializedName("Loaixe") // Khớp với Loaixe trong Django
    private Object Loaixe; 

    @SerializedName("Nhaxe") // Khớp với Nhaxe trong Django
    private Object Nhaxe;

    public VehicleManaged() {}

    // Getters
    public String getXeID() { return XeID != null ? XeID : "N/A"; }
    public String getBienSoXe() { return BienSoXe != null ? BienSoXe : "N/A"; }
    public String getTrangThai() { return TrangThai != null ? TrangThai : "Đang hoạt động"; }
    public Integer getSoGhe() { return SoGhe; }

    // Setters - Bổ sung để sửa lỗi "cannot find symbol"
    public void setXeID(String xeID) { this.XeID = xeID; }
    public void setBienSoXe(String bienSoXe) { this.BienSoXe = bienSoXe; }
    public void setTrangThai(String trangThai) { this.TrangThai = trangThai; }
    public void setSoGhe(Integer soGhe) { this.SoGhe = soGhe; }
    public void setLoaixe(Object loaixe) { this.Loaixe = loaixe; }
    public void setNhaxe(Object nhaxe) { this.Nhaxe = nhaxe; }

    /**
     * Bóc tách ID nhà xe từ trường Nhaxe
     */
    public String getNhaXeIDStr() {
        if (Nhaxe == null) return "";
        if (Nhaxe instanceof String) return (String) Nhaxe;
        if (Nhaxe instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) Nhaxe;
            Object id = map.get("NhaxeID");
            return id != null ? String.valueOf(id) : "";
        }
        return String.valueOf(Nhaxe);
    }

    /**
     * Bóc tách ID loại xe từ trường Loaixe
     */
    public String getLoaiXeIDStr() {
        if (Loaixe == null) return "N/A";
        if (Loaixe instanceof String) return (String) Loaixe;
        if (Loaixe instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) Loaixe;
            Object id = map.get("LoaixeID");
            return id != null ? String.valueOf(id) : "N/A";
        }
        return String.valueOf(Loaixe);
    }
}
