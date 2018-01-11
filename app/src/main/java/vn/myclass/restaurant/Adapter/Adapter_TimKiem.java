package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.ChiTietQuanAn_Activity;

/**
 * Created by boybe on 1/11/2018.
 */

public class Adapter_TimKiem extends RecyclerView.Adapter<Adapter_TimKiem.ViewHolderTimKiem> {

    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;
    public Adapter_TimKiem(Context context,int resource,List<QuanAnModel> quanAnModelList) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolderTimKiem extends RecyclerView.ViewHolder {
        TextView txtTenQuananOdau;
        TextView txtDiemTrungBinhQuanAn,txtDiaChiQuanAnODau,txtKhoanCachQuanAnODau;
        CardView cardView;
        public ViewHolderTimKiem(View itemView) {
            super(itemView);
            txtTenQuananOdau=itemView.findViewById(R.id.txtTenquananOdau);
            txtDiaChiQuanAnODau=itemView.findViewById(R.id.txtDiachiquananOdau);
            txtDiemTrungBinhQuanAn=itemView.findViewById(R.id.txtDiemtrungbinhQuanAn);
            txtKhoanCachQuanAnODau=itemView.findViewById(R.id.txtKhoangcachquananODau);
            cardView= (CardView) itemView.findViewById(R.id.cardViewOdau);
        }
    }
    @Override
    public Adapter_TimKiem.ViewHolderTimKiem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderTimKiem viewHolderTimKiem=new ViewHolderTimKiem(view);
        return viewHolderTimKiem;
    }

    @Override
    public void onBindViewHolder(Adapter_TimKiem.ViewHolderTimKiem holder, int position) {
        final QuanAnModel quanAnModel=quanAnModelList.get(position);
        holder.txtTenQuananOdau.setText(quanAnModel.getTenquanan());
        double tongdiemquanan=0;
        //tổng điểm trung bình của bình luận và tổng số hình bình luận
        if(quanAnModel.getBinhLuanModelList().size() > 0){
            for (BinhLuanModel binhLuanModel1:quanAnModel.getBinhLuanModelList()){
                tongdiemquanan+=binhLuanModel1.getChamdiem();
                Log.d("kiemtra14bl",binhLuanModel1.getChamdiem()+"");
            }
            double diemtrungbinhquanan=tongdiemquanan/(quanAnModel.getBinhLuanModelList().size());
            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f",diemtrungbinhquanan));
        }else {
            holder.txtDiemTrungBinhQuanAn.setText("0");
        }
        //Lấy địa chỉ quán ăn và hiển thị địa chỉ và km
        if (quanAnModel.getChiNhanhQuanAnModelList().size()>0){
            // lấy thằng đầu tiên làm đối tượng so sánh
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam=quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel:quanAnModel.getChiNhanhQuanAnModelList()){
                if (chiNhanhQuanAnModelTam.getKhoangcach()>chiNhanhQuanAnModel.getKhoangcach()){
                    chiNhanhQuanAnModelTam=chiNhanhQuanAnModel;
                }
            }
            holder.txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModelTam.getDiachi());
            holder.txtKhoanCachQuanAnODau.setText(String.format("%.1f",chiNhanhQuanAnModelTam.getKhoangcach())+"km" );
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ichitietQuanAn=new Intent(context, ChiTietQuanAn_Activity.class);
                    ichitietQuanAn.putExtra("quanan",quanAnModel);
                    context.startActivity(ichitietQuanAn);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }


}
