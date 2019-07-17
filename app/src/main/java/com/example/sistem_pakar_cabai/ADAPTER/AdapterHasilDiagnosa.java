package com.example.sistem_pakar_cabai.ADAPTER;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sistem_pakar_cabai.AKTIVITY.detail_data_penyakit;
import com.example.sistem_pakar_cabai.MODEL.Model_Data_Penyakit;
import com.example.sistem_pakar_cabai.R;

import java.util.List;


public class AdapterHasilDiagnosa extends RecyclerView.Adapter<AdapterHasilDiagnosa.MyViewHolder> {
    int success;


    private Context mContext ;
    private List<Model_Data_Penyakit> mData;
    RequestOptions option;

    LayoutInflater inflater;


    public AdapterHasilDiagnosa(Context mContext, List<Model_Data_Penyakit> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View view;
        inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.lst_row_hasil_diagnosa, parent, false);
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



}