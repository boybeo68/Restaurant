package vn.myclass.restaurant.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
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
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.myclass.restaurant.Adapter.Adapter_BinhLuan;
import vn.myclass.restaurant.Controller.ChitietQuanAnController;
import vn.myclass.restaurant.Controller.ThucDonController;
import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.Model.ThanhVienModel;
import vn.myclass.restaurant.Model.TienIchModel;
import vn.myclass.restaurant.R;

public class ChiTietQuanAn_Activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    QuanAnModel quanAnModel;
    TextView txtTenQuanAn, txtDiachiQuanAn, txtGioHoatDong, txtTrangThai, txtTongChiNhanh, txtTongBinhLuan, txtTongAnh;
    TextView txtTieudeToolbar, txtGioiHanGia, txtTenWifi, txtMatkhauWifi, txtNgaydangWifi,txtChamDiem_chitietQA,txtTongTien;
    ImageView imgHinhQuanAn, imgPlay;
    Button btnBinhluan,btnLuuLai;
    Toolbar toolbar;
    RecyclerView recyclerViewBinhluan;
    Adapter_BinhLuan adapter_binhLuan;
    GoogleMap googleMap;
    MapFragment mapFragment;
    ChiNhanhQuanAnModel chiNhanhQuanAnModelTam, chiNhanhQuanAnModel;
    LinearLayout lnkhungtienich, khungWifi;
    ChitietQuanAnController chitietQuanAnController;
    ThucDonController thucDonController;
    View khungtinhnang;
    int posstion = 0;
    VideoView videoView;
    RecyclerView recyclerThucdon;
    List<String>listQuanLuu;
    SharedPreferences sharedPreferences;
    String mauser;
    int tongtien;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);

        recyclerViewBinhluan = (RecyclerView) findViewById(R.id.recycle_BinhLuan_ChitetQuanAn);
        quanAnModel = getIntent().getParcelableExtra("quanan");

        sharedPreferences=getSharedPreferences("luudangnhap",MODE_PRIVATE);
        mauser=sharedPreferences.getString("mauser","");
//        Log.d("kiemtraintent",quanAnModel.getTenquanan());
        txtTenQuanAn = (TextView) findViewById(R.id.txtTenQuanAn_chitetQA);
        txtDiachiQuanAn = (TextView) findViewById(R.id.txtTenDiachi_chitetQA);
        txtGioHoatDong = (TextView) findViewById(R.id.txtGioHoatDong_chitietQA);
        txtTrangThai = (TextView) findViewById(R.id.txtTrangThai_chitietQA);
        txtTongChiNhanh = (TextView) findViewById(R.id.txtSoChiNhanh);
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
        btnBinhluan = (Button) findViewById(R.id.btnBinhLuan);
        videoView = (VideoView) findViewById(R.id.videoTrailer);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        txtChamDiem_chitietQA=findViewById(R.id.txtChamDiem_chitietQA);
        txtTongTien=findViewById(R.id.txtTongTien);
        btnLuuLai=findViewById(R.id.btnLuulai);
        recyclerThucdon = (RecyclerView) findViewById(R.id.recyclerThucDon);
        mapFragment.getMapAsync(this);
        listQuanLuu=new ArrayList<>();

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
        txtTongChiNhanh.setText(quanAnModel.getChiNhanhQuanAnModelList().size()+"");
        chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
        for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()) {
            if (chiNhanhQuanAnModelTam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()) {
                chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                posstion++;
            }

        }

        txtDiachiQuanAn.setText(chiNhanhQuanAnModelTam.getDiachi());
        chitietQuanAnController = new ChitietQuanAnController();
        thucDonController = new ThucDonController();
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

        if (quanAnModel.getVideogioithieu() != null) {
            if (!quanAnModel.getVideogioithieu().equals("")){
                videoView.setVisibility(View.VISIBLE);
                imgPlay.setVisibility(View.VISIBLE);
                imgHinhQuanAn.setVisibility(View.GONE);
                StorageReference storageVideo = FirebaseStorage.getInstance().getReference().child("video").child(quanAnModel.getVideogioithieu());
                storageVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        videoView.setVideoURI(uri);
                        videoView.seekTo(1000);

                    }
                });
            }

        } else {
            videoView.setVisibility(View.GONE);
            imgPlay.setVisibility(View.GONE);
            imgHinhQuanAn.setVisibility(View.VISIBLE);
        }
        //Load danh sách bình luận

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerViewBinhluan.setLayoutManager(layoutManager);
//        adapter_binhLuan = new Adapter_BinhLuan(this, quanAnModel.getBinhLuanModelList(), R.layout.custom_layout_binhluan_chitiet);
//        recyclerViewBinhluan.setAdapter(adapter_binhLuan);
//        adapter_binhLuan.notifyDataSetChanged();
//
//        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollvieChitiet);
//        nestedScrollView.smoothScrollTo(0, 0);

        //lấy dữ liệu từ controller wifi
        // cần lấy thằng nào thì truyền nó vào
        chitietQuanAnController.HienThiDanhSachWifiQuanAn(quanAnModel.getMaquanan(), txtTenWifi, txtMatkhauWifi, txtNgaydangWifi);

        thucDonController.getDanhSachThucDonQuanan(this, quanAnModel.getMaquanan(), recyclerThucdon,false,txtTongTien);
        tongtien=getIntent().getIntExtra("tongtien",0);

        khungWifi.setOnClickListener(this);
        khungtinhnang.setOnClickListener(this);
        btnBinhluan.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        btnLuuLai.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference noderoot=FirebaseDatabase.getInstance().getReference();
        noderoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ThanhVienModel thanhVienModel2 = dataSnapshot.child("thanhviens").child(mauser).getValue(ThanhVienModel.class);
                if (thanhVienModel2.getMaquanluu()!=null){
                    listQuanLuu.clear();
                    for (String maquan:thanhVienModel2.getMaquanluu()){
                        listQuanLuu.add(maquan);
                    }
                }

                Log.d("kiemtra15clist",listQuanLuu.size()+"");
                DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
//                Log.d("kiemtrabinhluan2", snapshotBinhLuan + "");
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
//                Log.d("kiemtrabinhluan4", quanAnModel.getBinhLuanModelList().size() + "");
                if(quanAnModel.getBinhLuanModelList().size() > 0){
                    txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
                    double tongdiemquanan=0;
                    //tổng điểm trung bình của bình luận và tổng số hình bình luận
                    for (BinhLuanModel binhLuanModel1:quanAnModel.getBinhLuanModelList()){
                        tongdiemquanan+=binhLuanModel1.getChamdiem();
                    }
                    double diemtrungbinhquanan=tongdiemquanan/(quanAnModel.getBinhLuanModelList().size());
                    txtChamDiem_chitietQA.setText(String.format("%.1f",diemtrungbinhquanan));
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChiTietQuanAn_Activity.this);
                recyclerViewBinhluan.setLayoutManager(layoutManager);
                adapter_binhLuan = new Adapter_BinhLuan(ChiTietQuanAn_Activity.this, quanAnModel.getBinhLuanModelList(), R.layout.custom_layout_binhluan_chitiet);
                recyclerViewBinhluan.setAdapter(adapter_binhLuan);
                adapter_binhLuan.notifyDataSetChanged();

                NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollvieChitiet);
                nestedScrollView.smoothScrollTo(0, 0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
        if (quanAnModel.getTienich() != null) {
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
        } else {
            lnkhungtienich.setVisibility(View.INVISIBLE);
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
                idangDuong.putExtra("latitude", quanAnModel.getChiNhanhQuanAnModelList().get(posstion).getLatitude());
                idangDuong.putExtra("longitude", quanAnModel.getChiNhanhQuanAnModelList().get(posstion).getLongitude());
                idangDuong.putExtra("tenquan", quanAnModel.getTenquanan());
                startActivity(idangDuong);
                break;
            case R.id.btnBinhLuan:
                Intent ibinhLuan = new Intent(ChiTietQuanAn_Activity.this, Binhluan_Actitivty.class);
                ibinhLuan.putExtra("diachi", quanAnModel.getChiNhanhQuanAnModelList().get(posstion).getDiachi());
                ibinhLuan.putExtra("tenquan", quanAnModel.getTenquanan());
                ibinhLuan.putExtra("maquan", quanAnModel.getMaquanan());
                startActivity(ibinhLuan);
            case R.id.imgPlay:
                videoView.start();
                MediaController mediaController = new MediaController(this);
                videoView.setMediaController(mediaController);
                imgPlay.setVisibility(View.GONE);
                break;
            case R.id.btnLuulai:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.create();
                alertBuilder.setMessage(R.string.LuuQuan);
                alertBuilder.setPositiveButton(R.string.DongY, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!listQuanLuu.contains(quanAnModel.getMaquanan())){
                            listQuanLuu.add(quanAnModel.getMaquanan());
                            FirebaseDatabase.getInstance().getReference().child("thanhviens").child(mauser).child("maquanluu").setValue(listQuanLuu).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ChiTietQuanAn_Activity.this, R.string.success, Toast.LENGTH_SHORT).show();

                                }
                            });
                        }else if(listQuanLuu.contains(quanAnModel.getMaquanan())) {
                            Toast.makeText(ChiTietQuanAn_Activity.this, R.string.QuanDaLuu, Toast.LENGTH_SHORT).show();
                        }
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                }).setNegativeButton(R.string.KhongDongY, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ChiTietQuanAn_Activity.this, "không Đồng ý", Toast.LENGTH_SHORT).show();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
                alertBuilder.show();
                break;
        }
    }
}
