package vn.myclass.restaurant.Model;

/**
 * Created by boybe on 11/6/2017.
 */

public class ChiNhanhQuanAnModel {
    String diachi;
    Double latitude,longitude,khoangcach;

    public ChiNhanhQuanAnModel() {
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(Double khoangcach) {
        this.khoangcach = khoangcach;
    }
}
