package com.example.ilovetruyen.util;

import static android.content.Context.MODE_PRIVATE;

import static java.util.stream.Collectors.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

public class UserStateHelper {

    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_PREFIX = "comicId-";

    public static void saveLoginStatus(Context context, boolean isLoggedIn, String fullName, String email, Integer userId, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", isLoggedIn);
        editor.putString("user_name", fullName);
        editor.putString("email", email);
        editor.putInt("userId", userId);
        editor.putString("password", password);
        editor.apply();
    }

    public static void logoutStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", false);
        editor.apply();
    }

    public static void saveReadComicId(Context context, int comicId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_PREFIX+comicId, comicId);
        editor.apply();
    }

    public static List<Integer> getReadComics(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences
                .getAll()
                .entrySet()
                .stream()
                .filter(entry-> entry.getKey().startsWith("comicId-"))
                .map(entry -> (Integer) entry.getValue())
                .collect(toList());
    }

    public static void removeReadComicId(Context context, int comicId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_PREFIX+comicId);
        editor.apply();
    }
}
