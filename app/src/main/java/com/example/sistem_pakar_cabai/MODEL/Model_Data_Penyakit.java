package com.example.sistem_pakar_cabai.MODEL;



public class Model_Data_Penyakit {
    private String id, jenis, gejala, pengendalian , image , nilai_mb , nilai_md , kode_gejala , selected;
    public boolean isSelected ;
    public Model_Data_Penyakit() {
    }


    public String getNilai_mb() {
        return nilai_mb;
    }

    public void setNilai_mb(String nilai_mb) {
        this.nilai_mb = nilai_mb;
    }

    public String getNilai_md() {
        return nilai_md;
    }

    public void setNilai_md(String nilai_md) {
        this.nilai_md = nilai_md;
    }

    public String getKode_gejala() {
        return kode_gejala;
    }

    public void setKode_gejala(String kode_gejala) {
        this.kode_gejala = kode_gejala;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Model_Data_Penyakit(Boolean isSelected, String selected, String kode_gejala, String id, String jenis, String gejala, String pengendalian , String image , String nilai_mb , String nilai_md) {
        this.id = id;
        this.jenis = jenis;
        this.gejala = gejala;
        this.pengendalian = pengendalian;
        this.image = image;
        this.nilai_mb = nilai_mb;
        this.nilai_md = nilai_md;
        this.kode_gejala = kode_gejala;
        this.selected = selected;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getPengendalian() {
        return pengendalian;
    }

    public void setPengendalian(String pengendalian) {
        this.pengendalian = pengendalian;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}