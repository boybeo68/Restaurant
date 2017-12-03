package vn.myclass.restaurant.Model;

import java.util.List;

/**
 * Created by boybe on 12/2/2017.
 */

public class ThemThucDonModel {
    String mathucdon,tenthucdon;
    MonanModel monanModel;

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

    public MonanModel getMonanModel() {
        return monanModel;
    }

    public void setMonanModel(MonanModel monanModel) {
        this.monanModel = monanModel;
    }
}
