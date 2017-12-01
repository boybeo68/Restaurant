package vn.myclass.restaurant.View.Fragments;

import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.myclass.restaurant.Model.ChonHinhBinhLuanModel;
import vn.myclass.restaurant.Model.ThucDonModel;
import vn.myclass.restaurant.Model.TienIchModel;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.ThemQuanAn_Activity;

/**
 * Created by boybe on 10/24/2017.
 */

public class ThemQuanAn_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button btnGioMoCua, btnGioDongCua;
    String gioMoCua, gioDongCua;
    Spinner spinnerKhuVuc, spinnerThucDon;
    List<ThucDonModel> thucDonModelList;
    List<String> khuVucList;
    List<String> thucDonList;
    List<TienIchModel> tienIchModelList;
    List<String>selecttienIchList;
    List<String>chinhanhList;
    ArrayAdapter<String> adapterKhuvuc;
    ArrayAdapter<String> adapterThucDon;
    LinearLayout khungtienich,khungChiNhanh,khungChuaChiNhanh;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_themquanan, container, false);
        btnGioMoCua = (Button) view.findViewById(R.id.btnGioMoCua);
        btnGioDongCua = (Button) view.findViewById(R.id.btnGioDongCua);
        spinnerKhuVuc = (Spinner) view.findViewById(R.id.spinnerKhuVuc);
        spinnerThucDon = (Spinner) view.findViewById(R.id.spinnerThucDon);
        khungtienich= (LinearLayout) view.findViewById(R.id.khungTienTich);
        khungChiNhanh= (LinearLayout) view.findViewById(R.id.khungChiNhanh);
        khungChuaChiNhanh= (LinearLayout) view.findViewById(R.id.khungChuaChiNhanh);
        cloneChiNhanh();


        thucDonModelList = new ArrayList<>();
        khuVucList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        tienIchModelList = new ArrayList<>();
        selecttienIchList=new ArrayList<>();
        chinhanhList=new ArrayList<>();
        adapterKhuvuc = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuvuc);
        adapterKhuvuc.notifyDataSetChanged();
        layDanhSachKhuVuc();


        adapterThucDon = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, thucDonList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();
        layDanhSachThucDon();
        layDanhSachTienIch();

        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);
        spinnerThucDon.setOnItemSelectedListener(this);
        return view;

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

    private void layDanhSachThucDon() {
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
            case R.id.spinnerThucDon:
                Log.d("kiemtra", thucDonList.get(position) + "-" + thucDonModelList.get(position).getMathucdon());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
