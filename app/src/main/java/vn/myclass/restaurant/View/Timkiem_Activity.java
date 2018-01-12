package vn.myclass.restaurant.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.myclass.restaurant.Adapter.Adapter_Quan_Da_Dang;
import vn.myclass.restaurant.Adapter.Adapter_TimKiem;
import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.Model.ThanhVienModel;
import vn.myclass.restaurant.R;

public class Timkiem_Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    TextView txtTieudetoolbar;
    RecyclerView recyclerViewQuandaTim;
    Location vitrihientai;
    List<QuanAnModel> quanAnModelList;
    EditText edtTimKiem;
    Button btnTimkiem;
    List<QuanAnModel> SearchQuanAnList;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timkiem);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTieudetoolbar = findViewById(R.id.txtTieudeToolbar);
        recyclerViewQuandaTim = findViewById(R.id.recyle_QuanDaTim);
        edtTimKiem = findViewById(R.id.edtTimkiem);
        btnTimkiem = findViewById(R.id.btnTimkiem);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtTieudetoolbar.setText(R.string.TimKiemTenquan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("toado", Context.MODE_PRIVATE);
        vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));
        quanAnModelList = new ArrayList<>();
        SearchQuanAnList = new ArrayList<>();
        docdulieuQuanAn();
        btnTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                String searchText = StringUtils.removeAccent(edtTimKiem.getText().toString().toLowerCase().trim()) ;
                docdulieuQuanAnTimkiem(searchText);
            }
        });

    }

    private void docdulieuQuanAn() {
        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                quanAnModelList.clear();
                DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");
                for (DataSnapshot valueQuanan : dataSnapshotQuanan.getChildren()) {
//            Log.d("kiemtra14testvaluequan",valueQuanan+"");
                    QuanAnModel quanAnModel = valueQuanan.getValue(QuanAnModel.class);
                    quanAnModel.setMaquanan(valueQuanan.getKey());
                    //Lấy danh sách hình ảnh quán ăn
                    DataSnapshot dataSnapshotHinhQA = dataSnapshot.child("hinhanhquanans").child(valueQuanan.getKey());
//                        Log.d("kiemtraMa",valueQuanan.getKey());
                    List<String> hinhanhList = new ArrayList<>();
                    for (DataSnapshot valueHinhanhQA : dataSnapshotHinhQA.getChildren()) {
//                            Log.d("kiemtrahinh",valueHinhanhQA.getValue()+"");
                        hinhanhList.add(valueHinhanhQA.getValue(String.class));
                    }
                    quanAnModel.setHinhanhquanan(hinhanhList);
                    //Lấy danh sách bình luân của quán ăn
                    DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
                    //do 1 quán ăn có nhiều bình luận ==>tạo list
                    List<BinhLuanModel> binhLuanModels = new ArrayList<>();

                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                        Log.d("kiemtra15cc", valueBinhLuan + "");
                        BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                        Log.d("kiemtra15cc", dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()) + "");
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
                    // lấy chi nhánh quán ăn
                    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();
                    DataSnapshot snapshotChiNhanh = dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
                    for (DataSnapshot valueChinhanhquanan : snapshotChiNhanh.getChildren()) {
                        ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChinhanhquanan.getValue(ChiNhanhQuanAnModel.class);
                        Location vitriquanan = new Location("");
                        vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                        vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                        double khoangcach = vitrihientai.distanceTo(vitriquanan) / 1000;
                        Log.d("toado", khoangcach + "==" + chiNhanhQuanAnModel.getDiachi());
                        chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                        chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
                    }
                    quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
                    quanAnModelList.add(quanAnModel);

                    Log.d("kiemtra14list", quanAnModelList.size() + "");
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Timkiem_Activity.this);
                    recyclerViewQuandaTim.setLayoutManager(layoutManager);
//                    Adapter_Quan_Da_Dang adapter_quan_da_dang=new Adapter_Quan_Da_Dang(User_Activity.this,R.layout.custom_layout_quan_da_dang,quanAnModelList,mauser,listQuanDaDang);
//                    recyclerViewQuandaDang.setAdapter(adapter_quan_da_dang);
//                    adapter_quan_da_dang.notifyDataSetChanged();
                    Adapter_TimKiem adapter_timKiem = new Adapter_TimKiem(Timkiem_Activity.this, R.layout.custom_layout_quandaluu, quanAnModelList);
                    recyclerViewQuandaTim.setAdapter(adapter_timKiem);
                    adapter_timKiem.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    private void docdulieuQuanAnTimkiem(final String searchText) {
        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                quanAnModelList.clear();
                DataSnapshot dataSnapshotQuanan = dataSnapshot.child("quanans");
                for (DataSnapshot valueQuanan : dataSnapshotQuanan.getChildren()) {
//            Log.d("kiemtra14testvaluequan",valueQuanan+"");
                    QuanAnModel quanAnModel = valueQuanan.getValue(QuanAnModel.class);
                    String tenquan = StringUtils.removeAccent(quanAnModel.getTenquanan());
                    if (tenquan.toLowerCase().contains(searchText)) {
                        quanAnModel.setMaquanan(valueQuanan.getKey());
                        //Lấy danh sách hình ảnh quán ăn
                        DataSnapshot dataSnapshotHinhQA = dataSnapshot.child("hinhanhquanans").child(valueQuanan.getKey());
//                        Log.d("kiemtraMa",valueQuanan.getKey());
                        List<String> hinhanhList = new ArrayList<>();
                        for (DataSnapshot valueHinhanhQA : dataSnapshotHinhQA.getChildren()) {
//                            Log.d("kiemtrahinh",valueHinhanhQA.getValue()+"");
                            hinhanhList.add(valueHinhanhQA.getValue(String.class));
                        }
                        quanAnModel.setHinhanhquanan(hinhanhList);
                        //Lấy danh sách bình luân của quán ăn
                        DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
                        //do 1 quán ăn có nhiều bình luận ==>tạo list
                        List<BinhLuanModel> binhLuanModels = new ArrayList<>();

                        for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                            Log.d("kiemtra15cc", valueBinhLuan + "");
                            BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                            Log.d("kiemtra15cc", dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()) + "");
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
                        // lấy chi nhánh quán ăn
                        List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();
                        DataSnapshot snapshotChiNhanh = dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
                        for (DataSnapshot valueChinhanhquanan : snapshotChiNhanh.getChildren()) {
                            ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChinhanhquanan.getValue(ChiNhanhQuanAnModel.class);
                            Location vitriquanan = new Location("");
                            vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                            vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                            double khoangcach = vitrihientai.distanceTo(vitriquanan) / 1000;
                            Log.d("toado", khoangcach + "==" + chiNhanhQuanAnModel.getDiachi());
                            chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                            chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
                        }
                        quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
                        quanAnModelList.add(quanAnModel);
                        Log.d("kiemtra14list", quanAnModelList.size() + "");
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Timkiem_Activity.this);
                        recyclerViewQuandaTim.setLayoutManager(layoutManager);
//                    Adapter_Quan_Da_Dang adapter_quan_da_dang=new Adapter_Quan_Da_Dang(User_Activity.this,R.layout.custom_layout_quan_da_dang,quanAnModelList,mauser,listQuanDaDang);
//                    recyclerViewQuandaDang.setAdapter(adapter_quan_da_dang);
//                    adapter_quan_da_dang.notifyDataSetChanged();
                        Adapter_TimKiem adapter_timKiem = new Adapter_TimKiem(Timkiem_Activity.this, R.layout.custom_layout_timkiem, quanAnModelList);
                        recyclerViewQuandaTim.setAdapter(adapter_timKiem);
                        adapter_timKiem.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static class StringUtils {

        public static String removeAccent(String s) {

            String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        }
    }
}