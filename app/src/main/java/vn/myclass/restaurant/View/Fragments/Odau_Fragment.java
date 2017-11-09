package vn.myclass.restaurant.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import vn.myclass.restaurant.Controller.Odau_Controller;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 10/24/2017.
 */

public class Odau_Fragment extends Fragment {
    Odau_Controller odau_controller;
    RecyclerView recyclerOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_odau,container,false);
        recyclerOdau= (RecyclerView) view.findViewById(R.id.rycyclerOdau);
        progressBar= (ProgressBar) view.findViewById(R.id.progress_barOdau);
        nestedScrollView= (NestedScrollView) view.findViewById(R.id.NestedScrollViewOdau);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //lấy sharepreferences từ màn hình slashscreen : tọa độ
        sharedPreferences=getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai=new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));

        odau_controller =new Odau_Controller(getContext());
        Log.d("kiemtratoado",sharedPreferences.getString("latitude","0") + "");
        odau_controller.getDanhsachquananController(getContext(),nestedScrollView,recyclerOdau,progressBar,vitrihientai);

    }
}
