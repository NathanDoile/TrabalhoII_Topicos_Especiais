package com.example.deputadosandroid.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deputadosandroid.API.Conexao;
import com.example.deputadosandroid.API.RestService;
import com.example.deputadosandroid.Model.Deputado;
import com.example.deputadosandroid.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesDeputadoActivity extends AppCompatActivity {

    TextView nome, sigla, cpf, dataNascimento, situacao;
    ImageView back, foto_perfil;
    String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_deputado);

        nome = findViewById(R.id.txt_nome_deputado);
        sigla = findViewById(R.id.txt_sigla);
        cpf = findViewById(R.id.txt_numero);
        dataNascimento = findViewById(R.id.txt_web);
        foto_perfil = findViewById(R.id.img_dep);
        situacao = findViewById(R.id.txt_web2);
        back = findViewById(R.id.btn);

        detailDeputados();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesDeputadoActivity.this, ListaDeputadosActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void detailDeputados() {
        int id = getIntent().getIntExtra("DEPUTADO_ID", 0);

        RestService restService = Conexao.criarApiService();
        Call<ResponseBody> call = restService.detalharDeputado(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        String responseData = response.body().string();

                        Deputado deputado = parseJson(responseData);

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

    private Deputado parseJson(String responseData) {
        try{
            JSONObject json = new JSONObject(responseData);
            JSONObject dadosObject = json.getJSONObject("dados");

            nome.setText("NOME: " + dadosObject.getString("nomeCivil"));
            cpf.setText("CPF: "+ dadosObject.getString("cpf"));
            dataNascimento.setText("DATA NASCIMENTO: "+ dadosObject.getString("dataNascimento"));

            JSONObject ultimoStatus = dadosObject.getJSONObject("ultimoStatus");
            foto = ultimoStatus.getString("urlFoto");
            sigla.setText("SIGLA PARTIDO: " + ultimoStatus.getString("siglaPartido"));
            situacao.setText("SITUAÇÃO: " + ultimoStatus.getString("situacao"));
            Picasso.get().load(foto).into(foto_perfil);
            
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return null;
    }



}