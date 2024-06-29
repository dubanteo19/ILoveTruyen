package com.example.ilovetruyen.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CloseAdsSharedVM extends ViewModel {
    private final MutableLiveData<java.lang.Boolean> isCloseAds = new androidx.lifecycle.MutableLiveData<>(false);

    public final void setCloseAds(boolean z) {
        this.isCloseAds.setValue(java.lang.Boolean.valueOf(z));
    }

    public final LiveData<java.lang.Boolean> getCloseAds() {
        return this.isCloseAds;
    }
}
