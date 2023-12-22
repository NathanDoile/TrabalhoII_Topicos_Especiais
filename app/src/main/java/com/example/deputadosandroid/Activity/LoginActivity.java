package com.example.deputadosandroid.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deputadosandroid.Banco.Auth;
import com.example.deputadosandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    ImageButton btn_back;
    AppCompatButton button_login;

    String email, senha;
    EditText edit_email, edit_senha;

    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button_login = findViewById(R.id.button_login);
        edit_email = findViewById(R.id.txt_email);
        edit_senha = findViewById(R.id.txt_senha);
        btn_back = findViewById(R.id.imageButton);
        ProgressBar progressBar1 = findViewById(R.id.progressBar2);
        mAuth = Auth.FirebaseAutenticacao();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InitialActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edit_email.getText().toString();
                senha = edit_senha.getText().toString();


                if(email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email ou Senha não informados",
                            Toast.LENGTH_SHORT).show();
                }else{
                    progressBar1.setVisibility(View.VISIBLE);
                    login(email,senha);
                }
            }
        });
    }

    private void login(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, ListaPartidosActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String excecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                excecao = "Usuário não cadastrado";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Email ou Senha incorretos";
                            } catch (Exception e) {
                                excecao = "Erro ao logar na aplicação" + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), excecao,
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}