package vn.myclass.restaurant.Model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boybe on 11/22/2017.
 */

public class ParsePolyline {
    public static List<LatLng> laydanhsachToado(String dataJson){
        List<LatLng>latLngs=new ArrayList<>();

        try {
            JSONObject jsonObject=new JSONObject(dataJson);
            JSONArray routes=jsonObject.getJSONArray("routes");
            for (int i=0;i<routes.length();i++){
                JSONObject jsonObjectRoutes=routes.getJSONObject(i);
                JSONObject overview_polyline=jsonObjectRoutes.getJSONObject("overview_polyline");
                String points=overview_polyline.getString("points");
//                Log.d("kiemtrapoin",points+"");
                latLngs= PolyUtil.decode(points);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return latLngs;
    }
}
