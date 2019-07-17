package com.example.sistem_pakar_cabai.ENTITY;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.sistem_pakar_cabai.R;

public class kritik extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kritik);

        Toolbar ToolBarAtas = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(ToolBarAtas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
