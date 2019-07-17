package com.example.sistem_pakar_cabai.MODEL;



public class Model_Data_Gejala {
    private String kode_gejala,gejala,selected;
    private boolean isSelected;

    public Model_Data_Gejala() {
    }


    public String getKode_gejala() {
        return kode_gejala;
    }

    public void setKode_gejala(String kode_gejala) {
        this.kode_gejala = kode_gejala;
    }

    public String getGejala() {
        return gejala;
    }


    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public Model_Data_Gejala(String kode_gejala, String gejala , String selected , Boolean isSelected) {
        this.kode_gejala = kode_gejala;
        this.gejala = gejala;
        this.selected = selected;
        this.isSelected = isSelected;
    }





}