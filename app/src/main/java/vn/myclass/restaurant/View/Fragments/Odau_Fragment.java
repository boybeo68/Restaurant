package vn.myclass.restaurant.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import vn.myclass.restaurant.Controller.Odau_Controller;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.Trangchu_Activity;

/**
 * Created by boybe on 10/24/2017.
 */

public class Odau_Fragment extends Fragment {
    Odau_Controller odau_controller;
    RecyclerView recyclerOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollView;
    LinearLayout khungdanhmuc,lnTimKiem,khunggantoi;
    RadioButton rd_DanhMuc;
    RadioGroup rdgTab;
    Button btn10km,btnTatca,btnTimkiem;
    EditText edtTimkiem;
    double khoangcach=10000.0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_odau,container,false);
        recyclerOdau= (RecyclerView) view.findViewById(R.id.rycyclerOdau);
        progressBar= (ProgressBar) view.findViewById(R.id.progress_barOdau);
        nestedScrollView= (NestedScrollView) view.findViewById(R.id.NestedScrollViewOdau);
        khungdanhmuc= (LinearLayout) view.findViewById(R.id.khungdanhmuc);
        rd_DanhMuc= (RadioButton) view.findViewById(R.id.rd_danhmuc);
        lnTimKiem=view.findViewById(R.id.lnTimkiem);
        rdgTab=view.findViewById(R.id.group_radio_tab);
        khunggantoi=view.findViewById(R.id.khungGanToi);
        btn10km=view.findViewById(R.id.btn10km);
        btnTatca=view.findViewById(R.id.btnTatCa);
        btnTimkiem=view.findViewById(R.id.btnTimkiem);
        edtTimkiem=view.findViewById(R.id.edtTimkiem);

        //lấy sharepreferences từ màn hình slashscreen : tọa độ
        sharedPreferences=getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        final Location vitrihientai=new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        rdgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = rdgTab.findViewById(i);
                int index = rdgTab.indexOfChild(radioButton);
                switch (index) {
                    case 0: //Gần tôi
                        khungdanhmuc.setVisibility(View.GONE);
                        lnTimKiem.setVisibility(View.GONE);
                        khunggantoi.setVisibility(View.VISIBLE);
                        btn10km.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                khoangcach =10.0;

                                odau_controller =new Odau_Controller(getContext());
                                Log.d("kiemtratoado",sharedPreferences.getString("latitude","0") + "");
                                odau_controller.getDanhsachquananController(getContext(),nestedScrollView,recyclerOdau,progressBar,vitrihientai,khoangcach);
                            }
                        });
                        btnTatca.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                khoangcach =10000.0;

                                odau_controller =new Odau_Controller(getContext());
                                Log.d("kiemtratoado",sharedPreferences.getString("latitude","0") + "");
                                odau_controller.getDanhsachquananController(getContext(),nestedScrollView,recyclerOdau,progressBar,vitrihientai,khoangcach);
                            }
                        });


//                        Intent iTrangChu=new Intent(getContext(), Trangchu_Activity.class);
//                        getActivity().startActivity(iTrangChu);

                        break;
                    case 1: //View
                        khungdanhmuc.setVisibility(View.GONE);
                        lnTimKiem.setVisibility(View.GONE);
                        khunggantoi.setVisibility(View.GONE);
                        break;
                    case 2: //Danh Mục
                        khungdanhmuc.setVisibility(View.VISIBLE);
                        lnTimKiem.setVisibility(View.GONE);
                        khunggantoi.setVisibility(View.GONE);
                        break;
                    case 3: //View
                        khungdanhmuc.setVisibility(View.GONE);
                        lnTimKiem.setVisibility(View.GONE);
                        khunggantoi.setVisibility(View.GONE);
                        break;
                    case 4: //Tìm kiếm
                        khungdanhmuc.setVisibility(View.GONE);
                        lnTimKiem.setVisibility(View.VISIBLE);
                        khunggantoi.setVisibility(View.GONE);
                        break;
                }
            }
        });


        odau_controller =new Odau_Controller(getContext());
        Log.d("kiemtratoado",sharedPreferences.getString("latitude","0") + "");
        odau_controller.getDanhsachquananController(getContext(),nestedScrollView,recyclerOdau,progressBar,vitrihientai,khoangcach);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
