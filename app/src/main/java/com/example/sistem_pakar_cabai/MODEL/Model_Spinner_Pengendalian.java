package com.example.sistem_pakar_cabai.MODEL;



public class Model_Spinner_Pengendalian {
    private String kode_pengendalian,pengendalian;


    public Model_Spinner_Pengendalian() {
    }

    public String getKode_pengendalian() {
        return kode_pengendalian;
    }

    public void setKode_pengendalian(String kode_pengendalian) {
        this.kode_pengendalian = kode_pengendalian;
    }

    public String getPengendalian() {
        return pengendalian;
    }

    public void setPengendalian(String pengendalian) {
        this.pengendalian = pengendalian;
    }

    public Model_Spinner_Pengendalian(String kode_pengendalian, String pengendalian ) {
        this.kode_pengendalian = kode_pengendalian;
        this.pengendalian = pengendalian;

    }





}