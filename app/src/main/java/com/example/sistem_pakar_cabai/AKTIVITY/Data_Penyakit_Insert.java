package com.example.sistem_pakar_cabai.AKTIVITY;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sistem_pakar_cabai.ADAPTER.Adapter_Spinner_Gejala;
import com.example.sistem_pakar_cabai.ADAPTER.Adapter_Spinner_Pengendalian;
import com.example.sistem_pakar_cabai.MODEL.Model_Spinner_Gejala;
import com.example.sistem_pakar_cabai.MODEL.Model_Spinner_Pengendalian;
import com.example.sistem_pakar_cabai.R;
import com.example.sistem_pakar_cabai.APP.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data_Penyakit_Insert extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_register ;

    Intent intent;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100

    TextView txt_hasil,txt_hasil_pengendalian;
    Spinner spinner_gejala;
    Spinner spinner_pengendalian;
    Adapter_Spinner_Gejala adapter;
    Adapter_Spinner_Pengendalian adapter_spinner_pengendalian;
    List<Model_Spinner_Gejala> listGejala = new ArrayList<Model_Spinner_Gejala>();
    List<Model_Spinner_Pengendalian> listPengendalian = new ArrayList<Model_Spinner_Pengendalian>();

    int success;
    ConnectivityManager conMgr;

    ImageView imageView;

    Bitmap bitmap, decoded;

    private String UPLOAD_URL = "http://pakar.klikcaritau.com/upload_image.php";
    private String url = "http://pakar.klikcaritau.com/insert_data_gejala.php.php";

    private static final String TAG = Data_Penyakit_Insert.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";



    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__penyakit__insert);

        Toolbar ToolBarAtas = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(ToolBarAtas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Tidak Ada Koneksi Internet . .", Snackbar.LENGTH_SHORT);
                snackbar.show();
                // Toast.makeText(getApplicationContext(), "No Internet Connection",
                //      Toast.LENGTH_LONG).show();
            }
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        btn_register = findViewById(R.id.btnSave);

        txt_hasil = (TextView) findViewById(R.id.txt_hasil);
        txt_hasil_pengendalian = (TextView) findViewById(R.id.txt_hasil_pengendalian);
        spinner_gejala = (Spinner) findViewById(R.id.spinner_gejala);
        spinner_pengendalian = (Spinner) findViewById(R.id.spinner_pengendalian);

        spinner_gejala.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt_hasil.setText(listGejala.get(position).getKode_gejala());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spinner_pengendalian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt_hasil_pengendalian.setText(listPengendalian.get(position).getKode_pengendalian());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        adapter = new Adapter_Spinner_Gejala(Data_Penyakit_Insert.this, listGejala);
        spinner_gejala.setAdapter(adapter);

        adapter_spinner_pengendalian = new Adapter_Spinner_Pengendalian(Data_Penyakit_Insert.this, listPengendalian);
        spinner_pengendalian.setAdapter(adapter_spinner_pengendalian );




        callData();
        callData_pengendalian();

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                EditText jenis_penyakit = findViewById(R.id.jenis_penyakit);
                TextView kode_gejala  = findViewById(R.id.txt_hasil);
                TextView kode_pengendalian = findViewById(R.id.txt_hasil_pengendalian);


                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {

                    if(jenis_penyakit.getText().toString().equals("")){
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Jenis Penyakit Tidak Boleh Kosong", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }else {
                        uploadImage(jenis_penyakit.getText().toString(), kode_gejala.getText().toString(), kode_pengendalian.getText().toString());
                    }

                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Tidak Ada Koneksi Internet . .", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    // Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imageView.setImageBitmap(decoded);
    }


    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width /(float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }







    private void callData() {
        listGejala.clear();

        String url = "http://pakar.klikcaritau.com/spinner_gejala.php"  ;

        pDialog = new ProgressDialog(Data_Penyakit_Insert.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");


        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Model_Spinner_Gejala item = new Model_Spinner_Gejala();

                                item.setKode_gejala(obj.getString("kode_gejala"));
                                item.setGejala(obj.getString("gejala"));

                                listGejala.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Data_Penyakit_Insert.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(final String j_penyakit, final String kd_gjl, final String kd_pengendalian) {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                Toast.makeText(Data_Penyakit_Insert.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                kosong();

                            } else {
                                Toast.makeText(Data_Penyakit_Insert.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        Toast.makeText(Data_Penyakit_Insert.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put("image", getStringImage(decoded));
                params.put("jenis_penyakit",j_penyakit);
                params.put("kode_gejala",kd_gjl);
                params.put("kode_pengendalian",kd_pengendalian);

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }


    private void kosong() {
        imageView.setImageResource(0);
    }




    private void callData_pengendalian() {
        listPengendalian.clear();

        String url = "http://pakar.klikcaritau.com/spinner_pengendalian.php"  ;

        pDialog = new ProgressDialog(Data_Penyakit_Insert.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");


        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Model_Spinner_Pengendalian item = new Model_Spinner_Pengendalian();

                                item.setKode_pengendalian(obj.getString("kode_pengendalian"));
                                item.setPengendalian(obj.getString("pengendalian"));

                                listPengendalian.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter_spinner_pengendalian.notifyDataSetChanged();

                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Data_Penyakit_Insert.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
