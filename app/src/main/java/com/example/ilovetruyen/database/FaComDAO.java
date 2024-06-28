package com.example.ilovetruyen.database;

import static com.example.ilovetruyen.database.DBHelper.TB_FACOMICS;
import static com.example.ilovetruyen.database.DBHelper.TB_FACOMICS_CUR_READ;
import static com.example.ilovetruyen.database.DBHelper.TB_FACOMICS_ID;
import static com.example.ilovetruyen.database.DBHelper.TB_FACOMICS_NAME;
import static com.example.ilovetruyen.database.DBHelper.TB_FACOMICS_THUMBIMAGE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ilovetruyen.model.FavoriteComics;

import java.util.ArrayList;
import java.util.List;

public class FaComDAO {
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    public FaComDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }
    public void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    public void openForReading() throws SQLException {
        System.out.println("--------------------"+dbHelper);
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }
    public List<FavoriteComics> getAllFavoriteComics() {
        List<FavoriteComics> favoriteComicsList = new ArrayList<>();
        String querry = "Select * from "+TB_FACOMICS;
        Cursor cursor = sqLiteDatabase.rawQuery(querry,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.println(cursor.getString(0));
            FavoriteComics favoriteComics = cursorToPerson(cursor);
            favoriteComicsList.add(favoriteComics);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return favoriteComicsList;
    }
    public Boolean insertData(String comicsId,String name,String thumbUrl,String currentRead ){
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
    private FavoriteComics cursorToPerson(Cursor cursor) {
        return new FavoriteComics(cursor.getString(0).trim(), cursor.getString(1).trim(), cursor.getString(2).trim(), cursor.getString(3).trim());
    }

    public Boolean deleteData(String comicsId){
        long result = sqLiteDatabase.delete(TB_FACOMICS,TB_FACOMICS_ID +" = ?",new String[]{comicsId});
        if(result  == -1){
            return false;//fail delete
        }else {
            return true; // delete success
        }
    }

    public boolean checkExist(String comicsId){
        String[] projection = null;
        String selection = TB_FACOMICS_ID + " = ?";
        String[] selectionArgs = { comicsId };
        Cursor cursor = sqLiteDatabase.query(
                TB_FACOMICS,    // Tên bảng
                projection,   // Các cột cần trả về (null sẽ lấy tất cả các cột)
                selection,    // Mệnh đề WHERE
                selectionArgs,// Đối số cho WHERE
                null,         // GROUP BY
                null,         // HAVING
                null          // ORDER BY
        );
        if(cursor.getCount() > 0 ){
            return true;
        }
        else {
            return false;
        }
    }
}
