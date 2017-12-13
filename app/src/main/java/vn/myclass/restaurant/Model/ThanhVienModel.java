package vn.myclass.restaurant.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by boybe on 10/31/2017.
 */

public class ThanhVienModel implements Parcelable {
   private String hoten,hinhanh;
   private String maThanhVien;

    protected ThanhVienModel(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
        maThanhVien = in.readString();
    }

    public ThanhVienModel() {
    }

    public static final Creator<ThanhVienModel> CREATOR = new Creator<ThanhVienModel>() {
        @Override
        public ThanhVienModel createFromParcel(Parcel in) {
            return new ThanhVienModel(in);
        }

        @Override
        public ThanhVienModel[] newArray(int size) {
            return new ThanhVienModel[size];
        }
    };

    public String getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hoten);
        dest.writeString(hinhanh);
        dest.writeString(maThanhVien);
    }
    public  void ThemThongTinThanhVien(ThanhVienModel thanhVienModel,String uid){
        DatabaseReference nodeThanhVien = FirebaseDatabase.getInstance().getReference().child("thanhviens");
        nodeThanhVien.child(uid).setValue(thanhVienModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("kiemtrathanhvien","thanh công");
            }
        });
    }
}
