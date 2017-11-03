package vn.myclass.restaurant.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.Adapter.AdapterRecycler_Odau;
import vn.myclass.restaurant.Controller.Interface.Odau_interface;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 10/29/2017.
 */

public class Odau_Controller {
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecycler_Odau adapterRecycler_odau;

    public Odau_Controller(Context context) {
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void getDanhsachquananController(RecyclerView recyclerViewOdau, final ProgressBar progressBar) {

        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewOdau.setLayoutManager(layoutManager);

        adapterRecycler_odau = new AdapterRecycler_Odau(quanAnModelList, R.layout.custom_layout_recycleview_odau);
        recyclerViewOdau.setAdapter(adapterRecycler_odau);


        Odau_interface odau_interface = new Odau_interface() {
            @Override
            public void getDanhsachQuananModel(QuanAnModel quanAnModel) {
//                Log.d("kiemtralist", quanAnModel.getTenquanan() + "");
                quanAnModelList.add(quanAnModel);
                adapterRecycler_odau.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        };

        quanAnModel.getDanhsachQuanAn(odau_interface);
    }
}
