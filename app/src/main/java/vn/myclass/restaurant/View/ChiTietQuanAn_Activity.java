package vn.myclass.restaurant.View;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import vn.myclass.restaurant.Model.QuanAnModel;
import vn.myclass.restaurant.R;

public class ChiTietQuanAn_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chi_tiet_quan_an);

        QuanAnModel quanAnModel=getIntent().getParcelableExtra("quanan");
        Log.d("kiemtraintent",quanAnModel.getTenquanan());
    }
}
