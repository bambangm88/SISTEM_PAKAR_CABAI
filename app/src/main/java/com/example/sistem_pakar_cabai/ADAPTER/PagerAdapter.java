package com.example.sistem_pakar_cabai.ADAPTER;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sistem_pakar_cabai.FRAGMENT.Fragment_Bantuan_Data_Penyakit;
import com.example.sistem_pakar_cabai.FRAGMENT.Fragment_Bantuan_Diagnosa;
import com.example.sistem_pakar_cabai.FRAGMENT.Fragment_Bantuan_Tentang;


public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment_Bantuan_Data_Penyakit();
            case 1:
                return new Fragment_Bantuan_Diagnosa();
            case 2:
                return new Fragment_Bantuan_Tentang();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Data Penyakit";
            case 1:
                return "Diagnosa";
            case 2:
                return "Tentang";
        }
        return super.getPageTitle(position);
    }
}