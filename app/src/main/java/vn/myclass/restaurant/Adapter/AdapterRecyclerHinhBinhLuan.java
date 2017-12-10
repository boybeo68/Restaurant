package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.Model.ImagesNicer;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.HienThiChiTietBinhLuan_Activity;

/**
 * Created by boybe on 11/17/2017.
 */

public class AdapterRecyclerHinhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan> {

    Context context;
    int resource;
    List<Bitmap> listhinh;
    BinhLuanModel binhLuanModel;
    boolean ichitietBinhluan;

    public  AdapterRecyclerHinhBinhLuan(Context context, int resource, List<Bitmap> listhinh, BinhLuanModel binhLuanModel,boolean ichitietBinhluan){
        this.context=context;
        this.resource=resource;
        this.listhinh=listhinh;
        this.binhLuanModel=binhLuanModel;
        this.ichitietBinhluan=ichitietBinhluan;

    }
    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imghinhBinhluan;
        TextView txtSoHinhbinhluan;
        FrameLayout khungsoHinhbinhluan;

        public ViewHolderHinhBinhLuan(View itemView) {
            super(itemView);
            imghinhBinhluan= (ImageView) itemView.findViewById(R.id.imghinhBinhluan);
            txtSoHinhbinhluan= (TextView) itemView.findViewById(R.id.txtSoHinhBinhLuan);
            khungsoHinhbinhluan= (FrameLayout) itemView.findViewById(R.id.khungSoHinhBinhLuan);
        }
    }
    @Override
    public AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan=new ViewHolderHinhBinhLuan(view);
        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan holder, final int position) {
        Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(listhinh.get(position), 500);
        holder.imghinhBinhluan.setImageBitmap(converetdImage);

        if (!ichitietBinhluan){
            if (position==3){

                int sohinhconlai=listhinh.size()-4;
                if (sohinhconlai>0){
                    holder.khungsoHinhbinhluan.setVisibility(View.VISIBLE);
                    holder.txtSoHinhbinhluan.setText("+"+sohinhconlai);
                    holder.imghinhBinhluan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent ichitietBinhLuan=new Intent(context, HienThiChiTietBinhLuan_Activity.class);
                            ichitietBinhLuan.putExtra("binhluanmodel",binhLuanModel);
                            context.startActivity(ichitietBinhLuan);
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (!ichitietBinhluan){
            if (listhinh.size()<4){
                return listhinh.size();
            }else {
                return 4;
            }

        }else {
            return listhinh.size();
        }

    }


}
