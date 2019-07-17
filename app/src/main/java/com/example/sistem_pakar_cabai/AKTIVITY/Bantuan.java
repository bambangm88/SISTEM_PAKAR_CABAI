package com.example.sistem_pakar_cabai.AKTIVITY;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.sistem_pakar_cabai.ADAPTER.PagerAdapter;
import com.example.sistem_pakar_cabai.R;

public class Bantuan extends AppCompatActivity {


    private ViewPager viewPager;
    private PagerAdapter adapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        Toolbar ToolBarAtas = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(ToolBarAtas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.slide);
        tabLayout.addTab(tabLayout.newTab().setText("Calls"));
        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Contacts"));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
