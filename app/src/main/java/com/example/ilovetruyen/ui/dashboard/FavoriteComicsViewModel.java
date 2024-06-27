package com.example.ilovetruyen.ui.dashboard;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ilovetruyen.database.DBHelper;
import com.example.ilovetruyen.model.FavoriteComics;

import java.util.ArrayList;
import java.util.List;

public class FavoriteComicsViewModel extends ViewModel {
    private MutableLiveData<List<FavoriteComics>> comicsData;
    private DBHelper dbHelper;

    public LiveData<List<FavoriteComics>> getDataList(Context context) {
        if (comicsData == null) {
            comicsData = new MutableLiveData<>();
            setData(context);
        }
        return comicsData;
    }


//    public FavoriteComicsViewModel(DBHelper dbHelper) {
//        this.dbHelper = dbHelper;
//
//    }

    public void setData(Context context) {
        dbHelper = new DBHelper(context);
        Cursor cursor = dbHelper.getAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "Danh sách trống", Toast.LENGTH_SHORT).show();
        } else {
            List<FavoriteComics> faCom = new ArrayList<>();
            while (cursor.moveToNext()) {
                FavoriteComics em = new FavoriteComics(cursor.getString(0).trim(), cursor.getString(1).trim(), cursor.getString(2).trim(), cursor.getString(3).trim());
                faCom.add(em);
            }
            cursor.close();
            dbHelper.close();
            comicsData.setValue(faCom);
        }
    }

    public void removeData(int position) {
        List<FavoriteComics> currentList = comicsData.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.remove(position);
            comicsData.setValue(currentList); //set lại bằng danh sách mới
        }
    }
}