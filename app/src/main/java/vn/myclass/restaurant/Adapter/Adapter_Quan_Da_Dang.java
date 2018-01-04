package vn.myclass.restaurant.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import vn.myclass.restaurant.Model.BinhLuanModel;
import vn.myclass.restaurant.Model.ChiNhanhQuanAnModel;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.Model.ThanhVienModel;
import vn.myclass.restaurant.R;
import vn.myclass.restaurant.View.ChiTietQuanAn_Activity;
import vn.myclass.restaurant.View.SuaQuanAn_Activity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by boybe on 12/13/2017.
 */

public class Adapter_Quan_Da_Dang extends RecyclerView.Adapter<Adapter_Quan_Da_Dang.ViewHolderQuanDaDang> {

    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;
    String mauser;
    List<String> listQuanDaDang;

    public Adapter_Quan_Da_Dang(Context context, int resource, List<QuanAnModel> quanAnModelList, String mauser, List<String> listQuanDaDang) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
        this.mauser = mauser;
        this.listQuanDaDang = listQuanDaDang;
    }

    public class ViewHolderQuanDaDang extends RecyclerView.ViewHolder {
        TextView txtTenQuananOdau;
        TextView txtDiemTrungBinhQuanAn, txtDiaChiQuanAnODau, txtKhoanCachQuanAnODau;
        CardView cardView;
        LinearLayout lnKhungSua;
        Button btnSuaQuan, btnXoaQuan;

        public ViewHolderQuanDaDang(View itemView) {
            super(itemView);
            txtTenQuananOdau = itemView.findViewById(R.id.txtTenquananOdau);
            txtDiaChiQuanAnODau = itemView.findViewById(R.id.txtDiachiquananOdau);
            txtDiemTrungBinhQuanAn = itemView.findViewById(R.id.txtDiemtrungbinhQuanAn);
            txtKhoanCachQuanAnODau = itemView.findViewById(R.id.txtKhoangcachquananODau);
            cardView = (CardView) itemView.findViewById(R.id.cardViewOdau);
            lnKhungSua = itemView.findViewById(R.id.lnkhungSua);
            btnSuaQuan = itemView.findViewById(R.id.btnSuaQuan);
            btnXoaQuan = itemView.findViewById(R.id.btnXoaQuan);
        }
    }

    @Override
    public Adapter_Quan_Da_Dang.ViewHolderQuanDaDang onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolderQuanDaDang viewHolderQuanDaDang = new ViewHolderQuanDaDang(view);
        return viewHolderQuanDaDang;
    }

    @Override
    public void onBindViewHolder(final Adapter_Quan_Da_Dang.ViewHolderQuanDaDang holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);
        holder.txtTenQuananOdau.setText(quanAnModel.getTenquanan());
        double tongdiemquanan = 0;
        //tổng điểm trung bình của bình luận và tổng số hình bình luận
        if (quanAnModel.getBinhLuanModelList().size() > 0) {
            for (BinhLuanModel binhLuanModel1 : quanAnModel.getBinhLuanModelList()) {
                tongdiemquanan += binhLuanModel1.getChamdiem();
                Log.d("kiemtra14bl", binhLuanModel1.getChamdiem() + "");
            }
            double diemtrungbinhquanan = tongdiemquanan / (quanAnModel.getBinhLuanModelList().size());
            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f", diemtrungbinhquanan));
        } else {
            holder.txtDiemTrungBinhQuanAn.setText("0");
        }

        //Lấy địa chỉ quán ăn và hiển thị địa chỉ và km
        if (quanAnModel.getChiNhanhQuanAnModelList().size() > 0) {
            // lấy thằng đầu tiên làm đối tượng so sánh
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()) {
                if (chiNhanhQuanAnModelTam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()) {
                    chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                }
            }
            holder.txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModelTam.getDiachi());
            holder.txtKhoanCachQuanAnODau.setText(String.format("%.1f", chiNhanhQuanAnModelTam.getKhoangcach()) + "km");

        }
        holder.btnXoaQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.create();
                alertBuilder.setMessage(R.string.XoaQuan);
                alertBuilder.setPositiveButton(R.string.DongY, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        removeIteminView(quanAnModel);
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
        holder.btnSuaQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.create();
                alertBuilder.setMessage(R.string.SuaQuan);
                alertBuilder.setPositiveButton(R.string.DongY, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent isuaQuan=new Intent(context,SuaQuanAn_Activity.class);
                        isuaQuan.putExtra("quanan",quanAnModel);
                        context.startActivity(isuaQuan);
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ichitietQuanAn=new Intent(context, ChiTietQuanAn_Activity.class);
                ichitietQuanAn.putExtra("quanan",quanAnModel);
                context.startActivity(ichitietQuanAn);

            }
        });
    }

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }
    private void removeIteminView(QuanAnModel quanAnModel){
        int curentPosition=quanAnModelList.indexOf(quanAnModel);
        quanAnModelList.remove(curentPosition);
        listQuanDaDang.remove(curentPosition);
        DatabaseReference nodeRoot=FirebaseDatabase.getInstance().getReference();
        nodeRoot.child("thanhviens").child(mauser).child("maquan").setValue(listQuanDaDang);
        nodeRoot.child("quanans").child(quanAnModel.getMaquanan()).removeValue();
        nodeRoot.child("chinhanhquanans").child(quanAnModel.getMaquanan()).removeValue();
        nodeRoot.child("binhluans").child(quanAnModel.getMaquanan()).removeValue();
        nodeRoot.child("hinhanhquanans").child(quanAnModel.getMaquanan()).removeValue();
        notifyItemRemoved(curentPosition);
    }



}
