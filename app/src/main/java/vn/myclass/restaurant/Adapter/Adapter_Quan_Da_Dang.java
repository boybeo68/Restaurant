package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 12/13/2017.
 */

public class Adapter_Quan_Da_Dang extends RecyclerView.Adapter<Adapter_Quan_Da_Dang.ViewHolderQuanDaDang> {

    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;

    public Adapter_Quan_Da_Dang(Context context,int resource,List<QuanAnModel> quanAnModelList) {
        this.quanAnModelList=quanAnModelList;
        this.resource=resource;
        this.context=context;
    }

    public class ViewHolderQuanDaDang extends RecyclerView.ViewHolder {
        TextView txtTenQuananOdau;
        TextView txtDiemTrungBinhQuanAn,txtDiaChiQuanAnODau,txtKhoanCachQuanAnODau;
        public ViewHolderQuanDaDang(View itemView) {
            super(itemView);
            txtTenQuananOdau=itemView.findViewById(R.id.txtTenquananOdau);
            txtDiaChiQuanAnODau=itemView.findViewById(R.id.txtDiachiquananOdau);
            txtDiemTrungBinhQuanAn=itemView.findViewById(R.id.txtDiemtrungbinhQuanAn);
            txtKhoanCachQuanAnODau=itemView.findViewById(R.id.txtKhoangcachquananODau);


        }
    }
    @Override
    public Adapter_Quan_Da_Dang.ViewHolderQuanDaDang onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Adapter_Quan_Da_Dang.ViewHolderQuanDaDang holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
