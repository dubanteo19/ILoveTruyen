package com.example.ilovetruyen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ilovetruyen.model.FavoriteComics;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "ilovecomics.db";
    public static final String TB_COMICS ="COMICS";
    public static final String TB_FACOMICS ="favorite_comics";

    public static final String TB_FACOMICS_ID = "idComics";
    public static final String TB_FACOMICS_NAME = "name";
    public static final String TB_FACOMICS_THUMBIMAGE = "thumbImage";
    public static final String TB_FACOMICS_CUR_READ = "currentRead";


    public DBHelper(@Nullable Context context) {
        super(context,TB_FACOMICS,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbFavoriteComics = "CREATE TABLE " + TB_FACOMICS+"("
                +TB_FACOMICS_ID +" nvarchar(255) Primary Key,"+TB_FACOMICS_NAME+" text,  " +TB_FACOMICS_THUMBIMAGE+" TEXT, " +TB_FACOMICS_CUR_READ+" text);";
        db.execSQL(tbFavoriteComics);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
