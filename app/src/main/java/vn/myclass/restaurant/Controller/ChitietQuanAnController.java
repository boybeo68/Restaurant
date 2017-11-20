package vn.myclass.restaurant.Controller;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.Controller.Interface.ChitietQuanAn_Interface;
import vn.myclass.restaurant.Model.WifiQuanAnModel;

/**
 * Created by boybe on 11/20/2017.
 */

public class ChitietQuanAnController {
    WifiQuanAnModel wifiQuanAnModel;
    List<WifiQuanAnModel>wifiQuanAnModelList;

    public ChitietQuanAnController() {
        wifiQuanAnModel=new WifiQuanAnModel();
        wifiQuanAnModelList=new ArrayList<>();

    }
    public void HienThiDanhSachWifiQuanAn(String maquanan, final TextView txtTenWifi, final TextView txtMatkhauWif, final TextView txtNgaydangWifi){

        ChitietQuanAn_Interface chitietQuanAn_interface=new ChitietQuanAn_Interface() {
            @Override
            public void HienThiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                Log.d("testwifi",wifiQuanAnModel.getTen());
                txtTenWifi.setText(wifiQuanAnModel.getTen());
                txtMatkhauWif.setText(wifiQuanAnModel.getMatkhau());
                txtNgaydangWifi.setText(wifiQuanAnModel.getNgaydang());
            }
        };
        wifiQuanAnModel.layDanhSachWifiQuanAn(maquanan,chitietQuanAn_interface);
    }
}
