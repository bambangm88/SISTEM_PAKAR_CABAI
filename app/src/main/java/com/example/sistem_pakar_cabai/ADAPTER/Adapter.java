package com.example.sistem_pakar_cabai.ADAPTER;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import com.example.sistem_pakar_cabai.AKTIVITY.Data_Penyakit;
import com.example.sistem_pakar_cabai.AKTIVITY.detail_data_penyakit;
import com.example.sistem_pakar_cabai.MODEL.Model_Data_Penyakit;
import com.example.sistem_pakar_cabai.R;
import com.example.sistem_pakar_cabai.APP.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Aws on 11/03/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    int success;


    private Context mContext ;
    private List<Model_Data_Penyakit> mData;
    RequestOptions option;

    LayoutInflater inflater;


    public Adapter(Context mContext, List<Model_Data_Penyakit> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View view;
        inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.list_row, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder ;
    }







    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {



        holder.tv_jenis_penyakit.setText(mData.get(position).getJenis());

        // Load Image from the internet and set it into Imageview using Glide
        Glide.with(mContext).load("http://pakar.klikcaritau.com/img/"+mData.get(position).getImage()+".jpg").apply(option).into(holder.img_penyakit);

        holder.cv_penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mData.get(position).getId();
                String jenis = mData.get(position).getJenis();
                String gejala = mData.get(position).getGejala();
                String pengendalian = mData.get(position).getPengendalian();
                String image = mData.get(position).getImage();
                String nilai_mb = mData.get(position).getNilai_mb();
                String nilai_md = mData.get(position).getNilai_md();

                Intent i = new Intent(mContext, detail_data_penyakit.class);
                // sending data process
                i.putExtra("id", id);
                i.putExtra("jenis",jenis);
                i.putExtra("gejala", gejala);
                i.putExtra("pengendalian", pengendalian);
                i.putExtra("image", image);
                i.putExtra("nilai_mb", nilai_mb);
                i.putExtra("nilai_md", nilai_md);


                mContext.startActivity(i);


            }
        });




        if(Data_Penyakit.level_status.equals("admin")){

        holder.cv_penyakit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = {"Edit", "Delete"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item] == "Edit"){

                            mContext.startActivity(new Intent(mContext,Data_Penyakit.class));
                            ((Activity)mContext).finish();

                        } else if (items[item] == "Delete"){

                            delete(mData.get(position).getId());

                        }



                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

        }



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_jenis_penyakit;
        ImageView img_penyakit;
        CardView cv_penyakit;




        public MyViewHolder(View itemView) {
            super(itemView);

            tv_jenis_penyakit = itemView.findViewById(R.id.title_penyakit);

            img_penyakit = itemView.findViewById(R.id.img_penyaki);
            cv_penyakit = itemView.findViewById(R.id.cvPenyakit_row);


        }
    }

    // fungsi untuk menghapus
    public void delete(final String idx){
        final String url_delete = "http://pakar.klikcaritau.com/delete.php";
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("results", "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt("success");

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());

                        Toast.makeText(mContext, "Delete Success", Toast.LENGTH_SHORT).show();
                        mContext.startActivity(new Intent(mContext,Data_Penyakit.class));
                        ((Activity)mContext).finish();


                    } else {
                        Toast.makeText(mContext, jObj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error: " + error.getMessage());
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

}