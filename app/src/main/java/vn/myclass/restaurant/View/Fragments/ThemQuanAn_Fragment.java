package vn.myclass.restaurant.View.Fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.myclass.restaurant.Model.ChonHinhBinhLuanModel;
import vn.myclass.restaurant.Model.MonanModel;
import vn.myclass.restaurant.Model.ThemThucDonModel;
import vn.myclass.restaurant.Model.ThucDonModel;
import vn.myclass.restaurant.Model.TienIchModel;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.DangKy_Activity;
import vn.myclass.restaurant.View.ThemQuanAn_Activity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by boybe on 10/24/2017.
 */

public class ThemQuanAn_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    final int RESULT_IMG1 = 111;
    final int RESULT_IMG2 = 112;
    final int RESULT_IMG3 = 113;
    final int RESULT_IMG4 = 114;
    final int RESULT_IMG5 = 115;
    final int RESULT_IMG6 = 116;
    final int RESULT_IMGTHUCDON = 117;
    final int RESULT_VIDEO = 200;
    Button btnGioMoCua, btnGioDongCua;
    String gioMoCua, gioDongCua;
    Spinner spinnerKhuVuc;
    List<ThucDonModel> thucDonModelList;
    List<String> khuVucList;
    List<String> thucDonList;
    List<TienIchModel> tienIchModelList;
    List<String>selecttienIchList;
    List<String>chinhanhList;
    List<ThemThucDonModel>themThucDonModelList;
    List<Bitmap>hinhDaChupList;
    List<Uri>hinhQuanAn;
    Uri videoSelect;
    ArrayAdapter<String> adapterKhuvuc;
    ImageView imgTam,imgHinQuan1,imgHinQuan2,imgHinQuan3,imgHinQuan4,imgHinQuan5,imgHinQuan6,imgVideo;
    LinearLayout khungtienich,khungChiNhanh,khungChuaChiNhanh,khungchuaThucDon;
    VideoView videoView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_themquanan, container, false);
        btnGioMoCua = (Button) view.findViewById(R.id.btnGioMoCua);
        btnGioDongCua = (Button) view.findViewById(R.id.btnGioDongCua);
        spinnerKhuVuc = (Spinner) view.findViewById(R.id.spinnerKhuVuc);

        khungtienich= (LinearLayout) view.findViewById(R.id.khungTienTich);
        khungChiNhanh= (LinearLayout) view.findViewById(R.id.khungChiNhanh);
        khungChuaChiNhanh= (LinearLayout) view.findViewById(R.id.khungChuaChiNhanh);
        khungchuaThucDon= (LinearLayout) view.findViewById(R.id.khungChuaThucDon);
        imgHinQuan1= (ImageView) view.findViewById(R.id.imgHinhQuan1);
        imgHinQuan2= (ImageView) view.findViewById(R.id.imgHinhQuan2);
        imgHinQuan3= (ImageView) view.findViewById(R.id.imgHinhQuan3);
        imgHinQuan4= (ImageView) view.findViewById(R.id.imgHinhQuan4);
        imgHinQuan5= (ImageView) view.findViewById(R.id.imgHinhQuan5);
        imgHinQuan6= (ImageView) view.findViewById(R.id.imgHinhQuan6);
        videoView= (VideoView) view.findViewById(R.id.videoView);
        imgVideo= (ImageView) view.findViewById(R.id.imgvideo);

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
        adapterKhuvuc = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuvuc);
        adapterKhuvuc.notifyDataSetChanged();
        layDanhSachKhuVuc();



        layDanhSachTienIch();
        cloneThucDon();

        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);
        imgHinQuan1.setOnClickListener(this);
        imgHinQuan2.setOnClickListener(this);
        imgHinQuan3.setOnClickListener(this);
        imgHinQuan4.setOnClickListener(this);
        imgHinQuan5.setOnClickListener(this);
        imgHinQuan6.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_IMG1:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    imgHinQuan1.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG2:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    imgHinQuan2.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG3:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    imgHinQuan3.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG4:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    imgHinQuan4.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG5:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    imgHinQuan5.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG6:
                if (RESULT_OK==resultCode){
                    Uri uri=data.getData();
                    imgHinQuan6.setImageURI(uri);
                    hinhQuanAn.add(uri);
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
                    MediaController mediaController=new MediaController(getContext());
                    videoView.setMediaController(mediaController);
                    Uri uri=data.getData();
                    videoSelect=uri;
                    Log.d("kiemtravideo",videoSelect+"");
                    videoView.setVideoURI(uri);
                    videoView.start();



                }
        }

    }

    private void cloneThucDon(){
        final View view2=LayoutInflater.from(getContext()).inflate(R.layout.layout_clone_thucdon,null);
        final Spinner spinnerThucDon = (Spinner) view2.findViewById(R.id.spinnerThucDon);
        Button btnThemThucDon = (Button) view2.findViewById(R.id.btnThemThucDon);
        final ImageButton imgXoaThucDon= (ImageButton) view2.findViewById(R.id.imgXoaThucDon);
        final EditText edtTenMon= (EditText) view2.findViewById(R.id.edtTenMon);
        final EditText edtGiaTien= (EditText) view2.findViewById(R.id.edtGiaTien);
        ImageView imgChupHinh= (ImageView) view2.findViewById(R.id.imgChupHinh);
        imgTam=imgChupHinh;
        //spinner
        ArrayAdapter<String> adapterThucDon = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, thucDonList);
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
                    Toast.makeText(getContext(), R.string.NhapTenMon, Toast.LENGTH_SHORT).show();
                }else if(edtGiaTien.getText().toString().trim().length()==0){
                    Toast.makeText(getContext(), R.string.NhapGiatien, Toast.LENGTH_SHORT).show();
                }else {
                    v.setVisibility(View.GONE);
                    imgXoaThucDon.setVisibility(View.VISIBLE);
                    long thoigian=Calendar.getInstance().getTimeInMillis();
                    String tenHinh=String.valueOf(thoigian)+".jpg";
                    int position=spinnerThucDon.getSelectedItemPosition();
                    String mathucDon=thucDonModelList.get(position).getMathucdon();
                    MonanModel monanModel=new MonanModel();
                    monanModel.setTenmon(edtTenMon.getText().toString());
                    monanModel.setGiatien(Long.parseLong(edtGiaTien.getText().toString()));
                    monanModel.setHinhanh(tenHinh);
                    ThemThucDonModel themThucDonModel=new ThemThucDonModel();
                    themThucDonModel.setMathucdon(mathucDon);
                    themThucDonModel.setMonanModel(monanModel);
                    themThucDonModelList.add(themThucDonModel);


                    cloneThucDon();
                }


            }
        });
        imgXoaThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khungchuaThucDon.removeView(view2);
            }
        });
        khungchuaThucDon.addView(view2);
    }
    private void cloneChiNhanh(){
        final View view1=LayoutInflater.from(getContext()).inflate(R.layout.layout_clone_chinhanh,null);

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
//                edtTenChiNhanh.setFocusableInTouchMode(false);

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

    private void layDanhSachTienIch() {
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot valueTienIch : dataSnapshot.getChildren()) {
                    String maTienIch = valueTienIch.getKey();
                    TienIchModel tienIchModel = valueTienIch.getValue(TienIchModel.class);
                    tienIchModel.setMaTienIch(maTienIch);
                    CheckBox checkBox=new CheckBox(getContext());
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


    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        switch (v.getId()) {
            case R.id.btnGioMoCua:
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioMoCua = hourOfDay + ":" + minute;
                        btnGioMoCua.setText(gioMoCua);
                    }
                }, gio, phut, true);
                timePickerDialog.show();
                break;
            case R.id.btnGioDongCua:
                TimePickerDialog DongPickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioDongCua = hourOfDay + ":" + minute;
                        btnGioDongCua.setText(gioDongCua);
                    }
                }, gio, phut, true);
                DongPickerDialog.show();
                break;
            case R.id.imgHinhQuan1:
                chonHinhtuGalaxy(RESULT_IMG1);
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
        }
    }

    private void layDanhSachKhuVuc() {
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot valueKhuVu : dataSnapshot.getChildren()) {
                    String tenKhuVuc = valueKhuVu.getKey();
                    khuVucList.add(tenKhuVuc);
                }
                adapterKhuvuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerKhuVuc:
                Log.d("kiemtra", khuVucList.get(position));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
