package com.example.ilovetruyen.retrofit;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class GoogleSignConfig {

    final static String clientKey ="590343439409-u6qpi7hsjl4a178ktpf8vv58nc6ivelc.apps.googleusercontent.com";
    public static GoogleSignInOptions createGoogleSignInOptions () {
        return new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientKey)
                .requestEmail()
                .build();
    }
}
