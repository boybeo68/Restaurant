package vn.myclass.restaurant.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.Adapter.Adapter_ChonHinhBinhLuan;
import vn.myclass.restaurant.Model.ChonHinhBinhLuanModel;
import vn.myclass.restaurant.R;

public class ChonHinhBinhLuan_Activity extends AppCompatActivity implements View.OnClickListener{
    List<ChonHinhBinhLuanModel>listDuongdan;
    List<String>listDuocChon;
    RecyclerView recyclerChonHinhBinhLuan;
    Adapter_ChonHinhBinhLuan adapterChonHinhBinhLuan;
    TextView txtXong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chon_hinh_binh_luan);

        listDuongdan=new ArrayList<>();
        listDuocChon=new ArrayList<>();
        recyclerChonHinhBinhLuan= (RecyclerView) findViewById(R.id.recyclerChonHinhBinhLuan);
        txtXong= (TextView) findViewById(R.id.txtXong);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        adapterChonHinhBinhLuan=new Adapter_ChonHinhBinhLuan(this,R.layout.custom_layout_chonhinhbinhluan,listDuongdan);
        recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);
        recyclerChonHinhBinhLuan.setAdapter(adapterChonHinhBinhLuan);




        int checkPermissionReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (checkPermissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getTatcaHinhAnhTrongMay();
        }
        txtXong.setOnClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getTatcaHinhAnhTrongMay();
            }
        }
    }
    public void getTatcaHinhAnhTrongMay(){
        //duyệt cột data trong máy
        String []projection= {MediaStore.Images.Media.DATA};
        //đường dẫn tất cả hình ảnh trong thẻ nhớ
        Uri uri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";

        Cursor cursor=getContentResolver().query(uri,projection,null,null,orderBy);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String duongdan=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ChonHinhBinhLuanModel chonHinhBinhLuanModel=new ChonHinhBinhLuanModel(duongdan,false);
            listDuongdan.add(chonHinhBinhLuanModel);
            adapterChonHinhBinhLuan.notifyDataSetChanged();
            cursor.moveToNext();
        }

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.txtXong:
                for (ChonHinhBinhLuanModel value: listDuongdan){
                    if (value.isIscheck()){
                        listDuocChon.add(value.getDuongdan());

                    }
                }
                Intent intendata=getIntent();
                intendata.putStringArrayListExtra("listhinhduocchon", (ArrayList<String>) listDuocChon);

                setResult(RESULT_OK,intendata);
                finish();
                break;
        }
    }
}
