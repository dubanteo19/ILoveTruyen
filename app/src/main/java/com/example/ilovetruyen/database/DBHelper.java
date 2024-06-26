package com.example.ilovetruyen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    public  SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
    public Boolean insertData(String comicsId,String name,String thumbUrl,String currentRead ){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_FACOMICS_ID,comicsId);
        contentValues.put(TB_FACOMICS_NAME,name);
        contentValues.put(TB_FACOMICS_THUMBIMAGE,thumbUrl);
        contentValues.put(TB_FACOMICS_CUR_READ,currentRead);
        long result = sqLiteDatabase.insert(TB_FACOMICS,null,contentValues);
        if(result == -1){
            return false; //fail insert
        }else{
            return true; // insert success
        }
    }
    public Boolean checkExist(String comicsId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT* FROM "+TB_FACOMICS+" WHERE "+TB_FACOMICS_ID+" = ?",new String[]{comicsId});
        if(cursor.getCount() >0){
            return true;//this item already exist
        }else {
            return false;
        }
    }
    public Boolean deleteData(String comicsId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TB_FACOMICS,TB_FACOMICS_ID +" = ?",new String[]{comicsId});
        if(result  == -1){
            return false;//fail delete
        }else {
            return true; // delete success
        }
    }
    public Cursor getItem(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String querry = "Select * from "+TB_FACOMICS +" where " +TB_FACOMICS_ID+" = "+id;
        Cursor cursor = sqLiteDatabase.rawQuery(querry,null);
        return cursor;
    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String querry = "Select * from "+TB_FACOMICS;
        Cursor cursor = sqLiteDatabase.rawQuery(querry,null);
        return cursor;
    }

}
