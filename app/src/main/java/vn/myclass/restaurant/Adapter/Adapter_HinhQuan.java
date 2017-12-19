package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.ImagesNicer;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 12/18/2017.
 */

public class Adapter_HinhQuan extends RecyclerView.Adapter<Adapter_HinhQuan.ViewHolderHinhQuan> {

    Context context;
    int resource;
    String maquan;
    List<String>listlinkHinh;
    public Adapter_HinhQuan(Context context, int resource,String maquan,List<String>listlinkHinh) {
        this.context=context;
        this.resource=resource;
        this.maquan=maquan;
        this.listlinkHinh=listlinkHinh;
    }

    public class ViewHolderHinhQuan extends RecyclerView.ViewHolder {
        ImageView imgView,imgXoa;
        public ViewHolderHinhQuan(View itemView) {
            super(itemView);
            imgView= (ImageView) itemView.findViewById(R.id.imgHinhDuocChonBinhLuan);
            imgXoa= (ImageView) itemView.findViewById(R.id.imgDelete);
        }
    }
    @Override
    public Adapter_HinhQuan.ViewHolderHinhQuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhQuan viewHolderHinhQuan=new ViewHolderHinhQuan(view);
        return viewHolderHinhQuan;
    }

    @Override
    public void onBindViewHolder(final Adapter_HinhQuan.ViewHolderHinhQuan holder, final int position) {
        final String hinhString=listlinkHinh.get(position);
//        Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(hinhduocchon, 300);


        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh").child(hinhString);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 200);
                holder.imgView.setImageBitmap(converetdImage);
            }
        });



        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.create();
                alertBuilder.setMessage(R.string.XoaHinh);
                alertBuilder.setPositiveButton(R.string.DongY, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeIteminView(hinhString);
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
        return listlinkHinh.size();
    }
    private void removeIteminView(String hinhduocchon){
        int curentPosition=listlinkHinh.indexOf(hinhduocchon);
        listlinkHinh.remove(curentPosition);
        DatabaseReference nodeRoot= FirebaseDatabase.getInstance().getReference();
        nodeRoot.child("hinhanhquanans").child(maquan).removeValue();
        for (String linkhinh:listlinkHinh){
            nodeRoot.child("hinhanhquanans").child(maquan).push().setValue(linkhinh);
        }
        notifyItemRemoved(curentPosition);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }



}
