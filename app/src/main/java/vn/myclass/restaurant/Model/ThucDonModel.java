package vn.myclass.restaurant.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.myclass.restaurant.Controller.Interface.ThucDon_interface;

/**
 * Created by boybe on 11/29/2017.
 */

public class ThucDonModel {
    String mathucdon,tenthucdon;
    // 1 thực đơn bao gồm nhiều món ăn
    List<MonanModel>monanModelList;

    public List<MonanModel> getMonanModelList() {
        return monanModelList;
    }

    public void setMonanModelList(List<MonanModel> monanModelList) {
        this.monanModelList = monanModelList;
    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }
    public void getDanhSachthucdonTheoMa(final String maquanan, final ThucDon_interface thucDon_interface){
        final DatabaseReference nodethucdonquanan= FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maquanan);
            nodethucdonquanan.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataThucDonQuanAn) {
                    final List<ThucDonModel>thucDonModels=new ArrayList<>();
                    for (DataSnapshot valueThucDon:dataThucDonQuanAn.getChildren()){

                        final ThucDonModel thucDonModel=new ThucDonModel();
                        final DatabaseReference nodeThucdon=FirebaseDatabase.getInstance().getReference().child("thucdons").child(valueThucDon.getKey());
                        nodeThucdon.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataThucDon) {
                                String mathucdon=dataThucDon.getKey();
                                thucDonModel.setMathucdon(mathucdon);
                                thucDonModel.setTenthucdon(dataThucDon.getValue(String.class));
                                List<MonanModel>monanModels=new ArrayList<>();
                                for (DataSnapshot valueMonan:dataThucDonQuanAn.child(mathucdon).getChildren()){
                                    MonanModel monanModel=valueMonan.getValue(MonanModel.class);
                                    monanModel.setMamon(valueMonan.getKey());
                                    monanModels.add(monanModel);
                                }
                                thucDonModel.setMonanModelList(monanModels);
                                thucDonModels.add(thucDonModel);
                                thucDon_interface.getThucDon(thucDonModels);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}
