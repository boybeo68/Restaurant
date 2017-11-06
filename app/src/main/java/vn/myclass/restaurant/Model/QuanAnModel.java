package vn.myclass.restaurant.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.myclass.restaurant.Controller.Interface.Odau_interface;

/**
 * Created by boybe on 10/25/2017.
 */

public class QuanAnModel {
    boolean giaohang;
    String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<BinhLuanModel> binhLuanModelList;
    List<ChiNhanhQuanAnModel>chiNhanhQuanAnModelList;

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    List<Bitmap> bitmapList;

    long giatoida;
    long giatoithieu;
    long luotthich;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference noderoot;

    public QuanAnModel() {

        noderoot = firebaseDatabase.getReference();
    }

    public QuanAnModel(boolean giaohang, String giodongcua, String giomocua, String tenquanan, String videogioithieu, String maquanan, List<String> tienich, List<String> hinhanhquanan, List<Bitmap> bitmapList, long giatoida, long giatoithieu, long luotthich) {
        this.giaohang = giaohang;
        this.giodongcua = giodongcua;
        this.giomocua = giomocua;
        this.tenquanan = tenquanan;
        this.videogioithieu = videogioithieu;
        this.maquanan = maquanan;
        this.tienich = tienich;
        this.hinhanhquanan = hinhanhquanan;
        this.bitmapList = bitmapList;
        this.giatoida = giatoida;
        this.giatoithieu = giatoithieu;
        this.luotthich = luotthich;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public void getDanhsachQuanAn(final Odau_interface odau_interface, final Location vitrihientai) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            //datasnapshot chính là dữ liệu của noderoot
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Lấy danh sách  quán ăn
                DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");
                // gặp key động dùng for để duyệt từng phần từ

                for (DataSnapshot valueQuanan : dataSnapshotQuanan.getChildren()) {
                    QuanAnModel quanAnModel = valueQuanan.getValue(QuanAnModel.class);
                    quanAnModel.setMaquanan(valueQuanan.getKey());
                    //Lấy danh sách hình ảnh quán ăn
                    DataSnapshot dataSnapshotHinhQA = dataSnapshot.child("hinhanhquanans").child(valueQuanan.getKey());
//                        Log.d("kiemtraMa",valueQuanan.getKey());
                    List<String> hinhanhList = new ArrayList<>();
                    for (DataSnapshot valueHinhanhQA : dataSnapshotHinhQA.getChildren()) {
//                            Log.d("kiemtrahinh",valueHinhanhQA.getValue()+"");
                        hinhanhList.add(valueHinhanhQA.getValue(String.class));
                    }
                    quanAnModel.setHinhanhquanan(hinhanhList);
                    //Lấy danh sách bình luân của quán ăn
                    DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
                    //do 1 quán ăn có nhiều bình luận ==>tạo list
                    List<BinhLuanModel> binhLuanModels = new ArrayList<>();

                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                        BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);

                        ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                        binhLuanModel.setThanhVienModel(thanhVienModel);// có được dữ liệu về thành viên của bình luận đó
                        binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                        List<String> hinhbinhluanList = new ArrayList<>();//1 bình luận có nhiều tấm hinh
                        DataSnapshot snapshotnodeHinhanhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                        for (DataSnapshot valuehinhanhbinhluan : snapshotnodeHinhanhBL.getChildren()) {
                            hinhbinhluanList.add(valuehinhanhbinhluan.getValue(String.class));
                        }
                        binhLuanModel.setHinhanhBinhLuanList(hinhbinhluanList);
                        binhLuanModels.add(binhLuanModel);
                    }
                    quanAnModel.setBinhLuanModelList(binhLuanModels);
                    // lấy chi nhánh quán ăn
                        List<ChiNhanhQuanAnModel>chiNhanhQuanAnModels =new ArrayList<>();
                        DataSnapshot snapshotChiNhanh=dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
                        for (DataSnapshot valueChinhanhquanan : snapshotChiNhanh.getChildren()){
                            ChiNhanhQuanAnModel chiNhanhQuanAnModel=valueChinhanhquanan.getValue(ChiNhanhQuanAnModel.class);
                            Location vitriquanan=new Location("");
                            vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                            vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                           double khoangcach= vitrihientai.distanceTo(vitriquanan)/1000;
                           Log.d("toado",khoangcach+"=="+chiNhanhQuanAnModel.getDiachi());
                           chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                           chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
                        }
                        quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);


                    odau_interface.getDanhsachQuananModel(quanAnModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        noderoot.addListenerForSingleValueEvent(valueEventListener);
    }
}
