package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.BitSet;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.ChiTietQuanAn_Activity;
import vn.myclass.restaurant.View.Trangchu_Activity;

/**
 * Created by boybe on 10/29/2017.
 */

public class AdapterRecycler_Odau extends RecyclerView.Adapter<AdapterRecycler_Odau.ViewHodel> {

    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;
    double khoangcach;


    public AdapterRecycler_Odau(Context context,List<QuanAnModel> quanAnModelList,int resource,double khoangcach){
            this.quanAnModelList=quanAnModelList;
            this.resource=resource;
            this.context=context;
            this.khoangcach=khoangcach;
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        TextView txtTenQuananOdau,txtTieudebinhluan2,txtTieudebinhluan,txtNodungbinhluan2,txtNodungbinhluan,txtTenNguoiDungbinhluan,txtTennguoidungbinhluan2;
        TextView txtChamDiemBinhLuan,txtChamDiemBinhLuan2,txtTongBinhLuan,txtTongHinhBinhLuan,txtDiemTrungBinhQuanAn,txtDiaChiQuanAnODau,txtKhoanCachQuanAnODau;
        Button btnDatMonOdau;
        ImageView imageHinhQuanAnODau;
        CircleImageView cicleImageUser2,cicleImageUser;
        LinearLayout containerBinhLuan,containerBinhLuan2;
        CardView cardView;
        public ViewHodel(View itemView) {
            super(itemView);
            txtTenQuananOdau= (TextView) itemView.findViewById(R.id.txtTenquananOdau);
            btnDatMonOdau= (Button) itemView.findViewById(R.id.btnDatmonOdau);
            imageHinhQuanAnODau= (ImageView) itemView.findViewById(R.id.imgHinhQuananOdau);
            txtTieudebinhluan= (TextView) itemView.findViewById(R.id.txtTieudebinhluan);
            txtTieudebinhluan2= (TextView) itemView.findViewById(R.id.txtTieudebinhluan2);
            txtNodungbinhluan= (TextView) itemView.findViewById(R.id.txtNoiDungbinhluan);
            txtNodungbinhluan2= (TextView) itemView.findViewById(R.id.txtNoiDungbinhluan2);
            txtChamDiemBinhLuan= (TextView) itemView.findViewById(R.id.txtChamdiembinhluan);
            txtChamDiemBinhLuan2= (TextView) itemView.findViewById(R.id.txtChamdiembinhluan2);
            txtTongBinhLuan= (TextView) itemView.findViewById(R.id.txtTongbl);
            txtTongHinhBinhLuan= (TextView) itemView.findViewById(R.id.txtTonghinhanhbl);
            txtDiemTrungBinhQuanAn= (TextView) itemView.findViewById(R.id.txtDiemtrungbinhQuanAn);
            containerBinhLuan= (LinearLayout) itemView.findViewById(R.id.containerBinhluan);
            containerBinhLuan2= (LinearLayout) itemView.findViewById(R.id.containerBinhluan2);
            cicleImageUser = (CircleImageView) itemView.findViewById(R.id.circleimageviewUser);
            cicleImageUser2 = (CircleImageView) itemView.findViewById(R.id.circleimageviewUser2);
            txtDiaChiQuanAnODau= (TextView) itemView.findViewById(R.id.txtDiachiquananOdau);
            txtKhoanCachQuanAnODau= (TextView) itemView.findViewById(R.id.txtKhoangcachquananODau);
            cardView= (CardView) itemView.findViewById(R.id.cardViewOdau);
            txtTenNguoiDungbinhluan=itemView.findViewById(R.id.txtTenNguoiBinhLuan);
            txtTennguoidungbinhluan2=itemView.findViewById(R.id.txtTenNguoiBinhLuan2);
        }
    }
    @Override
    public AdapterRecycler_Odau.ViewHodel onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHodel viewHodel=new ViewHodel(view);
        return viewHodel;
    }

    @Override
    public void onBindViewHolder(final AdapterRecycler_Odau.ViewHodel holder, int position) {
//        final QuanAnModel quanAnModel=quanAnModelList.get(position);
//        holder.txtTenQuananOdau.setText(quanAnModel.getTenquanan());
//
//        if (quanAnModel.isGiaohang()){
//            holder.btnDatMonOdau.setVisibility(View.VISIBLE);
//        }
//        Log.d("SizeHinh",quanAnModel.getHinhanhquanan().size()+"");
//        if (quanAnModel.getBitmapList().size()>0){
//            holder.imageHinhQuanAnODau.setImageBitmap(quanAnModel.getBitmapList().get(0));
//
//        }
//        //Lấy danh sách bình luận của quán ăn
//        if(quanAnModel.getBinhLuanModelList().size() > 0){
//            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModelList().get(0);
//            holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
//            holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung());
//            holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem()+"");
//            holder.txtTenNguoiDungbinhluan.setText(binhLuanModel.getThanhVienModel().getHoten());
//            holder.containerBinhLuan2.setVisibility(View.GONE);
//
//            setHinhAnhBinhLuan(holder.cicleImageUser,binhLuanModel.getThanhVienModel().getHinhanh());
//            if(quanAnModel.getBinhLuanModelList().size() >= 2){
//                holder.containerBinhLuan2.setVisibility(View.VISIBLE);
//                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhLuanModelList().get(1);
//                holder.txtTieudebinhluan2.setText(binhLuanModel2.getTieude());
//                holder.txtNodungbinhluan2.setText(binhLuanModel2.getNoidung());
//                holder.txtChamDiemBinhLuan2.setText(binhLuanModel2.getChamdiem()+"");
//                holder.txtTennguoidungbinhluan2.setText(binhLuanModel2.getThanhVienModel().getHoten());
//                setHinhAnhBinhLuan(holder.cicleImageUser2,binhLuanModel2.getThanhVienModel().getHinhanh());
//            }
//            holder.txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size()+"");
//            int tonghinhbinhluan=0;
//            double tongdiemquanan=0;
//            //tổng điểm trung bình của bình luận và tổng số hình bình luận
//            for (BinhLuanModel binhLuanModel1:quanAnModel.getBinhLuanModelList()){
//                tonghinhbinhluan+=binhLuanModel1.getHinhanhBinhLuanList().size();
//                tongdiemquanan+=binhLuanModel1.getChamdiem();
//            }
//            double diemtrungbinhquanan=tongdiemquanan/(quanAnModel.getBinhLuanModelList().size());
//            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f",diemtrungbinhquanan));
//            if (tonghinhbinhluan>0){
//                holder.txtTongHinhBinhLuan.setText(tonghinhbinhluan+"");
//            }
//
//        }else{
//            holder.containerBinhLuan.setVisibility(View.GONE);
//            holder.containerBinhLuan2.setVisibility(View.GONE);
//        }
//        //Lấy địa chỉ quán ăn và hiển thị địa chỉ và km
//        if (quanAnModel.getChiNhanhQuanAnModelList().size()>0){
//            // lấy thằng đầu tiên làm đối tượng so sánh
//            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam=quanAnModel.getChiNhanhQuanAnModelList().get(0);
//            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel:quanAnModel.getChiNhanhQuanAnModelList()){
//                if (chiNhanhQuanAnModelTam.getKhoangcach()>chiNhanhQuanAnModel.getKhoangcach()){
//                    chiNhanhQuanAnModelTam=chiNhanhQuanAnModel;
//                }
//            }
//            holder.txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModelTam.getDiachi());
//            holder.txtKhoanCachQuanAnODau.setText(String.format("%.1f",chiNhanhQuanAnModelTam.getKhoangcach())+"km" );
//
//        }
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ichitietQuanAn=new Intent(context, ChiTietQuanAn_Activity.class);
//                ichitietQuanAn.putExtra("quanan",quanAnModel);
//                context.startActivity(ichitietQuanAn);
//
//            }
//        });

        QuanAnModel quanAnModel=quanAnModelList.get(position);
        if (quanAnModel.getChiNhanhQuanAnModelList().size()>0){
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam=quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel:quanAnModel.getChiNhanhQuanAnModelList()){
                if (chiNhanhQuanAnModelTam.getKhoangcach()>chiNhanhQuanAnModel.getKhoangcach()){
                    chiNhanhQuanAnModelTam=chiNhanhQuanAnModel;
                }
            }
            //lấy các quán trong phạm vi 10000km
            if (chiNhanhQuanAnModelTam.getKhoangcach()>khoangcach){
                quanAnModelList.remove(position);
            }else {
                layQuanAnOgan(holder,position);
            }
        }

    }
    public  void layQuanAnOgan(final AdapterRecycler_Odau.ViewHodel holder, int position){
        final QuanAnModel quanAnModel=quanAnModelList.get(position);

        holder.txtTenQuananOdau.setText(quanAnModel.getTenquanan());

        if (quanAnModel.isGiaohang()){
            holder.btnDatMonOdau.setVisibility(View.VISIBLE);
        }
        Log.d("SizeHinh",quanAnModel.getHinhanhquanan().size()+"");
        if (quanAnModel.getBitmapList().size()>0){
            holder.imageHinhQuanAnODau.setImageBitmap(quanAnModel.getBitmapList().get(0));
        }
        //Lấy danh sách bình luận của quán ăn
        if(quanAnModel.getBinhLuanModelList().size() > 0){
            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModelList().get(0);
            holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
            holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung());
            holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem()+"");
            holder.txtTenNguoiDungbinhluan.setText(binhLuanModel.getThanhVienModel().getHoten());
            holder.containerBinhLuan2.setVisibility(View.GONE);

            setHinhAnhBinhLuan(holder.cicleImageUser,binhLuanModel.getThanhVienModel().getHinhanh());
            if(quanAnModel.getBinhLuanModelList().size() >= 2){
                holder.containerBinhLuan2.setVisibility(View.VISIBLE);
                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhLuanModelList().get(1);
                holder.txtTieudebinhluan2.setText(binhLuanModel2.getTieude());
                holder.txtNodungbinhluan2.setText(binhLuanModel2.getNoidung());
                holder.txtChamDiemBinhLuan2.setText(binhLuanModel2.getChamdiem()+"");
                holder.txtTennguoidungbinhluan2.setText(binhLuanModel2.getThanhVienModel().getHoten());
                setHinhAnhBinhLuan(holder.cicleImageUser2,binhLuanModel2.getThanhVienModel().getHinhanh());
            }
            holder.txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size()+"");
            int tonghinhbinhluan=0;
            double tongdiemquanan=0;
            //tổng điểm trung bình của bình luận và tổng số hình bình luận
            for (BinhLuanModel binhLuanModel1:quanAnModel.getBinhLuanModelList()){
                tonghinhbinhluan+=binhLuanModel1.getHinhanhBinhLuanList().size();
                tongdiemquanan+=binhLuanModel1.getChamdiem();
            }
            double diemtrungbinhquanan=tongdiemquanan/(quanAnModel.getBinhLuanModelList().size());
            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f",diemtrungbinhquanan));
            if (tonghinhbinhluan>0){
                holder.txtTongHinhBinhLuan.setText(tonghinhbinhluan+"");
            }

        }else{
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
        }
        //Lấy địa chỉ quán ăn và hiển thị địa chỉ và km
        if (quanAnModel.getChiNhanhQuanAnModelList().size()>0){
            // lấy thằng đầu tiên làm đối tượng so sánh
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam=quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel:quanAnModel.getChiNhanhQuanAnModelList()){
                if (chiNhanhQuanAnModelTam.getKhoangcach()>chiNhanhQuanAnModel.getKhoangcach()){
                    chiNhanhQuanAnModelTam=chiNhanhQuanAnModel;
                }
            }
            holder.txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModelTam.getDiachi());
            holder.txtKhoanCachQuanAnODau.setText(String.format("%.1f",chiNhanhQuanAnModelTam.getKhoangcach())+"km" );

        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ichitietQuanAn=new Intent(context, ChiTietQuanAn_Activity.class);
                ichitietQuanAn.putExtra("quanan",quanAnModel);
                context.startActivity(ichitietQuanAn);

            }
        });
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
        return quanAnModelList.size();
    }


}
