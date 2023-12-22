package com.example.deputadosandroid.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Partido {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("sigla")
    @Expose
    private String sigla;

    @SerializedName("urlLogo")
    @Expose
    private String urlLogo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }


    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Partido(int id, String sigla,String nome, String urlLogo) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.urlLogo = urlLogo;
    }
}
