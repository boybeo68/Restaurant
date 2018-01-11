package vn.myclass.restaurant.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.myclass.restaurant.Adapter.Adapter_DanhsachWifi;
import vn.myclass.restaurant.Controller.CapnhatWifi_Controller;
import vn.myclass.restaurant.Model.WifiQuanAnModel;
import vn.myclass.restaurant.R;

public class CapNhatDanhSachWifi extends AppCompatActivity implements View.OnClickListener {
    Button btnCapnhatWifi;
    RecyclerView recyclerDanhsachWifi;
    Adapter_DanhsachWifi adapter_danhsachWifi;
    WifiQuanAnModel wifiQuanAnModel;
    CapnhatWifi_Controller capnhatWifi_controller;
    String maquanan;
    Toolbar toolbar;
    TextView txtTieudetoolbar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cap_nhat_danh_sach_wifi);
        btnCapnhatWifi= (Button) findViewById(R.id. btnCapnhatWifi);
        recyclerDanhsachWifi= (RecyclerView) findViewById(R.id.recyclerDanhSachWifi);
        maquanan=getIntent().getStringExtra("maquanan");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTieudetoolbar = findViewById(R.id.txtTieudeToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtTieudetoolbar.setText(R.string.DanhSachWifi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerDanhsachWifi.setLayoutManager(layoutManager);
        capnhatWifi_controller=new CapnhatWifi_Controller(this);
        capnhatWifi_controller.HienThiDanhSachWifi(maquanan,recyclerDanhsachWifi);
        btnCapnhatWifi.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnCapnhatWifi:
                Intent intent=new Intent(CapNhatDanhSachWifi.this,PopupCapNhatWifi.class);
                intent.putExtra("maquanan",maquanan);
                startActivity(intent);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
