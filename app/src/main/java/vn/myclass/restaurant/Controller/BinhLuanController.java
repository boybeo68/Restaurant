package vn.myclass.restaurant.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import vn.myclass.restaurant.Model.BinhLuanModel;

/**
 * Created by boybe on 11/27/2017.
 */

public class BinhLuanController {
    BinhLuanModel binhLuanModel;
    Context context;

    public BinhLuanController(Context context) {
        this.context=context;
        binhLuanModel=new BinhLuanModel();

    }
    public void themBinhLuan(Context context, String maQuanAn, BinhLuanModel binhLuanModel, List<Bitmap> listHinh){
        binhLuanModel.themBinhLuan(context,maQuanAn,binhLuanModel,listHinh);
    }
}
