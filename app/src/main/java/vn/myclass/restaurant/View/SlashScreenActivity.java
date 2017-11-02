package vn.myclass.restaurant.View;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import vn.myclass.restaurant.R;

public class SlashScreenActivity extends AppCompatActivity {
    TextView txtVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flashscreen);
        txtVersion= (TextView) findViewById(R.id.txtVer);
        try {
            // Lấy ra version app
            PackageInfo packageInfo= getPackageManager().getPackageInfo(getPackageName(),0);
            txtVersion.setText(getString(R.string.version)+": "+packageInfo.versionName);
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentDangNhap=new Intent(SlashScreenActivity.this,DangNhapActivity.class);
                    startActivity(intentDangNhap);
                    finish();

                }
            },2000);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
