package com.example.deputadosandroid.Banco;

import com.google.firebase.auth.FirebaseAuth;

public class Auth {
    private static FirebaseAuth auth;

    public static FirebaseAuth FirebaseAutenticacao(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }


}
