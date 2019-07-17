package com.example.sistem_pakar_cabai.AKTIVITY;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import com.example.sistem_pakar_cabai.ADAPTER.Adapter_Diagnosa;
import com.example.sistem_pakar_cabai.MODEL.Model_Data_Penyakit;
import com.example.sistem_pakar_cabai.MODEL.Model_Data_Penyakit_Question;
import com.example.sistem_pakar_cabai.R;
import com.example.sistem_pakar_cabai.APP.AppController;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data_Diagnosa extends AppCompatActivity {


    int success;
    private List<Model_Data_Penyakit> lst_data_penyakit= new ArrayList<>();
    List<Model_Data_Penyakit_Question> listGejala = new ArrayList<Model_Data_Penyakit_Question>();
    private RecyclerView myrv;
    private List<Model_Data_Penyakit> mData ;
    List<Double> Nilai_Diagnosa = new ArrayList<>();
    private List<Model_Data_Penyakit> mData_question ;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    String tag_json_obj = "json_obj_req";
    Context context;
    private ArrayList<String> a ;
    private ArrayList<String> students;
    public String tes ;

    public  static ArrayList<String> selectedStrings ;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);
        Toolbar ToolBarAtas = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(ToolBarAtas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myrv = (RecyclerView)findViewById(R.id.rv_data_diagnosa);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myrv.setLayoutManager(mManager);
        mAdapter = new Adapter_Diagnosa(this, lst_data_penyakit);
        myrv.setAdapter(mAdapter);
        myrv.setNestedScrollingEnabled(false);

        display_data();

        Button btn_diagnosa = findViewById(R.id.btn_diagnosa);
        btn_diagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hasil_Data_Diagnosa();
            }
        });

    }



    public void display_data() {

        pd = new ProgressDialog(this );
        pd.setMessage("Loading . . ");
        pd.setCancelable(false);
        pd.show();


        final String URL_JSON = "http://pakar.klikcaritau.com/view_data_penyakit.php";
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
                                    model.setKode_gejala(data.getString("kode_gejala"));
                                    //Toast.makeText(MainActivity.this,anime.toString(),Toast.LENGTH_SHORT).show();
                                    Nilai_Diagnosa.add(Double.parseDouble(data.getString("nilai_mb")));
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




    //Algoritma Certainty Factor

    private void Hasil_Data_Diagnosa(){

            String data = "",datas = "";

            List<Model_Data_Penyakit> mData = ((Adapter_Diagnosa)mAdapter).getKodegejala_selected();

            for(int i = 0; i < mData.size(); i++){
                Model_Data_Penyakit mKode = mData.get(i);
                if(mKode.isSelected()==true){
                    data = data + "," + mKode.getKode_gejala().toString();
                }
            }

        String[] items = data.split(",");
        int itemCount = items.length - 1;


        if (itemCount < 3){
          Toast alert = Toast.makeText(Data_Diagnosa.this,"min 3 gejala yang dipilih",Toast.LENGTH_SHORT);
          View toastView = alert.getView();
          toastView.setBackgroundResource(R.color.colorPremier);
          alert.show();
        }else{


            callData(data);







        }



    }



    private void callData(final String data_kode) {
        listGejala.clear();
        int [] MB_E2 ;

        final String URL_JSON = "http://pakar.klikcaritau.com/view_konsultasi.php";

        pd = new ProgressDialog(Data_Diagnosa.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");


        StringRequest eventoReq = new StringRequest(Request.Method.POST,URL_JSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("results", response.toString());

                        // Parsing json
                        try {
                            JSONArray j= new JSONArray(response);

                            List<Double> Nilai_MB = new ArrayList<>();
                            List<Double> Nilai_MD = new ArrayList<>();
                            for (int i = 0; i < j.length(); i++) {

                                JSONObject obj = j.getJSONObject(i);


                                Model_Data_Penyakit_Question item = new Model_Data_Penyakit_Question();

                                item.setKode_gejala(obj.getString("kode_gejala"));
                                item.setGejala(obj.getString("gejala"));

                                 double MB = Double.parseDouble(obj.getString("nilai_mb"));
                                 double MD = Double.parseDouble(obj.getString("nilai_md"));
                                 Nilai_MB.add(MB);
                                 Nilai_MD.add(MD);
                                 listGejala.add(item);
                            }

                            //ALGORITMA CERTANTY FACTOR

                            if (Nilai_MB.size() == 3) {
                                Double MB_h_E1_E2 = Nilai_MB.get(0) + (Nilai_MB.get(1) * (1-Nilai_MB.get(0))) ;
                                Double MB_h_E1_E2_E3 = MB_h_E1_E2 + (Nilai_MB.get(2) * (1-MB_h_E1_E2));

                                Double MD_h_E1_E2 = Nilai_MD.get(0) + (Nilai_MD.get(1) * (1-Nilai_MD.get(0))) ;
                                Double MD_h_E1_E2_E3 = MD_h_E1_E2 + (Nilai_MD.get(2) * (1-MD_h_E1_E2));

                                Double CF = MB_h_E1_E2_E3 - MD_h_E1_E2_E3 ;
                                double hasil = CF * 100 ;



                                DecimalFormat df = new DecimalFormat("#.##");

                                double a = Double.parseDouble(String.valueOf(CF));


                                Hasil.persentaseCF = hasil+" "+"%";
                                Hasil.nilai_probabilitas =  menemukan_nilai_terdekat(Nilai_Diagnosa,a);


                                startActivity(new Intent(Data_Diagnosa.this,Hasil.class));


                            } else if (Nilai_MB.size() == 4) {
                                Double MB_h_E1_E2 = Nilai_MB.get(0) + (Nilai_MB.get(1) * (1-Nilai_MB.get(0))) ;
                                Double MB_h_E1_E2_E3 = MB_h_E1_E2 + (Nilai_MB.get(2) * (1-MB_h_E1_E2));
                                Double MB_h_E1_E2_E3_E4 = MB_h_E1_E2_E3 + (Nilai_MB.get(3) * (1-MB_h_E1_E2_E3));

                                Double MD_h_E1_E2 = Nilai_MD.get(0) + (Nilai_MD.get(1) * (1-Nilai_MD.get(0))) ;
                                Double MD_h_E1_E2_E3 = MD_h_E1_E2 + (Nilai_MD.get(2) * (1-MD_h_E1_E2));
                                Double MD_h_E1_E2_E3_E4 = MD_h_E1_E2_E3 + (Nilai_MD.get(3) * (1-MD_h_E1_E2_E3));

                                Double CF = MB_h_E1_E2_E3_E4 - MD_h_E1_E2_E3_E4 ;
                                double hasil = CF * 100 ;

                                DecimalFormat df = new DecimalFormat("#.##");

                                double a = Double.parseDouble(String.valueOf(CF));


                                Hasil.persentaseCF = hasil+" "+"%";
                                Hasil.nilai_probabilitas =  menemukan_nilai_terdekat(Nilai_Diagnosa,a);


                                startActivity(new Intent(Data_Diagnosa.this,Hasil.class));

                            } else if (Nilai_MB.size() == 5) {
                                Double MB_h_E1_E2 = Nilai_MB.get(0) + (Nilai_MB.get(1) * (1-Nilai_MB.get(0))) ;
                                Double MB_h_E1_E2_E3 = MB_h_E1_E2 + (Nilai_MB.get(2) * (1-MB_h_E1_E2));
                                Double MB_h_E1_E2_E3_E4 = MB_h_E1_E2_E3 + (Nilai_MB.get(3) * (1-MB_h_E1_E2_E3));
                                Double MB_h_E1_E2_E3_E4_E5 = MB_h_E1_E2_E3_E4 + (Nilai_MB.get(4) * (1-MB_h_E1_E2_E3_E4));

                                Double MD_h_E1_E2 = Nilai_MD.get(0) + (Nilai_MD.get(1) * (1-Nilai_MD.get(0))) ;
                                Double MD_h_E1_E2_E3 = MD_h_E1_E2 + (Nilai_MD.get(2) * (1-MD_h_E1_E2));
                                Double MD_h_E1_E2_E3_E4 = MD_h_E1_E2_E3 + (Nilai_MD.get(3) * (1-MD_h_E1_E2_E3));
                                Double MD_h_E1_E2_E3_E4_E5 = MD_h_E1_E2_E3_E4 + (Nilai_MD.get(4) * (1-MD_h_E1_E2_E3_E4));

                                Double CF = MB_h_E1_E2_E3_E4_E5 - MD_h_E1_E2_E3_E4_E5 ;
                                double hasil = CF * 100 ;

                                DecimalFormat df = new DecimalFormat("#.##");

                                double a = Double.parseDouble(String.valueOf(CF));


                                Hasil.persentaseCF = hasil+" "+"%";
                                Hasil.nilai_probabilitas =  menemukan_nilai_terdekat(Nilai_Diagnosa,a);


                                startActivity(new Intent(Data_Diagnosa.this,Hasil.class));

                            } else if (Nilai_MB.size() == 6) {
                                Double MB_h_E1_E2 = Nilai_MB.get(0) + (Nilai_MB.get(1) * (1-Nilai_MB.get(0))) ;
                                Double MB_h_E1_E2_E3 = MB_h_E1_E2 + (Nilai_MB.get(2) * (1-MB_h_E1_E2));
                                Double MB_h_E1_E2_E3_E4 = MB_h_E1_E2_E3 + (Nilai_MB.get(3) * (1-MB_h_E1_E2_E3));
                                Double MB_h_E1_E2_E3_E4_E5 = MB_h_E1_E2_E3_E4 + (Nilai_MB.get(4) * (1-MB_h_E1_E2_E3_E4));
                                Double MB_h_E1_E2_E3_E4_E5_E6 = MB_h_E1_E2_E3_E4_E5 + (Nilai_MB.get(5) * (1-MB_h_E1_E2_E3_E4_E5));

                                Double MD_h_E1_E2 = Nilai_MD.get(0) + (Nilai_MD.get(1) * (1-Nilai_MD.get(0))) ;
                                Double MD_h_E1_E2_E3 = MD_h_E1_E2 + (Nilai_MD.get(2) * (1-MD_h_E1_E2));
                                Double MD_h_E1_E2_E3_E4 = MD_h_E1_E2_E3 + (Nilai_MD.get(3) * (1-MD_h_E1_E2_E3));
                                Double MD_h_E1_E2_E3_E4_E5 = MD_h_E1_E2_E3_E4 + (Nilai_MD.get(4) * (1-MD_h_E1_E2_E3_E4));
                                Double MD_h_E1_E2_E3_E4_E5_E6 = MD_h_E1_E2_E3_E4_E5 + (Nilai_MD.get(5) * (1-MD_h_E1_E2_E3_E4_E5));

                                Double CF = MB_h_E1_E2_E3_E4_E5_E6 - MD_h_E1_E2_E3_E4_E5_E6 ;
                                double hasil = CF * 100 ;

                                DecimalFormat df = new DecimalFormat("#.##");

                                double a = Double.parseDouble(String.valueOf(CF));


                                Hasil.persentaseCF = hasil+" "+"%";
                                Hasil.nilai_probabilitas =  menemukan_nilai_terdekat(Nilai_Diagnosa,a);


                                startActivity(new Intent(Data_Diagnosa.this,Hasil.class));

                            } else if (Nilai_MB.size() == 7) {
                                Double MB_h_E1_E2 = Nilai_MB.get(0) + (Nilai_MB.get(1) * (1-Nilai_MB.get(0))) ;
                                Double MB_h_E1_E2_E3 = MB_h_E1_E2 + (Nilai_MB.get(2) * (1-MB_h_E1_E2));
                                Double MB_h_E1_E2_E3_E4 = MB_h_E1_E2_E3 + (Nilai_MB.get(3) * (1-MB_h_E1_E2_E3));
                                Double MB_h_E1_E2_E3_E4_E5 = MB_h_E1_E2_E3_E4 + (Nilai_MB.get(4) * (1-MB_h_E1_E2_E3_E4));
                                Double MB_h_E1_E2_E3_E4_E5_E6 = MB_h_E1_E2_E3_E4_E5 + (Nilai_MB.get(5) * (1-MB_h_E1_E2_E3_E4_E5));
                                Double MB_h_E1_E2_E3_E4_E5_E6_E7 = MB_h_E1_E2_E3_E4_E5_E6 + (Nilai_MB.get(6) * (1-MB_h_E1_E2_E3_E4_E5_E6));

                                Double MD_h_E1_E2 = Nilai_MD.get(0) + (Nilai_MD.get(1) * (1-Nilai_MD.get(0))) ;
                                Double MD_h_E1_E2_E3 = MD_h_E1_E2 + (Nilai_MD.get(2) * (1-MD_h_E1_E2));
                                Double MD_h_E1_E2_E3_E4 = MD_h_E1_E2_E3 + (Nilai_MD.get(3) * (1-MD_h_E1_E2_E3));
                                Double MD_h_E1_E2_E3_E4_E5 = MD_h_E1_E2_E3_E4 + (Nilai_MD.get(4) * (1-MD_h_E1_E2_E3_E4));
                                Double MD_h_E1_E2_E3_E4_E5_E6 = MD_h_E1_E2_E3_E4_E5 + (Nilai_MD.get(5) * (1-MD_h_E1_E2_E3_E4_E5));
                                Double MD_h_E1_E2_E3_E4_E5_E6_E7 = MD_h_E1_E2_E3_E4_E5_E6 + (Nilai_MD.get(6) * (1-MD_h_E1_E2_E3_E4_E5_E6));

                                Double CF = MB_h_E1_E2_E3_E4_E5_E6_E7 - MD_h_E1_E2_E3_E4_E5_E6_E7 ;
                                double hasil = CF * 100 ;

                                DecimalFormat df = new DecimalFormat("#.##");

                                double a = Double.parseDouble(String.valueOf(CF));


                                Hasil.persentaseCF = hasil+" "+"%";
                                Hasil.nilai_probabilitas =  menemukan_nilai_terdekat(Nilai_Diagnosa,a);


                                startActivity(new Intent(Data_Diagnosa.this,Hasil.class));

                            } else if (Nilai_MB.size() == 8) {
                                Double MB_h_E1_E2 = Nilai_MB.get(0) + (Nilai_MB.get(1) * (1-Nilai_MB.get(0))) ;
                                Double MB_h_E1_E2_E3 = MB_h_E1_E2 + (Nilai_MB.get(2) * (1-MB_h_E1_E2));
                                Double MB_h_E1_E2_E3_E4 = MB_h_E1_E2_E3 + (Nilai_MB.get(3) * (1-MB_h_E1_E2_E3));
                                Double MB_h_E1_E2_E3_E4_E5 = MB_h_E1_E2_E3_E4 + (Nilai_MB.get(4) * (1-MB_h_E1_E2_E3_E4));
                                Double MB_h_E1_E2_E3_E4_E5_E6 = MB_h_E1_E2_E3_E4_E5 + (Nilai_MB.get(5) * (1-MB_h_E1_E2_E3_E4_E5));
                                Double MB_h_E1_E2_E3_E4_E5_E6_E7 = MB_h_E1_E2_E3_E4_E5_E6 + (Nilai_MB.get(6) * (1-MB_h_E1_E2_E3_E4_E5_E6));
                                Double MB_h_E1_E2_E3_E4_E5_E6_E7_E8 = MB_h_E1_E2_E3_E4_E5_E6_E7 + (Nilai_MB.get(7) * (1-MB_h_E1_E2_E3_E4_E5_E6_E7));

                                Double MD_h_E1_E2 = Nilai_MD.get(0) + (Nilai_MD.get(1) * (1-Nilai_MD.get(0))) ;
                                Double MD_h_E1_E2_E3 = MD_h_E1_E2 + (Nilai_MD.get(2) * (1-MD_h_E1_E2));
                                Double MD_h_E1_E2_E3_E4 = MD_h_E1_E2_E3 + (Nilai_MD.get(3) * (1-MD_h_E1_E2_E3));
                                Double MD_h_E1_E2_E3_E4_E5 = MD_h_E1_E2_E3_E4 + (Nilai_MD.get(4) * (1-MD_h_E1_E2_E3_E4));
                                Double MD_h_E1_E2_E3_E4_E5_E6 = MD_h_E1_E2_E3_E4_E5 + (Nilai_MD.get(5) * (1-MD_h_E1_E2_E3_E4_E5));
                                Double MD_h_E1_E2_E3_E4_E5_E6_E7 = MD_h_E1_E2_E3_E4_E5_E6 + (Nilai_MD.get(6) * (1-MD_h_E1_E2_E3_E4_E5_E6));
                                Double MD_h_E1_E2_E3_E4_E5_E6_E7_E8 = MD_h_E1_E2_E3_E4_E5_E6_E7 + (Nilai_MD.get(7) * (1-MD_h_E1_E2_E3_E4_E5_E6_E7));

                                Double CF = MB_h_E1_E2_E3_E4_E5_E6_E7_E8 - MD_h_E1_E2_E3_E4_E5_E6_E7_E8 ;
                                double hasil = CF * 100 ;

                                DecimalFormat df = new DecimalFormat("#.##");

                                double a = Double.parseDouble(String.valueOf(CF));


                                Hasil.persentaseCF = hasil+" "+"%";
                                Hasil.nilai_probabilitas =  menemukan_nilai_terdekat(Nilai_Diagnosa,a);


                                startActivity(new Intent(Data_Diagnosa.this,Hasil.class));


                            }else if (Nilai_MB.size() == 9) {
                                Double MB_h_E1_E2 = Nilai_MB.get(0) + (Nilai_MB.get(1) * (1-Nilai_MB.get(0))) ;
                                Double MB_h_E1_E2_E3 = MB_h_E1_E2 + (Nilai_MB.get(2) * (1-MB_h_E1_E2));
                                Double MB_h_E1_E2_E3_E4 = MB_h_E1_E2_E3 + (Nilai_MB.get(3) * (1-MB_h_E1_E2_E3));
                                Double MB_h_E1_E2_E3_E4_E5 = MB_h_E1_E2_E3_E4 + (Nilai_MB.get(4) * (1-MB_h_E1_E2_E3_E4));
                                Double MB_h_E1_E2_E3_E4_E5_E6 = MB_h_E1_E2_E3_E4_E5 + (Nilai_MB.get(5) * (1-MB_h_E1_E2_E3_E4_E5));
                                Double MB_h_E1_E2_E3_E4_E5_E6_E7 = MB_h_E1_E2_E3_E4_E5_E6 + (Nilai_MB.get(6) * (1-MB_h_E1_E2_E3_E4_E5_E6));
                                Double MB_h_E1_E2_E3_E4_E5_E6_E7_E8 = MB_h_E1_E2_E3_E4_E5_E6_E7 + (Nilai_MB.get(7) * (1-MB_h_E1_E2_E3_E4_E5_E6_E7));
                                Double MB_h_E1_E2_E3_E4_E5_E6_E7_E8_E9 = MB_h_E1_E2_E3_E4_E5_E6_E7_E8 + (Nilai_MB.get(8) * (1-MB_h_E1_E2_E3_E4_E5_E6_E7_E8));

                                Double MD_h_E1_E2 = Nilai_MD.get(0) + (Nilai_MD.get(1) * (1-Nilai_MD.get(0))) ;
                                Double MD_h_E1_E2_E3 = MD_h_E1_E2 + (Nilai_MD.get(2) * (1-MD_h_E1_E2));
                                Double MD_h_E1_E2_E3_E4 = MD_h_E1_E2_E3 + (Nilai_MD.get(3) * (1-MD_h_E1_E2_E3));
                                Double MD_h_E1_E2_E3_E4_E5 = MD_h_E1_E2_E3_E4 + (Nilai_MD.get(4) * (1-MD_h_E1_E2_E3_E4));
                                Double MD_h_E1_E2_E3_E4_E5_E6 = MD_h_E1_E2_E3_E4_E5 + (Nilai_MD.get(5) * (1-MD_h_E1_E2_E3_E4_E5));
                                Double MD_h_E1_E2_E3_E4_E5_E6_E7 = MD_h_E1_E2_E3_E4_E5_E6 + (Nilai_MD.get(6) * (1-MD_h_E1_E2_E3_E4_E5_E6));
                                Double MD_h_E1_E2_E3_E4_E5_E6_E7_E8 = MD_h_E1_E2_E3_E4_E5_E6_E7 + (Nilai_MD.get(7) * (1-MD_h_E1_E2_E3_E4_E5_E6_E7));
                                Double MD_h_E1_E2_E3_E4_E5_E6_E7_E8_E9 = MD_h_E1_E2_E3_E4_E5_E6_E7_E8 + (Nilai_MD.get(8) * (1-MD_h_E1_E2_E3_E4_E5_E6_E7_E8));

                                Double CF = MB_h_E1_E2_E3_E4_E5_E6_E7_E8_E9 - MD_h_E1_E2_E3_E4_E5_E6_E7_E8_E9 ;
                                double hasil = CF * 100 ;

                                DecimalFormat df = new DecimalFormat("#.##");

                                double a = Double.parseDouble(String.valueOf(CF));


                                Hasil.persentaseCF = hasil+" "+"%";
                                Hasil.nilai_probabilitas =  menemukan_nilai_terdekat(Nilai_Diagnosa,a);


                                startActivity(new Intent(Data_Diagnosa.this,Hasil.class));

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pd.cancel();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error", "Error: " + error.getMessage());
                Toast.makeText(Data_Diagnosa.this, error.getMessage(), Toast.LENGTH_LONG).show();
                pd.cancel();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                //mengirim value melalui parameter ke database


                String[] kode_gejala = data_kode.split(",");

                if (kode_gejala.length == 4){
                    params.put("G1", kode_gejala[1]);
                    params.put("G2", kode_gejala[2]);
                    params.put("G3", kode_gejala[3]);
                } else if (kode_gejala.length == 5 ) {
                    params.put("G1", kode_gejala[1]);
                    params.put("G2", kode_gejala[2]);
                    params.put("G3", kode_gejala[3]);
                    params.put("G4", kode_gejala[4]);
                }else if (kode_gejala.length == 6) {
                    params.put("G1", kode_gejala[1]);
                    params.put("G2", kode_gejala[2]);
                    params.put("G3", kode_gejala[3]);
                    params.put("G4", kode_gejala[4]);
                    params.put("G5", kode_gejala[5]);
                }else if (kode_gejala.length == 7) {
                    params.put("G1", kode_gejala[1]);
                    params.put("G2", kode_gejala[2]);
                    params.put("G3", kode_gejala[3]);
                    params.put("G4", kode_gejala[4]);
                    params.put("G5", kode_gejala[5]);
                    params.put("G6", kode_gejala[6]);
                }else if (kode_gejala.length == 8) {
                    params.put("G1", kode_gejala[1]);
                    params.put("G2", kode_gejala[2]);
                    params.put("G3", kode_gejala[3]);
                    params.put("G4", kode_gejala[4]);
                    params.put("G5", kode_gejala[5]);
                    params.put("G6", kode_gejala[6]);
                    params.put("G7", kode_gejala[7]);
                } else if (kode_gejala.length == 9){
                    params.put("G1", kode_gejala[1]);
                    params.put("G2", kode_gejala[2]);
                    params.put("G3", kode_gejala[3]);
                    params.put("G4", kode_gejala[4]);
                    params.put("G5", kode_gejala[5]);
                    params.put("G6", kode_gejala[6]);
                    params.put("G7", kode_gejala[7]);
                    params.put("G8", kode_gejala[8]);
                } else if (kode_gejala.length == 10){
                    params.put("G1", kode_gejala[1]);
                    params.put("G2", kode_gejala[2]);
                    params.put("G3", kode_gejala[3]);
                    params.put("G4", kode_gejala[4]);
                    params.put("G5", kode_gejala[5]);
                    params.put("G6", kode_gejala[6]);
                    params.put("G7", kode_gejala[7]);
                    params.put("G8", kode_gejala[8]);
                    params.put("G9", kode_gejala[9]);
                }



                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(eventoReq);
    }


    static double menemukan_nilai_terdekat(List<Double> list, double d){
        if(list.size() == 0){
            return -1;
        }

        if(list.size() == 1){
            return list.get(0);
        }

        double current = list.get(0);
        double currentMin = Math.abs(list.get(0) - d);

        for(int i = 1; i < list.size(); i ++){

            double difference = Math.abs(list.get(i) - d);

            if(currentMin > difference){
                currentMin = difference;

                current = list.get(i);
            }
        }

        return current;
    }






}
