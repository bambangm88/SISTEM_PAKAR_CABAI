package com.example.sistem_pakar_cabai.ADAPTER;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sistem_pakar_cabai.AKTIVITY.Data_Diagnosa;
import com.example.sistem_pakar_cabai.AKTIVITY.detail_data_penyakit;
import com.example.sistem_pakar_cabai.MODEL.Model_Data_Penyakit;
import com.example.sistem_pakar_cabai.MODEL.Model_Diagnosa_Selected;
import com.example.sistem_pakar_cabai.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.sistem_pakar_cabai.AKTIVITY.Data_Diagnosa.selectedStrings;



public class Adapter_Diagnosa extends RecyclerView.Adapter<Adapter_Diagnosa.MyViewHolder> {

    private Context mContext ;
    private List<Model_Data_Penyakit> mData;

    RequestOptions option;


    LayoutInflater inflater;


    public Adapter_Diagnosa(Context mContext, List<Model_Data_Penyakit> mData ) {
        this.mContext = mContext;
        this.mData = mData;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view;
        inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.list_row_diagnosa, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder ;
    }




    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {



        holder.tv_data.setText(mData.get(position).getGejala());
        holder.tv_kode_gejala.setText(mData.get(position).getKode_gejala());
        holder.checkBox.setChecked(mData.get(position).isSelected());
        holder.checkBox.setTag(mData.get(position).getKode_gejala());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){

                     CheckBox cb = (CheckBox) v ;
                     mData.get(position).setSelected(cb.isChecked());
                    //Toast alert = Toast.makeText(mContext,"Checked",Toast.LENGTH_SHORT);
                   // View toastView = alert.getView();
                   // toastView.setBackgroundResource(R.color.orangeMuda);
                   // alert.show();

                } else{

                    mData.get(position).setSelected(false);
                }
            }
        });







    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public List<Model_Data_Penyakit> getKodegejala_selected(){
        return mData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_data;
        TextView tv_kode_gejala;
        CheckBox checkBox;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_data = itemView.findViewById(R.id.title_gejala);
            tv_kode_gejala = itemView.findViewById(R.id.kode_gejala);
            checkBox = itemView.findViewById(R.id.cbGejala);


           }
    }


}