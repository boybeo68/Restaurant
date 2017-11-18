package vn.myclass.restaurant.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.myclass.restaurant.Adapter.AdapterRecyclerHinhBinhLuan;
import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.R;

public class HienThiChiTietBinhLuan_Activity extends AppCompatActivity {
    CircleImageView circleImageView;
    TextView txtTieudeBinhLuan,txtNoidungBinhluan,txtChamDiembinhluan;
    RecyclerView recyclerViewHinhBinhluan;
    BinhLuanModel binhLuanModel;
    List<Bitmap>bitmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan_chitiet);
        circleImageView= (CircleImageView)findViewById(R.id.circleimageviewUser);
        txtTieudeBinhLuan= (TextView)findViewById(R.id.txtTieudebinhluan);
        txtNoidungBinhluan= (TextView)findViewById(R.id.txtNoiDungbinhluan);
        txtChamDiembinhluan= (TextView)findViewById(R.id.txtChamdiembinhluan);
        recyclerViewHinhBinhluan= (RecyclerView)findViewById(R.id.recyle_hinhbinhluan);

        bitmapList=new ArrayList<>();

        binhLuanModel = getIntent().getParcelableExtra("binhluanmodel");
        txtTieudeBinhLuan.setText(binhLuanModel.getTieude());
        txtNoidungBinhluan.setText(binhLuanModel.getNoidung());
        txtChamDiembinhluan.setText(binhLuanModel.getChamdiem()+"");
//
        setHinhAnhBinhLuan(circleImageView,binhLuanModel.getThanhVienModel().getHinhanh());
//        Lấy ra list hình ảnh của  từng bình luậnbình luận
        for (String linkhinh : binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size()==binhLuanModel.getHinhanhBinhLuanList().size()){
                        AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan=new AdapterRecyclerHinhBinhLuan(HienThiChiTietBinhLuan_Activity.this,R.layout.custom_layout_hinhbinhluan,bitmapList,binhLuanModel,true);
                        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(HienThiChiTietBinhLuan_Activity.this,2);
                        recyclerViewHinhBinhluan.setLayoutManager(layoutManager);
                        recyclerViewHinhBinhluan.setAdapter(adapterRecyclerHinhBinhLuan);
                        adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }

    }
    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}
