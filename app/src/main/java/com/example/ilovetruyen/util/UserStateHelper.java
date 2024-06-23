package com.example.ilovetruyen.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserStateHelper {
    public static void saveLoginStatus(Context context, boolean isLoggedIn,String fullName, String email, Integer userId, String password){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in" , isLoggedIn);
        editor.putString("user_name" , fullName);
        editor.putString("email", email);
        editor.putInt("userId", userId);
        editor.putString("password", password);
        editor.apply();
    }
    public static void logoutStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
