package vn.myclass.restaurant.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.myclass.restaurant.Adapter.Adapter_ChonHinhBinhLuan;
import vn.myclass.restaurant.Adapter.Adapter_HienThiHinhDuocChon;
import vn.myclass.restaurant.Controller.BinhLuanController;
import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.Model.ImagesNicer;
import vn.myclass.restaurant.R;

public class Binhluan_Actitivty extends AppCompatActivity implements View.OnClickListener {

    final int RESULT_IMG2 = 212;
    final int RESULT_IMG3 = 213;
    final int RESULT_IMG4 = 214;
    final int RESULT_IMG5 = 215;
    final int RESULT_IMG6 = 216;
    final int RESULT_CHUPHINH=222;
    TextView txtTenQuanan,txtDiaChiQuanan,txtDang;
    EditText edtTieudebinhluan,edtNoidungbinhluan,edtChamDiemBinhLuan;
    RecyclerView recyclerChonHinhBinhLuan;
    ImageButton imgChonHinhAnh,imgChupHinh;
    Toolbar toolbar;
    List<Bitmap>hinhDaChupList;
    List<Bitmap>hinhQuanAnBitMap;
    Adapter_HienThiHinhDuocChon adapter_hienThiHinhDuocChon;
    String maquan;
    BinhLuanModel binhLuanModel;
    SharedPreferences sharedPreferences;
    BinhLuanController binhLuanController;
    ImageView imgTam,imgHinQuan2,imgHinQuan3,imgHinQuan4,imgHinQuan5,imgHinQuan6;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_binhluan);

        String tenquanan=getIntent().getStringExtra("tenquan");
        String diachi=getIntent().getStringExtra("diachi");
        maquan=getIntent().getStringExtra("maquan");
        Log.d("kiemtra",diachi);

        addConTroll();
        hinhQuanAnBitMap=new ArrayList<>();
        hinhDaChupList=new ArrayList<>();
        txtTenQuanan.setText(tenquanan);
        txtDiaChiQuanan.setText(diachi);
        sharedPreferences=getSharedPreferences("luudangnhap",MODE_PRIVATE);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        imgChonHinhAnh.setOnClickListener(this);
        imgChupHinh.setOnClickListener(this);
        txtDang.setOnClickListener(this);
        imgHinQuan2.setOnClickListener(this);
        imgHinQuan3.setOnClickListener(this);
        imgHinQuan4.setOnClickListener(this);
        imgHinQuan5.setOnClickListener(this);
        imgHinQuan6.setOnClickListener(this);
        binhLuanController=new BinhLuanController(this);
        edtChamDiemBinhLuan.setFilters(new InputFilter[]{new InputFilterMinMax(0, 10)});

    }
    private void addConTroll() {
        txtTenQuanan= (TextView) findViewById(R.id.txtTenQuanAn);
        txtDiaChiQuanan= (TextView) findViewById(R.id.txtDiachiquanan);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        imgChonHinhAnh= (ImageButton) findViewById(R.id.btnChonHinh);
        recyclerChonHinhBinhLuan= (RecyclerView) findViewById(R.id.recyclerChonHinhBinhLuan);
        txtDang= (TextView) findViewById(R.id.txtDang);
        edtTieudebinhluan= (EditText) findViewById(R.id.edtTieudeBinhluan);
        edtNoidungbinhluan= (EditText) findViewById(R.id.edtNoiDungBinhluan);
        imgChupHinh= (ImageButton) findViewById(R.id.imgChupHinh);
        edtChamDiemBinhLuan= (EditText) findViewById(R.id.edtChamDiemBL);
        imgHinQuan2= (ImageView) findViewById(R.id.imgHinhQuan2);
        imgHinQuan3= (ImageView) findViewById(R.id.imgHinhQuan3);
        imgHinQuan4= (ImageView) findViewById(R.id.imgHinhQuan4);
        imgHinQuan5= (ImageView) findViewById(R.id.imgHinhQuan5);
        imgHinQuan6= (ImageView) findViewById(R.id.imgHinhQuan6);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.txtDang:
                binhLuanModel=new BinhLuanModel();
                String tieude=edtTieudebinhluan.getText().toString();
                String noidung=edtNoidungbinhluan.getText().toString();
                String mauser=sharedPreferences.getString("mauser","");
                double chamdiem=Double.parseDouble(edtChamDiemBinhLuan.getText().toString());
                binhLuanModel.setChamdiem(chamdiem);
                binhLuanModel.setLuotthich(0);
                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setTieude(tieude);
                binhLuanModel.setMauser(mauser);
                binhLuanController.themBinhLuan(this,maquan,binhLuanModel,hinhQuanAnBitMap);
//                finish();
                ProgressDialog progressDialog=ProgressDialog.show(this,getString(R.string.ThongBao), getString(R.string.TaiHinh), false, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                });
                progressDialog.show();
                break;
            case R.id.imgChupHinh:
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,RESULT_CHUPHINH);
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
        }

    }
    private void chonHinhtuGalaxy(int requestcode){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Chọn hình ..."),requestcode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_CHUPHINH:
                Bitmap bitmapchuphinh= (Bitmap) data.getExtras().get("data");
                hinhDaChupList.add(bitmapchuphinh);
                hinhQuanAnBitMap.add(bitmapchuphinh);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
                adapter_hienThiHinhDuocChon=new Adapter_HienThiHinhDuocChon(this,R.layout.custom_layouthinhduocchon,hinhDaChupList);
                recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);
                recyclerChonHinhBinhLuan.setAdapter(adapter_hienThiHinhDuocChon);
                adapter_hienThiHinhDuocChon.notifyDataSetChanged();
                Log.d("kiemtrachuphinh",hinhDaChupList.size()+"");
                break;
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
        }
    }
    public class InputFilterMinMax implements InputFilter {
        private int min;
        private int max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //noinspection EmptyCatchBlock
            try {
                int input = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}

