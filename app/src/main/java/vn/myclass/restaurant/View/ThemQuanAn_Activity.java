package vn.myclass.restaurant.View;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import vn.myclass.restaurant.R;

public class ThemQuanAn_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btnGioMoCua,btnGioDongCua;
    String gioMoCua,gioDongCua;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themquanan);
        addControll();
        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
    }

    private void addControll() {
        btnGioMoCua= (Button) findViewById(R.id.btnGioMoCua);
        btnGioDongCua= (Button) findViewById(R.id.btnGioDongCua);

    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        switch (v.getId()){
            case R.id.btnGioMoCua:

                TimePickerDialog timePickerDialog = new TimePickerDialog(ThemQuanAn_Activity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioMoCua=hourOfDay+":"+minute;
                        btnGioMoCua.setText(gioMoCua);
                    }
                },gio,phut,true);

                timePickerDialog.show();
//                Toast.makeText(this, "testhieenj lên", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnGioDongCua:

                TimePickerDialog DongPickerDialog = new TimePickerDialog(ThemQuanAn_Activity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioDongCua=hourOfDay+":"+minute;
                        btnGioDongCua.setText(gioDongCua);
                    }
                },gio,phut,true);

                DongPickerDialog.show();
//                Toast.makeText(this, "testhieenj lên", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
