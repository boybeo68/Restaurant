package vn.myclass.restaurant.View;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import vn.myclass.restaurant.Adapter.Angi_Odau_Adapter;
import vn.myclass.restaurant.R;

public class Trangchu_Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener {

    private ViewPager viewPager;
    private Angi_Odau_Adapter angi_odau_adapter;
    RadioButton rdAngi,rdOdau;
    RadioGroup group_radio_angi_odau;
    ImageView imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu_activity);
        viewPager= (ViewPager) findViewById(R.id.viewpager_trangchu);
        rdAngi= (RadioButton) findViewById(R.id.rd_angi);
        rdOdau= (RadioButton) findViewById(R.id.rd_odau);
        imgUser=findViewById(R.id.imguser);
        group_radio_angi_odau= (RadioGroup) findViewById(R.id.group_radio_angi_odau);
        angi_odau_adapter=new Angi_Odau_Adapter(getSupportFragmentManager());
        viewPager.setAdapter(angi_odau_adapter);
        viewPager.addOnPageChangeListener(this);
        group_radio_angi_odau.setOnCheckedChangeListener(this);
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iUser=new Intent(Trangchu_Activity.this,User_Activity.class);
                startActivity(iUser);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:rdOdau.setChecked(true);
                break;
            case 1:rdAngi.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rd_odau:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rd_angi:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
