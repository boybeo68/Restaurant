package vn.myclass.restaurant.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boybe on 10/31/2017.
 */

public class ThanhVienModel implements Parcelable {
    private String hoten, hinhanh;
    private String maThanhVien;
    private List<String> maquan;
    private List<String> maquanluu;
    private List<QuanAnModel> quanAnModelList;

    public List<String> getMaquanluu() {
        return maquanluu;
    }

    public void setMaquanluu(List<String> maquanluu) {
        this.maquanluu = maquanluu;
    }

    public List<QuanAnModel> getQuanAnModelList() {
        return quanAnModelList;
    }

    public void setQuanAnModelList(List<QuanAnModel> quanAnModelList) {
        this.quanAnModelList = quanAnModelList;
    }

    public List<String> getMaquan() {
        return maquan;
    }

    public void setMaquan(List<String> maquan) {
        this.maquan = maquan;
    }

    protected ThanhVienModel(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
        maThanhVien = in.readString();
        maquan = in.createStringArrayList();
        maquanluu = in.createStringArrayList();
        quanAnModelList = new ArrayList<QuanAnModel>();
        in.readTypedList(quanAnModelList, QuanAnModel.CREATOR);
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
        dest.writeStringList(maquan);
        dest.writeStringList(maquanluu);
        dest.writeTypedList(quanAnModelList);
    }

    public void ThemThongTinThanhVien(final ThanhVienModel thanhVienModel, final String uid) {
//        DatabaseReference nodeThanhVien = FirebaseDatabase.getInstance().getReference().child("thanhviens");
//        nodeThanhVien.child(uid).setValue(thanhVienModel).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Log.d("kiemtrathanhvien","thanh công");
//            }
//        });
        final List<String> listUid = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("thanhviens").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot valueThanhvien : dataSnapshot.getChildren()) {
//                    Log.d("kiemtra13", valueThanhvien.getKey() + "");
                    listUid.add(valueThanhvien.getKey());
//                    Log.d("kiemtra14", listUid.size() + "");
                }
                //kiểm tra xem uid đã tồn tại hay chưa
                if (!listUid.contains(uid)) {
                    DatabaseReference nodeThanhVien = FirebaseDatabase.getInstance().getReference().child("thanhviens");
                    nodeThanhVien.child(uid).setValue(thanhVienModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                            Log.d("kiemtrathanhvien", "thanh công");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
