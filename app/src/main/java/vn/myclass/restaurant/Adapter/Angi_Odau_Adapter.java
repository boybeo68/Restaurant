package vn.myclass.restaurant.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.myclass.restaurant.View.Fragments.ThemQuanAn_Fragment;
import vn.myclass.restaurant.View.Fragments.Odau_Fragment;

/**
 * Created by boybe on 10/24/2017.
 */

public class Angi_Odau_Adapter extends FragmentStatePagerAdapter {
    ThemQuanAn_Fragment angi_fragment;
    Odau_Fragment odau_fragment;
    public Angi_Odau_Adapter(FragmentManager fm) {
        super(fm);
        angi_fragment=new ThemQuanAn_Fragment();
        odau_fragment =new Odau_Fragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return odau_fragment;
            case 1: return  angi_fragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
