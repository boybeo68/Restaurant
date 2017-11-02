package vn.myclass.restaurant.Controller;

import vn.myclass.restaurant.Model.ThanhVienModel;

/**
 * Created by boybe on 10/31/2017.
 */

public class Dangki_Controller {
    ThanhVienModel thanhVienModel;
    public void Dangki_Controller(){
        thanhVienModel=new ThanhVienModel(

        );

    }
    public void ThemThongTinThanVienController(ThanhVienModel thanhVienModel,String uid){
        thanhVienModel.ThemThongTinThanhVien(thanhVienModel,uid);
    }
}
