package vn.myclass.restaurant.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.myclass.restaurant.Controller.Interface.ChitietQuanAn_Interface;

/**
 * Created by boybe on 11/20/2017.
 */

public class WifiQuanAnModel {
    String ten, matkhau, ngaydang;

    public WifiQuanAnModel() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }
    private DatabaseReference nodeWifiquanan;
    public void layDanhSachWifiQuanAn(String maquanan, final ChitietQuanAn_Interface chitietQuanAn_interface){
        nodeWifiquanan= FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);
        nodeWifiquanan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot valueWifi : dataSnapshot.getChildren()){
                    WifiQuanAnModel wifiQuanAnModel=valueWifi.getValue(WifiQuanAnModel.class);
                    //Interface dùng để sau khi download hết dữ liệu ms kích hiển thị lên , tránh đa tiến trình
                    chitietQuanAn_interface.HienThiDanhSachWifi(wifiQuanAnModel);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
