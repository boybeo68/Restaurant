package vn.myclass.restaurant.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.Adapter.AdapterRecycler_Odau;
import vn.myclass.restaurant.Controller.Interface.Odau_interface;
import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 10/29/2017.
 */

public class Odau_Controller {
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecycler_Odau adapterRecycler_odau;
    int itemdaco = 3;

    public Odau_Controller(Context context) {
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void getDanhsachquananController(Context context,final NestedScrollView nestedScrollView, RecyclerView recyclerViewOdau, final ProgressBar progressBar, final Location vitrihientai,double khoangcach) {

        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewOdau.setLayoutManager(layoutManager);

        adapterRecycler_odau = new AdapterRecycler_Odau(context,quanAnModelList, R.layout.custom_layout_recycleview_odau,khoangcach);
        recyclerViewOdau.setAdapter(adapterRecycler_odau);
        progressBar.setVisibility(View.VISIBLE);
        // không sét sự kiên scroll của recyclerView vì nó nằm trong NestedScrollView sẽ không biết roll cái nào nếu recyc cũng để match parent

        final Odau_interface odau_interface = new Odau_interface() {
            @Override
            public void getDanhsachQuananModel(final QuanAnModel quanAnModel) {
//                Log.d("kiemtralist", quanAnModel.getTenquanan() + "");

                final List<Bitmap> bitmaps = new ArrayList<>();
                for (String linkHinh : quanAnModel.getHinhanhquanan()) {

                    StorageReference storageHinhanh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkHinh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                            quanAnModel.setBitmapList(bitmaps);
                            if (quanAnModel.getBitmapList().size() == quanAnModel.getHinhanhquanan().size()) {
                                quanAnModelList.add(quanAnModel);
                                adapterRecycler_odau.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }

            }
        };
        //sự kiên phải dưới interface để nó hiển thị lên trc
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if (scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()) {
                        itemdaco += 3;
                        quanAnModel.getDanhsachQuanAn(odau_interface, vitrihientai, itemdaco, itemdaco - 3);
                    }
                }
            }
        });

        quanAnModel.getDanhsachQuanAn(odau_interface, vitrihientai, itemdaco, 0);
    }
}
