package com.example.deputadosandroid.Activity;

import static android.widget.Toast.makeText;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deputadosandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ConfiguracoesActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference;

    private BottomNavigationView bottomNavigationView;

    private CardView cardDelete, cardSair;

    private FirebaseUser user;

    private TextView email, username;

    private String uid;

    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        bottomNavigationView = findViewById(R.id.bottomNavigationConfig);

        cardDelete = findViewById(R.id.cardDelete);

        cardSair = findViewById(R.id.cardSair);

        databaseReference = getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();

        myDialog = new Dialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        uid = user.getUid();

        email = findViewById(R.id.textView6);

        username = findViewById(R.id.textView5);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return itemSelecionado(item);
            }
        });

        cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ConfiguracoesActivity.this);

                alert.setTitle("Tem certeza que deseja excluir sua conta?");

                alert.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent ia = new Intent(ConfiguracoesActivity.this, LoginActivity.class);

                        startActivity(ia);

                        makeText(ConfiguracoesActivity.this, "Conta deletada com sucesso", Toast.LENGTH_SHORT).show();

                        user.delete();

                        getInstance().getReference().child("Users").child(uid).removeValue();

                        FirebaseAuth.getInstance().signOut();
                    }
                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        makeText(ConfiguracoesActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();

            }
        });

        cardSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ConfiguracoesActivity.this);
                alert.setTitle("Tem certeza que deseja sair?");

                alert.setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent ia = new Intent(ConfiguracoesActivity.this, LoginActivity.class);

                        startActivity(ia);

                        makeText(ConfiguracoesActivity.this, "Logout realizado com sucesso", Toast.LENGTH_SHORT).show();

                        FirebaseAuth.getInstance().signOut();
                    }
                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                alert.show();
            }
        });

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0)
                {

                    if (snapshot.hasChild("email")){

                        String bio = snapshot.child("email").getValue().toString();

                        email.setText("Email: " + bio);
                    }
                    if (snapshot.hasChild("nome")){

                        String bio = snapshot.child("nome").getValue().toString();

                        username.setText("Nome usuário: " + bio);
                    }
            }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
    }

    private boolean itemSelecionado(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.home_footer) {

            startActivity(new Intent(this, ListaPartidosActivity.class));
        }
        else if (itemId == R.id.deputados_footer) {

            startActivity(new Intent(this, ListaDeputadosActivity.class));
        }

        return false;
    }
}