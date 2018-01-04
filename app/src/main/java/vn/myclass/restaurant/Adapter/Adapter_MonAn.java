package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.DatMon;
import vn.myclass.restaurant.Model.MonanModel;
import vn.myclass.restaurant.Model.ThucDonModel;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.ChiTietQuanAn_Activity;

/**
 * Created by boybe on 11/29/2017.
 */

public class Adapter_MonAn extends RecyclerView.Adapter<Adapter_MonAn.ViewHolderMonAn> {
    Context context;
    List<MonanModel>monanModels;
    boolean isuaquan;
    ThucDonModel thucDonModel;
    String maquan;
    TextView txtTongTien;
    public static List<DatMon>datMonList =new ArrayList<>();
    int tongtien=0;
    public Adapter_MonAn(Context context, List<MonanModel>monanModels,boolean isuaquan,ThucDonModel thucDonModel,String maquan,TextView txtTongTien) {
        this.context=context;
        this.monanModels=monanModels;
        this.isuaquan=isuaquan;
        this.thucDonModel=thucDonModel;
        this.maquan=maquan;
        this.txtTongTien=txtTongTien;
    }

    public class ViewHolderMonAn extends RecyclerView.ViewHolder {
        ImageView imgGiamSoLuong,imgTangSoLuong;
        ImageButton imgXoathudon;
        TextView txtTenMonAn,txtSoLuong,txtGiatien;
        public ViewHolderMonAn(View itemView) {
            super(itemView);
            txtTenMonAn= (TextView) itemView.findViewById(R.id.txtTenMonAn);
            imgGiamSoLuong= (ImageView) itemView.findViewById(R.id.imgGiamSoLuong);
            imgTangSoLuong= (ImageView) itemView.findViewById(R.id.imgTangSoLuong);
            txtSoLuong= (TextView) itemView.findViewById(R.id.txtSoluongMon);
            txtGiatien= (TextView) itemView.findViewById(R.id.txtGiatienmonAn);
            imgXoathudon=itemView.findViewById(R.id.imgXoaThucDon);
        }
    }
    @Override
    public Adapter_MonAn.ViewHolderMonAn onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_layout_monan,parent,false);
        ViewHolderMonAn viewHolderMonAn=new ViewHolderMonAn(view);
        return viewHolderMonAn;
    }

    @Override
    public void onBindViewHolder(final Adapter_MonAn.ViewHolderMonAn holder, int position) {
        datMonList.clear();
        final MonanModel monanModel=monanModels.get(position);
        holder.txtTenMonAn.setText("-"+monanModel.getTenmon());
        // gán giá trị vào View để không bị biến final
        final int giagoc= Integer.parseInt(String.valueOf(monanModel.getGiatien()));
        holder.txtSoLuong.setTag(0);
        holder.imgTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumberFormat numberFormat = new DecimalFormat("###,###");
                int dem =Integer.parseInt(holder.txtSoLuong.getTag().toString());
                dem++;
                int giasau=0;
                giasau=dem*giagoc;
                holder.txtSoLuong.setText(dem+"");
                holder.txtSoLuong.setTag(dem);
                holder.txtGiatien.setText(numberFormat.format(giasau)+"đ");


                DatMon datMonTag= (DatMon) holder.imgGiamSoLuong.getTag();
                if (datMonTag!=null){
                    Adapter_MonAn.datMonList.remove(datMonTag);
                }

                DatMon datMon=new DatMon();
                datMon.setSoLuong(dem);
                datMon.setTenMonAn(monanModel.getTenmon());
                datMon.setGiaTien(giasau);
                holder.imgGiamSoLuong.setTag(datMon);
                Adapter_MonAn.datMonList.add(datMon);
                tongtien=0;
                for (DatMon datMon1:Adapter_MonAn.datMonList){
                    tongtien+=datMon1.getGiaTien();
                    Log.d("kiemtratang",datMon1.getTenMonAn()+"-"+datMon1.getSoLuong()+"-"+datMon1.getGiaTien()+"-"+tongtien);
                }
                txtTongTien.setText(numberFormat.format(tongtien)+"đ");

            }
        });
        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberFormat numberFormat = new DecimalFormat("###,###");
                int dem =Integer.parseInt(holder.txtSoLuong.getTag().toString());
                int giasau = 0;
                if (dem!=0){
                    dem--;
                    giasau=dem*giagoc;

                    DatMon datMonTag= (DatMon) holder.imgGiamSoLuong.getTag();
                    if (datMonTag!=null){
                        Adapter_MonAn.datMonList.remove(datMonTag);
                    }
                    DatMon datMon=new DatMon();
                    datMon.setSoLuong(dem);
                    datMon.setTenMonAn(monanModel.getTenmon());
                    datMon.setGiaTien(giasau);
                    holder.imgGiamSoLuong.setTag(datMon);
                    if (dem!=0){
                        Adapter_MonAn.datMonList.add(datMon);
                    }
                    Log.d("kiemtra",Adapter_MonAn.datMonList.size()+"");
                    tongtien=0;
                    for (DatMon datMon1:Adapter_MonAn.datMonList){
                        tongtien+=datMon1.getGiaTien();
                        Log.d("kiemtragiam",datMon1.getTenMonAn()+"-"+datMon1.getSoLuong()+"-"+tongtien);
                    }
                }
                holder.txtSoLuong.setText(dem+"");
                holder.txtSoLuong.setTag(dem);
                holder.txtGiatien.setText(numberFormat.format(giasau)+"đ");
                txtTongTien.setText(numberFormat.format(tongtien)+"đ");
            }
        });
        if (isuaquan){
            holder.imgGiamSoLuong.setVisibility(View.GONE);
            holder.imgTangSoLuong.setVisibility(View.GONE);
            holder.txtSoLuong.setVisibility(View.GONE);
            holder.txtGiatien.setVisibility(View.GONE);
            holder.imgXoathudon.setVisibility(View.VISIBLE);
            holder.imgXoathudon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.create();
                    alertBuilder.setMessage(R.string.XoaThucDon);
                    alertBuilder.setPositiveButton(R.string.DongY, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removePosition(monanModel);
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    }).setNegativeButton(R.string.KhongDongY, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "không Đồng ý", Toast.LENGTH_SHORT).show();
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    });
                    alertBuilder.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return monanModels.size();
    }
    private void removePosition(MonanModel monanModel){
        int currentposition=monanModels.indexOf(monanModel);
        monanModels.remove(currentposition);

        DatabaseReference nodeRoot= FirebaseDatabase.getInstance().getReference();
        nodeRoot.child("thucdonquanans").child(maquan).child(thucDonModel.getMathucdon()).removeValue();
        for (int i=0;i<monanModels.size();i++){
            MonanModel monanModel1moi = new MonanModel();
            monanModel1moi=monanModels.get(i);
            nodeRoot.child("thucdonquanans").child(maquan).child(thucDonModel.getMathucdon()).push().setValue(monanModel1moi);
        }
        notifyItemRemoved(currentposition);
    }


}
