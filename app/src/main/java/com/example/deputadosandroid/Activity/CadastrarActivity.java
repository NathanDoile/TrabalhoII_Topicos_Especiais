package com.example.deputadosandroid.Activity;

import static android.widget.Toast.makeText;
import static com.example.deputadosandroid.Banco.Auth.FirebaseAutenticacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deputadosandroid.Banco.Auth;
import com.example.deputadosandroid.Model.User;
import com.example.deputadosandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrarActivity extends AppCompatActivity {


    private ProgressBar progressBar;

    private Button button_cadastrar;

    private FirebaseAuth autenticacao;

    private DatabaseReference reference;

    private String nome, email, senha;

    private EditText edt_senha, edt_email, edt_confirm_senha, edt_nome;

    private FirebaseDatabase banco;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        button_cadastrar = findViewById(R.id.button_salvar_cadastro);

        autenticacao = FirebaseAutenticacao();

        edt_email = findViewById(R.id.txt_email);

        edt_senha = findViewById(R.id.txt_senha);

        edt_confirm_senha = findViewById(R.id.txt_confirmar_senha);

        edt_nome = findViewById(R.id.txt_nome);

        progressBar = findViewById(R.id.progressBar);

        nome = edt_nome.getText().toString();

        email = edt_email.getText().toString();

        senha = edt_senha.getText().toString();

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                nome = edt_nome.getText().toString();

                email = edt_email.getText().toString();

                senha = edt_senha.getText().toString();

                autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        User users = new User(nome, email, senha);

                        banco = FirebaseDatabase.getInstance();

                        reference = banco.getReference("Users");

                        String id = autenticacao.getCurrentUser().getUid();

                        reference.child(id).setValue(users);

                        Intent intent = new Intent(CadastrarActivity.this, ListaPartidosActivity.class);

                        startActivity(intent);

                        finish();

                        makeText(CadastrarActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}