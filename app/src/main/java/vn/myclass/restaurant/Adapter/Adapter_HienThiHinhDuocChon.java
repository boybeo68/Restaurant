package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.myclass.restaurant.Model.ImagesNicer;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 11/27/2017.
 */

public class Adapter_HienThiHinhDuocChon extends RecyclerView.Adapter<Adapter_HienThiHinhDuocChon.ViewHolderHinhChon>{

    Context context;
    int resource;
    List<Bitmap>listhinhduocchon;
    public Adapter_HienThiHinhDuocChon(Context context, int resource, List<Bitmap>listhinhduocchon) {
        this.context=context;
        this.resource=resource;
        this.listhinhduocchon=listhinhduocchon;
    }

    public class ViewHolderHinhChon extends RecyclerView.ViewHolder {
        ImageView imgView,imgXoa;
        public ViewHolderHinhChon(View itemView) {
            super(itemView);
            imgView= (ImageView) itemView.findViewById(R.id.imgHinhDuocChonBinhLuan);
            imgXoa= (ImageView) itemView.findViewById(R.id.imgDelete);

        }
    }
    @Override
    public Adapter_HienThiHinhDuocChon.ViewHolderHinhChon onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhChon viewHolderHinhChon=new ViewHolderHinhChon(view);
        return viewHolderHinhChon;
    }

    @Override
    public void onBindViewHolder(Adapter_HienThiHinhDuocChon.ViewHolderHinhChon holder, int position) {
        Bitmap hinhduocchon=listhinhduocchon.get(position);
        Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(hinhduocchon, 300);
        holder.imgView.setImageBitmap(converetdImage);
        holder.imgXoa.setTag(position);
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri= (int) v.getTag();
                listhinhduocchon.remove(vitri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {

        return listhinhduocchon.size();
    }


}
