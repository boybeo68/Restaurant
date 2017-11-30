package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.Model.DatMon;
import vn.myclass.restaurant.Model.MonanModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 11/29/2017.
 */

public class Adapter_MonAn extends RecyclerView.Adapter<Adapter_MonAn.ViewHolderMonAn> {
    Context context;
    List<MonanModel>monanModels;
    public static List<DatMon>datMonList =new ArrayList<>();
    public Adapter_MonAn(Context context, List<MonanModel>monanModels) {
        this.context=context;
        this.monanModels=monanModels;
    }

    public class ViewHolderMonAn extends RecyclerView.ViewHolder {
        ImageView imgGiamSoLuong,imgTangSoLuong;

        TextView txtTenMonAn,txtSoLuong,txtGiatien;
        public ViewHolderMonAn(View itemView) {
            super(itemView);
            txtTenMonAn= (TextView) itemView.findViewById(R.id.txtTenMonAn);
            imgGiamSoLuong= (ImageView) itemView.findViewById(R.id.imgGiamSoLuong);
            imgTangSoLuong= (ImageView) itemView.findViewById(R.id.imgTangSoLuong);
            txtSoLuong= (TextView) itemView.findViewById(R.id.txtSoluongMon);
            txtGiatien= (TextView) itemView.findViewById(R.id.txtGiatienmonAn);
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
                holder.imgGiamSoLuong.setTag(datMon);
                Adapter_MonAn.datMonList.add(datMon);

                for (DatMon datMon1:Adapter_MonAn.datMonList){
                    Log.d("kiemtra",datMon1.getTenMonAn()+"-"+datMon1.getSoLuong());
                }

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
//                        if (dem==0){
//                            DatMon datMon2 = (DatMon) holder.imgGiamSoLuong.getTag();
//                            Adapter_MonAn.datMonList.remove(datMon2);
//                        }
                    }

                    DatMon datMon=new DatMon();
                    datMon.setSoLuong(dem);
                    datMon.setTenMonAn(monanModel.getTenmon());
                    holder.imgGiamSoLuong.setTag(datMon);
                    if (dem!=0){
                        Adapter_MonAn.datMonList.add(datMon);
                    }


                    Log.d("kiemtra",Adapter_MonAn.datMonList.size()+"");
                    for (DatMon datMon1:Adapter_MonAn.datMonList){
                        Log.d("kiemtra",datMon1.getTenMonAn()+"-"+datMon1.getSoLuong());
                    }
                }
                holder.txtSoLuong.setText(dem+"");
                holder.txtSoLuong.setTag(dem);
                holder.txtGiatien.setText(numberFormat.format(giasau)+"đ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return monanModels.size();
    }


}
