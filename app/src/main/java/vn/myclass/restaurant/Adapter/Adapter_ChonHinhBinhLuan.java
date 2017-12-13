package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.List;

import vn.myclass.restaurant.Model.ChonHinhBinhLuanModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 11/23/2017.
 * Chọn list hình ảnh theo đường dẫn trong máy
 * không dùng list này nữa
 */

public class Adapter_ChonHinhBinhLuan extends RecyclerView.Adapter<Adapter_ChonHinhBinhLuan.ViewHolderChonHinhBL> {

    Context context;
    int resource;
    List<ChonHinhBinhLuanModel>listDuongdan;
    public Adapter_ChonHinhBinhLuan(Context context, int resource, List<ChonHinhBinhLuanModel>listDuongdan) {
        this.context=context;
        this.resource=resource;
        this.listDuongdan=listDuongdan;
    }

    public class ViewHolderChonHinhBL extends RecyclerView.ViewHolder {
        ImageView imageView;
        CheckBox checkBox;
        public ViewHolderChonHinhBL(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxChonHinhBinhLuan);
        }
    }
    @Override
    public Adapter_ChonHinhBinhLuan.ViewHolderChonHinhBL onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderChonHinhBL viewHolderChonHinhBL=new ViewHolderChonHinhBL(view);
        return viewHolderChonHinhBL;
    }

    @Override
    public void onBindViewHolder(Adapter_ChonHinhBinhLuan.ViewHolderChonHinhBL holder, final int position) {
        final ChonHinhBinhLuanModel chonHinhBinhLuanModel=listDuongdan.get(position);
        Uri uri=Uri.parse(chonHinhBinhLuanModel.getDuongdan());
        holder.imageView.setImageURI(uri);
        holder.checkBox.setChecked(chonHinhBinhLuanModel.isIscheck());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox= (CheckBox) v;
                listDuongdan.get(position).setIscheck(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDuongdan.size();
    }


}
