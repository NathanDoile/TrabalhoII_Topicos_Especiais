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

import com.example.deputadosandroid.Adapters.PartidosAdapter;
import com.example.deputadosandroid.API.Conexao;
import com.example.deputadosandroid.Model.Partido;
import com.example.deputadosandroid.API.RestService;
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

public class ListaPartidosActivity extends AppCompatActivity {

    private PartidosAdapter partidosAdapter;

    private RecyclerView recyclerView;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_partido);

        recyclerView = findViewById(R.id.lista_partidos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return itemSelecionado(item);
            }
        });

        listarPartidos();
    }

    private void listarPartidos() {

        RestService restService = criarApiService();

        Call<ResponseBody> call = restService.listarPartidos();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();

                        List<Partido> partidos = parseJson(responseData);

                        setupRecyclerView(partidos);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });

    }

    private List<Partido> parseJson(String responseData) {
        List<Partido> partidos = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(responseData);

            JSONArray array = jsonObject.getJSONArray("dados");

            for (int i = 0; i < array.length(); i++) {
                JSONObject partidoJson = array.getJSONObject(i);

                int id = partidoJson.getInt("id");

                String sigla = partidoJson.getString("sigla");

                String nome = partidoJson.getString("nome");

                String urlLogo = partidoJson.getString("uri");

                Partido partido = new Partido(id, sigla, nome, urlLogo);

                partidos.add(partido);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return  partidos;
    }

    private void setupRecyclerView(List<Partido> partidos) {
        partidosAdapter = new PartidosAdapter(partidos, new PartidosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Partido partido) {

                Intent intent = new Intent(ListaPartidosActivity.this, DetalhesPartidoActivity.class);

                intent.putExtra("PARTIDO_ID", partido.getId());

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(partidosAdapter);
    }

    private boolean itemSelecionado(MenuItem item) {
        int itemId = item.getItemId();

       if (itemId == R.id.deputados_footer) {
            startActivity(new Intent(this, ListaDeputadosActivity.class));
            return true;
        } else if (itemId == R.id.configuracoes) {
            startActivity(new Intent(this, ConfiguracoesActivity.class));
        }
        return false;
    }

}