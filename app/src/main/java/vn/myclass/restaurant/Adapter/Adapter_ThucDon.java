package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

import vn.myclass.restaurant.Model.ThucDonModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 11/29/2017.
 */

public class Adapter_ThucDon extends RecyclerView.Adapter<Adapter_ThucDon.ViewHolderThucDon> {

    Context context;
    List<ThucDonModel>thucDonModells;
    boolean isuaquan;
    String maquan;
    TextView txtTongTien;
    public Adapter_ThucDon(Context context, List<ThucDonModel>thucDonModels,boolean isuaquan,String maquan,TextView txtTongtien) {
        this.context=context;
        this.thucDonModells=thucDonModels;
        this.isuaquan=isuaquan;
        this.maquan=maquan;
        this.txtTongTien=txtTongtien;
    }

    public class ViewHolderThucDon extends RecyclerView.ViewHolder {
        TextView txtTenThucDon;
        RecyclerView recyclerMonAn;
        public ViewHolderThucDon(View itemView) {
            super(itemView);
            txtTenThucDon= (TextView) itemView.findViewById(R.id.txtTenThucdon);
            recyclerMonAn= (RecyclerView) itemView.findViewById(R.id.rycyclerMonAn);
        }
    }
    @Override
    public Adapter_ThucDon.ViewHolderThucDon onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_layout_thucdon,parent,false);
        ViewHolderThucDon viewHolderThucDon=new ViewHolderThucDon(view);
        return viewHolderThucDon;
    }

    @Override
    public void onBindViewHolder(Adapter_ThucDon.ViewHolderThucDon holder, int position) {
        ThucDonModel thucDonModel=thucDonModells.get(position);
        holder.txtTenThucDon.setText(thucDonModel.getTenthucdon());
        holder.recyclerMonAn.setLayoutManager(new LinearLayoutManager(context));
        Adapter_MonAn adapter_monAn=new Adapter_MonAn(context,thucDonModel.getMonanModelList(),isuaquan,thucDonModel,maquan,txtTongTien);
        holder.recyclerMonAn.setAdapter(adapter_monAn);
        adapter_monAn.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return thucDonModells.size();
    }


}
