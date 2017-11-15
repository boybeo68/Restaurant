package vn.myclass.restaurant.View;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.R;

public class ChiTietQuanAn_Activity extends AppCompatActivity {
    QuanAnModel quanAnModel;
    TextView txtTenQuanAn,txtDiachiQuanAn,txtGioHoatDong,txtTrangThai,txtTongCheckin,txtTongBinhLuan,txtTongAnh;
    ImageView imgHinhQuanAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);

        quanAnModel=getIntent().getParcelableExtra("quanan");
        Log.d("kiemtraintent",quanAnModel.getTenquanan());
        txtTenQuanAn= (TextView) findViewById(R.id.txtTenQuanAn_chitetQA);
        txtDiachiQuanAn= (TextView) findViewById(R.id.txtTenDiachi_chitetQA);
        txtGioHoatDong= (TextView) findViewById(R.id.txtGioHoatDong_chitietQA);
        txtTrangThai= (TextView) findViewById(R.id.txtTrangThai_chitietQA);
        txtTongCheckin= (TextView) findViewById(R.id.txtSoCheckin_chitetQA);
        txtTongBinhLuan= (TextView) findViewById(R.id.txtSoBinhLuan_chitietQA);
        txtTongAnh= (TextView) findViewById(R.id.txtSoHinhAnh_chitietQA);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.d("kiemtrabinhluan",quanAnModel.getBinhLuanModelList().size()+"-"+quanAnModel.getBinhLuanModelList().get(0).getThanhVienModel().getHoten());
        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        txtDiachiQuanAn.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtGioHoatDong.setText(quanAnModel.getGiomocua()+"-"+quanAnModel.getGiodongcua());
        txtTongAnh.setText(quanAnModel.getHinhanhquanan().size()+"");
        txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size()+"");
    }
}
