package vn.myclass.restaurant.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.myclass.restaurant.Adapter.Adapter_BinhLuan;
import vn.myclass.restaurant.Adapter.Adapter_Quan_Da_Dang;
import vn.myclass.restaurant.Controller.Interface.Odau_interface;
import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.Model.ThanhVienModel;
import vn.myclass.restaurant.R;

public class User_Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView txtTenUser,txtEmailUser,txtTieudetoolbar;
    Toolbar toolbar;
    String mauser;
    Location vitrihientai;
    QuanAnModel quanAnModel;
    RecyclerView recyclerViewQuandaDang;
    Adapter_Quan_Da_Dang adapter_quan_da_dang;
    List<QuanAnModel> quanAnModelList;
    CircleImageView circleImageUser;
    private ProgressDialog progressDialog;
    Button btnDangXuat;
    private FirebaseAuth mAuth;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user);

        txtTenUser=findViewById(R.id.txtTenUser);
        txtEmailUser=findViewById(R.id.txtEmailUser);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTieudetoolbar=findViewById(R.id.txtTieudeToolbar);
        recyclerViewQuandaDang=findViewById(R.id.recyle_QuandaDang);
        btnDangXuat=findViewById(R.id.btnDangXuat);
        circleImageUser=findViewById(R.id.circleimageviewUser);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences=getSharedPreferences("luudangnhap",MODE_PRIVATE);
        mauser=sharedPreferences.getString("mauser","");
        String emailUser=sharedPreferences.getString("emailuser","");

        sharedPreferences=getSharedPreferences("toado", Context.MODE_PRIVATE);
        vitrihientai=new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        txtTenUser.setText(emailUser);
        txtEmailUser.setText(emailUser);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtTieudetoolbar.setText(emailUser);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent=new Intent(User_Activity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        });
        quanAnModelList = new ArrayList<>();
        docDulieuDatabase2();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    public void docDulieuDatabase2(){
        DatabaseReference nodeRoot=FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(mauser).getValue(ThanhVienModel.class);
                if (thanhVienModel.getMaquan()!=null){
                    for (final String maquanan:thanhVienModel.getMaquan()){
                        Log.d("kiemtra15cUser",maquanan);
                        DataSnapshot valueQuanan = dataSnapshot.child("quanans").child(maquanan);
                        Log.d("kiemtra14testvaluequan",valueQuanan+"");
                        quanAnModel = valueQuanan.getValue(QuanAnModel.class);
                        quanAnModel.setMaquanan(valueQuanan.getKey());
                        //Lấy danh sách hình ảnh quán ăn
                        DataSnapshot dataSnapshotHinhQA = dataSnapshot.child("hinhanhquanans").child(valueQuanan.getKey());
//                              Log.d("kiemtraMa",valueQuanan.getKey());
                        List<String> hinhanhList = new ArrayList<>();
                        for (DataSnapshot valueHinhanhQA : dataSnapshotHinhQA.getChildren()) {
//                              Log.d("kiemtrahinh",valueHinhanhQA.getValue()+"");
                            hinhanhList.add(valueHinhanhQA.getValue(String.class));
                        }
                        quanAnModel.setHinhanhquanan(hinhanhList);
                        //Lấy danh sách bình luân của quán ăn
                        DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
                        //do 1 quán ăn có nhiều bình luận ==>tạo list
                        List<BinhLuanModel> binhLuanModels = new ArrayList<>();

                        for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                            BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);

                            ThanhVienModel thanhVienModel2 = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                            binhLuanModel.setThanhVienModel(thanhVienModel2);// có được dữ liệu về thành viên của bình luận đó
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
                        // lấy chi nhánh quán ăn
                        List<ChiNhanhQuanAnModel>chiNhanhQuanAnModels =new ArrayList<>();
                        DataSnapshot snapshotChiNhanh=dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
                        for (DataSnapshot valueChinhanhquanan : snapshotChiNhanh.getChildren()){
                            ChiNhanhQuanAnModel chiNhanhQuanAnModel=valueChinhanhquanan.getValue(ChiNhanhQuanAnModel.class);
                            Location vitriquanan=new Location("");
                            vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                            vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                            double khoangcach= vitrihientai.distanceTo(vitriquanan)/1000;
                            Log.d("toado",khoangcach+"=="+chiNhanhQuanAnModel.getDiachi());
                            chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                            chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
                        }
                        quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
                        quanAnModelList.add(quanAnModel);
                        Log.d("kiemtra14list",quanAnModelList.size()+"");
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(User_Activity.this);
                        recyclerViewQuandaDang.setLayoutManager(layoutManager);
                        adapter_quan_da_dang=new Adapter_Quan_Da_Dang(User_Activity.this,R.layout.custom_layout_quan_da_dang,quanAnModelList);
                        recyclerViewQuandaDang.setAdapter(adapter_quan_da_dang);

                        adapter_quan_da_dang.notifyDataSetChanged();
                    }
                }
                setHinhAnhBinhLuan(circleImageUser,thanhVienModel.getHinhanh());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        nodeRoot.addValueEventListener(valueEventListener);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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

