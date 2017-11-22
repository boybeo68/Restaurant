package vn.myclass.restaurant.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import vn.myclass.restaurant.Controller.DanDuongToiQuanAnController;
import vn.myclass.restaurant.R;

public class DanDuongQuanAn_Activity extends AppCompatActivity implements OnMapReadyCallback{
    GoogleMap googleMap;
    MapFragment mapFragment;
    double latitude,longitude;
    String tenquan;
    SharedPreferences sharedPreferences;
    Location vitrihientai;
    DanDuongToiQuanAnController danDuongToiQuanAnController;
    String duongdan="";

    //https://maps.googleapis.com/maps/api/directions/json?origin=111,222&destination=333,444&key=AIzaSyDgujYQDYYXGBfKdCJB9MK0HmfsCssAmWY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dan_duong_quan_an);
        latitude=getIntent().getDoubleExtra("latitude",0);
        longitude=getIntent().getDoubleExtra("longitude",0);
        tenquan=getIntent().getStringExtra("tenquan");
        mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Vị trí người dùng
        sharedPreferences=getSharedPreferences("toado", Context.MODE_PRIVATE);
        vitrihientai=new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        duongdan="https://maps.googleapis.com/maps/api/directions/json?origin="+vitrihientai.getLatitude()+","+vitrihientai.getLongitude()+"&destination="+latitude+","+longitude+"&key=AIzaSyBGSYj-6AO-z79XbvjTLudVk8bFsIk1jSU";
        danDuongToiQuanAnController=new DanDuongToiQuanAnController();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        googleMap.clear();
        LatLng latLng = new LatLng(vitrihientai.getLatitude(), vitrihientai.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(getResources().getString(R.string.ViTriHienTai));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        Marker markerHienTai = googleMap.addMarker(markerOptions);
        markerHienTai.showInfoWindow();

        //add Maker quan an
        LatLng latLngQuanAn = new LatLng(latitude, longitude);
        MarkerOptions markerOptionsQuanAn = new MarkerOptions();
        markerOptionsQuanAn.position(latLngQuanAn);
        markerOptionsQuanAn.title(tenquan);
        Marker markerQuanAn=  googleMap.addMarker(markerOptionsQuanAn);
        markerQuanAn.showInfoWindow();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
        danDuongToiQuanAnController.HienThiDanDuongToiQuanAn(googleMap,duongdan);
    }
}
