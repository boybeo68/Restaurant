package vn.myclass.restaurant.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.myclass.restaurant.Controller.Interface.ChitietQuanAn_Interface;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.ChiTietQuanAn_Activity;

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
//        nodeWifiquanan.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot valueWifi : dataSnapshot.getChildren()){
//                    WifiQuanAnModel wifiQuanAnModel=valueWifi.getValue(WifiQuanAnModel.class);
//                    //Interface dùng để sau khi download hết dữ liệu ms kích hiển thị lên , tránh đa tiến trình
//                    chitietQuanAn_interface.HienThiDanhSachWifi(wifiQuanAnModel);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        nodeWifiquanan.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Log.d("kiemtra2018",dataSnapshot.getValue()+"");
                WifiQuanAnModel wifiQuanAnModel=dataSnapshot.getValue(WifiQuanAnModel.class);
                chitietQuanAn_interface.HienThiDanhSachWifi(wifiQuanAnModel);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void  ThemWifiQuanAn(final Context context, WifiQuanAnModel wifiQuanAnModel,String maquanan){
        DatabaseReference datanodeWifiQuanan=FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);
        datanodeWifiQuanan.push().setValue(wifiQuanAnModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(context,context.getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
            }
        });
    }
}
