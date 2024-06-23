package com.example.ilovetruyen.favoriteComics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteComicsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FavoriteComicsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}