package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 11/16/2017.
 */

public class Adapter_BinhLuan extends RecyclerView.Adapter<Adapter_BinhLuan.ViewHodel> {
    Context context;
    List<BinhLuanModel>binhLuanModelList;
    int layout;
    List<Bitmap>bitmapList;

    public Adapter_BinhLuan(Context context, List<BinhLuanModel>binhLuanModelList,int layout){
        this.context=context;
        this.binhLuanModelList=binhLuanModelList;
        this.layout=layout;
        bitmapList=new ArrayList<>();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView txtTieudeBinhLuan,txtNoidungBinhluan,txtChamDiembinhluan;
        RecyclerView recyclerViewHinhBinhluan;
        public ViewHodel(View itemView) {
            super(itemView);
            circleImageView= (CircleImageView) itemView.findViewById(R.id.circleimageviewUser);
            txtTieudeBinhLuan= (TextView) itemView.findViewById(R.id.txtTieudebinhluan);
            txtNoidungBinhluan= (TextView) itemView.findViewById(R.id.txtNoiDungbinhluan);
            txtChamDiembinhluan= (TextView) itemView.findViewById(R.id.txtChamdiembinhluan);
            recyclerViewHinhBinhluan= (RecyclerView) itemView.findViewById(R.id.recyle_hinhbinhluan);

        }
    }

    @Override
    public Adapter_BinhLuan.ViewHodel onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHodel viewHodel=new ViewHodel(view);
        return viewHodel;
    }

    @Override
    public void onBindViewHolder(final Adapter_BinhLuan.ViewHodel holder, int position) {
        final BinhLuanModel binhLuanModel=binhLuanModelList.get(position);
        holder.txtTieudeBinhLuan.setText(binhLuanModel.getTieude());
        holder.txtNoidungBinhluan.setText(binhLuanModel.getNoidung());
        holder.txtChamDiembinhluan.setText(binhLuanModel.getChamdiem()+"");
//
        setHinhAnhBinhLuan(holder.circleImageView,binhLuanModel.getThanhVienModel().getHinhanh());
//        Lấy ra list hình ảnh của  từng bình luậnbình luận
        for (String linkhinh : binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size()==binhLuanModel.getHinhanhBinhLuanList().size()){
                        AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan=new AdapterRecyclerHinhBinhLuan(context,R.layout.custom_layout_hinhbinhluan,bitmapList);
                        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(context,2);
                        holder.recyclerViewHinhBinhluan.setLayoutManager(layoutManager);
                        holder.recyclerViewHinhBinhluan.setAdapter(adapterRecyclerHinhBinhLuan);
                        adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }



    }
    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        int sobinhluan=binhLuanModelList.size();
        if (sobinhluan>=5){
            return 5;
        }else{
            return binhLuanModelList.size();
        }
    }


}
