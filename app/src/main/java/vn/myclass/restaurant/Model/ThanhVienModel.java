package vn.myclass.restaurant.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by boybe on 10/31/2017.
 */

public class ThanhVienModel  {
    String hoten,hinhanh;
    String maThanhVien;

    public String getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    private DatabaseReference nodeThanhvien;

    public ThanhVienModel() {
        nodeThanhvien= FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }

    public ThanhVienModel(String hoten, String hinhanh) {
        this.hoten = hoten;
        this.hinhanh = hinhanh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
    public  void ThemThongTinThanhVien(ThanhVienModel thanhVienModel,String uid){
        nodeThanhvien.child(uid).setValue(thanhVienModel);
    }
}
