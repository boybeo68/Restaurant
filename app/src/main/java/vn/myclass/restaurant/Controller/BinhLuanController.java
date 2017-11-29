package vn.myclass.restaurant.Controller;

import java.util.List;

import vn.myclass.restaurant.Model.BinhLuanModel;

/**
 * Created by boybe on 11/27/2017.
 */

public class BinhLuanController {
    BinhLuanModel binhLuanModel;

    public BinhLuanController() {
        binhLuanModel=new BinhLuanModel();

    }
    public void themBinhLuan(String maQuanAn, BinhLuanModel binhLuanModel, List<String> listHinh){
        binhLuanModel.themBinhLuan(maQuanAn,binhLuanModel,listHinh);
    }
}
