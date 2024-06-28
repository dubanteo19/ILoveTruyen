package com.example.ilovetruyen.util;

import static android.content.Context.MODE_PRIVATE;
import static java.util.stream.Collectors.toList;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class UserStateHelper {

    public static final String PREF_NAME = "user_prefs";
    public static final String KEY_PREFIX = "comicId-";
    public static final String LIKE_STATUS = "like_comicId-";

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

    public static void saveAdminStatus(Context context, boolean isAdmin){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isAdmin", isAdmin);
        editor.apply();

    }
    public static void saveEditComicStatus(Context context, boolean isEditComic){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isEditComic", isEditComic);
        editor.apply();
    }
    public static void saveDeleteComicStatus(Context context, boolean isDeleteComic){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDeleteComic", isDeleteComic);
        editor.apply();
    }

    public static void logoutStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", false);
        editor.remove("user_name");
        editor.remove("email");
        editor.remove("userId");
        editor.remove("password");
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

    public static void saveLikeStatus(Context context, int comicId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LIKE_STATUS+comicId, comicId);
        editor.apply();
    }
    public static void removeLikeStatus(Context context, int comicId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LIKE_STATUS+comicId);
        editor.apply();
    }
    public static void saveRecentlyReadComicId(Context context, int comicId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("recentlyComicId", comicId);
        editor.apply();
    }
}
