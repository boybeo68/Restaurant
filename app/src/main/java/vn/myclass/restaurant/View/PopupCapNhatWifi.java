package vn.myclass.restaurant.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.myclass.restaurant.Controller.CapnhatWifi_Controller;
import vn.myclass.restaurant.Model.WifiQuanAnModel;
import vn.myclass.restaurant.R;

public class PopupCapNhatWifi extends AppCompatActivity implements View.OnClickListener {
    EditText edtNhapTenWifi, edtNhatMatKhauWifi;
    Button btnCapNhatWifi;
    CapnhatWifi_Controller capnhatWifi_controller;
    WifiQuanAnModel wifiQuanAnModel;
    String maquanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_cap_nhat_wifi);
         maquanan=getIntent().getStringExtra("maquanan");
        edtNhapTenWifi = (EditText) findViewById(R.id.edtNhapTenWifi);
        edtNhatMatKhauWifi = (EditText) findViewById(R.id.edtNhapMatkhauWifi);
        btnCapNhatWifi = (Button) findViewById(R.id.btnCapnhatWifipopup);

        btnCapNhatWifi.setOnClickListener(this);
        capnhatWifi_controller = new CapnhatWifi_Controller(this);
    }

    @Override
    public void onClick(View v) {
        String tenWifi = edtNhapTenWifi.getText().toString();
        String matKhauWifi = edtNhatMatKhauWifi.getText().toString();


        if (tenWifi.trim().length() > 0 && matKhauWifi.trim().length() > 0) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngaydang = simpleDateFormat.format(calendar.getTime());

            wifiQuanAnModel = new WifiQuanAnModel();
            wifiQuanAnModel.setTen(tenWifi);
            wifiQuanAnModel.setNgaydang(ngaydang);
            wifiQuanAnModel.setMatkhau(matKhauWifi);
            capnhatWifi_controller.ThemWifiQuanAn(this, wifiQuanAnModel,maquanan);
        } else {
            Toast.makeText(this, getResources().getString(R.string.LoiCapNhatWifi), Toast.LENGTH_SHORT).show();
        }
    }
}
