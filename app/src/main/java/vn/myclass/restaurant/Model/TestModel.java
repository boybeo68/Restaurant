package vn.myclass.restaurant.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by boybe on 12/4/2017.
 */

public class TestModel implements Parcelable {
    private  String ten;
    private  long giatoida, giatoithieu;

    public TestModel() {
    }

    protected TestModel(Parcel in) {
        ten = in.readString();
        giatoida = in.readLong();
        giatoithieu = in.readLong();
    }

    public static final Creator<TestModel> CREATOR = new Creator<TestModel>() {
        @Override
        public TestModel createFromParcel(Parcel in) {
            return new TestModel(in);
        }

        @Override
        public TestModel[] newArray(int size) {
            return new TestModel[size];
        }
    };

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ten);
        dest.writeLong(giatoida);
        dest.writeLong(giatoithieu);
    }
}
