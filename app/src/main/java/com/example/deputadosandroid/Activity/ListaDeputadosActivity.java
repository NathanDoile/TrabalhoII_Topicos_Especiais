package com.example.deputadosandroid.Activity;

import static com.example.deputadosandroid.API.Conexao.criarApiService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.deputadosandroid.API.Conexao;
import com.example.deputadosandroid.API.RestService;
import com.example.deputadosandroid.Adapters.DeputadosAdapter;
import com.example.deputadosandroid.Adapters.PartidosAdapter;
import com.example.deputadosandroid.Model.Deputado;
import com.example.deputadosandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaDeputadosActivity extends AppCompatActivity {

    private DeputadosAdapter deputadosAdapter;

    private RecyclerView recyclerView;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_deputados);

        recyclerView = findViewById(R.id.lista_deputados);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView = findViewById(R.id.bottomNavigationViewDeputados);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return itemSelecionado(item);
            }
        });


        listarDeputados();
    }

    private void listarDeputados() {

        RestService restService = criarApiService();

        Call<ResponseBody> call = restService.listarDeputados();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();

                        List<Deputado> deputados = parseJson(responseData);

                        setupRecyclerView(deputados);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });

    }



    private List<Deputado> parseJson(String responseData) {

        List<Deputado> deputados = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(responseData);

            JSONArray array = jsonObject.getJSONArray("dados");

            for (int i = 0; i < array.length(); i++) {

                JSONObject deputadoJson = array.getJSONObject(i);

                int id = deputadoJson.getInt("id");

                String nome = deputadoJson.getString("nome");

                String siglaPartido = deputadoJson.getString("siglaPartido");

                String urlFoto = deputadoJson.getString("urlFoto");

                Deputado deputado = new Deputado(id, nome, siglaPartido, urlFoto);

                deputados.add(deputado);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return  deputados;
    }

    private void setupRecyclerView(List<Deputado> deputados) {
        deputadosAdapter = new DeputadosAdapter(deputados, new DeputadosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Deputado deputado) {

                Intent intent = new Intent(ListaDeputadosActivity.this, DetalhesDeputadoActivity.class);

                intent.putExtra("DEPUTADO_ID", deputado.getId());

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(deputadosAdapter);
    }

    private boolean itemSelecionado(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.home_footer) {
            startActivity(new Intent(this, ListaPartidosActivity.class));
        }
        else if (itemId == R.id.configuracoes) {
            startActivity(new Intent(this, ConfiguracoesActivity.class));
        }
        return false;
    }

}