package com.example.sistem_pakar_cabai.FRAGMENT;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.example.sistem_pakar_cabai.R;

public class FragmentApp extends Fragment {


    public FragmentApp() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_app,container, false);
        setHasOptionsMenu(true);


        //menerapkan tool bar sesuai id toolbar | ToolBarAtas adalah variabel buatan sndiri
        Toolbar ToolBarAtas2 = view.findViewById(R.id.toolbar_tentang);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((AppCompatActivity)getActivity()).setSupportActionBar(ToolBarAtas2);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return  view;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

           switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
