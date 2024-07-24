package com.example.project_1201345_12012093.ui.aboutus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUsModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AboutUsModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is about us fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}