package vn.myclass.restaurant.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.myclass.restaurant.Adapter.Adapter_Chinhanh_tontai;
import vn.myclass.restaurant.Adapter.Adapter_HienThiHinhDuocChon;
import vn.myclass.restaurant.Adapter.Adapter_HinhQuan;
import vn.myclass.restaurant.Controller.ThucDonController;
import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.ImagesNicer;
import vn.myclass.restaurant.Model.MonanModel;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.Model.ThemThucDonModel;
import vn.myclass.restaurant.Model.ThucDonModel;
import vn.myclass.restaurant.Model.TienIchModel;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.Fragments.ThemQuanAn_Fragment;

public class SuaQuanAn_Activity extends AppCompatActivity implements View.OnClickListener {
    final int RESULT_IMG2 = 112;
    final int RESULT_IMG3 = 113;
    final int RESULT_IMG4 = 114;
    final int RESULT_IMG5 = 115;
    final int RESULT_IMG6 = 116;
    final int RESULT_IMGTHUCDON = 117;
    final int RESULT_VIDEO = 200;
    FrameLayout frameVideo;
    Button btnGioMoCua, btnGioDongCua,btnThemQuanAn;
    RadioGroup rdgTrangThai,rdVideo;
    EditText edtGiaToiDa,edtGiaToiThieu;
    String gioMoCua, gioDongCua,khuVuc;
    Spinner spinnerKhuVuc;
    List<ThucDonModel> thucDonModelList;
    List<String> khuVucList;
    List<String> thucDonList;
    List<TienIchModel> tienIchModelList;
    List<String>selecttienIchList;
    List<String>chinhanhList;
    List<ChiNhanhQuanAnModel>chinhanhListTontai;
    List<ThemThucDonModel>themThucDonModelList;
    List<Bitmap>hinhDaChupList;
    List<Uri>hinhQuanAn;
    List<Bitmap>hinhQuanAnBitMap;
    List<String>listMaQuanThanhVien;
    List<Bitmap>bitmapList;
    Uri videoSelect;
    ArrayAdapter<String> adapterKhuvuc;
    ImageView imgTam,imgHinQuan2,imgHinQuan3,imgHinQuan4,imgHinQuan5,imgHinQuan6,imgVideo;
    LinearLayout khungtienich,khungChiNhanh,khungChuaChiNhanh,khungchuaThucDon,lnTongtien;
    VideoView videoView;
    String maQuanAn,mauser;
    QuanAnModel quanAnModel;
    MonanModel monanModel;
    SharedPreferences sharedPreferences;
    TextView txtTenQuanAn,txtTieudetoolbar;
    Toolbar toolbar;
    RecyclerView recyclerViewkhungChiNhanhtontai;
    Bitmap converetdImage;
    ThucDonController thucDonController;
    RecyclerView recyclerThucdon,recyclerViewHinhQuan;
    List<Bitmap>listKiemtra;
    TextView txtTongtien;
    final DatabaseReference nodeRoot= FirebaseDatabase.getInstance().getReference();
    ChiNhanhQuanAnModel chiNhanhQuanAnModel=new ChiNhanhQuanAnModel();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sua_quan_an);
        quanAnModel = getIntent().getParcelableExtra("quanan");
        btnGioMoCua = (Button) findViewById(R.id.btnGioMoCua);
        btnGioDongCua = (Button) findViewById(R.id.btnGioDongCua);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        khungtienich= (LinearLayout) findViewById(R.id.khungTienTich);
        khungChiNhanh= (LinearLayout) findViewById(R.id.khungChiNhanh);
        khungChuaChiNhanh= (LinearLayout) findViewById(R.id.khungChuaChiNhanh);
        khungchuaThucDon= (LinearLayout) findViewById(R.id.khungChuaThucDon);
        recyclerViewkhungChiNhanhtontai=findViewById(R.id.khungChiNhanhTontai);
        imgHinQuan2= (ImageView) findViewById(R.id.imgHinhQuan2);
        imgHinQuan3= (ImageView) findViewById(R.id.imgHinhQuan3);
        imgHinQuan4= (ImageView) findViewById(R.id.imgHinhQuan4);
        imgHinQuan5= (ImageView) findViewById(R.id.imgHinhQuan5);
        imgHinQuan6= (ImageView) findViewById(R.id.imgHinhQuan6);
        videoView= (VideoView) findViewById(R.id.videoView);
        imgVideo= (ImageView) findViewById(R.id.imgvideo);
        btnThemQuanAn= (Button) findViewById(R.id.btnThemQuanAn);
        rdgTrangThai= (RadioGroup) findViewById(R.id.rdTrangThai);
        edtGiaToiDa= (EditText) findViewById(R.id.edGiaToiDa);
        edtGiaToiThieu= (EditText) findViewById(R.id.edGiaToiThieu);
        recyclerViewHinhQuan=findViewById(R.id.recyclerHinhQuan);
        txtTenQuanAn=findViewById(R.id.edTenQuan);
        txtTongtien=findViewById(R.id.txtTongTien);
        rdVideo=findViewById(R.id.rdgVideo);
        frameVideo=findViewById(R.id.frameVideo);
        txtTieudetoolbar=findViewById(R.id.txtTieudeToolbar);
        recyclerThucdon = (RecyclerView) findViewById(R.id.recyclerThucDon);
        lnTongtien=findViewById(R.id.lnTongtien);
        thucDonController = new ThucDonController();
        lnTongtien.setVisibility(View.GONE);
        thucDonController.getDanhSachThucDonQuanan(this, quanAnModel.getMaquanan(), recyclerThucdon,true,txtTongtien);

        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        edtGiaToiDa.setText(quanAnModel.getGiatoida()+"");
        edtGiaToiThieu.setText(quanAnModel.getGiatoithieu()+"");
        btnGioMoCua.setText(quanAnModel.getGiomocua());
        btnGioDongCua.setText(quanAnModel.getGiodongcua());
        maQuanAn=quanAnModel.getMaquanan();



        sharedPreferences=this.getSharedPreferences("luudangnhap", Context.MODE_PRIVATE);
        mauser=sharedPreferences.getString("mauser","");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtTieudetoolbar.setText(R.string.SuaQuanAn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        laydanhsachChinhanhTontai();
        cloneChiNhanh();


        thucDonModelList = new ArrayList<>();
        khuVucList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        tienIchModelList = new ArrayList<>();
        selecttienIchList=new ArrayList<>();
        chinhanhList=new ArrayList<>();
        themThucDonModelList=new ArrayList<>();
        hinhDaChupList=new ArrayList<>();
        hinhQuanAn=new ArrayList<>();
        hinhQuanAnBitMap=new ArrayList<>();
        listMaQuanThanhVien=new ArrayList<>();
        listKiemtra=new ArrayList<>();
        chinhanhListTontai=new ArrayList<>();
        bitmapList=new ArrayList<>();

        rdVideo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = rdVideo.findViewById(i);
                int index = rdVideo.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button
//                        Toast.makeText(getContext(), "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        frameVideo.setVisibility(View.VISIBLE);
                        break;
                    case 1: // secondbutton
//                        Toast.makeText(getContext(), "Selected button number " + index, Toast.LENGTH_SHORT).show();
                        frameVideo.setVisibility(View.GONE);
                        break;
                }
            }
        });

        layDanhSachTienIch();
        cloneThucDon();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SuaQuanAn_Activity.this,LinearLayoutManager.HORIZONTAL,false);
        Adapter_HinhQuan adapter_hinhQuan=new Adapter_HinhQuan(SuaQuanAn_Activity.this,R.layout.custom_layouthinhduocchon,quanAnModel.getMaquanan(),quanAnModel.getHinhanhquanan());
        recyclerViewHinhQuan.setLayoutManager(layoutManager);
        recyclerViewHinhQuan.setAdapter(adapter_hinhQuan);
        adapter_hinhQuan.notifyDataSetChanged();
//        for (String linkhinh : quanAnModel.getHinhanhquanan()){
//            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
//            long ONE_MEGABYTE = 1024 * 1024;
//            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                @Override
//                public void onSuccess(byte[] bytes) {
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                    Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 300);
//                    bitmapList.add(converetdImage);
//                    if (bitmapList.size()==quanAnModel.getHinhanhquanan().size()){
//                        Log.d("kiemtra19",bitmapList.size()+"");
//                        Log.d("kiemtra19",quanAnModel.getHinhanhquanan()+"");
//                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SuaQuanAn_Activity.this,LinearLayoutManager.HORIZONTAL,false);
//                        Adapter_HinhQuan adapter_hinhQuan=new Adapter_HinhQuan(SuaQuanAn_Activity.this,R.layout.custom_layouthinhduocchon,bitmapList,quanAnModel.getMaquanan(),quanAnModel.getHinhanhquanan());
//                        recyclerViewHinhQuan.setLayoutManager(layoutManager);
//                        recyclerViewHinhQuan.setAdapter(adapter_hinhQuan);
//                        adapter_hinhQuan.notifyDataSetChanged();
//                    }
//                }
//            });
//        }


        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        imgHinQuan2.setOnClickListener(this);
        imgHinQuan3.setOnClickListener(this);
        imgHinQuan4.setOnClickListener(this);
        imgHinQuan5.setOnClickListener(this);
        imgHinQuan6.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        btnThemQuanAn.setOnClickListener(this);
    }

    private void cloneThucDon() {
        final View view2=LayoutInflater.from(this).inflate(R.layout.layout_clone_thucdon,null);
        final Spinner spinnerThucDon = (Spinner) view2.findViewById(R.id.spinnerThucDon);
        Button btnThemThucDon = (Button) view2.findViewById(R.id.btnThemThucDon);
        final ImageButton imgXoaThucDon= (ImageButton) view2.findViewById(R.id.imgXoaThucDon);
        final EditText edtTenMon= (EditText) view2.findViewById(R.id.edtTenMon);
        final EditText edtGiaTien= (EditText) view2.findViewById(R.id.edtGiaTien);
        ImageView imgChupHinh= (ImageView) view2.findViewById(R.id.imgChupHinh);
        imgTam=imgChupHinh;
        //spinner
        ArrayAdapter<String> adapterThucDon = new ArrayAdapter<String>(SuaQuanAn_Activity.this, android.R.layout.simple_list_item_1, thucDonList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();
        if (thucDonList.size()==0){
            layDanhSachThucDon(adapterThucDon);
        }
        imgChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,RESULT_IMGTHUCDON);
            }
        });

        btnThemThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTenMon.getText().toString().trim().length() == 0) {
                    Toast.makeText(SuaQuanAn_Activity.this, R.string.NhapTenMon, Toast.LENGTH_SHORT).show();
                }else if(edtGiaTien.getText().toString().trim().length()==0){
                    Toast.makeText(SuaQuanAn_Activity.this, R.string.NhapGiatien, Toast.LENGTH_SHORT).show();
                }else {
                    v.setVisibility(View.GONE);
                    imgXoaThucDon.setVisibility(View.VISIBLE);
                    long thoigian= Calendar.getInstance().getTimeInMillis();
                    String tenHinh=String.valueOf(thoigian)+".jpg";
                    int position=spinnerThucDon.getSelectedItemPosition();
                    String mathucDon=thucDonModelList.get(position).getMathucdon();
                    monanModel=new MonanModel();
                    monanModel.setTenmon(edtTenMon.getText().toString());
                    monanModel.setGiatien(Long.parseLong(edtGiaTien.getText().toString()));
                    monanModel.setHinhanh(tenHinh);
                    ThemThucDonModel themThucDonModel=new ThemThucDonModel();
                    themThucDonModel.setMathucdon(mathucDon);
                    themThucDonModel.setMonanModel(monanModel);
                    imgXoaThucDon.setTag(themThucDonModel);
                    themThucDonModelList.add(themThucDonModel);
                    Log.d("kiemtralistthucdon",themThucDonModelList.size()+"");

                    cloneThucDon();
                }


            }
        });
        imgXoaThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position=spinnerThucDon.getSelectedItemPosition();
//                String mathucDon=thucDonModelList.get(position).getMathucdon();
//                ThemThucDonModel themThucDonModel=new ThemThucDonModel();
//                themThucDonModel.setMathucdon(mathucDon);
//                themThucDonModel.setMonanModel(monanModel);
                ThemThucDonModel thucDonModel1= (ThemThucDonModel) v.getTag();
                themThucDonModelList.remove(thucDonModel1);
                Log.d("kiemtralistthucdon",themThucDonModelList.size()+"");

                khungchuaThucDon.removeView(view2);

            }
        });
        khungchuaThucDon.addView(view2);
    }

    private void layDanhSachTienIch() {
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot valueTienIch : dataSnapshot.getChildren()) {
                    String maTienIch = valueTienIch.getKey();
                    TienIchModel tienIchModel = valueTienIch.getValue(TienIchModel.class);
                    tienIchModel.setMaTienIch(maTienIch);
                    CheckBox checkBox=new CheckBox(SuaQuanAn_Activity.this);
                    checkBox.setText(tienIchModel.getTentienich());
                    int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
                    checkBox.setButtonDrawable(id);
                    checkBox.setTag(maTienIch);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String matienich=buttonView.getTag().toString();
                            if (isChecked){
                                selecttienIchList.add(matienich);
                            }else {
                                selecttienIchList.remove(matienich);
                            }
                        }
                    });
                    khungtienich.addView(checkBox);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void layDanhSachThucDon(final ArrayAdapter<String> adapterThucDon) {
        FirebaseDatabase.getInstance().getReference().child("thucdons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot valueThucDon : dataSnapshot.getChildren()) {
                    ThucDonModel thucDonModel = new ThucDonModel();
                    thucDonModel.setMathucdon(valueThucDon.getKey());
                    thucDonModel.setTenthucdon(valueThucDon.getValue(String.class));
                    thucDonModelList.add(thucDonModel);
                    thucDonList.add(thucDonModel.getTenthucdon());
                }
                adapterThucDon.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void chonHinhtuGalaxy(int requestcode){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Chọn hình ..."),requestcode);
    }
    class DownloadToaDo extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray results=jsonObject.getJSONArray("results");
                for (int i=0;i<results.length();i++){
                    JSONObject object=results.getJSONObject(i);
                    String address = object.getString("formatted_address");
                    JSONObject geometry=object.getJSONObject("geometry");
                    JSONObject location=geometry.getJSONObject("location");
                    double latitude= (double) location.get("lat");
                    double longitude= (double) location.get("lng");

                    ChiNhanhQuanAnModel chiNhanhQuanAnModel = new ChiNhanhQuanAnModel();
                    chiNhanhQuanAnModel.setDiachi(address);
                    chiNhanhQuanAnModel.setLatitude(latitude);
                    chiNhanhQuanAnModel.setLongitude(longitude);
                    FirebaseDatabase.getInstance().getReference().child("chinhanhquanans").child(maQuanAn).push().setValue(chiNhanhQuanAnModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void cloneChiNhanh(){
        final View view1= LayoutInflater.from(this).inflate(R.layout.layout_clone_chinhanh,null);

        final ImageButton imgThemChiNhanh= (ImageButton) view1.findViewById(R.id.imgThemChiNhanh);
        final ImageButton imgXoaChiNhanh= (ImageButton) view1.findViewById(R.id.imgXoaChiNhanh);
        imgThemChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtTenChiNhanh= (EditText) view1.findViewById(R.id.edtTenChiNhanh);
                String tenchinhanh=edtTenChiNhanh.getText().toString();

                //tạo 1 view mới để add
                v.setVisibility(View.GONE);
                imgXoaChiNhanh.setVisibility(View.VISIBLE);
                imgXoaChiNhanh.setTag(tenchinhanh);

                chinhanhList.add(tenchinhanh);
                edtTenChiNhanh.setFocusable(false);

                Log.d("kiemtraList",chinhanhList.size()+"");

                cloneChiNhanh();
            }
        });
        imgXoaChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenchinhanh=v.getTag().toString();
                chinhanhList.remove(tenchinhanh);
                khungChuaChiNhanh.removeView(view1);
                Log.d("kiemtraList",chinhanhList.size()+"");
            }
        });
        khungChuaChiNhanh.addView(view1);
    }
    private void laydanhsachChinhanhTontai(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SuaQuanAn_Activity.this);
        recyclerViewkhungChiNhanhtontai.setLayoutManager(layoutManager);
        Adapter_Chinhanh_tontai adapterChinhanhTontai=new Adapter_Chinhanh_tontai(SuaQuanAn_Activity.this,quanAnModel.getChiNhanhQuanAnModelList(),R.layout.layout_clone_chinhanh_datontai,maQuanAn);
        recyclerViewkhungChiNhanhtontai.setAdapter(adapterChinhanhTontai);
        adapterChinhanhTontai.notifyDataSetChanged();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_IMG2:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 300);
                        imgHinQuan2.setImageBitmap(converetdImage);
                        hinhQuanAnBitMap.add(bitmap);
                        Log.d("kiemtrabit",hinhQuanAnBitMap.size()+"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RESULT_IMG3:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 300);
                        imgHinQuan3.setImageBitmap(converetdImage);
                        hinhQuanAnBitMap.add(bitmap);
                        Log.d("kiemtrabit",hinhQuanAnBitMap.size()+"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RESULT_IMG4:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 300);
                        imgHinQuan4.setImageBitmap(converetdImage);
                        hinhQuanAnBitMap.add(bitmap);
                        Log.d("kiemtrabit",hinhQuanAnBitMap.size()+"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RESULT_IMG5:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 300);
                        imgHinQuan5.setImageBitmap(converetdImage);
                        hinhQuanAnBitMap.add(bitmap);
                        Log.d("kiemtrabit",hinhQuanAnBitMap.size()+"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RESULT_IMG6:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 300);
                        imgHinQuan6.setImageBitmap(converetdImage);
                        hinhQuanAnBitMap.add(bitmap);
                        Log.d("kiemtrabit",hinhQuanAnBitMap.size()+"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RESULT_IMGTHUCDON:
                Bitmap bitmap= (Bitmap) data.getExtras().get("data");
                imgTam.setImageBitmap(bitmap);
                hinhDaChupList.add(bitmap);

                break;
            case RESULT_VIDEO:
                if (RESULT_OK==resultCode){
                    imgVideo.setVisibility(View.GONE);
                    MediaController mediaController=new MediaController(SuaQuanAn_Activity.this);
                    videoView.setMediaController(mediaController);
                    Uri uri=data.getData();
                    videoSelect=uri;
                    Log.d("kiemtravideo",videoSelect+"");
                    videoView.setVideoURI(uri);
                    videoView.start();
                }
        }

    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        switch (v.getId()) {
            case R.id.btnGioMoCua:
                TimePickerDialog timePickerDialog = new TimePickerDialog(SuaQuanAn_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioMoCua = hourOfDay + ":" + minute;
                        btnGioMoCua.setText(gioMoCua);
                    }
                }, gio, phut, true);
                timePickerDialog.show();
                break;
            case R.id.btnGioDongCua:
                TimePickerDialog DongPickerDialog = new TimePickerDialog(SuaQuanAn_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioDongCua = hourOfDay + ":" + minute;
                        btnGioDongCua.setText(gioDongCua);
                    }
                }, gio, phut, true);
                DongPickerDialog.show();
                break;
            case R.id.imgHinhQuan2:
                chonHinhtuGalaxy(RESULT_IMG2);
                break;
            case R.id.imgHinhQuan3:
                chonHinhtuGalaxy(RESULT_IMG3);
                break;
            case R.id.imgHinhQuan4:
                chonHinhtuGalaxy(RESULT_IMG4);
                break;
            case R.id.imgHinhQuan5:
                chonHinhtuGalaxy(RESULT_IMG5);
                break;
            case R.id.imgHinhQuan6:
                chonHinhtuGalaxy(RESULT_IMG6);
                break;
            case R.id.imgvideo:
                Intent intent=new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chọn video ..."),RESULT_VIDEO);
                break;
            case R.id.btnThemQuanAn:
                ProgressDialog progressDialog=ProgressDialog.show(this,getString(R.string.ThongBao), getString(R.string.ThongBaoSuaQuan), false, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                });
                progressDialog.show();
                suaQuanAn();
                break;
        }
    }

    private void suaQuanAn() {
        long giaToiDa=Long.parseLong(edtGiaToiDa.getText().toString());
        long giaToiThieu=Long.parseLong(edtGiaToiThieu.getText().toString());
        int rdchecked=rdgTrangThai.getCheckedRadioButtonId();
        boolean giaoHang=false;
        if (rdchecked==R.id.rdGiaoHang){
            giaoHang=true;
        }else {
            giaoHang=false;
        }
        DatabaseReference nodeQuanAn=nodeRoot.child("quanans");
        for (String chinhanh :chinhanhList){
            String urlGeoCoding="https://maps.googleapis.com/maps/api/geocode/json?address="+chinhanh.replace(" ","%20")+"&key=AIzaSyAEnCj0g2NfDXdytA0C28A1XLP2is-aLDw";
            DownloadToaDo downloadToaDo=new DownloadToaDo();
            downloadToaDo.execute(urlGeoCoding);
        }

        final QuanAnModel quanAnModelthem=new QuanAnModel();
        quanAnModelthem.setTenquanan(txtTenQuanAn.getText().toString());
        quanAnModelthem.setGiatoida(giaToiDa);
        quanAnModelthem.setGiatoithieu(giaToiThieu);
        quanAnModelthem.setGiaohang(giaoHang);

        quanAnModelthem.setTienich(selecttienIchList);
        quanAnModelthem.setLuotthich(0);
        quanAnModelthem.setGiomocua(btnGioMoCua.getText().toString());
        quanAnModelthem.setGiodongcua(btnGioDongCua.getText().toString());

        if (hinhQuanAnBitMap.size()!=0){
            nodeQuanAn.child(maQuanAn).setValue(quanAnModelthem);
            for (int j=0;j<hinhQuanAnBitMap.size();j++){
                String mahinhanh=nodeRoot.child("hinhanhquanans").child(maQuanAn).push().getKey();
                nodeRoot.child("hinhanhquanans").child(maQuanAn).child(mahinhanh).setValue(mahinhanh+j+".jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = hinhQuanAnBitMap.get(j);
                converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 300);
                converetdImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                FirebaseStorage.getInstance().getReference().child("hinhanh/"+mahinhanh+j+".jpg").putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        listKiemtra.add(converetdImage);
                        if (listKiemtra.size()==hinhQuanAnBitMap.size()){
                            Toast.makeText(SuaQuanAn_Activity.this, R.string.success, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SuaQuanAn_Activity.this,Trangchu_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }else {
            nodeQuanAn.child(maQuanAn).setValue(quanAnModelthem).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(SuaQuanAn_Activity.this, R.string.success, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SuaQuanAn_Activity.this,Trangchu_Activity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        for (int i=0 ;i< themThucDonModelList.size() ; i++){
            nodeRoot.child("thucdonquanans").child(maQuanAn).child(themThucDonModelList.get(i).getMathucdon()).push().setValue(themThucDonModelList.get(i).getMonanModel());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = hinhDaChupList.get(i);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            FirebaseStorage.getInstance().getReference().child("hinhanh/"+themThucDonModelList.get(i).getMonanModel().getHinhanh()).putBytes(data);
        }
    }
}
