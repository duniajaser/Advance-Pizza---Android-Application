package com.example.project_1201345_12012093.ui.specialoffer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpecialOfferModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SpecialOfferModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is special offer fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}