package vn.myclass.restaurant.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.myclass.restaurant.Controller.Interface.Odau_interface;

/**
 * Created by boybe on 10/25/2017.
 */

public class QuanAnModel implements Parcelable{
    private boolean giaohang;
    private String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    private List<String> tienich;
    private List<String> hinhanhquanan;
    private List<BinhLuanModel> binhLuanModelList;
    private List<ChiNhanhQuanAnModel>chiNhanhQuanAnModelList;
    private List<Bitmap> bitmapList;
    private List<ThucDonModel>thucDonModelList;
    private long giatoida;
    private long giatoithieu;
    private long luotthich;

    public List<ThucDonModel> getThucDonModelList() {
        return thucDonModelList;
    }

    public void setThucDonModelList(List<ThucDonModel> thucDonModelList) {
        this.thucDonModelList = thucDonModelList;
    }

    protected QuanAnModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        giatoida = in.readLong();
        giatoithieu = in.readLong();
        luotthich = in.readLong();
        chiNhanhQuanAnModelList = new ArrayList<ChiNhanhQuanAnModel>();
        in.readTypedList(chiNhanhQuanAnModelList,ChiNhanhQuanAnModel.CREATOR);
        binhLuanModelList = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhLuanModelList,BinhLuanModel.CREATOR);
    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };

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

    private DatabaseReference noderoot;

    public QuanAnModel() {
        noderoot = FirebaseDatabase.getInstance().getReference();
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
    private DataSnapshot dataRoot;

    public void getDanhsachQuanAn(final Odau_interface odau_interface, final Location vitrihientai, final int itemtieptheo, final int itemdaco) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            //datasnapshot chính là dữ liệu của noderoot
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot=dataSnapshot;
                layDanhSachQuanAn(dataSnapshot,odau_interface,vitrihientai,itemtieptheo,itemdaco);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if (dataRoot!=null){
            // nếu khác null thì chỉ lấy ra không download lại nữa
            layDanhSachQuanAn(dataRoot,odau_interface,vitrihientai,itemtieptheo,itemdaco);
        }else {
            // nếu ==null thì ms down load lại trên server
            noderoot.addListenerForSingleValueEvent(valueEventListener);
        }


    }
    private  void layDanhSachQuanAn(DataSnapshot dataSnapshot, Odau_interface odau_interface,Location vitrihientai,int itemtieptheo,int itemdaco){
        //Lấy danh sách  quán ăn
        DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");
        // gặp key động dùng for để duyệt từng phần từ

        int i=0;

        for (DataSnapshot valueQuanan : dataSnapshotQuanan.getChildren()) {
//            Log.d("kiemtra14testvaluequan",valueQuanan+"");

            if (i == itemtieptheo){
                break;
            }
            if (i < itemdaco){
                i++;
                continue;
            }
            i++;
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
                Log.d("kiemtra15cc",valueBinhLuan+"");
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                Log.d("kiemtra15cc",dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser())+"");
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
   public void getDanhsachSearch(final Odau_interface odau_interface, final Location vitrihientai,String textSearch){
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        noderoot.addListenerForSingleValueEvent(valueEventListener);

   }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);
        dest.writeString(videogioithieu);
        dest.writeString(maquanan);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhquanan);
        dest.writeLong(giatoida);
        dest.writeLong(giatoithieu);
        dest.writeLong(luotthich);
        dest.writeTypedList(chiNhanhQuanAnModelList);
        dest.writeTypedList(binhLuanModelList);
    }
}
