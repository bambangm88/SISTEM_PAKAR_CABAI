package com.example.sistem_pakar_cabai.AKTIVITY;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sistem_pakar_cabai.ADAPTER.AdapterHasilDiagnosa;
import com.example.sistem_pakar_cabai.MODEL.Model_Data_Penyakit;
import com.example.sistem_pakar_cabai.R;
import com.example.sistem_pakar_cabai.APP.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hasil extends AppCompatActivity {


    public static double nilai_probabilitas ;
    public static String persentaseCF ;

    int success;
    private List<Model_Data_Penyakit> lst_data_penyakit= new ArrayList<>();
    private RecyclerView myrv;

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    String tag_json_obj = "json_obj_req";
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        Toolbar ToolBarAtas = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(ToolBarAtas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myrv = (RecyclerView)findViewById(R.id.rv_data_hasil_diagnosa);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myrv.setLayoutManager(mManager);
        mAdapter = new AdapterHasilDiagnosa(this, lst_data_penyakit);
        myrv.setAdapter(mAdapter);
        myrv.setNestedScrollingEnabled(false);

        display_data(nilai_probabilitas);

        TextView persentase = findViewById(R.id.nilaiCF);
        persentase.setText(persentaseCF);

    }



    public void display_data(final double probilitas) {

        pd = new ProgressDialog(this );
        pd.setMessage("Loading . . ");
        pd.setCancelable(false);
        pd.show();


        final String URL_JSON = "http://pakar.klikcaritau.com/view_hasil_diagnosa.php";
        StringRequest reqData = new StringRequest(Request.Method.POST, URL_JSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.cancel();
                        Log.e("Response: ", response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt("value");
                            if (success == 1) {
                                mAdapter.notifyDataSetChanged();

                                String getObject = jObj.getString("results");
                                JSONArray jsonArray = new JSONArray(getObject);

                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    Model_Data_Penyakit model = new Model_Data_Penyakit();

                                    model.setId(data.getString("id_penyakit"));
                                    model.setJenis(data.getString("jenis_penyakit"));
                                    model.setImage(data.getString("image"));
                                    model.setPengendalian(data.getString("pengendalian"));
                                    model.setGejala(data.getString("gejala"));
                                    model.setNilai_mb(data.getString("nilai_mb"));
                                    model.setNilai_md(data.getString("nilai_md"));
                                    //Toast.makeText(MainActivity.this,anime.toString(),Toast.LENGTH_SHORT).show();
                                    lst_data_penyakit.add(model);
                                    mAdapter.notifyDataSetChanged();


                                }
                            }
                        } catch (JSONException e) {
                            pd.cancel();
                            e.printStackTrace();
                        }

                        mAdapter.notifyDataSetChanged();
                        pd.cancel();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley", "error : " + error.getMessage());
                pd.cancel();

            }}
        ){

            @Override
            protected Map<String, String> getParams(){


                Map<String,String> params = new HashMap<String, String>();
                //mengirim value melalui parameter ke database

                params.put("probabilitas", String.valueOf(probilitas));
                return params;
            }


        };


        AppController.getInstance().addToRequestQueue(reqData,tag_json_obj);
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
