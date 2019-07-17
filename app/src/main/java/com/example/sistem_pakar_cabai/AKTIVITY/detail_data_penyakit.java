package com.example.sistem_pakar_cabai.AKTIVITY;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sistem_pakar_cabai.R;

public class detail_data_penyakit extends AppCompatActivity {


    TextView id,txt_jenis,txt_gejala,txt_pengendalian,txt_nilai_mb,txt_nilai_md;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_penyakit);


        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(ToolBarAtas2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String id = getIntent().getExtras().getString("id");
        String jenis = getIntent().getExtras().getString("jenis");
        String gejala = getIntent().getExtras().getString("gejala");
        String pengendalian = getIntent().getExtras().getString("pengendalian");
        String image = getIntent().getExtras().getString("image");
        String nilai_mb = getIntent().getExtras().getString("nilai_mb");
        String nilai_md = getIntent().getExtras().getString("nilai_md");


        txt_jenis = findViewById(R.id.tittle);
        txt_gejala = findViewById(R.id.txt_gejala);
        txt_pengendalian = findViewById(R.id.txt_pengendalian);
        txt_nilai_mb = findViewById(R.id.txt_nilai_mb);
        txt_nilai_md = findViewById(R.id.txt_nilai_md);
        img = findViewById(R.id.img_penyakit);


        txt_jenis.setText(jenis);
        txt_gejala.setText(gejala);
        txt_pengendalian.setText(pengendalian);
        txt_nilai_mb.setText(nilai_mb);
        txt_nilai_md.setText(nilai_md);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        // set image using Glide
        Glide.with(this).load("http://pakar.klikcaritau.com/img/"+image+".jpg").apply(requestOptions).into(img);




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


