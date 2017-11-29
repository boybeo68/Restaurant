package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.myclass.restaurant.Model.MonanModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 11/29/2017.
 */

public class Adapter_MonAn extends RecyclerView.Adapter<Adapter_MonAn.ViewHolderMonAn> {
    Context context; List<MonanModel>monanModels;
    public Adapter_MonAn(Context context, List<MonanModel>monanModels) {
        this.context=context;
        this.monanModels=monanModels;
    }

    public class ViewHolderMonAn extends RecyclerView.ViewHolder {
        TextView txtTenMonAn;
        public ViewHolderMonAn(View itemView) {
            super(itemView);
            txtTenMonAn= (TextView) itemView.findViewById(R.id.txtTenMonAn);
        }
    }
    @Override
    public Adapter_MonAn.ViewHolderMonAn onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_layout_monan,parent,false);
        ViewHolderMonAn viewHolderMonAn=new ViewHolderMonAn(view);
        return viewHolderMonAn;
    }

    @Override
    public void onBindViewHolder(Adapter_MonAn.ViewHolderMonAn holder, int position) {
        MonanModel monanModel=monanModels.get(position);
        holder.txtTenMonAn.setText("-"+monanModel.getTenmon());
    }

    @Override
    public int getItemCount() {
        return monanModels.size();
    }


}
