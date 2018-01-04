package vn.myclass.restaurant.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import vn.myclass.restaurant.Adapter.Adapter_ThucDon;
import vn.myclass.restaurant.Controller.Interface.ThucDon_interface;
import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.Model.ThucDonModel;

/**
 * Created by boybe on 11/29/2017.
 */

public class ThucDonController {
    ThucDonModel thucDonModel;

    public ThucDonController() {
        thucDonModel =new ThucDonModel();
    }

    public void getDanhSachThucDonQuanan(final Context context, final String maQuanAn, final RecyclerView recyclerView, final boolean isuaquan, final TextView txtTongTien){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        //lấy toàn bộ cái interface đã đc thêm dữ liệu
        ThucDon_interface thucDon_interface=new ThucDon_interface() {
            @Override
            public void getThucDon(List<ThucDonModel> thucDonModelList) {
                Adapter_ThucDon adapter_thucDon=new Adapter_ThucDon(context,thucDonModelList,isuaquan,maQuanAn,txtTongTien);
                recyclerView.setAdapter(adapter_thucDon);
                adapter_thucDon.notifyDataSetChanged();

            }
        };
        thucDonModel.getDanhSachthucdonTheoMa(maQuanAn,thucDon_interface);
    }
}
