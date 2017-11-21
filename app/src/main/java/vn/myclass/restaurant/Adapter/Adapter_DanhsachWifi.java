package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vn.myclass.restaurant.Model.WifiQuanAnModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 11/20/2017.
 */

public class Adapter_DanhsachWifi extends RecyclerView.Adapter<Adapter_DanhsachWifi.ViewHolderWifi> {
    Context context;
    int resource;
    List<WifiQuanAnModel>wifiQuanAnModelList;

    public Adapter_DanhsachWifi(Context context, int resource, List<WifiQuanAnModel>wifiQuanAnModelList){
        this.context=context;
        this.resource=resource;
        this.wifiQuanAnModelList=wifiQuanAnModelList;

    }
    public class ViewHolderWifi extends RecyclerView.ViewHolder {
        TextView txtTenWifi,txtMatKhauWifi,txtNgaydang;
        public ViewHolderWifi(View itemView) {
            super(itemView);
           txtTenWifi= (TextView) itemView.findViewById(R.id.txtTenWifi);
           txtMatKhauWifi= (TextView) itemView.findViewById(R.id.txtMatkhauWifi);
           txtNgaydang= (TextView) itemView.findViewById(R.id.txtNgayDangWifi);

        }
    }
    @Override
    public Adapter_DanhsachWifi.ViewHolderWifi onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderWifi viewHolderWifi=new ViewHolderWifi(view);
        return viewHolderWifi;
    }

    @Override
    public void onBindViewHolder(Adapter_DanhsachWifi.ViewHolderWifi holder, int position) {
        WifiQuanAnModel wifiQuanAnModel=wifiQuanAnModelList.get(position);
        holder.txtTenWifi.setText(wifiQuanAnModel.getTen());
        holder.txtMatKhauWifi.setText(wifiQuanAnModel.getMatkhau());
        holder.txtNgaydang.setText(wifiQuanAnModel.getNgaydang());

    }

    @Override
    public int getItemCount() {
        return wifiQuanAnModelList.size();
    }


}
