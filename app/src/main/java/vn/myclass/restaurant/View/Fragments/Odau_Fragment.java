package vn.myclass.restaurant.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.myclass.restaurant.Controller.Odau_Controller;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 10/24/2017.
 */

public class Odau_Fragment extends Fragment {
    Odau_Controller odau_controller;
    RecyclerView recyclerOdau;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_odau,container,false);
        recyclerOdau= (RecyclerView) view.findViewById(R.id.rycyclerOdau);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        odau_controller =new Odau_Controller(getContext());
        odau_controller.getDanhsachquananController(recyclerOdau);

    }
}
