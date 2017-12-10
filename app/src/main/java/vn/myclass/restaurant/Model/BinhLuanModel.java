package vn.myclass.restaurant.Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.R;

/**
 * Created by boybe on 11/1/2017.
 */

public class BinhLuanModel implements Parcelable{
    double chamdiem;
    long luotthich;
    ThanhVienModel thanhVienModel;
    String noidung;
    String tieude;
    String manbinhluan;
    List<String> hinhanhBinhLuanList;
    String mauser;
    List<Bitmap>listBitMap;
    boolean kiemtra=false;


    public BinhLuanModel() {
    }

    protected BinhLuanModel(Parcel in) {
        chamdiem = in.readDouble();
        luotthich = in.readLong();
        noidung = in.readString();
        tieude = in.readString();
        manbinhluan = in.readString();
        hinhanhBinhLuanList = in.createStringArrayList();
        mauser = in.readString();
        thanhVienModel=in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getManbinhluan() {
        return manbinhluan;
    }

    public void setManbinhluan(String manbinhluan) {
        this.manbinhluan = manbinhluan;
    }

    public List<String> getHinhanhBinhLuanList() {
        return hinhanhBinhLuanList;
    }

    public void setHinhanhBinhLuanList(List<String> hinhanhBinhLuanList) {
        this.hinhanhBinhLuanList = hinhanhBinhLuanList;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(chamdiem);
        dest.writeLong(luotthich);
        dest.writeString(noidung);
        dest.writeString(tieude);
        dest.writeString(manbinhluan);
        dest.writeStringList(hinhanhBinhLuanList);
        dest.writeString(mauser);
        dest.writeParcelable(thanhVienModel,flags);
    }
    public void themBinhLuan(final Context context, String maQuanAn, BinhLuanModel binhLuanModel, final List<Bitmap> listHinh){
        DatabaseReference nodeBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        // Sử dụng push để tạo key động
        final String mabinhluan =  nodeBinhLuan.child(maQuanAn).push().getKey();
        listBitMap=new ArrayList<>();
        nodeBinhLuan.child(maQuanAn).child(mabinhluan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    if(listHinh.size() > 0){
//                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh/"+uri.getLastPathSegment());
//                            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                }
//                            });


                            for (int j=0;j<listHinh.size();j++){

//                                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());
                                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(mabinhluan+"mabinhluan"+j);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                Bitmap bitmap = listHinh.get(j);
                                final Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 500);

                                converetdImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();
                                FirebaseStorage.getInstance().getReference().child("hinhanh/"+mabinhluan+"mabinhluan"+j).putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        listBitMap.add(converetdImage);
//                                        Log.d("kiemtralisthinh",listBitMap.size()+"--"+listHinh.size());
//                                        Toast.makeText(context, R.string.TaiHinh, Toast.LENGTH_SHORT).show();
                                        if (listBitMap.size()==listHinh.size()){
//                                            Log.d("kiemtralisthinhdieukien",listBitMap.size()+"--"+listHinh.size());
                                            Toast.makeText(context, R.string.TaiHinhAnh, Toast.LENGTH_SHORT).show();
                                            ((Activity)context).finish();

                                        }
                                    }
                                });


                            }



                        }
                    }

                }
        });
    }
}
