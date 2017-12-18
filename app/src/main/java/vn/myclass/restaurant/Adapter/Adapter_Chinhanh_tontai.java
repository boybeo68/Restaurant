package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 12/17/2017.
 */

public class Adapter_Chinhanh_tontai extends RecyclerView.Adapter<Adapter_Chinhanh_tontai.ViewHolderChinanh>{
    Context context; List<ChiNhanhQuanAnModel>listChinhanh;int resource;String maquan;

    public Adapter_Chinhanh_tontai(Context context, List<ChiNhanhQuanAnModel>listChinhanh, int resource,String maquan) {
        this.context=context;
        this.listChinhanh=listChinhanh;
        this.resource=resource;
        this.maquan=maquan;
    }

    public class ViewHolderChinanh extends RecyclerView.ViewHolder {
        TextView txtTenChiNhanhTontai;
        ImageButton imgXoaChiNhanh;
        public ViewHolderChinanh(View itemView) {
            super(itemView);
            txtTenChiNhanhTontai=itemView.findViewById(R.id.txtTenChiNhanhDatontai);
            imgXoaChiNhanh= (ImageButton) itemView.findViewById(R.id.imgXoaChiNhanh);

        }
    }

    @Override
    public Adapter_Chinhanh_tontai.ViewHolderChinanh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderChinanh viewHolderChinanh=new ViewHolderChinanh(view);
        return viewHolderChinanh;
    }

    @Override
    public void onBindViewHolder(Adapter_Chinhanh_tontai.ViewHolderChinanh holder, int position) {
        final ChiNhanhQuanAnModel chinhanh=listChinhanh.get(position);
        holder.txtTenChiNhanhTontai.setText(chinhanh.getDiachi());
        holder.imgXoaChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.create();
                alertBuilder.setMessage(R.string.XoaChiNhanh);
                alertBuilder.setPositiveButton(R.string.DongY, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        removeIteminView(chinhanh);
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                }).setNegativeButton(R.string.KhongDongY, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
                alertBuilder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listChinhanh.size();
    }
    private void removeIteminView(ChiNhanhQuanAnModel chiNhanh){
        int curentPosition=listChinhanh.indexOf(chiNhanh);
        listChinhanh.remove(curentPosition);
        DatabaseReference nodeRoot= FirebaseDatabase.getInstance().getReference();
        nodeRoot.child("chinhanhquanans").child(maquan).removeValue();
        for (int i=0;i<listChinhanh.size();i++){
            ChiNhanhQuanAnModel chiNhanhQuanAnModel = new ChiNhanhQuanAnModel();
            chiNhanhQuanAnModel=listChinhanh.get(i);
            nodeRoot.child("chinhanhquanans").child(maquan).push().setValue(chiNhanhQuanAnModel);
        }

        notifyItemRemoved(curentPosition);
    }


}
