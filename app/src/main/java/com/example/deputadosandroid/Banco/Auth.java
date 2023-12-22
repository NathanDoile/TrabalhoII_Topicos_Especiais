package com.example.deputadosandroid.Banco;

import static com.google.firebase.auth.FirebaseAuth.getInstance;
import static java.util.Objects.isNull;

import com.google.firebase.auth.FirebaseAuth;

public class Auth {
    private static FirebaseAuth auth;

    public static FirebaseAuth FirebaseAutenticacao(){

        if(isNull(auth)){

            auth = getInstance();
        }
        return auth;
    }


}
