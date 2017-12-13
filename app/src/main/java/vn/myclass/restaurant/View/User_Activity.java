package vn.myclass.restaurant.View;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import vn.myclass.restaurant.R;

public class User_Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView txtTenUser,txtEmailUser,txtTieudetoolbar;
    Toolbar toolbar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user);

        txtTenUser=findViewById(R.id.txtTenUser);
        txtEmailUser=findViewById(R.id.txtEmailUser);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTieudetoolbar=findViewById(R.id.txtTieudeToolbar);

        sharedPreferences=getSharedPreferences("luudangnhap",MODE_PRIVATE);
        String mauser=sharedPreferences.getString("mauser","");
        String emailUser=sharedPreferences.getString("emailuser","");
        txtTenUser.setText(mauser);
        txtEmailUser.setText(emailUser);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtTieudetoolbar.setText(emailUser);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

