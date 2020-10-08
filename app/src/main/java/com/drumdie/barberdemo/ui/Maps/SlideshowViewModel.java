package com.drumdie.barberdemo.ui.Maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Acá iría un mapa con la barbería incluída");
    }

    public LiveData<String> getText() {
        return mText;
    }
}