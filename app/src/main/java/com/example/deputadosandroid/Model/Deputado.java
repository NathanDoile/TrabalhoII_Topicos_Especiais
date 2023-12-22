package com.example.deputadosandroid.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deputado {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("siglaPartido")
    @Expose
    private String siglaPartido;

    @SerializedName("urlFoto")
    @Expose
    private String urlFoto;

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

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Deputado(int id, String nome, String siglaPartido, String urlFoto) {
        this.id = id;
        this.nome = nome;
        this.siglaPartido = siglaPartido;
        this.urlFoto = urlFoto;
    }

    public Deputado() {
    }
}
