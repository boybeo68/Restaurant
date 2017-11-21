package vn.myclass.restaurant.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.Adapter.Adapter_DanhsachWifi;
import vn.myclass.restaurant.Controller.Interface.ChitietQuanAn_Interface;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.Model.WifiQuanAnModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 11/20/2017.
 */

public class CapnhatWifi_Controller {
    WifiQuanAnModel wifiQuanAnModel;
    Context context;
    List<WifiQuanAnModel>wifiQuanAnModelList;

    public CapnhatWifi_Controller(Context context) {
        wifiQuanAnModel = new WifiQuanAnModel();
        this.context=context;
    }

    public void HienThiDanhSachWifi(String maquanan, final RecyclerView recyclerView) {
        wifiQuanAnModelList=new ArrayList<>();
        ChitietQuanAn_Interface chitietQuanAn_interface = new ChitietQuanAn_Interface() {
            @Override
            public void HienThiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                Adapter_DanhsachWifi adapter_danhsachWifi=new Adapter_DanhsachWifi(context, R.layout.layout_wifi_chitietquanan,wifiQuanAnModelList);
                recyclerView.setAdapter(adapter_danhsachWifi);
                adapter_danhsachWifi.notifyDataSetChanged();

            }
        };
        wifiQuanAnModel.layDanhSachWifiQuanAn(maquanan, chitietQuanAn_interface);
    }
    public void ThemWifiQuanAn(Context context, WifiQuanAnModel wifiQuanAnModel,String maquanan){
        wifiQuanAnModel.ThemWifiQuanAn(context,wifiQuanAnModel,maquanan);
    }
}

