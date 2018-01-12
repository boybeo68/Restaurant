package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.myclass.restaurant.Model.ImagesNicer;
import vn.myclass.restaurant.Model.TienIchModel;
import vn.myclass.restaurant.Model.WifiQuanAnModel;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.ChiTietQuanAn_Activity;

/**
 * Created by boybe on 1/12/2018.
 */

public class AdapterTienich extends RecyclerView.Adapter<AdapterTienich.ViewHodelTienIch> {

    Context context; int resource; List<TienIchModel> tienIchModelList;
    public AdapterTienich(Context context, int resource, List<TienIchModel> tienIchModelList) {
        this.context=context;
        this.resource=resource;
        this.tienIchModelList=tienIchModelList;
    }

    public class ViewHodelTienIch extends RecyclerView.ViewHolder {
        TextView txtTenTienich;
        ImageView imghinhTienich;
        public ViewHodelTienIch(View itemView) {
            super(itemView);
            txtTenTienich=itemView.findViewById(R.id.txtTentienich);
            imghinhTienich=itemView.findViewById(R.id.hinhtienich);
        }
    }
    @Override
    public AdapterTienich.ViewHodelTienIch onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHodelTienIch viewHodelTienIch=new ViewHodelTienIch(view);
        return viewHodelTienIch;
    }

    @Override
    public void onBindViewHolder(final AdapterTienich.ViewHodelTienIch holder, int position) {
        TienIchModel tienIchModel=tienIchModelList.get(position);
        holder.txtTenTienich.setText(tienIchModel.getTentienich());
        setHinhAnhTienich(holder.imghinhTienich,tienIchModel.getHinhtienich());
    }

    @Override
    public int getItemCount() {
        return tienIchModelList.size();
    }
    private void setHinhAnhTienich(final ImageView circleImageView, String linkhinh){
        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                Bitmap converetdImage = ImagesNicer.getResizedBitmapLength(bitmap, 100);
                circleImageView.setImageBitmap(converetdImage);
            }
        });
    }



}
