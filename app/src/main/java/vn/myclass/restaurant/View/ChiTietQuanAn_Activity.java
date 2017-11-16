package vn.myclass.restaurant.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.R;

public class ChiTietQuanAn_Activity extends AppCompatActivity {
    QuanAnModel quanAnModel;
    TextView txtTenQuanAn,txtDiachiQuanAn,txtGioHoatDong,txtTrangThai,txtTongCheckin,txtTongBinhLuan,txtTongAnh;
    TextView txtTieudeToolbar;
    ImageView imgHinhQuanAn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);

        quanAnModel=getIntent().getParcelableExtra("quanan");
//        Log.d("kiemtraintent",quanAnModel.getTenquanan());
        txtTenQuanAn= (TextView) findViewById(R.id.txtTenQuanAn_chitetQA);
//        txtDiachiQuanAn= (TextView) findViewById(R.id.txtTenDiachi_chitetQA);
        txtGioHoatDong= (TextView) findViewById(R.id.txtGioHoatDong_chitietQA);
        txtTrangThai= (TextView) findViewById(R.id.txtTrangThai_chitietQA);
        txtTongCheckin= (TextView) findViewById(R.id.txtSoCheckin_chitetQA);
        txtTongBinhLuan= (TextView) findViewById(R.id.txtSoBinhLuan_chitietQA);
        txtTongAnh= (TextView) findViewById(R.id.txtSoHinhAnh_chitietQA);
        txtTieudeToolbar= (TextView) findViewById(R.id.txtTieudeToolbar);
        imgHinhQuanAn= (ImageView) findViewById(R.id.imgAnhQuaAn_chitetQA);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Chi nhánh quán ăn
        LinearLayout linearLayout= (LinearLayout)findViewById(R.id.linear);      //find the linear layout
        linearLayout.removeAllViews();                              //add this too
        for(int i=0; i<quanAnModel.getChiNhanhQuanAnModelList().size();i++){          //looping to create 5 textviews

            TextView chiNhanh= new TextView(this);              //dynamically create textview
            chiNhanh.setLayoutParams(new LinearLayout.LayoutParams(             //select linearlayoutparam- set the width & height
                    ViewGroup.LayoutParams.MATCH_PARENT, 48));
            chiNhanh.setGravity(Gravity.CENTER_VERTICAL);                       //set the gravity too
            chiNhanh.setText(i+1+": "+quanAnModel.getChiNhanhQuanAnModelList().get(i).getDiachi());//adding text
            chiNhanh.setPadding(0,2,0,2);
            linearLayout.addView(chiNhanh);                                     //inflating :)
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.d("kiemtrabinhluan",quanAnModel.getBinhLuanModelList().size()+"-"+quanAnModel.getBinhLuanModelList().get(0).getThanhVienModel().getHoten());
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        String giohientai=simpleDateFormat.format(calendar.getTime());
        String gioMoCua=quanAnModel.getGiomocua();
        String gioDongCua=quanAnModel.getGiodongcua();
        try {
            Date dateHienTai=simpleDateFormat.parse(giohientai);
            Date dateMoCua=simpleDateFormat.parse(gioMoCua);
            Date dateDongCua=simpleDateFormat.parse(gioDongCua);
            if (dateHienTai.after(dateMoCua)&& dateHienTai.before(dateDongCua)){
                txtTrangThai.setText(getString(R.string.DangMoCua));
            }else {
                txtTrangThai.setText(getString(R.string.DaDongCua));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        txtTenQuanAn.setText(quanAnModel.getTenquanan());
//        txtDiachiQuanAn.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        Log.d("kiemtraCN",quanAnModel.getChiNhanhQuanAnModelList().size()+"");
        txtGioHoatDong.setText(quanAnModel.getGiomocua()+"-"+quanAnModel.getGiodongcua());
        txtTongAnh.setText(quanAnModel.getHinhanhquanan().size()+"");
        txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size()+"");
        txtTieudeToolbar.setText(quanAnModel.getTenquanan());
        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imgHinhQuanAn.setImageBitmap(bitmap);
            }
        });
    }
}
