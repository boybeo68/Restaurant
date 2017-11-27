package vn.myclass.restaurant.Model;

/**
 * Created by boybe on 11/26/2017.
 */

public class ChonHinhBinhLuanModel {
    String duongdan;
    boolean ischeck;

    public ChonHinhBinhLuanModel(String duongdan, boolean ischeck) {
        this.duongdan=duongdan;
        this.ischeck=ischeck;
    }

    public String getDuongdan() {
        return duongdan;
    }

    public void setDuongdan(String duongdan) {
        this.duongdan = duongdan;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }
}
