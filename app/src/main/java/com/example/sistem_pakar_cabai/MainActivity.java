package com.example.sistem_pakar_cabai;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sistem_pakar_cabai.AKTIVITY.Bantuan;
import com.example.sistem_pakar_cabai.AKTIVITY.Data_Penyakit;
import com.example.sistem_pakar_cabai.AKTIVITY.tentang;
import com.example.sistem_pakar_cabai.AKTIVITY.Data_Diagnosa;
import com.example.sistem_pakar_cabai.ENTITY.Index;
import com.example.sistem_pakar_cabai.ENTITY.Login;
import com.example.sistem_pakar_cabai.ENTITY.kritik;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import static com.example.sistem_pakar_cabai.ENTITY.Index.TAG_ID;
import static com.example.sistem_pakar_cabai.ENTITY.Index.TAG_USERNAME;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.satu, R.drawable.dua, R.drawable.tiga, R.drawable.empat};

    CardView btn_tentang
            ,btn_data_cabai
            ,btn_data_gejala
            ,btn_bantuan;

    Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        btn_tentang = findViewById(R.id.r4);
        btn_data_cabai = findViewById(R.id.r1);
        btn_data_gejala = findViewById(R.id.r2);
        btn_bantuan = findViewById(R.id.r3);

        btn_tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, tentang.class));

            }
        });

        btn_data_cabai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Data_Penyakit.class));

            }
        });

        btn_data_gejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Data_Diagnosa.class));

            }
        });

        btn_bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Bantuan.class));

            }
        });



    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.bantuan) {

            startActivity(new Intent(MainActivity.this, Bantuan.class));

            return true;
        }
        if (id == R.id.tentang) {

            startActivity(new Intent(MainActivity.this, tentang.class));

            return true;
        }
        if (id == R.id.keluar) {

            keluar();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    //----------------

    //perintah keluar ------------
    private void keluar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("yakin keluar?")
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);


                        // TODO Auto-generated method stub
                        // update login session ke FALSE dan mengosongkan nilai id dan username
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(Login.session_status, false);
                        editor.putString(TAG_ID, null);
                        editor.putString(TAG_USERNAME, null);
                        editor.commit();

                        startActivity(new Intent(MainActivity.this, Index.class));
                        finish();
                        Toast.makeText(getApplicationContext(),"Berhasil Keluar",Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
//----------------








}
