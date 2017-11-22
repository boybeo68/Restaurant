package vn.myclass.restaurant.Controller;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.myclass.restaurant.Model.DownLoadPolyline;
import vn.myclass.restaurant.Model.ParsePolyline;

/**
 * Created by boybe on 11/22/2017.
 */

public class DanDuongToiQuanAnController {
    ParsePolyline parsePolyline;
    DownLoadPolyline downLoadPolyline;
    public DanDuongToiQuanAnController() {

    }
    public void HienThiDanDuongToiQuanAn(GoogleMap googleMap,String duongDan){
        parsePolyline=new ParsePolyline();
        downLoadPolyline=new DownLoadPolyline();
        downLoadPolyline.execute(duongDan);
        try {
            String dataJson=downLoadPolyline.get();
            List<LatLng>latLngList=parsePolyline.laydanhsachToado(dataJson);
            PolylineOptions polylineOptions=new PolylineOptions()
                    .color(Color.CYAN)
                    .width(15);
            for (LatLng toado:latLngList){
               polylineOptions.add(toado);
            }
            Polyline polyline=googleMap.addPolyline(polylineOptions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
