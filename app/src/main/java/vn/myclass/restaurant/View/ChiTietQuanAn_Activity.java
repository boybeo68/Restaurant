package vn.myclass.restaurant.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.Frame;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vn.myclass.restaurant.Adapter.Adapter_BinhLuan;
import vn.myclass.restaurant.Controller.ChitietQuanAnController;
import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.Model.TienIchModel;
import vn.myclass.restaurant.R;

public class ChiTietQuanAn_Activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    QuanAnModel quanAnModel;
    TextView txtTenQuanAn, txtDiachiQuanAn, txtGioHoatDong, txtTrangThai, txtTongCheckin, txtTongBinhLuan, txtTongAnh;
    TextView txtTieudeToolbar, txtGioiHanGia, txtTenWifi, txtMatkhauWifi, txtNgaydangWifi;
    ImageView imgHinhQuanAn;
    Button btnBinhluan;
    Toolbar toolbar;
    RecyclerView recyclerViewBinhluan;
    Adapter_BinhLuan adapter_binhLuan;
    GoogleMap googleMap;
    MapFragment mapFragment;
    ChiNhanhQuanAnModel chiNhanhQuanAnModelTam, chiNhanhQuanAnModel;
    LinearLayout lnkhungtienich, khungWifi;
    ChitietQuanAnController chitietQuanAnController;
    View khungtinhnang;
    int posstion = 0;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);

        recyclerViewBinhluan = (RecyclerView) findViewById(R.id.recycle_BinhLuan_ChitetQuanAn);
        quanAnModel = getIntent().getParcelableExtra("quanan");
//        Log.d("kiemtraintent",quanAnModel.getTenquanan());
        txtTenQuanAn = (TextView) findViewById(R.id.txtTenQuanAn_chitetQA);
        txtDiachiQuanAn = (TextView) findViewById(R.id.txtTenDiachi_chitetQA);
        txtGioHoatDong = (TextView) findViewById(R.id.txtGioHoatDong_chitietQA);
        txtTrangThai = (TextView) findViewById(R.id.txtTrangThai_chitietQA);
        txtTongCheckin = (TextView) findViewById(R.id.txtSoCheckin_chitetQA);
        txtTongBinhLuan = (TextView) findViewById(R.id.txtSoBinhLuan_chitietQA);
        txtTongAnh = (TextView) findViewById(R.id.txtSoHinhAnh_chitietQA);
        txtTieudeToolbar = (TextView) findViewById(R.id.txtTieudeToolbar);
        imgHinhQuanAn = (ImageView) findViewById(R.id.imgAnhQuaAn_chitetQA);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        txtGioiHanGia = (TextView) findViewById(R.id.txtGioihanGia);
        lnkhungtienich = (LinearLayout) findViewById(R.id.khungtienich);
        txtTenWifi = (TextView) findViewById(R.id.txtTenWifi);
        txtMatkhauWifi = (TextView) findViewById(R.id.txtMatkhauWifi);
        khungWifi = (LinearLayout) findViewById(R.id.khungWifi);
        txtNgaydangWifi = (TextView) findViewById(R.id.txtNgayDangWifi);
        khungtinhnang = (View) findViewById(R.id.khungtinhnang);
        btnBinhluan= (Button) findViewById(R.id.btnBinhLuan);
        mapFragment.getMapAsync(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

//        //Danh sách Chi nhánh quán ăn
//        LinearLayout linearLayout= (LinearLayout)findViewById(R.id.linear);      //find the linear layout
//        linearLayout.removeAllViews();                              //add this too
//        for(int i=0; i<quanAnModel.getChiNhanhQuanAnModelList().size();i++){          //looping to create size textviews
//
//            TextView chiNhanh= new TextView(this);              //dynamically create textview
//            chiNhanh.setLayoutParams(new LinearLayout.LayoutParams(             //select linearlayoutparam- set the width & height
//                    ViewGroup.LayoutParams.MATCH_PARENT, 48));
//            chiNhanh.setGravity(Gravity.CENTER_VERTICAL);                       //set the gravity too
//            chiNhanh.setText(i+1+": "+quanAnModel.getChiNhanhQuanAnModelList().get(i).getDiachi());//adding text
//            chiNhanh.setPadding(0,2,0,2);
//            linearLayout.addView(chiNhanh);                                     //inflating :)
//        }
//        Chi nhánh gần nhất
        chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
        for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()) {
            if (chiNhanhQuanAnModelTam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()) {
                chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                posstion++;
            }

        }
        txtDiachiQuanAn.setText(chiNhanhQuanAnModelTam.getDiachi());
        chitietQuanAnController = new ChitietQuanAnController();
        hienThiChiTietQuanan();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void hienThiChiTietQuanan() {
        //        Log.d("kiemtrabinhluan",quanAnModel.getBinhLuanModelList().size()+"-"+quanAnModel.getBinhLuanModelList().get(0).getThanhVienModel().getHoten());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String giohientai = simpleDateFormat.format(calendar.getTime());
        String gioMoCua = quanAnModel.getGiomocua();
        String gioDongCua = quanAnModel.getGiodongcua();
        try {
            Date dateHienTai = simpleDateFormat.parse(giohientai);
            Date dateMoCua = simpleDateFormat.parse(gioMoCua);
            Date dateDongCua = simpleDateFormat.parse(gioDongCua);
            if (dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)) {
                txtTrangThai.setText(getString(R.string.DangMoCua));
                txtTrangThai.setTextColor(Color.parseColor("#0bbe2c"));
            } else {
                txtTrangThai.setText(getString(R.string.DaDongCua));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        txtTenQuanAn.setText(quanAnModel.getTenquanan());
//        txtDiachiQuanAn.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        Log.d("kiemtraCN", quanAnModel.getChiNhanhQuanAnModelList().size() + "");
        txtGioHoatDong.setText(quanAnModel.getGiomocua() + "-" + quanAnModel.getGiodongcua());
        txtTongAnh.setText(quanAnModel.getHinhanhquanan().size() + "");
        txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        txtTieudeToolbar.setText(quanAnModel.getTenquanan());


        downHinhTienIch();


        if (quanAnModel.getGiatoida() != 0 && quanAnModel.getGiatoithieu() != 0) {
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoithieu = numberFormat.format(quanAnModel.getGiatoithieu()) + "đ";
            String giatoida = numberFormat.format(quanAnModel.getGiatoida()) + "đ";
            txtGioiHanGia.setText(giatoithieu + "-" + giatoida);
        } else {
            txtGioiHanGia.setVisibility(View.INVISIBLE);
        }


        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgHinhQuanAn.setImageBitmap(bitmap);
            }
        });
        //Load danh sách bình luận
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBinhluan.setLayoutManager(layoutManager);
        adapter_binhLuan = new Adapter_BinhLuan(this, quanAnModel.getBinhLuanModelList(), R.layout.custom_layout_binhluan_chitiet);
        recyclerViewBinhluan.setAdapter(adapter_binhLuan);
        adapter_binhLuan.notifyDataSetChanged();
        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollvieChitiet);
        nestedScrollView.smoothScrollTo(0, 0);

        //lấy dữ liệu từ controller wifi
        // cần lấy thằng nào thì truyền nó vào
        chitietQuanAnController.HienThiDanhSachWifiQuanAn(quanAnModel.getMaquanan(), txtTenWifi, txtMatkhauWifi, txtNgaydangWifi);
        khungWifi.setOnClickListener(this);
        khungtinhnang.setOnClickListener(this);
        btnBinhluan.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        double latitude = quanAnModel.getChiNhanhQuanAnModelList().get(posstion).getLatitude();
        Log.d("testkc", posstion + "");
        double longitude = quanAnModel.getChiNhanhQuanAnModelList().get(posstion).getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(quanAnModel.getTenquanan());
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
    }

    private void downHinhTienIch() {

        for (String matienich : quanAnModel.getTienich()) {
            DatabaseReference nodeTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);
                    StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView imageTienIch = new ImageView(ChiTietQuanAn_Activity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                            layoutParams.setMargins(30, 10, 10, 10);
                            imageTienIch.setLayoutParams(layoutParams);
                            imageTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageTienIch.setPadding(5, 5, 5, 5);
                            imageTienIch.setImageBitmap(bitmap);
                            lnkhungtienich.addView(imageTienIch);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.khungWifi:
                Intent iDanhSachWifi = new Intent(ChiTietQuanAn_Activity.this, CapNhatDanhSachWifi.class);
                iDanhSachWifi.putExtra("maquanan", quanAnModel.getMaquanan());
                startActivity(iDanhSachWifi);
                break;
            case R.id.khungtinhnang:
                Intent idangDuong = new Intent(ChiTietQuanAn_Activity.this, DanDuongQuanAn_Activity.class);
                idangDuong.putExtra("latitude",quanAnModel.getChiNhanhQuanAnModelList().get(posstion).getLatitude());
                idangDuong.putExtra("longitude",quanAnModel.getChiNhanhQuanAnModelList().get(posstion).getLongitude());
                idangDuong.putExtra("tenquan",quanAnModel.getTenquanan());
                startActivity(idangDuong);
                break;
            case R.id.btnBinhLuan:
                Intent ibinhLuan = new Intent(ChiTietQuanAn_Activity.this, Binhluan_Actitivty.class);
                ibinhLuan.putExtra("diachi",quanAnModel.getChiNhanhQuanAnModelList().get(posstion).getDiachi());
                ibinhLuan.putExtra("tenquan",quanAnModel.getTenquanan());
                startActivity(ibinhLuan);

        }
    }
}
