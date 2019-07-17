package com.example.sistem_pakar_cabai.AKTIVITY;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.sistem_pakar_cabai.FRAGMENT.FragmentApp;
import com.example.sistem_pakar_cabai.FRAGMENT.FragmentPakar;
import com.example.sistem_pakar_cabai.FRAGMENT.FragmentPenulis;
import com.example.sistem_pakar_cabai.R;

public class tentang extends AppCompatActivity {

    private TextView mTextMessage;

    Fragment fragment ;


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_pakar:
                    fragment = new FragmentPakar();
                    loadFragment(fragment) ;
                    return true;
                case R.id.navigation_app:
                    fragment = new FragmentApp();
                    loadFragment(fragment) ;
                    return true;
                case R.id.navigation_penulis:
                    fragment = new FragmentPenulis();
                    loadFragment(fragment) ;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new FragmentPakar());


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onBackPressed() {
        this.finish();
    }
}
