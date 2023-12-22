package com.example.deputadosandroid.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deputadosandroid.API.Conexao;
import com.example.deputadosandroid.API.RestService;
import com.example.deputadosandroid.Model.Partido;
import com.example.deputadosandroid.R;
import com.google.android.gms.common.api.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesPartidoActivity extends AppCompatActivity {


    TextView nome, sigla, numero, website;
    ImageView back;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_partido);

        nome = findViewById(R.id.textView4);
        sigla = findViewById(R.id.textView);
        numero = findViewById(R.id.textView2);
        website = findViewById(R.id.textView3);
        back = findViewById(R.id.imageButton2);

        detailPartido();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesPartidoActivity.this, ListaPartidosActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void detailPartido() {
        int id = getIntent().getIntExtra("PARTIDO_ID", 0);

        RestService restService = Conexao.criarApiService();
        Call<ResponseBody> call = restService.detalharPartido(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        String responseData = response.body().string();

                        Partido partido = parseJson(responseData);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private Partido parseJson(String responseData) {
        try{
            JSONObject json = new JSONObject(responseData);
            JSONObject dadosObject = json.getJSONObject("dados");

            sigla.setText("SIGLA: " + dadosObject.getString("sigla"));
            nome.setText("NOME: " + dadosObject.getString("nome"));

            website.setText("WEBSITE: " + dadosObject.getString("urlWebSite"));
            numero.setText("NÚMERO PARTIDO: " + dadosObject.getString("numeroEleitoral"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}