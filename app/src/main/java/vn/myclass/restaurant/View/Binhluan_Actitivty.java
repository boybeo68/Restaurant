package vn.myclass.restaurant.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.myclass.restaurant.Adapter.Adapter_ChonHinhBinhLuan;
import vn.myclass.restaurant.Adapter.Adapter_HienThiHinhDuocChon;
import vn.myclass.restaurant.Controller.BinhLuanController;
import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.R;

public class Binhluan_Actitivty extends AppCompatActivity implements View.OnClickListener {

    TextView txtTenQuanan,txtDiaChiQuanan,txtDang;
    EditText edtTieudebinhluan,edtNoidungbinhluan;
    RecyclerView recyclerChonHinhBinhLuan;
    ImageButton imgChonHinhAnh;
    Toolbar toolbar;
    final int REQUEST_CHONHINHBINHLUAN=11;
    List<String>listhinhduocchon;
    Adapter_HienThiHinhDuocChon adapter_hienThiHinhDuocChon;
    String maquan;
    BinhLuanModel binhLuanModel;
    SharedPreferences sharedPreferences;
    BinhLuanController binhLuanController;
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
        txtTenQuanan.setText(tenquanan);
        txtDiaChiQuanan.setText(diachi);
        sharedPreferences=getSharedPreferences("luudangnhap",MODE_PRIVATE);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        imgChonHinhAnh.setOnClickListener(this);
        txtDang.setOnClickListener(this);
        binhLuanController=new BinhLuanController();
        listhinhduocchon=new ArrayList<>();
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
            case R.id.btnChonHinh:
                Intent ichonhinh=new Intent(Binhluan_Actitivty.this,ChonHinhBinhLuan_Activity.class);
                startActivityForResult(ichonhinh,REQUEST_CHONHINHBINHLUAN);
                break;
            case R.id.txtDang:
                binhLuanModel=new BinhLuanModel();
                String tieude=edtTieudebinhluan.getText().toString();
                String noidung=edtNoidungbinhluan.getText().toString();
                String mauser=sharedPreferences.getString("mauser","");
                binhLuanModel.setChamdiem(0);
                binhLuanModel.setLuotthich(0);
                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setTieude(tieude);
                binhLuanModel.setMauser(mauser);
                binhLuanController.themBinhLuan(maquan,binhLuanModel,listhinhduocchon);
                finish();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CHONHINHBINHLUAN){
            if (resultCode==RESULT_OK){
                listhinhduocchon=data.getStringArrayListExtra("listhinhduocchon");
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
                adapter_hienThiHinhDuocChon=new Adapter_HienThiHinhDuocChon(this,R.layout.custom_layouthinhduocchon,listhinhduocchon);
                recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);
                recyclerChonHinhBinhLuan.setAdapter(adapter_hienThiHinhDuocChon);
                adapter_hienThiHinhDuocChon.notifyDataSetChanged();
            }
        }
    }
}

